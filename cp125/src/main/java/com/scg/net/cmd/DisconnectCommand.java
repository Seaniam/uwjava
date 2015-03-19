package com.scg.net.cmd;

/**
 * The command to disconnect, this command has no target.
 *
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public final class DisconnectCommand extends AbstractCommand<Object> {

    private static final long serialVersionUID = -2148533327944546162L;

    /**
     * Construct an DisconnectCommand.
     */
    public DisconnectCommand() {
        super();
    }

    /**
     * Execute this Command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        getReceiver().execute(this);
    }
}
