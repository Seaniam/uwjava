package com.scg.domain;

import com.scg.util.Name;

import javax.swing.event.EventListenerList;
import java.beans.*;
import java.io.Serializable;

/**
 * A consultant who is kept on staff (receives benefits).
 *
 * @author Sean Carberry
 * @version 6
 * @since 2/23/15
 */
public final class StaffConsultant extends Consultant implements Serializable {

    /** Pay rate property name. */
    static String PAY_RATE_PROPERTY_NAME = "payRate";

    /** Pay rate */
    private int payRate;

    /** Pay rate property name. */
    static String SICK_LEAVE_HOURS_PROPERTY_NAME = "sickLeaveHours";

    /** Sick leave hours */
    private int sickLeaveHours;

    /** Vacation hours property name. */
    static String VACATION_HOURS_PROPERTY_NAME = "vacationHours";

    /** Vacation hours */
    private int vacationHours;

    /** list of event listeners */
    private EventListenerList listenerList = new EventListenerList();

    /** vetoable change support listeners */
    private VetoableChangeSupport vceListeners = new VetoableChangeSupport(this);


    /**
     * Creates a new instance of StaffConsultant
     *
     * @param name the consultant's name.
     * @param rate the pay rate in cents.
     * @param sickLeave the sick leave hours.
     * @param vacation the vacation hours.
     */
    public StaffConsultant(Name name, int rate, int sickLeave, int vacation) {
        super(name);
        this.payRate = rate;
        this.sickLeaveHours = sickLeave;
        this.vacationHours = vacation;
    }

    /**
     * Get the current pay rate.
     * @return the pay rate in cents.
     */
    public int getPayRate() {
        return this.payRate;
    }

    /**
     * Set the pay rate. Fires the VetoableChange event.
     * @param payRate the pay rate in cents.
     * @throws PropertyVetoException if a veto occurs.
     */
    public void setPayRate(int payRate) throws PropertyVetoException {
        int oldPayRate = getPayRate();

        if (payRate < 0) {
            throw new IllegalArgumentException("Pay rate must be a positive value.");
        }

        try {
            vceListeners.fireVetoableChange(PAY_RATE_PROPERTY_NAME, oldPayRate, payRate);
            PropertyChangeEvent evt = new PropertyChangeEvent(this, PAY_RATE_PROPERTY_NAME, this.payRate, payRate);
            firePropertyChangeEvent(evt);
            this.payRate = payRate;
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a general property change listener.
     * @param l the listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        listenerList.add(PropertyChangeListener.class, l);
    }

    /**
     * Remove a general property change listener.
     * @param l the listener.
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        listenerList.remove(PropertyChangeListener.class, l);
    }

    /**
     * Fires property change event.
     * @param evnt propety change event.
     */
    private void firePropertyChangeEvent(PropertyChangeEvent evnt) {
        PropertyChangeListener [] listeners;
        listeners = listenerList.getListeners(PropertyChangeListener.class);
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(evnt);
        }
    }

    /**
     * Adds a payRate property change listener.
     * @param l the listener
     */
    public void addPayRateListener(PropertyChangeListener l) {
        listenerList.add(PropertyChangeListener.class, l);
    }

    /**
     * Removes a payRate property change listener.
     * @param l the listener
     */
    public void removePayRateListener(PropertyChangeListener l) {
        listenerList.remove(PropertyChangeListener.class, l);
    }

    /**
     * Adds a vetoable change listener.
     * @param l the listener
     */
    public void addVetoableChangeListener(VetoableChangeListener l) {
//        listenerList.add(VetoableChangeListener.class, l);
            vceListeners.addVetoableChangeListener(l);
    }

    /**
     * Removes a vetoable change listener.
     * @param l the listener
     */
    public void removeVetoableChangeListener(VetoableChangeListener l) {
//        listenerList.remove(VetoableChangeListener.class, l);
        vceListeners.removeVetoableChangeListener(l);
    }

    /**
     * Get the available sick leave.
     * @return the available sick leave hours.
     */
    public int getSickLeaveHours() {
        return this.sickLeaveHours;
    }

    /**
     * Set the sick leave hours. Fires the PropertyChange event.
     * @param sickLeaveHours the available sick leave hours.
     */
    public void setSickLeaveHours(int sickLeaveHours) {
        PropertyChangeEvent evt = new PropertyChangeEvent(this, SICK_LEAVE_HOURS_PROPERTY_NAME, this.sickLeaveHours, sickLeaveHours);
        firePropertyChangeEvent(evt);
        this.sickLeaveHours = sickLeaveHours;
    }

    /**
     * Adds a sickLeaveHours property change listener.
     * @param l the listener.
     */
    public void addSickLeaveHoursListener(PropertyChangeListener l) {
        listenerList.add(PropertyChangeListener.class, l);
    }

    /**
     * Removes a sickLeaveHours property change listener.
     * @param l the listener.
     */
    public void removeSickLeaveHoursListener(PropertyChangeListener l) {
        listenerList.remove(PropertyChangeListener.class, l);
    }

    /**
     * Get the available vacation hours.
     * @return the available vacation hours.
     */
    public int getVacationHours() {
        return this.vacationHours;
    }

    /**
     * Set the vacation hours. Fires the ProperrtyChange event.
     * @param vacationHours the vacation hours.
     */
    public void setVacationHours(int vacationHours) {
        PropertyChangeEvent evt = new PropertyChangeEvent(this, VACATION_HOURS_PROPERTY_NAME, this.vacationHours, vacationHours);
        firePropertyChangeEvent(evt);
        this.vacationHours = vacationHours;
    }

    /**
     * Adds a vacationHours property change listener.
     * @param l the listener.
     */
    public void addVacationHoursListener(PropertyChangeListener l) {
        listenerList.add(PropertyChangeListener.class, l);
    }

    /**
     * Removes a vacationHours property change listener.
     * @param l the listener.
     */
    public void removeVacationHoursListener(PropertyChangeListener l) {
        listenerList.remove(PropertyChangeListener.class, l);
    }

    /**
     * Compares instance of StaffConsultant.
     * @param o StaffConsultant instance object.
     * @return true if this is the same StaffConsultant instance.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StaffConsultant that = (StaffConsultant) o;

        if (payRate != that.payRate) return false;
        if (sickLeaveHours != that.sickLeaveHours) return false;
        if (vacationHours != that.vacationHours) return false;

        return true;
    }

    /**
     * Creates the hashcode for the StaffConsultant instance.
     * @return the hashcode for the StaffConsultant instance.
     */
    @Override
    public int hashCode() {
        int result = payRate;
        result = 31 * result + sickLeaveHours;
        result = 31 * result + vacationHours;
        return result;
    }

}


