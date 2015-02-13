package com.scg.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * A consultants time. Maintains date, skill, account and hours data.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/12/15
 */
public final class ConsultantTime implements Serializable {

    /** Serialization UID **/
    private static final long serialVersionUID = -1754022357226785353L;

    /** Date consultant worked. */
    private Date date;

    /** Consultant's skill type. */
    private Skill skillType;

    /** Hours consultant worked on account. */
    private int hours;

    /** Account consultant worked on. */
    private Account account;

    /**
     * Creates new instance of ConsultantTime
     * @param date Date of the consultant's time card
     * @param account Account the consultant worked on
     * @param skillType consultant's skill type
     * @param hours number of hours consultant worked
     * @throws IllegalArgumentException
     */
    public ConsultantTime(Date date, Account account, Skill skillType, int hours) throws IllegalArgumentException {
        if (hours <= 0) {
            throw new IllegalArgumentException("Hours must be greater than 0.");
        }

        this.setDate(date);
        this.setAccount(account);
        this.setSkillType(skillType);
        this.setHours(hours);
    }

    /**
     * Compares instance of ConsultantTime.
     * @param o ConsultantTime instance object.
     * @return true if this is the same ConsultantTime instance.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConsultantTime that = (ConsultantTime) o;

        if (hours != that.hours) return false;
        if (!account.equals(that.account)) return false;
        if (!date.equals(that.date)) return false;
        if (skillType != that.skillType) return false;

        return true;
    }

    /**
     * Creates the hashcode for the ConsultantTime instance.
     * @return the hashcode for the ConsultantTime instance.
     */
    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + skillType.hashCode();
        result = 31 * result + hours;
        result = 31 * result + account.hashCode();
        return result;
    }

    /**
     * Setter for this account.
     * @param account account worked on.
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Getter for this account.
     * @return account worked on.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Setter for the date work was done.
     * @param date date work was done.
     */
    public void setDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date can not be set to null values");
        }
        this.date = date;
    }

    /**
     * Getter for the date work was done.
     * @return date work was done.
     */
    public Date getDate() {
        return new Date(date.getTime());
    }

    /**
     * Setter for the consultant's skill type.
     * @param skillType consultant's skill type.
     */
    public void setSkillType(Skill skillType) {
        this.skillType = skillType;
    }

    /**
     * Getter for the consultant's skill type.
     * @return consultant's skill type.
     */
    public Skill getSkillType() {
        return skillType;
    }

    /**
     * Setter for the hours worked.
     * @param hours number of hours consultant worked
     */
    public void setHours(int hours) throws IllegalArgumentException {
        if (hours <= 0) {
            throw new IllegalArgumentException("Hours must be greater than 0.");
        }
        this.hours = hours;
    }

    /**
     * Getter for the hours worked
     * @return number of hours worked
     */
    public int getHours() {
        return hours;
    }

    /**
     * Determines if the time is billable.
     * @return true if time is billable.
     */
    public boolean isBillable() {
        return account.isBillable();
    }

    /**
     * String representation of consultant time.
     * @return ConsultantTime as formatted string.
     */
    @Override
    public String toString() {
        return "ConsultantTime{" +
                "date=" + date +
                ", skillType=" + skillType +
                ", hours=" + hours +
                ", account=" + account +
                '}';
    }
}
