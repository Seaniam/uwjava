package com.scg.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility helper class to help with string/layout formatting and
 * currency formatting.
 *
 * @author Sean Carberry
 * @version 4
 * @since 2/2/15
 */
public final class FormatHelper {

    /**
     * Utility method to pad a string longer than n characters on the right.
     * @param s the string to pad.
     * @param n the set number of characters to pad up to.
     * @return formatted string with space padding to the right of a string.
     */
    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    /**
     * Utility method to pad a string longer than n characters on the left.
     * @param s the string to pad.
     * @param n the set number of characters to pad up to.
     * @return formatted string with space padding to the left of a string.
     */
    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }

    /**
     * Utility to format an int into US currency.
     * @param charge integer for line charge.
     * @return string formatted in US currency.
     */
    public static String currencyFormat(int charge) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);
        return String.format("%s", currencyFormat.format(charge));
    }

}
