package com.scg.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.event.EventListenerList;
import java.beans.PropertyVetoException;

/**
 * Responsible for modifying the pay rate and sick leave and vacation hours of staff consultants.
 *
 * @author Sean Carberry
 * @version 6
 * @since 2/23/15
 */
public final class HumanResourceManager {

    /** list of event listeners */
    private EventListenerList listenerList = new EventListenerList();

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("HumanResourceManager");

    /**
     * Constructor.
     */
    public HumanResourceManager() {}

    /**
     * Sets the pay rate for a staff consultant.
     * @param c the consultant.
     * @param newPayRate the new pay rate for the consultant.
     */
    public void adjustPayRate(StaffConsultant c, int newPayRate) {
        int oldPay = c.getPayRate();
        double rate = (double) (newPayRate - oldPay)/oldPay;

        try {
            log.info("% change = (" + newPayRate + " - " + oldPay + ")/" + oldPay + " = " + rate);
            c.setPayRate(newPayRate);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the sick leave hours for a staff consultant.
     * @param c the consultant.
     * @param newSickLeaveHours the new sick leave hours for the consultant.
     */
    public void adjustSickLeaveHours(StaffConsultant c, int newSickLeaveHours) {
        c.setSickLeaveHours(newSickLeaveHours);
    }

    /**
     * Sets the vacation hours for a staff consultant.
     * @param c the consultant.
     * @param newVacationHours the new vacation hours for the consultant.
     */
    public void adjustVacationHours(StaffConsultant c, int newVacationHours) {
        c.setVacationHours(newVacationHours);
    }

    /**
     * Fires a voluntary termination event for the staff consultant.
     * @param c the consultant resigning
     */
    public void acceptResignation(Consultant c) {
        TerminationEvent evt = new TerminationEvent(HumanResourceManager.class, c, true);
        fireVoluntaryTerminationEvent(evt);
    }

    /**
     * Fires an involuntary termination event for the staff consultant.
     * @param c the consultant being terminated
     */
    public void terminate(Consultant c) {
        TerminationEvent evt = new TerminationEvent(HumanResourceManager.class, c, false);
        fireForcedTerminationEvent(evt);
    }

    /**
     * Adds a termination listener.
     * @param l the listener to add.
     */
    public void addTerminationListener(TerminationListener l) {
        listenerList.add(TerminationListener.class, l);
    }

    /**
     * Removes a termination listener.
     * @param l the listener to remove.
     */
    public void removeTerminationListener(TerminationListener l) {
        listenerList.remove(TerminationListener.class, l);
    }

    /**
     * Fires a voluntary termination event.
     * @param evnt the voluntary termination event.
     */
    private void fireVoluntaryTerminationEvent(TerminationEvent evnt) {
        TerminationListener [] listeners;
        listeners = listenerList.getListeners(TerminationListener.class);
        for (TerminationListener listener : listeners) {
            listener.voluntaryTermination(evnt);
        }
    }

    /**
     * Fires a forced termination event.
     * @param evnt the forced termination event.
     */
    private void fireForcedTerminationEvent(TerminationEvent evnt) {
        TerminationListener [] listeners;
        listeners = listenerList.getListeners(TerminationListener.class);
        for (TerminationListener listener : listeners) {
            listener.forcedTermination(evnt);
        }
    }

}

