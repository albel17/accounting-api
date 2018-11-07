package com.project.rest;

import com.project.common.Response;
import com.project.service.AccountingService;

import javax.inject.Inject;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Accounting controller
 */
@Path("/accounting")
public class AccountingController {

    private AccountingService accountingService;

    @Inject
    public AccountingController(AccountingService accountingService) {
        this.accountingService = accountingService;
    }

    /**
     * money transfer between accounts
     *
     * @param sourceId source account ID
     * @param targetId target account ID
     * @param amount amount of money transfered
     * @return response entity
     */
    @PUT
    @Path("/{sourceId}/{targetId}/{amount}")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response transfer(@PathParam("sourceId") Long sourceId,
                                              @PathParam("targetId") Long targetId,
                                              @PathParam("amount") Long amount) {
        Response response = accountingService.transfer(sourceId, targetId, amount);
        if(response.getErrors().isEmpty()) {
            return javax.ws.rs.core.Response.ok().entity(response).build();
        } else {
            return javax.ws.rs.core.Response.status(javax.ws.rs.core.Response.Status.FORBIDDEN).entity(accountingService.transfer(sourceId, targetId, amount)).build();
        }
    }

}