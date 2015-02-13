package com.scg.util;

import java.io.Serializable;

/**
 * A simple mailing address.
 * Does no validity checking for things like valid states or postal codes.
 * Instances of this class are immutable.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/27/15
 */
public final class Address implements Comparable<Address>, Serializable {

    /** Unique serialization ID */
    private static final long serialVersionUID = -5751318509810721687L;

    /** The street number. */
    private final String streetNumber;

    /** The city */
    private final String city;

    /** The state code enum. */
    private final StateCode state;

    /** the postal code. */
    private final String postalCode;

    /**
     * Construct an Address.
     * @param streetNumber the street number.
     * @param city the city.
     * @param state the state.
     * @param postalCode the postal code.
     */
    public Address(String streetNumber,
                   String city,
                   StateCode state,
                   String postalCode) {

        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    /**
     * Get the street number number for this address.
     * @return the street number.
     */
    public String getStreetNumber() {
        return this.streetNumber;
    }

    /**
     * Gets the city for this address.
     * @return the city.
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Get the state for this address.
     * @return the state.
     */
    public StateCode getState() {
        return this.state;
    }

    /**
     * Gets the postal code for this address.
     * @return the postal code.
     */
    public String getPostalCode() {
        return this.postalCode;
    }


    /**
     * Compares instance of Address.
     * @param o Address instance object.
     * @return true if this is the same Address instance.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (!city.equals(address.city)) return false;
        if (!postalCode.equals(address.postalCode)) return false;
        if (state != address.state) return false;
        if (!streetNumber.equals(address.streetNumber)) return false;

        return true;
    }

    /**
     * Creates the hashcode for the Address instance.
     * @return the hashcode for the Address instance.
     */
    @Override
    public int hashCode() {
        int result = streetNumber.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + postalCode.hashCode();
        return result;
    }

    /**
     * String representation of Address.
     * @return Address as formatted string.
     */
    @Override
    public String toString() {
        StringBuilder address = new StringBuilder();

        address.append(streetNumber + "\n");
        address.append(city + ", " + state + " " + postalCode);

        return address.toString();
    }

    /**
     * Compares this Address with the specified Address.
     *
     * @param other the Address to be compared.
     * @return a negative integer, zero, or a positive integer as this
     * Address is less than, equal to, or greater than the specified Address.
     */
    @Override
    public int compareTo(Address other) {
        String zip = this.getPostalCode(),
               otherzip = other.getPostalCode();

        if (zip.compareTo(otherzip) > 0) {
            return 1;
        } else if (zip.compareTo(otherzip) < 0) {
            return -1;
        }

        StateCode state = this.getState(),
                  otherstate = other.getState();

        if (state.compareTo(otherstate) > 0) {
            return 1;
        } else if (state.compareTo(otherstate) < 0) {
            return -1;
        }

        String city = this.getCity(),
               othercity = other.getCity();

        if (city.compareTo(othercity) > 0) {
            return 1;
        } else if (city.compareTo(othercity) < 0) {
            return -1;
        }

        String street = this.getStreetNumber(),
               otherstreet = other.getStreetNumber();

        if (street.compareTo(otherstreet) > 0) {
            return 1;
        } else if (street.compareTo(otherstreet) < 0) {
            return -1;
        }

        return 0;
    }
}
