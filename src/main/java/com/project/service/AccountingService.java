package com.project.service;

import com.project.common.Response;

/**
 * Money transfer service
 */
public interface AccountingService {

    /**
     * money transfer between accounts
     *
     * @param sourceId source account ID
     * @param targetId target account ID
     * @param amount amount of money transfered
     * @return response entity
     */
    Response transfer(Long sourceId, Long targetId, Long amount);
}
