package com.scg.domain;

import java.io.Serializable;

/**
 * Describes the implementation for an Account
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/12/15
 */
public interface Account extends Serializable {

    /**
     * Getter for the name of the account.
     * @return the name of the account.
     */
    public String getName();

    /**
     * Determines if this account is billable
     * @return true if account is billable, otherwise false
     */
    public boolean isBillable();
}
