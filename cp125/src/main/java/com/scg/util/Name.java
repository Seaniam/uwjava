package com.scg.util;

/**
 * Encapsulates the name of a person (first, middle and last).
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/12/15
 */
public class Name {

    /** Name instance first name */
    private String firstName;
    /** Name instance middle name */
    private String middleName;
    /** Name instance last name */
    private String lastName;

    /**
     * Creates a new instance of Name without initial values.
     */
    public Name() {
    }

    /**
     * Creates a new instance of Name.
     * @param lastName last name
     * @param firstName first name
     */
    public Name(String lastName, String firstName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Creates a new instance of Name.
     * @param lastName last name
     * @param firstName first name
     * @param middleName middle name
     */
    public Name(String lastName, String firstName, String middleName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    /**
     * Gets the value of the first name.
     * @return first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for value of first name.
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the value of the middle name.
     * @return middle name.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Setter for value of middle name.
     * @param middleName middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets the value of the last name.
     * @return last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for value of last name.
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Compares instance of Name.
     * @param o ConsultantTime instance object.
     * @return true if this is the same ConsultantTime instance.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Name name = (Name) o;

        if (!firstName.equals(name.firstName)) return false;
        if (!lastName.equals(name.lastName)) return false;
        if ((middleName != null) && !middleName.equals(name.middleName)) return false;

        return true;
    }

    /**
     * Creates Name instance hashCode signature.
     * @return hashCode signature.
     */
    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        if (middleName != null) {
            result = 31 * result + middleName.hashCode();
        }
        result = 31 * result + lastName.hashCode();
        return result;
    }

    /**
     * Create string representation of this object in the format
     * "LastName, FirstName MiddleName".
     * @return formatted name
     */
    @Override
    public String toString() {
        StringBuilder formattedName = new StringBuilder();

        if (lastName != null && !lastName.isEmpty()) {
            formattedName.append(lastName);
        }

        if (firstName != null && !firstName.isEmpty()) {
            formattedName.append(", " + firstName);
        }

        if (middleName != null && !middleName.isEmpty()) {
            formattedName.append(" " + middleName);
        }

        return formattedName.toString();
    }
}
