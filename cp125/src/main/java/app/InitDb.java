package app;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.persistent.DbServer;
import com.scg.util.ListFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sean Carberry
 * @version 7
 * @since 3/3/15
 */
public final class InitDb {

    /**
     * Entry point.
     *
     * @param args not used.
     * @throws Exception if anything goes awry.
     */
    public static void main(String[] args) throws Exception {
        final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
        final List<Consultant> consultants = new ArrayList<Consultant>();
        final List<TimeCard> timeCards = new ArrayList<TimeCard>();
        ListFactory.populateLists(accounts, consultants, timeCards);

        DbServer dbServer = new DbServer("jdbc:derby://localhost:1527/memory:scgDb", "student", "student");

        for(ClientAccount account : accounts) {
            dbServer.addClient(account);
        }

        for(Consultant consultant : consultants) {
            dbServer.addConsultant(consultant);
        }

        for(TimeCard timeCard : timeCards) {
            dbServer.addTimeCard(timeCard);
        }

    }

}
