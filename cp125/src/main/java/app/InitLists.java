package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.util.ListFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Carberry
 * @version 5
 * @since 2/9/15
 */
public final class InitLists {

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("InitLists");

    /**
     * Prevent instantiation.
     */
    private InitLists() {
    }

    /**
     * The application method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        // Create Flists to be populated by factory
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);

        // serialize clients
        serializeClients(accounts);

        // serialize clients
        serializeTimeCards(timeCards);
    }

    /**
     * Serialize the client list
     * @param clients List of ClientAccounts.
     */
    public static void serializeClients(List<ClientAccount> clients) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("ClientList.ser"));
            out.writeObject(clients);
            out.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Serialize the timecard list
     * @param cards List of TimeCards.
     */
    public static void serializeTimeCards(List<TimeCard> cards) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("TimeCardList.ser"));
            out.writeObject(cards);
            out.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
