package com.scg.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("CompensationManager");

    /**
     * Constructor
     */
    public CompensationManager() {}

    /**
     * Processes to final pay rate change.
     * @param evt a change event for the payRate property
     */
    public void propertyChange(PropertyChangeEvent evt) {
        int payRate = (Integer) evt.getNewValue();
        int oldPayRate = (Integer) evt.getOldValue();

        log.info("Pay rate changed, from " + oldPayRate + " to " + payRate + " for " + evt.getSource());
    }


    /**
     * Rejects any raise over 5%.
     * @param evt a vetoable change event for the payRate property.
     * @throws PropertyVetoException
     */
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        int payRate = (Integer) evt.getNewValue(),
            oldPayRate = (Integer) evt.getOldValue();
        double rate = (double) (payRate - oldPayRate)/oldPayRate;

        // Ensure pay rate isn't less than 0 and that the increase doesn't exceed 5%
        if (payRate <= 0) {
            PropertyChangeEvent e = new PropertyChangeEvent(this, "Minimum pay must be greater than 0", null, payRate);
            throw new PropertyVetoException("Pay rate less than 0.", e);
        } else if (oldPayRate != 0 && rate > 0.05) {
            PropertyChangeEvent e = new PropertyChangeEvent(this, "Can not raise pay greater than 5%", null, payRate);
            throw new PropertyVetoException("Pay rate increase greater than 5%.", e);
        } else {
            log.info("APPROVED pay rate change, from " + oldPayRate + " to " + payRate + " for " + evt.getSource());
        }
    }
}
