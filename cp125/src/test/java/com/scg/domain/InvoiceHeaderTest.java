package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/**
 * @author Sean Carberry
 * @version 4
 * @since 1/31/15
 */
public class InvoiceHeaderTest {
    InvoiceHeader testHeader;
    Address testAddr;
    ClientAccount testClient;
    Name clientContact;

    @Before
    public void setUp() throws Exception {
        testAddr = new Address("1234", "Portland", StateCode.OR, "90023");
        clientContact = new Name("Greenshoes", "Joe");
        testClient = new ClientAccount("Fantastic Mr. Fox", clientContact, testAddr);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 20);
        cal.set(Calendar.YEAR, 2015);
        Date invDate = cal.getTime();
        Date invMonth = cal.getTime();
        testHeader = new InvoiceHeader("testBusiness", testAddr, testClient, invDate, invMonth);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        String headerOutput = "Invoice for:\n" +
                            "testBusiness\n" +
                            "1234\n" +
                            "Portland, OR 90023\n" +
                            "Greenshoes, Joe\n" +
                            "\n" +
                            "Invoice for Month of: January 2015\n" +
                            "Invoice Date: January 20, 2015\n";
        assertEquals(testHeader.toString(), headerOutput);
    }
}
