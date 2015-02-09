package com.scg.util;

import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;

import java.util.*;

/**
 * Utility class for processing TimeCard lists.
 *
 * @author Sean Carberry
 * @version 4
 * @since 2/2/15
 */
public final class TimeCardListUtil {

    /**
     * Sorts this list into ascending order, by the start date.
     *
     * @param timeCards the list of time cards to sort
     */
    public static void sortByStartDate(List<TimeCard> timeCards) {
        Collections.sort(timeCards, TimeCardListUtil.dateTimeCardComparator);
    }

    /**
     * Sorts this list into ascending order by consultant name.
     *
     * @param timeCards the list of time cards to sort
     */
    public static void sortByConsultantName(List<TimeCard> timeCards) {
        TimeCardConsultantComparator tcComparator = new TimeCardConsultantComparator();
        Collections.sort(timeCards, tcComparator);
    }

    /**
     * Get a list of TimeCards that cover dates that fall within a date range.
     * Each time may have time entries through out one week beginning with the time card start date.
     *
     * @param timeCards the list of time cards to extract the sub set from.
     * @param dateRange The DateRange within which the dates of the returned TimeCards must fall.
     * @return a list of TimeCards having dates fall within the date range.
     */
    public static List<TimeCard> getTimeCardsForDateRange(List<TimeCard> timeCards,
                                                          DateRange dateRange) {

        List timeCardList = new ArrayList<TimeCard>();

        for (TimeCard card : timeCards) {
            if (dateRange.isInRange(card.getWeekStartingDay())) {
                timeCardList.add(card);
            }
        }

        return timeCardList;

    }


    /**
     * Get a list of TimeCards for the specified consultant.
     *
     * @param timeCards the list of time cards to extract the sub set from.
     * @param consultant the consultant whose TimeCards will be obtained.
     * @return a list of TimeCards for the specified consultant.
     */
    public static List<TimeCard> getTimeCardsForConsultant(List<TimeCard> timeCards,
                                                           Consultant consultant) {

        List timeCardList = new ArrayList<TimeCard>();

        for (TimeCard card : timeCards) {
            if (card.getConsultant().equals(consultant)) {
                timeCardList.add(card);
            }
        }

        return timeCardList;
    }


    /**
     * Static comparator for comparing TimeCards by date.
     */
    public static Comparator<TimeCard> dateTimeCardComparator = new Comparator<TimeCard>() {

        /**
         * Compares two TimeCards by date.
         * @param t1 The first TimeCard to compare.
         * @param t2 the second TimeCard to compare.
         * @return -1 if the first TimeCard date is before the second TimeCard date,
         * 1 if the first TimeCard date is after the second TimeCard date, and 0
         * if the TimeCards have the same exact date.
         */
        public int compare(TimeCard t1, TimeCard t2) {

            Date date1 = t1.getWeekStartingDay(),
                 date2 = t2.getWeekStartingDay();

            if(date1.before(date2)) {
                return -1;
            } else if (date1.after(date2)) {
                return 1;
            }

            return 0;
        }
    };

}
