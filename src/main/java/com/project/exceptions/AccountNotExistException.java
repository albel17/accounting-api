package com.project.exceptions;

/**
 * Exception for non existing accounts
 */
public class AccountNotExistException extends RuntimeException {

    public AccountNotExistException(String message) {
        super(message);
    }
}
