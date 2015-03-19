package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.client.InvoiceClient;
import com.scg.net.cmd.AddClientCommand;
import com.scg.net.cmd.AddConsultantCommand;
import com.scg.net.cmd.Command;
import com.scg.util.ListFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public class Assignment08 {

    /** the port number to use **/
    private static final int portNumber = 10888;

    /** the host name to use **/
    private static final String hostName = "localhost";

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("Assignment08");

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

        ListFactory.populateLists(accounts, consultants, timeCards);

        log.info("Creating invoice client and sending timecards through.");
        final InvoiceClient client = new InvoiceClient(hostName, portNumber, timeCards);

        log.info("Running client.");
        client.run();

        log.info("Shutting down client.");
        client.sendShutdown(hostName, portNumber);
    }

}
