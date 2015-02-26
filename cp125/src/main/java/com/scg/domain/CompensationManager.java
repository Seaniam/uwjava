package com.scg.domain;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

/**
 * Approves or rejects compensation changes.
 *
 * @author Sean Carberry
 * @version 6
 * @since 2/20/15
 */
public final class CompensationManager extends Object implements PropertyChangeListener, VetoableChangeListener {

    /**
     * Constructor
     */
    public CompensationManager() {

    }

    /**
     * TODO: make this reject raises over 5%
     * Rejects any raise over 5%.
     * @param evt a vetoable change event for the payRate property
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    /**
     * Processes to final pay rate change.
     * @param evt a change event for the payRate property
     * @throws PropertyVetoException
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {

    }
}
