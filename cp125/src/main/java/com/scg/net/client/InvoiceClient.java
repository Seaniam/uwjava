package com.scg.net.client;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.TimeCard;
import com.scg.net.cmd.AddClientCommand;
import com.scg.net.cmd.AddConsultantCommand;
import com.scg.net.cmd.AddTimeCardCommand;
import com.scg.net.cmd.CreateInvoicesCommand;
import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The client of the InvoiceServer.
 *
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public final class InvoiceClient {

    /** host name **/
    private final String host;
    /** port number **/
    private final int port;
    /** time card list **/
    private final List<TimeCard> timeCardList;

    /** The invoice month. */
    private static final int INVOICE_MONTH = Calendar.MARCH;
    /** The invoice year. */
    private static final int INVOICE_YEAR = 2006;

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("InvoiceClient");

    /**
     * Construct an InvoiceClient with a host and port for the server.
     * @param host the host for the server.
     * @param port the port for the server.
     * @param timeCardList the list of timeCards to send to the server.
     */
    public InvoiceClient(String host, int port, List<TimeCard> timeCardList) {
        this.host = host;
        this.port = port;
        this.timeCardList = timeCardList;
    }

    /**
     * Runs this InvoiceClient, sending clients, consultants, and time cards to the server,
     * then sending the command to create invoices for a specified month.
     */
    public void run() {
        Socket client;
        ObjectOutputStream out = null;

        try {
            client = new Socket(host, port);
//            inputStream = client.getInputStream();
            out = new ObjectOutputStream(client.getOutputStream());
            client.shutdownInput();

            sendClients(out);
            sendConsultants(out);
            sendTimeCards(out);
            createInvoices(out, INVOICE_MONTH, INVOICE_YEAR);
            sendDisconnect(out);
            client.shutdownOutput();
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Send the clients to the server.
     * @param out the output stream connecting this client to the server.
     */
    public void sendClients(ObjectOutputStream out) {
        // first client
        Name clientOneContact = new Name("Jingleheimerschmidt", "John", "Jacob");
        Address clientOneAddr = new Address("12345 Somewhere Street", "Boulder", StateCode.CO, "91234");
        ClientAccount clientOne = new ClientAccount("Super Account One", clientOneContact, clientOneAddr);
        AddClientCommand clientOneCommand = new AddClientCommand(clientOne);

        // second client
        Name clientTwoContact = new Name("James", "Jesse");
        Address clientTwoAddr = new Address("54321 Disney Road", "Annaheim", StateCode.CA, "90345");
        ClientAccount clientTwo = new ClientAccount("Super Account One", clientTwoContact, clientTwoAddr);
        AddClientCommand clientTwoCommand = new AddClientCommand(clientTwo);

        try {
            log.info("Sending clients");
            out.writeObject(clientOneCommand);
            out.writeObject(clientTwoCommand);
            log.info("Done sending clients");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send the consultants to the server.
     * @param out the output stream connecting this client to the server.
     */
    public void sendConsultants(ObjectOutputStream out) {
        // consultant 1
        Name c1Name = new Name("Potter", "Harry", "James");
        Consultant c1 = new Consultant(c1Name);
        AddConsultantCommand c1cmd = new AddConsultantCommand(c1);

        // consultant 2
        Name c2Name = new Name("Weasley", "Ronald");
        Consultant c2 = new Consultant(c2Name);
        AddConsultantCommand c2cmd = new AddConsultantCommand(c2);

        // consultant 3
        Name c3Name = new Name("Granger", "Hermione");
        Consultant c3 = new Consultant(c3Name);
        AddConsultantCommand c3cmd = new AddConsultantCommand(c3);

        try {
            log.info("Sending consultants");
            out.writeObject(c1cmd);
            out.writeObject(c2cmd);
            out.writeObject(c3cmd);
            log.info("Done sending consultants");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Send the time cards to the server.
     * @param out the output stream connecting this client to the server.
     */
    public void sendTimeCards(ObjectOutputStream out) {
        log.info("Sending timecards");
        for (TimeCard card : timeCardList) {
            AddTimeCardCommand tc = new AddTimeCardCommand(card);

            try {
                out.writeObject(tc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("Done sending timecards");
    }

    /**
     * Send the disconnect command to the server.
     * @param out the output stream connecting this client to the server.
     */
    public void sendDisconnect(OutputStream out) {
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send the command to the server to create invoices for the specified month.
     * @param out the output stream connecting this client to the server.
     * @param month the month to create invoice for.
     * @param year the year to create invoice for.
     */
    public void createInvoices(ObjectOutputStream out, int month, int year) {
        log.info("Sending the command to create invoices");

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        Date date = calendar.getTime();

        CreateInvoicesCommand createInvoicesCommand = new CreateInvoicesCommand(date);

        try {
            out.writeObject(createInvoicesCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("Done sending the command to create invoices");
    }

    /**
     * Send the quit command to the server.
     * @param host the host for the server.
     * @param port the port for the server.
     */
    public static void sendShutdown(String host, int port) {
        log.info("Sending the command to shut down");

    }

}
