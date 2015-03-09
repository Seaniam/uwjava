package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.persistent.DbServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Creates an invoice from the data in the database.
 *
 * @author Sean Carberry
 * @version 7
 * @since 3/3/15
 */
public final class Assignment07 {
//
//    Instantiate the DbServer, and retrieve the list of clients from the database.
//    Using the getInvoice method create and print the invoice for each client.

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("Assignment07");

    /** The invoice month. */
    private static final int INVOICE_MONTH = Calendar.MARCH;

    /** The test year. */
    private static final int INVOICE_YEAR = 2006;

    /**
     * Entry point.
     *
     * @param args not used.
     * @throws Exception if anything goes awry.
     */
    public static void main(String[] args) throws Exception {
        List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        List<Consultant> consultants = new ArrayList<Consultant>();
        List<TimeCard> timeCards = new ArrayList<TimeCard>();
        List<Invoice> invoices = new ArrayList<Invoice>();

        DbServer dbServer = new DbServer("jdbc:derby://localhost:1527/memory:scgDb", "student", "student");

        accounts = dbServer.getClients();
        consultants = dbServer.getConsultants();

        for(ClientAccount account : accounts) {
            Invoice inv = dbServer.getInvoice(account, INVOICE_MONTH, INVOICE_YEAR);
            invoices.add(inv);
        }

        if (invoices.size() > 0) {
            // Print them
            System.out.println();
            System.out.println("==================================================================================");
            System.out.println("=============================== I N V O I C E S ==================================");
            System.out.println("==================================================================================");
            System.out.println();
            printInvoices(invoices, System.out);
            // Now print it to a file
            PrintStream writer;
            try {
                writer = new PrintStream(new FileOutputStream("invoice.txt"));
                printInvoices(invoices, writer);
            } catch (final IOException ex) {
                log.error("Unable to print invoice.", ex);
            }
        }
    }

    /**
     * Print the invoice to a PrintStream.
     *
     * @param invoices the invoices to print
     * @param out The output stream; can be System.out or a text file.
     */
    private static void printInvoices(final List<Invoice> invoices, final PrintStream out) {
        for (final Invoice invoice : invoices) {
            out.println(invoice.toReportString());
        }
    }

}
