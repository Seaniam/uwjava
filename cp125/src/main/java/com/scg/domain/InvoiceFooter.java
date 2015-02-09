package com.scg.domain;

import com.scg.util.FormatHelper;

/**
 * Footer for Invoices.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/27/15
 */
final class InvoiceFooter {

    /** name of the business */
    private String businessName;

    /** page number counter **/
    private int pageNumber = 1;

    /**
     * Construct an InvoiceFooter.
     * @param businessName name of business to include in footer.
     */
    InvoiceFooter(String businessName) {

        this.businessName = businessName;
    }

    /**
     * Increment the current page number by one.
     */
    public void incrementPageNumber() {
        this.pageNumber++;
    }

    /**
     * Print the formatted footer.
     * @return formatted footer string.
     */
    @Override
    public String toString() {
        StringBuilder foot = new StringBuilder();

        foot.append("\n\n");
        foot.append(FormatHelper.padRight(businessName, 70));
        foot.append("Page: ");
        foot.append(FormatHelper.padLeft(String.valueOf(pageNumber), 3));
        foot.append("\n");
        foot.append("===============================================================================\n\n");

        incrementPageNumber();

        return foot.toString();
    }
}
