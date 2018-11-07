package com.project.config;

import com.project.datasourse.AccountingDAO;
import com.project.datasourse.impl.AccountingDAOImpl;
import com.project.service.AccountingService;
import com.project.service.impl.AccountingServiceImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;

/**
 * Bean binding configuration
 */
public class ProjectBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(AccountingServiceImpl.class).to(AccountingService.class).in(Singleton.class);
        bind(AccountingDAOImpl.class).to(AccountingDAO.class).in(Singleton.class);
    }
}
