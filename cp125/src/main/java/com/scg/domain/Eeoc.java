package com.scg.domain;

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

    /**
     * Simply prints a message indicating that the consultant quit.
     * @param evt the termination event.
     */
    @Override
    public void voluntaryTermination(TerminationEvent evt) {
        this.voluntaryTerminationCount += 1;
    }

    /**
     * Simply prints a message indicating that the consultant was fired.
     * @param evt the termination event.
     */
    @Override
    public void forcedTermination(TerminationEvent evt) {
        this.forcedTerminationCount += 1;
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
