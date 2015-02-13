package com.scg.domain;

import com.scg.util.FormatHelper;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Encapsulates a single billable item to be included in an invoice.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/26/15
 */
public class InvoiceLineItem implements Serializable {

    /** Unique serialization ID */
    private static final long serialVersionUID = -2257392872349703982L;

    /** date format **/
    SimpleDateFormat ROW_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    /** This invoice line item's date **/
    private Date date;

    /** the consultant who worked **/
    private Consultant consultant;

    /** the skill employed on this item **/
    private Skill skill;

    /** number of hours worked **/
    private int hours;

    /**
     * Creates a new instance of InvoiceLineItem.
     * @param date The date of this line item.
     * @param consultant Consultant for this line item.
     * @param skill Skill for this line item.
     * @param hours Hours for this line item.
     */
    InvoiceLineItem(Date date, Consultant consultant, Skill skill, int hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("Hours worked must be greater than 0.");
        }

        this.date = date;
        this.consultant = consultant;
        this.skill = skill;
        this.hours = hours;
    }

    /**
     * Get the consultant for this line item.
     * @return The consultant.
     */
    public Consultant getConsultant() {
        return this.consultant;
    }

    /**
     * Get the skill for this line item.
     * @return the skill.
     */
    public Skill getSkill() {
        return this.skill;
    }

    /**
     * Get the hours for this line item.
     * @return The hours.
     */
    public int getHours() {
        return this.hours;
    }

    /**
     * Get the charge for this line item.
     * @return the charge.
     */
    public int getCharge() {
        int hoursWorked = this.getHours();
        int costPerHour = this.getSkill().getRate();
        int charge = hoursWorked * costPerHour;

        return charge;
    }

    /**
     * Utility to format an int into US currency.
     * @param charge integer for line charge.
     * @return string formatted in US currency.
     */
    public String currencyFormat(int charge) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return String.format("%s", currencyFormat.format(charge));
    }

    /**
     * Print the date, consultant, skill, hours and charge for this line item.
     * @return Formatted string.
     */
    @Override
    public String toString() {
        StringBuilder lineItem = new StringBuilder();

        // create line charges
        lineItem.append(FormatHelper.padRight(ROW_DATE_FORMAT.format(date), 12));
        lineItem.append(FormatHelper.padRight(consultant.getName().toString(), 29));
        lineItem.append(FormatHelper.padRight(getSkill().getSkillType(), 21));
        lineItem.append(FormatHelper.padLeft(String.valueOf(hours), 5));

        // format and show line charge
        lineItem.append(FormatHelper.padLeft(currencyFormat(getCharge()), 12));
        lineItem.append("\n");

        return lineItem.toString();
    }
}
