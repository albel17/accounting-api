package com.project.datasourse;

import com.project.common.Response;

/**
 * Accounting DAO
 */
public interface AccountingDAO {

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
