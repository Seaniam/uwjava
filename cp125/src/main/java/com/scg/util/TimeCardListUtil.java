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
     * Prevent instantiation.
     */
    private TimeCardListUtil() {

    }

    /**
     * Sorts this list into ascending order, by the start date.
     * Will use TimeCard's compareTo for natural sorting.
     *
     * @param timeCards the list of time cards to sort.
     */
    public static void sortByStartDate(List<TimeCard> timeCards) {
        Collections.sort(timeCards);
    }

    /**
     * Sorts this list into ascending order by consultant name.
     *
     * @param timeCards the list of time cards to sort.
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
}
