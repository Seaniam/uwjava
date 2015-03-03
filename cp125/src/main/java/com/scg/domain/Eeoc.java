package com.scg.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The EEOC monitors SCG's terminations.
 *
 * @author Sean Carberry
 * @version 6
 * @since 2/20/15
 */
public final class Eeoc implements TerminationListener {

    private int forcedTerminationCount = 0;
    private int voluntaryTerminationCount = 0;

    /** This class' logger. */
    private static final Logger log = LoggerFactory.getLogger("Termination");

    /**
     * Simply prints a message indicating that the consultant quit.
     * @param evt the termination event.
     */
    @Override
    public void voluntaryTermination(TerminationEvent evt) {
        this.voluntaryTerminationCount += 1;
        log.info(evt.getConsultant().getName().toString() + " quit.");
    }

    /**
     * Simply prints a message indicating that the consultant was fired.
     * @param evt the termination event.
     */
    @Override
    public void forcedTermination(TerminationEvent evt) {
        this.forcedTerminationCount += 1;
        log.info(evt.getConsultant().getName().toString() + " was fired.");
    }

    /**
     * Gets the forced termination count.
     * @return the forced termination count.
     */
    public int forcedTerminationCount() {
        return this.forcedTerminationCount;
    }

    /**
     * Gets the voluntary termination count.
     * @return the voluntary termination count.
     */
    public int voluntaryTerminationCount() {
        return this.voluntaryTerminationCount;
    }

}
