package com.scg.net.cmd;

import com.scg.domain.TimeCard;

import java.sql.Time;

/**
 * Command that adds a TimeCard to the server's list of TimeCards.
 *
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public final class AddTimeCardCommand extends AbstractCommand<TimeCard> {

    private static final long serialVersionUID = -2481920279800353353L;

    /**
     * Construct an AddTimeCardCommand with a target.
     * @param target the target of this Command.
     */
    public AddTimeCardCommand(TimeCard target) {
        super(target);
    }

    /**
     * Execute this command by calling receiver.execute(this).
     */
    @Override
    public void execute() {
        getReceiver().execute(this);
    }
}
