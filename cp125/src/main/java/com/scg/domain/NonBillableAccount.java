package com.scg.domain;

import java.io.Serializable;

/**
 * Encapsulates the concept of a non-billable account, such as sick leave, vacation, or business development.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/12/15
 */
public enum NonBillableAccount implements Account, Serializable {

    SICK_LEAVE("Sick Leave"),
    VACATION("Vacation"),
    BUSINESS_DEVELOPMENT("Business Development");

    /** name of Account */
//    private String name;

    private String nonBillableType;

    /**
     * Constructor for NonBillableAccount
     * @param nonBillableType String for non billable type
     */
    NonBillableAccount(String nonBillableType) {
//        this.name = nonBillableType;
        this.nonBillableType = nonBillableType;
    }

    /**
     * Gets the nonBillableAccount type in string format.
     *
     * @return the non-billable account type in string format.
     */
//    public String toString() {
//        return name;
//    }

    /**
     * Getter for the name of the account.
     * @return the name of the account.
     */
    @Override
    public String getName() {
        return nonBillableType;
    }

    /**
     * TODO: spec'd as always returning false (temporarily?)
     * Determines if this account is billable
     * @return true if account is billable, otherwise false
     */
    @Override
    public boolean isBillable() {
        return false;
    }
}
