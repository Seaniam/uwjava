package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.persistent.DbServer;
import com.scg.util.Name;
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

        PrintStream so = System.out;

        accounts = dbServer.getClients();
        consultants = dbServer.getConsultants();

        // Print consultants
        so.println();
        so.println("==================================================================================");
        so.println("=========================== C O N S U L T A N T S ================================");
        so.println("==================================================================================");
        so.println();

        for (Consultant consultant : consultants) {
            Name cname = consultant.getName();
            so.println(cname.getLastName() + ", " + cname.getFirstName() + " " + cname.getMiddleName());
            so.println();
        }

        // get accounts for invoices
        for(ClientAccount account : accounts) {
            Invoice inv = dbServer.getInvoice(account, INVOICE_MONTH, INVOICE_YEAR);
            invoices.add(inv);
        }

        // Print invoices
        if (invoices.size() > 0) {
            so.println();
            so.println("==================================================================================");
            so.println("=============================== I N V O I C E S ==================================");
            so.println("==================================================================================");
            so.println();

            for (final Invoice invoice : invoices) {
                so.println(invoice.toReportString());
            }
        }
    }

}
