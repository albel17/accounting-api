package com.project.datasourse.impl;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import com.project.common.Response;
import com.project.datasourse.AccountingDAO;
import com.project.common.Account;
import com.project.exceptions.AccountNotExistException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Accounting DAO implementation
 */
public class AccountingDAOImpl implements AccountingDAO {

    private static final Logger logger = LoggerFactory.getLogger(AccountingDAOImpl.class);

    private EmbeddedPostgres pg;
    private HikariDataSource ds;

    @PostConstruct
    public void init() throws IOException, SQLException {
        pg = EmbeddedPostgres.start();
        HikariConfig config = new HikariConfig();
        Connection connection = pg.getPostgresDatabase().getConnection();
        config.setJdbcUrl(connection.getMetaData().getURL());

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("init.sql");
        ScriptRunner runner = new ScriptRunner(connection);
        InputStreamReader reader = new InputStreamReader(is);
        runner.runScript(reader);
        reader.close();

        connection.close();
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("minimum-idle", "1");
        config.addDataSourceProperty("maximum-pool-size", "20");
        config.addDataSourceProperty("auto-commit", "false");
        ds = new HikariDataSource(config);
    }

    @PreDestroy
    public void close() throws IOException {
        ds.close();
        pg.close();
    }

    public Response transfer(Long sourceId, Long targetId, Long amount) {
        Response response = new Response();
        PreparedStatement statement = null;
        try (Connection connection = ds.getConnection();
             PreparedStatement statement2 = connection.prepareStatement("SELECT id, balance from accounts where id = ? OR id = ? FOR UPDATE");
             PreparedStatement statement3 = connection.prepareStatement("UPDATE accounts SET balance = ? WHERE id = ?");
             PreparedStatement statement4 = connection.prepareStatement("END;")) {
            statement = connection.prepareStatement("BEGIN;");
            statement.execute();
            statement2.setLong(1, sourceId);
            statement2.setLong(2, targetId);
            ResultSet resultSet = statement2.executeQuery();

            List<Account> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new Account(resultSet.getLong(1), resultSet.getLong(2)));
            }

            Long sourceBalance = getBalance(result, sourceId, "source account does not exist", response);
            Long targetBalance = getBalance(result, targetId, "target account does not exist", response);

            response.setSourceBalance(sourceBalance);
            response.setTargetBalance(targetBalance);

            if (sourceBalance == null || targetBalance == null) {
                statement4.execute();
                return response;
            }

            if (amount <= 0) {
                response.getErrors().add("amount must be positive");
            } else if (sourceBalance < amount) {
                response.getErrors().add("low balance");
            } else {

                statement3.setLong(1, sourceBalance - amount);
                statement3.setLong(2, sourceId);
                statement3.execute();

                statement3.setLong(1, targetBalance + amount);
                statement3.setLong(2, targetId);
                statement3.execute();

                statement4.execute();

                response.setSourceBalance(sourceBalance - amount);
                response.setTargetBalance(targetBalance + amount);
            }
        } catch (SQLException e) {
            response.getErrors().add("internal server error");
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.execute("ROLLBACK;");
                    statement.execute("END;");
                }
            } catch (SQLException e1) {
                logger.error(e1.getMessage());
            }
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return response;
    }

    private Long getBalance(List<Account> list, Long id, String errorMessage, Response response) {
        try {
            return list.stream()
                    .filter(r -> r.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new AccountNotExistException(errorMessage))
                    .getBalance();
        } catch (AccountNotExistException e) {
            response.getErrors().add(e.getLocalizedMessage());
            return null;
        }
    }
}
