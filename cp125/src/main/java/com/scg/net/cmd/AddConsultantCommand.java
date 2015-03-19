package com.scg.net.cmd;

import com.scg.domain.Consultant;

/**
 * The command to add a Consultant to a list maintained by the server.
 *
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public final class AddConsultantCommand extends AbstractCommand<Consultant> {

    private static final long serialVersionUID = 8570132430635422490L;

    /**
     * Construct an AddConsultantCommand with a target.
     * @param target the target of this Command.
     */
    public AddConsultantCommand(Consultant target) {
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
