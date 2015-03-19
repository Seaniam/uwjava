package com.scg.net.cmd;

import com.scg.net.server.CommandProcessor;

/**
 * The superclass of all Command objects, implements the command role
 * in the Command design pattern.
 *
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public abstract class AbstractCommand<T> implements Command<T> {

    private static final long serialVersionUID = 4199692218771758071L;

    private transient CommandProcessor receiver;
    private T target = null;

    /**
     * Construct an AbstractCommand without a target; called from subclasses.
     */
    public AbstractCommand() {}

    /**
     * Construct an AbstractCommand with a target; called from subclasses.
     * @param target the target
     */
    public AbstractCommand(T target) {
        this.target = target;
    }

    /**
     * Gets the CommandProcessor receiver for this Command.
     * @return the receiver for this Command.
     */
    public final CommandProcessor getReceiver() {
        return receiver;
    }

    /**
     * Set the CommandProcessor that will execute this Command.
     * @param receiver the receiver for this Command.
     */
    public final void setReceiver(CommandProcessor receiver) {
        this.receiver = receiver;
    }

    /**
     * Return the target of this Command.
     * @return the target.
     */
    public final T getTarget() {
        return target;
    }

    /**
     * A string representation of this command.
     * @return the command in string format.
     */
    public String toString() {
        return this.getClass().getSimpleName() + ", target " + target;
    }


}
