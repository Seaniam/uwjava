package com.scg.domain;

import com.scg.util.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * A consultant.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/12/15
 */
public class Consultant implements Comparable<Consultant>, Serializable {

    /**
     * Name of the consultant
     * @serial
     */
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

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("Consultant");

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
        String thisLast = this.getName().getLastName(),
               otherLast = other.getName().getLastName();

        if (thisLast.compareTo(otherLast) > 0) {
            return 1;
        } else if (thisLast.compareTo(otherLast) < 0) {
            return -1;
        }

        String thisMiddle = this.getName().getMiddleName(),
               otherMiddle = other.getName().getMiddleName();

        if (thisMiddle.compareTo(otherMiddle) > 0) {
            return 1;
        } else if (thisMiddle.compareTo(otherMiddle) < 0) {
            return -1;
        }

        String thisFirst = this.getName().getFirstName(),
               otherFirst = other.getName().getFirstName();

        if (thisFirst.compareTo(otherFirst) > 0) {
            return 1;
        } else if (thisFirst.compareTo(otherFirst) < 0) {
            return -1;
        }

        // otherwise, they are identical
        return 0;
    }

    /**
     * Writes to an object output stream the fields in Consultant.
     * @param oos An Object Output Stream.
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        ObjectOutputStream.PutField fields = oos.putFields();
        fields.put("x", name);
        oos.writeFields();
    }

    /**
     * Nominates the replacement of Consultant serialization.
     * @return the SerializationProxy.
     */
    private Object writeReplace() {
        log.info("Serializing " + this.getName().toString());
        return new SerializationProxy(this);
    }

    /**
     * Validate before deserialization.
     * @param ois ObjectInputStream.
     * @throws InvalidObjectException
     */
    private void readObject(ObjectInputStream ois) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    /**
     * A serialization replacement for Consultant.
     */
    private static class SerializationProxy implements Serializable {
        /** Serialization version UID */
        private static final long serialVersionUID = -5790567275048647124L;

        /** Logger for the serialization class */
        private static final Logger log = LoggerFactory.getLogger("SerializationProxy");

        /** Name field */
        private Name x;

        /**
         * Constructor for serialization proxy.
         * @param consultant the Consultant object to serialize.
         */
        SerializationProxy(final Consultant consultant) {
            x = consultant.name;
        }

        /**
         * Returns the instance of Consultant when deserialization occurs.
         * @return a deserialized Consultant object.
         */
        private Object readResolve() {
            log.info("Deserializing: " + x.toString());
            return new Consultant(x);
        }
    }

}
