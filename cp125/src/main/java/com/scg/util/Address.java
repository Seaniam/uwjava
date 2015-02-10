package com.scg.util;

/**
 * A simple mailing address.
 * Does no validity checking for things like valid states or postal codes.
 * Instances of this class are immutable.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/27/15
 */
public final class Address {

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

    //    TODO: add comparable for address because the concatenated address
    //    might be broken if you don't control each piece of the name.
}
