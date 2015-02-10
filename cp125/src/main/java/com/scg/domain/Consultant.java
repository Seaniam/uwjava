package com.scg.domain;

import com.scg.util.Name;

/**
 * A consultant.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/12/15
 */
public class Consultant implements Comparable<Consultant> {

    /** Name of the consultant */
    private Name name;

    /**
     * Creates a new instance of a client.
     * @param name String with name of client.
     */
    public Consultant(Name name) {
        this.name = name;
    }

    /**
     * Getter for client's name.
     * @return client's name.
     */
    public final Name getName() {
        return name;
    }

    /**
     * String representation of consultant's name.
     * @return consultant's name.
     */
    @Override
    public final String toString() {
        return name.toString();
    }

    /**
     * Compares this Consultant object with the specified object for order.
     * Returns a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object, the
     * consultant name is used to perform the comparison.
     *
     * @param other the Object to be compared.
     * @return a negative integer, zero, or a positive integer as this
     * object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Consultant other) {
        String thisName = this.getName().toString(),
               otherName = other.getName().toString();

        // Check consultant name
        // TODO: use compareTo method in Name class
        if (thisName.compareTo(otherName) > 0) {
            return 1;
        } else if (thisName.compareTo(otherName) < 0) {
            return -1;
        }

        // else must be same
        return 0;
    }
}
