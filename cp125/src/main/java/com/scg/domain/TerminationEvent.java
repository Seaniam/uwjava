package com.scg.domain;

import java.util.EventObject;

/**
 * Event used to notify listeners of a Consultant's termination.
 *
 * @author Sean Carberry
 * @version 6
 * @since 2/20/15
 */
public final class TerminationEvent extends EventObject {

    /** Serialization unique ID */
    private static final long serialVersionUID = -1636286904757914859L;

    /** consultant */
    private Consultant consultant;

    /** voluntary termination flag */
    private boolean voluntary;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @param consultant The consultant being terminated.
     * @param voluntary Was the termination voluntary.
     * @throws IllegalArgumentException if source is null.
     */
    public TerminationEvent(Object source, Consultant consultant, boolean voluntary) {
        super(source);
        this.consultant = consultant;
        this.voluntary = voluntary;
    }

    /**
     * Gets the consultant that was terminated.
     * @return the terminated consultant.
     */
    public Consultant getConsultant() {
        return consultant;
    }

    /**
     * Gets the voluntary termination status.
     * @return the voluntary termination status.
     */
    public boolean isVoluntary() {
        return voluntary;
    }
}

