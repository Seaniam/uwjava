package com.scg.domain;

import com.scg.util.Address;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Header for Invoices.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/27/15
 */
final class InvoiceHeader {

    /** Constant for invoice month formatting **/
    SimpleDateFormat DATE_FORMAT_MONTH = new SimpleDateFormat("MMMM yyyy");
    /** Constant for invoice date formatting **/
    SimpleDateFormat INVOICE_DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy");

    /** Business name **/
    private final String businessName;
    /** Address of business **/
    private final Address businessAddress;
    /** Client account **/
    private final ClientAccount client;
    /** Date invoice was issued **/
    private final Date invoiceDate;
    /** Date month to be billed for **/
    private final Date invoiceForMonth;

    /**
     * Construct an InvoiceHeader.
     * @param businessName name of business issuing invoice.
     * @param businessAddress address of business issuing invoice.
     * @param client client for the invoice with this header.
     * @param invoiceDate date of the invoice with this header.
     * @param invoiceForMonth month of billable charges for invoice with this header.
     */
    public InvoiceHeader(String businessName,
                         Address businessAddress,
                         ClientAccount client,
                         Date invoiceDate,
                         Date invoiceForMonth) {

        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.client = client;
        this.invoiceDate = invoiceDate;
        this.invoiceForMonth = invoiceForMonth;
    }


    /**
     * Print this InvoiceHeader.
     * @return Formatted string of the information in this header.
     */
    @Override
    public String toString() {
        StringBuilder header = new StringBuilder();

        header.append("Invoice for:\n");
        header.append(businessName + "\n");
        header.append(businessAddress.toString() + "\n");
        header.append(client.getContact() + "\n\n");
        header.append("Invoice for Month of: " + DATE_FORMAT_MONTH.format(invoiceForMonth) + "\n");
        header.append("Invoice Date: " + INVOICE_DATE_FORMAT.format(invoiceDate) + "\n");

        return header.toString();
    }
}
