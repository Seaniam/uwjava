package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.util.DateRange;
import com.scg.util.ListFactory;
import com.scg.util.TimeCardListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Sean Carberry
 * @version 5
 * @since 2/9/15
 */
public class Assignment05 {

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("Assignment05");

    /** accounts list */
    private static List<ClientAccount> accounts = null;

    /** time cards list */
    private static List<TimeCard> timeCards = null;

    /** The invoice month. */
    private static final int INVOICE_MONTH = Calendar.MARCH;

    /** The test year. */
    private static final int INVOICE_YEAR = 2006;

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

    /**
     * Create invoices for the clients from the timecards.
     *
     * @param accounts the accounts to create the invoices for
     * @param timeCards the time cards to create the invoices from
     *
     * @return the created invoices
     */
    private static List<Invoice> createInvoices(final List<ClientAccount> accounts,
                                                final List<TimeCard> timeCards) {
        final List<Invoice> invoices = new ArrayList<Invoice>();

        final List<TimeCard> timeCardList = TimeCardListUtil
                .getTimeCardsForDateRange(timeCards, new DateRange(INVOICE_MONTH, INVOICE_YEAR));
        for (final ClientAccount account : accounts) {
            final Invoice invoice = new Invoice(account, INVOICE_MONTH, INVOICE_YEAR);
            invoices.add(invoice);
            for (final TimeCard currentTimeCard : timeCardList) {
                invoice.extractLineItems(currentTimeCard);
            }
        }

        return invoices;
    }

    /**
     * Prevent instantiation.
     */
    private Assignment05() {
    }

    /**
     * The application method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {

        deSerializeLists();

        // Print time cards
        ListFactory.printTimeCards(timeCards, System.out);

        // Create the Invoices
        final List<Invoice> invoices = createInvoices(accounts, timeCards);

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

    /**
     * De-serialize the client and time card lists.
     */
    public static void deSerializeLists() {
        // Deserialize clients
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("ClientList.ser"));
            accounts = (List<ClientAccount>)in.readObject();
            in.close();
        }
        catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

        // Deserialize time cards
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("TimeCardList.ser"));
            timeCards = (List<TimeCard>)in.readObject();
            in.close();
        }
        catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
