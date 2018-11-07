package com.project.service.impl;

import com.project.common.Response;
import com.project.datasourse.AccountingDAO;
import com.project.service.AccountingService;

import javax.inject.Inject;

/**
 * Money transfer service implementation
 */
public class AccountingServiceImpl implements AccountingService {

    private AccountingDAO accountingDAO;

    @Inject
    public AccountingServiceImpl(AccountingDAO accountingDAO) {
        this.accountingDAO = accountingDAO;
    }

    @Override
    public Response transfer(Long sourceId, Long targetId, Long amount) {
        return accountingDAO.transfer(sourceId, targetId, amount);
    }
}
