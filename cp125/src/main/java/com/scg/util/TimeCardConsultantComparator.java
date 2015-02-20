package com.scg.util;

import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;

import java.util.Comparator;
import java.util.Date;

/**
 * Compares two TimeCard objects by ascending consultant, timecard week, totalHours,
 * totalBillableHours and totalNonBillableHours.
 *
 * @author Sean Carberry
 * @version 4
 * @since 2/2/15
 */
public final class TimeCardConsultantComparator implements Comparator<TimeCard> {

    /**
     * Compares its two arguments for order. Returns a negative integer, zero, or a positive integer
     * as the first argument is less than, equal to, or greater than the second.
     * Specified by: compare in interface Comparator<TimeCard>
     *
     * @param firstTimeCard the first object to be compared.
     * @param secondTimeCard the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the first argument is
     * less than, equal to, or greater than the second.
     */
    public int compare(TimeCard firstTimeCard, TimeCard secondTimeCard) {

        // Check consultant
        Consultant firstConsultant = firstTimeCard.getConsultant(),
                   secondConsultant = secondTimeCard.getConsultant();

        int consultantCompare = firstConsultant.compareTo(secondConsultant);

        if (consultantCompare != 0) {
            return consultantCompare;
        }

        // Check work week
        Date firstWeek = firstTimeCard.getWeekStartingDay(),
             secondWeek = secondTimeCard.getWeekStartingDay();

        if (firstWeek.before(secondWeek)) {
            return -1;
        } else if (firstWeek.after(secondWeek)) {
            return 1;
        }

        // Check total hours
        int firstTotalHours = firstTimeCard.getTotalHours(),
            secondTotalHours = secondTimeCard.getTotalHours();

        if (firstTotalHours > secondTotalHours) {
            return 1;
        } else if (firstTotalHours < secondTotalHours) {
            return -1;
        }

        // Check billable hours
        int firstBillable = firstTimeCard.getTotalBillableHours(),
            secondBillable = secondTimeCard.getTotalBillableHours();

        if (firstBillable > secondBillable) {
            return 1;
        } else if (firstBillable < secondBillable) {
            return -1;
        }

        // Check non-billable hours
        int firstNonBillable = firstTimeCard.getTotalNonBillableHours(),
            secondNonBillable = secondTimeCard.getTotalNonBillableHours();

        if (firstNonBillable > secondNonBillable) {
            return 1;
        } else if (firstNonBillable < secondNonBillable) {
            return -1;
        }

        // Guess it must be the same
        return 0;

    }
}
