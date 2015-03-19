package com.scg.net.cmd;

import com.scg.domain.ClientAccount;
import com.scg.net.server.CommandProcessor;

/**
 * The command to add a Client to a list maintained by the server.
 *
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public final class AddClientCommand extends AbstractCommand<ClientAccount> {

    private static final long serialVersionUID = 4785849494627552627L;

    //TODO: Use this pattern in all the other add classes.
    /**
     * Construct an AddClientCommand with a target.
     * @param target The target of this Command.
     */
    public AddClientCommand(ClientAccount target) {
        super(target);
    }

    /**
     * Execute this Command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        getReceiver().execute(this);
    }
}
