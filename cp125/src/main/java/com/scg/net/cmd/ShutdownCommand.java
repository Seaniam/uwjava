package com.scg.net.cmd;

import java.io.IOException;

/**
 * This Command will cause the CommandProcessor to shutdown the server.
 *
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public final class ShutdownCommand extends AbstractCommand<Object> {
    // TODO: Void instead of Object?
    private static final long serialVersionUID = -7671502565041615667L;

    /**
     * Construct an ShutdownCommand.
     */
    public ShutdownCommand() {
        super();
    }

    /**
     * The method called by the receiver.
     * This method must be implemented by subclasses to send a
     * reference to themselves to the receiver by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        try {
            getReceiver().execute(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
