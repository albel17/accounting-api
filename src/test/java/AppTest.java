import com.project.common.Response;
import com.project.datasourse.impl.AccountingDAOImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class AppTest {

    private AccountingDAOImpl dao;

    @Before
    public void init() throws IOException, SQLException {
        dao = new AccountingDAOImpl();
        dao.init();
    }

    @After
    public void close() throws IOException {
        dao.close();
    }

    @Test
    public void simpleTransferTest() {
        Response response = dao.transfer(1L, 2L, 1L);
        Response expectedResponse = new Response(99L, 201L, new ArrayList<>());
        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void wrongSourceTest() {
        Response response = dao.transfer(100L, 3L, 1L);
        Response expectedResponse = new Response(null, 450L, Collections.singletonList("source account does not exist"));
        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void negativeTransferTest() {
        Response response = dao.transfer(1L, 2L, -1L);
        Response expectedResponse = new Response(100L, 200L, Collections.singletonList("amount must be positive"));
        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void lowBalanceTest() {
        Response response = dao.transfer(1L, 2L, 100L);
        Response expectedResponse = new Response(0L, 300L, new ArrayList<>());
        Assert.assertEquals(expectedResponse, response);
        response = dao.transfer(1L, 2L, 100L);
        expectedResponse = new Response(0L, 300L, Collections.singletonList("low balance"));
        Assert.assertEquals(expectedResponse, response);
    }
}
