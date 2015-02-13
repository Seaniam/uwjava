package com.scg.domain;

import com.scg.util.FormatHelper;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Encapsulates a time card capable of storing a consultant's billable and non-billable hours for a week.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/12/15
 */
public class TimeCard implements Comparable<TimeCard>, Serializable {

    /** Unique serialization ID */
    private static final long serialVersionUID = 8237137652611791100L;

    /** Constant for date formatter. */
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy");

    /** Constant for row date formatter. */
    SimpleDateFormat ROW_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    /** Consultant who worked on this time card. */
    private Consultant consultant;

    /** Date stated working. */
    private Date weekStartingDay = null;

    /** Total hours worked. */
    private int totalHours = 0;

    /** Total billable hours worked. */
    private int totalBillableHours = 0;

    /** Total non-billable hours worked. */
    private int totalNonBillableHours = 0;

    /** List for consulting hour lines */
    private List<ConsultantTime> consultingHours = new ArrayList<ConsultantTime>();

    /**
     * Creates new instance of TimeCard.
     * @param consultant the consultant.
     * @param weekStartingDay day consultant started working.
     */
    public TimeCard(Consultant consultant, Date weekStartingDay) {
        this.consultant = consultant;
        this.weekStartingDay = weekStartingDay;
    }

    /**
     * Add a ConsultantTime object to this TimeCard
     * @param consultantTime the ConsultantTime to add
     */
    public void addConsultantTime(ConsultantTime consultantTime) {
        consultingHours.add(consultantTime);
    }

    /**
     * Returns the billable hours (if any) in this TimeCard for the specified Client.
     * @param clientName name of client.
     * @return list of billable hours for the client.
     */
    public List<ConsultantTime> getBillableHoursForClient(String clientName) {
        List<ConsultantTime> clientBillableHours = new ArrayList<ConsultantTime>();

        for (ConsultantTime time : consultingHours) {
            if (time.getAccount().getName().equals(clientName)) {
                clientBillableHours.add(time);
            }
        }

        return clientBillableHours;
    }

    /**
     * Getter for consultant on this time card.
     * @return the consultant.
     */
    public Consultant getConsultant() {
        return consultant;
    }

    /**
     * Getter for total billable hours on time card.
     * @return total billable hours.
     */
    public int getTotalBillableHours() {
        int tbh = 0;

        if(consultingHours.size() > 0) {
            for(ConsultantTime row : consultingHours) {
                if (row.isBillable()) {
                    tbh += row.getHours();
                }
            }
        }

        this.totalBillableHours = tbh;

        return totalBillableHours;
    }

    /**
     * Getter for total hours on time card.
     * @return total hours.
     */
    public int getTotalHours() {
        return getTotalBillableHours() + getTotalNonBillableHours();
    }

    /**
     * Getter for total non-billable hours on time card.
     * @return total non-billable hours.
     */
    public int getTotalNonBillableHours() {
        int tnb = 0;

        if(consultingHours.size() > 0) {
            for(ConsultantTime row : consultingHours) {
                if (!row.isBillable()) {
                    tnb += row.getHours();
                }
            }
        }

        this.totalNonBillableHours = tnb;

        return totalNonBillableHours;
    }

    /**
     * Getter for day of week started.
     * @return date started.
     */
    public Date getWeekStartingDay() {
        return weekStartingDay;
    }

    /**
     * Create a string representation of this object,
     * suitable for printing the entire time card.
     * @return a formatted report.
     */
    public String toReportString() {
        StringBuilder report = new StringBuilder();
        String reportSeparator = String.format("%68s\n", " ").replace(" ", "=");
        int midCol = 34;

        report.append(reportSeparator);

        // Consultant name field
        String consultantName = getConsultant().getName().toString();
        String consultantField = String.format("Consultant: %s", consultantName);
        report.append(FormatHelper.padRight(consultantField, midCol));

        // Week Starting field
        String weekStartField = "Week Starting: " + DATE_FORMAT.format(getWeekStartingDay()).toString();
        report.append(FormatHelper.padLeft(weekStartField, midCol));


        // Column headers
        String accountCol = "Account",
               dateCol = "Date",
               hoursCol = "Hours",
               skillCol = "Skill";

        String colNames = String.format("%s  %s  %s  %s\n", FormatHelper.padRight(accountCol, 27),
                                                            FormatHelper.padRight(dateCol, 10),
                                                            FormatHelper.padRight(hoursCol, 5),
                                                            FormatHelper.padRight(skillCol, 20));

        String colSeparators = String.format("%s  %s  %s  %s\n", FormatHelper.padRight("---------------------------", 27),
                                                                 FormatHelper.padRight("----------", 10),
                                                                 FormatHelper.padRight("-----", 5),
                                                                 FormatHelper.padRight("--------------------", 20));

        StringBuilder billableRows = new StringBuilder();
        StringBuilder nonBillableRows = new StringBuilder();

        if(consultingHours.size() > 0) {
            for(ConsultantTime row : consultingHours) {
                int hours = row.getHours();
                String date = ROW_DATE_FORMAT.format(row.getDate()).toString();

                String rowCols = String.format("%s  %s  %s  %s\n", FormatHelper.padRight(row.getAccount().getName(), 27),
                                                                   FormatHelper.padRight(date, 10),
                                                                   FormatHelper.padLeft(String.valueOf(hours), 5),
                                                                   FormatHelper.padRight(row.getSkillType().getSkillType(), 20));

                if (row.isBillable()) {
                    billableRows.append(rowCols);
                } else {
                    nonBillableRows.append(rowCols);
                }
            }
        }

        // Billable rows
        report.append("\n\nBillable Time:\n");
        report.append(colNames);
        report.append(colSeparators);
        report.append(billableRows.toString());

        // Non-billable rows
        report.append("\nNon-billable Time:\n");
        report.append(colNames);
        report.append(colSeparators);
        report.append(nonBillableRows.toString());

        // Summary
        int summaryPadding = 13;
        report.append("\nSummary:\n");
        report.append(FormatHelper.padRight("Total Billable:", midCol));
        report.append(FormatHelper.padLeft(String.valueOf(getTotalBillableHours()) + "\n", summaryPadding));
        report.append(FormatHelper.padRight("Total Non-billable:", midCol));
        report.append(FormatHelper.padLeft(String.valueOf(getTotalNonBillableHours()) + "\n", summaryPadding));
        report.append(FormatHelper.padRight("Total Hours:", midCol));
        report.append(FormatHelper.padLeft(String.valueOf(getTotalHours()) + "\n", summaryPadding));

        report.append(reportSeparator);

        return report.toString();
    }

    /**
     * Create a string representation of this object,
     * consisting of the consultant name and the time
     * card week starting day.
     * @return a string containing the consultant name and the time card week starting day
     */
    @Override
    public String toString() {
        return "TimeCard{" +
                "consultant=" + consultant +
                ", weekStartingDay=" + weekStartingDay +
                '}';
    }

    /**
     * Compares TimeCard, by ascending date order, consultant, totalHours, totalBillableHours
     * and totalNonBillableHours.
     * Specified by: compareTo in interface Comparable<TimeCard>
     *
     * @param other the TimeCard to compare to.
     * @return a negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(TimeCard other) {

        // compare date
        Date startDay = this.getWeekStartingDay(),
             otherStartDay = other.getWeekStartingDay();

        if (startDay.before(otherStartDay)) {
            return 1;
        } else if (startDay.after(otherStartDay)) {
            return -1;
        }

        // compare consultant
        String consultantName = this.getConsultant().getName().toString(),
               otherConsultantName = other.getConsultant().getName().toString();

        if (consultantName.compareTo(otherConsultantName) > 0) {
            return 1;
        } else if (consultantName.compareTo(otherConsultantName) < 0) {
            return -1;
        }

        // compare total hours
        int thisTotalHrs = this.getTotalHours(),
            otherTotalHrs = other.getTotalHours();

        if (thisTotalHrs > otherTotalHrs) {
            return 1;
        } else if (thisTotalHrs < otherTotalHrs) {
            return -1;
        }

        // compare total billable hours
        int thisTotalBillable = this.getTotalBillableHours(),
            otherTotalBillable = other.getTotalBillableHours();

        if (thisTotalBillable > otherTotalBillable) {
            return 1;
        } else if (thisTotalBillable < otherTotalBillable) {
            return -1;
        }

        // compare total non-billable hours
        int thisNonBillable = this.getTotalNonBillableHours(),
            otherNonBillable = other.getTotalNonBillableHours();

        if (thisNonBillable > otherNonBillable) {
            return 1;
        } else if (thisNonBillable < otherNonBillable) {
            return -1;
        }

        // Else must be same
        return 0;
    }
}
