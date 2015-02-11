package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.Name;

/**
 * Encapsulates the information for a billable client account.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/12/15
 */
public class ClientAccount implements Account, Comparable<ClientAccount> {

    /** account name */
    private String name;
    /** account contact */
    private Name contact;
    /** account address */
    private Address address;

    /**
     * Creates a new instance of ClientAccount
     * @param name String with name of client
     * @param contact Name of the contact person for this client
     * @param address address of the client account
     */
    public ClientAccount(String name, Name contact, Address address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    /**
     * Getter for the client address.
     * @return the client address.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Update the account address.
     * @param address the new account address.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Getter for the contact on the account.
     * @return contact for the account.
     */
    public Name getContact() {
        return contact;
    }

    /**
     * Setter for contact name on the account.
     * @param contact contact name.
     */
    public void setContact(Name contact) {
        this.contact = contact;
    }

    /**
     * Getter for the name of the account.
     * @return the name of the account.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * TODO: is supposed to be always true by design (temporarily?)
     * Determines if this account is billable
     * @return true if account is billable, otherwise false
     */
    @Override
    public boolean isBillable() {
        return true;
    }

    /**
     * Compares this Client with the specified Client for order,
     * ordering by name, contact, and finally address. Returns a
     * negative integer, zero, or a positive integer as this
     * Client is less than, equal to, or greater than the
     * specified Client.
     *
     * @param other the Client to be compared.
     * @return a negative integer, zero, or a positive integer as this
     * Client is less than, equal to, or greater than the specified Client.
     */
    @Override
    public int compareTo(ClientAccount other) {

        // Check name
        String thisName = this.name,
               otherName = other.name;

        if(thisName.compareTo(otherName) > 0) {
            return 1;
        } else if (thisName.compareTo(otherName) < 0) {
            return -1;
        }

        // Check contact
        Name thisContact = this.getContact(),
             otherContact = other.getContact();

        if(thisContact.compareTo(otherContact) > 0) {
            return 1;
        } else if (thisContact.compareTo(otherContact) < 0) {
            return -1;
        }

        // Check address
        Address thisAddress = this.getAddress(),
                otherAddress = other.getAddress();

        if(thisAddress.compareTo(otherAddress) > 0) {
            return 1;
        } else if (thisAddress.compareTo(otherAddress) < 0) {
            return -1;
        }

        // else must be same
        return 0;
    }
}
