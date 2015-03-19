package com.scg.net.cmd;

import com.scg.net.server.CommandProcessor;

import java.io.Serializable;

/**
 * The superclass of all Command objects, implements the command role in
 * the Command design pattern.
 *
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public interface Command<T> extends Serializable {
    /**
     * The method called by the receiver.
     * This method must be implemented by subclasses to send a reference
     * to themselves to the receiver by calling receiver.execute(this).
     */
    void execute();

    /**
     * Return the target of this Command.
     * @return the target.
     */
    T getTarget();

    /**
     * Set the CommandProcessor that will execute this Command.
     * @param receiver the receiver for this Command.
     */
    void setReceiver(CommandProcessor receiver);

    /**
     * Gets the CommandProcessor receiver for this Command.
     * @return the receiver for this Command.
     */
    CommandProcessor getReceiver();

}
