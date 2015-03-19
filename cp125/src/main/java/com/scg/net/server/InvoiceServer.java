package com.scg.net.server;

import com.scg.domain.ClientAccount;
import com.scg.domain.Consultant;
import com.scg.net.cmd.Command;
import com.scg.util.ListFactory;
import com.sun.corba.se.spi.activation.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.SocketHandler;

/**
 * The server for creation of account invoices based on time cards sent from the client.
 *
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public final class InvoiceServer {

    /** the port to listen on **/
    private static int port;

    /** the server socket to listen on **/
    private static ServerSocket server;

    /** the list of clients **/
    private static List<ClientAccount> clientList;

    /** the list of consultants **/
    private static List<Consultant> consultantList;

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("InvoiceServer");

    /**
     * Construct an InvoiceServer with a port.
     * @param port The port for this server to listen on
     * @param clientList the initial list of clients
     * @param consultantList the initial list of consultants
     */
    public InvoiceServer(int port, List<ClientAccount> clientList, List<Consultant> consultantList) throws IOException {
        this.port = port;
        this.clientList = clientList;
        this.consultantList = consultantList;
    }

    /**
     * Run this server, establishing connections, receiving commands,
     * and sending them to the CommandProcesser.
     */
    public void run() throws IOException {
        try {

            server = new ServerSocket(port);
            ObjectInputStream in = null;

            while (!server.isClosed()) {

                try(Socket serverClient = server.accept()) {
//                    client.shutdownInput();
                    in = new ObjectInputStream(serverClient.getInputStream());

                    CommandProcessor cp = new CommandProcessor(serverClient, clientList, consultantList, this);
//                    cp.setOutPutDirectoryName("server");

                    while(!serverClient.isClosed()) {
                        Object obj = in.readObject();

                        if(obj instanceof Command<?>) {
                            Command<?> command = (Command<?>) obj;
                            command.setReceiver(cp);
                            command.execute();
                        } else {
                            log.info("Input stream did not receive a proper command object.");
                        }
                    }
                } catch (final SocketException sx) {
                    log.debug("Socket exception:", sx);
                } catch (final IOException ex) {
                    log.debug("IO exception:", ex);
                } catch (final ClassNotFoundException ex) {
                    log.debug("Class not found exception:", ex);
                }

                shutdown();
            }
        } catch (final IOException ex) {
            log.debug("IO exception:", ex);
        } finally {

        }
    }

    /**
     * Shutdown the server.
     */
    void shutdown() throws IOException {
        try {
            if (!server.isClosed()) {
                server.close();
            }
        } catch (final IOException ex) {
            log.debug("Shutdown server:", ex);
        }

    }

}
