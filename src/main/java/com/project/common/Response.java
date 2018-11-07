package com.project.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Response entity
 */
public class Response {

    public Response() {
    }

    public Response(Long sourceBalance, Long targetBalance, List<String> errors) {
        this.sourceBalance = sourceBalance;
        this.targetBalance = targetBalance;
        this.errors = errors;
    }

    private Long sourceBalance;

    private Long targetBalance;

    private List<String> errors = new ArrayList<>();

    public Long getSourceBalance() {
        return sourceBalance;
    }

    public void setSourceBalance(Long sourceBalance) {
        this.sourceBalance = sourceBalance;
    }

    public Long getTargetBalance() {
        return targetBalance;
    }

    public void setTargetBalance(Long targetBalance) {
        this.targetBalance = targetBalance;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return com.google.common.base.Objects.equal(sourceBalance, response.sourceBalance) &&
                com.google.common.base.Objects.equal(targetBalance, response.targetBalance) &&
                com.google.common.base.Objects.equal(errors, response.errors);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(sourceBalance, targetBalance, errors);
    }
}
