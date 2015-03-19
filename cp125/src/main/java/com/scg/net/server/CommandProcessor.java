package com.scg.net.server;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.domain.Invoice;
import com.scg.domain.TimeCard;
import com.scg.net.cmd.*;
import com.scg.util.DateRange;
import com.scg.util.TimeCardListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.midi.Receiver;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The command processor for the invoice server. Implements the receiver
 * role in the Command design pattern.
 *
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public final class CommandProcessor {

    /** the socket connection **/
    private final Socket connection;

    /** Client list **/
    private final List<ClientAccount> clientList;

    /** Consultant list **/
    private final List<Consultant> consultantList;

    /** TimeCard list **/
    private final List<TimeCard> timeCardList = new ArrayList<TimeCard>();

    /** the Invoice server **/
    private final InvoiceServer server;

    /** client list **/
//    private String outputDirectoryName = ".";

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("CommandProcessor");

    /**
     * Construct a CommandProcessor.
     * @param connection the Socket connecting the server to the client.
     * @param clientList the ClientList to add Clients to.
     * @param consultantList the ConsultantList to add Consultants to.
     * @param server the server that created this command processor
     */
    public CommandProcessor(Socket connection, List<ClientAccount> clientList, List<Consultant> consultantList, InvoiceServer server) {
        this.connection = connection;
        this.clientList = clientList;
        this.consultantList = consultantList;
        this.server = server;
    }

//    /**
//     * Set the output directory name.
//     * @param outPutDirectoryName the output directory name.
//     */
//    public void setOutPutDirectoryName(String outPutDirectoryName) {
//        this.outputDirectoryName = outPutDirectoryName;
//    }

    /**
     * Execute and AddTimeCardCommand.
     * @param command the command to execute.
     */
    public void execute(AddTimeCardCommand command) {
        log.info("Execute " + command);
        timeCardList.add(command.getTarget());
    }

    /**
     * Execute an AddClientCommand.
     * @param command the command to execute.
     */
    public void execute(AddClientCommand command) {
        log.info("Execute " + command);
        clientList.add(command.getTarget());
    }

    /**
     * Execute and AddConsultantCommand.
     * @param command the command to execute.
     */
    public void execute(AddConsultantCommand command) {
        log.info("Execute " + command);
        consultantList.add(command.getTarget());
    }

    /**
     * Execute a CreateInvoicesCommand.
     * @param command the command to execute.
     */
    public void execute(CreateInvoicesCommand command) {
        log.info("Execute " + command);
        Invoice invoce = null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(command.getTarget());

        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        List<Invoice> invoices = new ArrayList<Invoice>();
        List<TimeCard> timeCards = TimeCardListUtil.getTimeCardsForDateRange(timeCardList, new DateRange(month, year));

        String monthName = new SimpleDateFormat("MMMM").format(command.getTarget());

        for (ClientAccount account : clientList) {
            Invoice invoice = new Invoice(account, month, year);
            invoices.add(invoice);

            for (TimeCard currentTimeCard : timeCards) {
                invoice.extractLineItems(currentTimeCard);
            }
        }

        File dir = new File("Invoices");
        if (!dir.exists()) {
            if (dir.mkdir()) {
                log.info("Creating directory.");
            } else {
                log.info("Failed to create directory.");
            }
        }


        try {
            for (final Invoice invoice : invoices) {
                String clientName = invoice.getClientAccount().getName().replace(" ", "-");
                String fileName = dir + "/" + clientName + "-" + monthName + "-Invoice.txt";
                PrintWriter writer = new PrintWriter(fileName, "UTF-8");
                writer.println(invoice.toReportString());
                writer.close();
            }
        } catch (final IOException ex) {
            log.error("Unable to print invoice.", ex);
        }
    }

    /**
     * Execute a DisconnectCommand.
     * @param command the input DisconnectCommand.
     */
    public void execute(DisconnectCommand command) {
        try {
            connection.close();
        } catch (final IOException e) {
            log.debug("Exception during disconnect");
        }
    }

    /**
     * Execute a ShutdownCommand. Closes any current connections, stops listening
     * for connections and then terminates the server, without calling System.exit.
     * @param command the input ShutdownCommand.
     */
    public void execute(ShutdownCommand command) throws IOException {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.shutdown();
        }
    }
}
