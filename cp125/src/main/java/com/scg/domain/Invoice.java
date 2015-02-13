package com.scg.domain;

import com.scg.util.DateRange;
import com.scg.util.FormatHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.*;

/**
 * Invoice encapsulates the attributes and behavior to create client invoices for
 * a given time period from time cards. The invoicing business' name and address
 * are obtained from a properties file. The name of the property file is
 * specified by the PROP_FILE_NAME static member.
 *
 * @author Sean Carberry
 * @version 4
 * @since 1/27/15
 */
public final class Invoice implements Serializable {

    /** serialization UID */
    private static final long serialVersionUID = 5277630064251969734L;

    /** Property containing the invoicing business name. */
    static String BUSINESS_NAME_PROP;

    /** Property containing the invoicing business city. */
    static String BUSINESS_CITY_PROP;

    /** Property containing the invoicing business state. */
    static String BUSINESS_STATE_PROP;

    /** Property containing the invoicing business street address. */
    static String BUSINESS_STREET_PROP;

    /** Property containing the invoicing business zip or postal code. */
    static String BUSINESS_ZIP_PROP;

    /** String constant for "N/A". */
    static String NA;

    /** Name of property file containing invoicing business info. */
    static String PROP_FILE_NAME;

    /** Number of invoice lines per invoice page **/
    static final int LINES_PER_PAGE = 5;

    /** client */
    private final ClientAccount client;

    /** month invoice was delivered */
    private final int invoiceMonth;

    /** year invoice was delivered */
    private final int invoiceYear;

    /** invoice date range **/
    private final DateRange dateRange;

    /** invoice consultant **/
    private Consultant consultant;

    /** invoice hour totals **/
    private int totalHours;

    /** invoice charge totals **/
    private int totalCharges;

    /** list of line items that are billable for this invoice **/
    private ArrayList<InvoiceLineItem> invoiceLineItems = new ArrayList<InvoiceLineItem>();

    /**
     * Construct an Invoice for a client.
     */
    public Invoice(ClientAccount client, int invoiceMonth, int invoiceYear) {

        this.client = client;
        this.invoiceMonth = invoiceMonth;
        this.invoiceYear = invoiceYear;

        this.PROP_FILE_NAME = "invoice.properties";

        // Set header constants using IO
        setHeaderConstants();

        this.dateRange = new DateRange(invoiceMonth, invoiceYear);
    }

    /**
     * Get the invoice month. This is the 0-based month number.
     * @return the invoice month number.
     */
    public int getInvoiceMonth() {
        return invoiceMonth;
    }

    /**
     * Get the client for this Invoice.
     * @return the client.
     */
    public ClientAccount getClientAccount() {
        return client;
    }

    /**
     * Get the total hours for this Invoice.
     * @return Total hours.
     */
    public int getTotalHours() {
        return this.totalHours;
    }

    /**
     * Get the total charges for this Invoice.
     * @return Total charges.
     */
    public int getTotalCharges() {
        return this.totalCharges;
    }

    /**
     * Extract the billable hours for this Invoice's client from
     * the input TimeCard and add them to the line items. Only
     * those hours for the client and month unique to this
     * invoice will be added.
     *
     * @param timeCard the TimeCard potentially containing line items
     *                 for this Invoices client.
     */
    public void extractLineItems(TimeCard timeCard) {
        List<ConsultantTime> lineItems = timeCard.getBillableHoursForClient(client.getName());

        consultant = timeCard.getConsultant();

        for (ConsultantTime item : lineItems) {
            Date itemDate = item.getDate();

            if(item != null && dateRange.isInRange(itemDate)) {
                int rate = item.getSkillType().getRate();
                int hours = item.getHours();
                int total = hours * rate;

                totalHours += hours;
                totalCharges += total;

                InvoiceLineItem invLineItem = new InvoiceLineItem(itemDate, consultant, item.getSkillType(), hours);

                invoiceLineItems.add(invLineItem);
            }
        }

    }

    /**
     * Read the file contents of invoice properties and
     * set the constants for the invoice header.
     */
    private void setHeaderConstants() {

        Properties invoiceProps = new Properties();
        InputStream inputFile = null;

        try {

            inputFile = new FileInputStream(PROP_FILE_NAME);

            // load a properties file
            invoiceProps.load(inputFile);

            BUSINESS_NAME_PROP = (String) invoiceProps.get("business.name");
            BUSINESS_STREET_PROP = (String) invoiceProps.get("business.street");
            BUSINESS_CITY_PROP = (String) invoiceProps.get("business.city");
            BUSINESS_STATE_PROP = (String) invoiceProps.get("business.state");
            BUSINESS_ZIP_PROP = (String) invoiceProps.get("business.zip");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputFile != null) {
                try {
                    inputFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * Create the letterhead for the invoice.
     * @return string representation of the letterhead.
     */
    public String getLetterHead() {
        StringBuilder letterHead = new StringBuilder();
        letterHead.append(BUSINESS_NAME_PROP + "\n");
        letterHead.append(BUSINESS_STREET_PROP + "\n");
        letterHead.append(BUSINESS_CITY_PROP + ", ");
        letterHead.append(BUSINESS_STATE_PROP + " ");
        letterHead.append(BUSINESS_ZIP_PROP);

        return letterHead.toString();
    }

    /**
     * String representation of Invoice.
     * @return a string representation of the invoice.
     */
    @Override
    public String toString() {
        return "Invoice{" +
                "client=" + client +
                ", invoiceMonth=" + invoiceMonth +
                ", invoiceYear=" + invoiceYear +
                '}';
    }

    /**
     * Returns a string of the formatted invoice header. Contains company info
     * and client info.
     * @return String value of the formatted invoice header.
     */
    public String getInvoiceHead() {
        StringBuilder header = new StringBuilder();

        header.append(getLetterHead() + "\n\n");

        if (client != null) {
            header.append(new InvoiceHeader(client.getName(),
                    client.getAddress(),
                    client,
                    new Date(),
                    this.dateRange.getStartDate()).toString());
        }

        return header.toString();
    }

    /**
     * Returns a string of the formatted invoice columns.
     * @return String value of the formatted header columns.
     */
    public String getLineColumns() {
        StringBuilder columns = new StringBuilder();

        columns.append("\n");
        columns.append(FormatHelper.padRight("Date", 12));
        columns.append(FormatHelper.padRight("Consultant", 29));
        columns.append(FormatHelper.padRight("Skill", 21));
        columns.append(FormatHelper.padRight("Hours", 7));
        columns.append("Charge");
        columns.append("\n");
        columns.append(FormatHelper.padRight("----------", 12));
        columns.append(FormatHelper.padRight("---------------------------", 29));
        columns.append(FormatHelper.padRight("------------------", 21));
        columns.append(FormatHelper.padRight("-----", 7));
        columns.append("----------");
        columns.append("\n");

        return columns.toString();
    }

    /**
     * Creates the final charges line of the invoice.
     * @return String representation of the final charges line.
     */
    public String getFinalInvoiceCharges() {
        StringBuilder totals = new StringBuilder();

        totals.append("\n");
        totals.append(FormatHelper.padRight("Total: ", 62));
        totals.append(FormatHelper.padLeft(String.valueOf(totalHours), 5));
        totals.append(FormatHelper.padLeft(String.valueOf(FormatHelper.currencyFormat(totalCharges)), 12));
        totals.append("\n");

        return totals.toString();
    }

    /**
     * Create a formatted string containing the printable invoice.
     * Includes a header and footer on each page.
     * @return The formatted invoice as a string.
     */
    public String toReportString() {
        StringBuilder report = new StringBuilder();
        int lineItemsCount = 1,
            totalLineItems = invoiceLineItems.size();
        String header = getInvoiceHead();
        String columns = getLineColumns();
        InvoiceFooter footer = new InvoiceFooter(BUSINESS_NAME_PROP);


        if (totalLineItems > 0) {

            report.append(header);
            report.append(columns);

            for (InvoiceLineItem item : invoiceLineItems) {

                report.append(item.toString());

                // Show header if this is a new page
                if (lineItemsCount % this.LINES_PER_PAGE == 0 && totalLineItems != lineItemsCount) {
                    report.append(footer.toString());

                    report.append(header);
                    report.append(columns);
                }

                lineItemsCount++;

            }

            report.append(getFinalInvoiceCharges());
            report.append(footer.toString());

        } else {
            // Empty invoice
            report.append(getInvoiceHead());
            report.append(NA);
            report.append(footer.toString());
        }

        return report.toString();
    }
}
