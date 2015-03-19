package com.scg.net.cmd;

import java.util.Date;

/**
 * The command to create invoices for a specified month.
 *
 * @author Sean Carberry
 * @version 8
 * @since 3/10/15
 */
public final class CreateInvoicesCommand extends AbstractCommand<Date> {

    private static final long serialVersionUID = -3637040248762482120L;

    /**
     * Construct a CreateInvoicesCommand with a target month, which should be
     * obtained by getting the desired month constant from Calendar.
     * @param target the target month
     */
    public CreateInvoicesCommand(Date target) {
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
