package com.scg.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Encapsulates a range of two dates, inclusive of the start date and end date.
 *
 * @author Sean Carberry
 * @version 4
 * @since 2/2/15
 */
public final class DateRange {

    /** start date **/
    private final Date startDate;
    /** end date **/
    private final Date endDate;

    /**
     * Construct a DateRange given two dates.
     * @param startDate the start date for this DateRange.
     * @param endDate the end date for this DateRange.
     */
    public DateRange(Date startDate, Date endDate) {

        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("Start date can not be after end date.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Construct a DateRange for the given month.
     * @param month the month for which this DateRange should be constructed. This integer constant should be
     *              obtained from the java.util.Calendar class by month name, e.g. Calendar.JANUARY.
     * @param year the calendar year.
     */
    public DateRange(int month, int year) {
        Date start, end;

        // Set start date
//        TODO: abstract out to set min and set max
        Calendar cal = new GregorianCalendar(year,month, 1);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

        start = cal.getTime();

        // Set end date
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

        end = cal.getTime();

        if (end.before(start)) {
            throw new IllegalArgumentException("Start date can not be after end date.");
        }

        this.startDate = start;
        this.endDate = end;
    }

    /**
     * Construct a DateRange given two date strings in the correct format.
     * @param start String representing the start date, of the form MM/dd/yyyy.
     * @param end String representing the end date, of the form MM/dd/yyyy.
     */
    public DateRange(String start, String end) {

        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        Date sDate = null,
             eDate = null;

        try {
            sDate = formatter.parse(start);
            eDate = formatter.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (sDate == null || eDate == null) {
            throw new IllegalArgumentException("a start date and end date must be provided");
        }

        Date startDate = null,
             endDate = null;

        Calendar cal = Calendar.getInstance();

        // Set start date
        cal.setTime(sDate);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));

        startDate = cal.getTime();

        // Set end date
        cal.setTime(eDate);
//        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

        endDate = cal.getTime();

        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("Start date can not be after end date.");
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns the start date for this DateRange.
     * @return the start date for this DateRange.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Returns the end date for this DateRange.
     * @return the end date for this DateRange.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Returns true if the specified date is within the range start date <= date <= end date.
     * @param date the date to check for being within this DateRange.
     * @return true if the specified date is within this DateRange.
     */
    public boolean isInRange(Date date) {
        Date start = getStartDate(),
             end = getEndDate();
        boolean inRange = (date.after(start) || date.equals(start)) && (date.before(end) || date.equals(end));

        return inRange;
    }
}
