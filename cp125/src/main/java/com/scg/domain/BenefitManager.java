package com.scg.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Logs changes in benefits.
 *
 * @author Sean Carberry
 * @version 6
 * @since 2/20/15
 */
public final class BenefitManager extends Object implements PropertyChangeListener {

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("BenefitManager");

    /**
     * Constructor
     */
    public BenefitManager() {}

    /**
     * Logs the change.
     * @param evt a property change event for the sickLeaveHours or vacationHours, or payRate property.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        StringBuilder changeEventLog = new StringBuilder();

        changeEventLog.append(evt.getPropertyName().toString());
        changeEventLog.append(" changed from ");
        changeEventLog.append(evt.getOldValue());
        changeEventLog.append(" to ");
        changeEventLog.append(evt.getNewValue());
        changeEventLog.append(" for ");
        changeEventLog.append(evt.getSource());

        log.info(changeEventLog.toString());
    }
}
