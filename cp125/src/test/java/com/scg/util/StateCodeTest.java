package com.scg.util;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Sean Carberry
 * @version 4
 * @since 1/31/15
 */
public class StateCodeTest {
    @Test
    public void testGetStateCode() throws Exception {
        assertEquals(StateCode.CA.getStateCode(), "California");
        assertEquals(StateCode.WA.getStateCode(), "Washington");
    }
}
