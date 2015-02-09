package com.scg.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Sean Carberry
 * @version 4
 * @since 1/31/15
 */
public class InvoiceFooterTest {
    InvoiceFooter testFooter;

    @Before
    public void setUp() throws Exception {
        testFooter = new InvoiceFooter("testBusiness");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testIncrementPageNumber() throws Exception {
        String footerOutput = "\n\ntestBusiness                                                          Page:   1\n===============================================================================\n\n";
        assertEquals(testFooter.toString(), footerOutput);
        testFooter.incrementPageNumber();
        footerOutput = "\n\ntestBusiness                                                          Page:   3\n===============================================================================\n\n";
        assertEquals(testFooter.toString(), footerOutput);
    }

    @Test
    public void testToString() throws Exception {
        String footerOutput = "\n\ntestBusiness                                                          Page:   1\n===============================================================================\n\n";
        assertEquals(testFooter.toString(), footerOutput);
    }
}
