package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.ListFactory;
import com.scg.util.Name;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Sean Carberry
 * @version 4
 * @since 1/31/15
 */
public class InvoiceTest {
    SimpleDateFormat INV_DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy");

    Invoice testInvoice;
    ClientAccount testClient;
    Name testContact;
    Address testAddr;
    Date startDate, endDate;
    final List<ClientAccount> accounts = new ArrayList<ClientAccount>();
    final List<Consultant> consultants = new ArrayList<Consultant>();
    final List<TimeCard> timeCards = new ArrayList<TimeCard>();

    @Before
    public void setUp() throws Exception {
        ListFactory.populateLists(accounts, consultants, timeCards);

        Calendar startCal = new GregorianCalendar(2006,2, 1);
        startCal.set(Calendar.HOUR_OF_DAY, startCal.getActualMinimum(Calendar.HOUR_OF_DAY));
        startCal.set(Calendar.MINUTE, startCal.getActualMinimum(Calendar.MINUTE));
        startCal.set(Calendar.SECOND, startCal.getActualMinimum(Calendar.SECOND));
        startCal.set(Calendar.MILLISECOND, startCal.getActualMinimum(Calendar.MILLISECOND));

        startDate = startCal.getTime();

        Calendar endCal = new GregorianCalendar(2006,2, 1);
        endCal.set(Calendar.DATE, endCal.getActualMaximum(Calendar.DATE));
        endCal.set(Calendar.HOUR_OF_DAY, endCal.getActualMaximum(Calendar.HOUR_OF_DAY));
        endCal.set(Calendar.MINUTE, endCal.getActualMaximum(Calendar.MINUTE));
        endCal.set(Calendar.SECOND, endCal.getActualMaximum(Calendar.SECOND));
        endCal.set(Calendar.MILLISECOND, endCal.getActualMaximum(Calendar.MILLISECOND));

        endDate = endCal.getTime();

        ClientAccount testAccount = accounts.get(1);
        testContact = timeCards.get(0).getConsultant().getName();
        testAddr = testAccount.getAddress();
        testClient = new ClientAccount(testAccount.getName(), testContact, testAddr);

        testInvoice = new Invoice(testClient, startCal.MARCH, 2006);

        testInvoice.extractLineItems(timeCards.get(2));
    }

    @After
    public void tearDown() throws Exception {

    }

//    @Test
//    public void testGetStartDate() throws Exception {
//        Date invStart = testInvoice.getStartDate();
//        assertEquals(invStart, startDate);
//    }
//
//    @Test
//    public void testGetEndDate() throws Exception {
//        Date invEnd = testInvoice.getEndDate();
//        assertEquals(invEnd, endDate);
//    }

    @Test
    public void testGetInvoiceMonth() throws Exception {
        int invMonth = testInvoice.getInvoiceMonth();
        assertEquals(invMonth, 2);
    }

    @Test
    public void testGetClientAccount() throws Exception {
        ClientAccount invClient = testInvoice.getClientAccount();
        assertTrue(invClient.getName().equals(testClient.getName()));
        assertTrue(invClient.getAddress().equals(testClient.getAddress()));
        assertTrue(invClient.getContact().equals(testClient.getContact()));
    }

    @Test
    public void testGetTotalHours() throws Exception {
        int invTotalHours = testInvoice.getTotalHours();
        assertEquals(invTotalHours, 40);
    }

    @Test
    public void testGetTotalCharges() throws Exception {
        int invTotalCharges = testInvoice.getTotalCharges();
        assertEquals(invTotalCharges, 8000);
    }

    @Test
    public void testExtractLineItems() throws Exception {
        // TODO: Fix this test, it isn't easily testable
        // assertEquals(testInvoice.getInvoiceLineItems().size(), 5);
    }

    @Test
    public void testGetLetterHead() throws Exception {
        String lh = "The Small Consulting Group\n" +
                    "1616 Index Ct.\n" +
                    "Renton, WA 98058";
        assertEquals(testInvoice.getLetterHead(), lh);
    }

    @Test
    public void testToString() throws Exception {
        String stringOutput = "Invoice{client=" + testInvoice.getClientAccount() + ", invoiceMonth=2, invoiceYear=2006}";
        assertEquals(testInvoice.toString(), stringOutput);
    }

    @Test
    public void testGetInvoiceHead() throws Exception {
        // TODO: this seems brittle due to invoice date output (maybe refactor method?)
        String invoiceHeadOutput = "The Small Consulting Group\n" +
                                    "1616 Index Ct.\n" +
                                    "Renton, WA 98058\n" +
                                    "\n" +
                                    "Invoice for:\n" +
                                    "FooBar Enterprises\n" +
                                    "1024 Kilobyte Dr.\n" +
                                    "Silicone Gulch, CA 94105\n" +
                                    "Coder, Carl\n" +
                                    "\n" +
                                    "Invoice for Month of: March 2006\n" +
                                    "Invoice Date: " + INV_DATE_FORMAT.format(new Date()) + "\n";
        assertEquals(testInvoice.getInvoiceHead(), invoiceHeadOutput);
    }

    @Test
    public void testGetLineColumns() throws Exception {
        String lineColOutput = "\n" +
                                "Date        Consultant                   Skill                Hours  Charge\n" +
                                "----------  ---------------------------  ------------------   -----  ----------\n";
        assertEquals(testInvoice.getLineColumns(), lineColOutput);
    }

    @Test
    public void testGetFinalInvoiceCharges() throws Exception {
        String finalChargeOutput = "\n" +
                                    "Total:                                                           40   $8,000.00\n";
        assertEquals(testInvoice.getFinalInvoiceCharges(), finalChargeOutput);
    }

    @Test
    public void testToReportString() throws Exception {
        String reportOutput = "The Small Consulting Group\n" +
                                "1616 Index Ct.\n" +
                                "Renton, WA 98058\n" +
                                "\n" +
                                "Invoice for:\n" +
                                "FooBar Enterprises\n" +
                                "1024 Kilobyte Dr.\n" +
                                "Silicone Gulch, CA 94105\n" +
                                "Coder, Carl\n" +
                                "\n" +
                                "Invoice for Month of: March 2006\n" +
                                "Invoice Date: "+ INV_DATE_FORMAT.format(new Date()) +"\n" +
                                "\n" +
                                "Date        Consultant                   Skill                Hours  Charge\n" +
                                "----------  ---------------------------  ------------------   -----  ----------\n" +
                                "03/06/2006  Architect, Ann S.            System Architect         8   $1,600.00\n" +
                                "03/08/2006  Architect, Ann S.            System Architect         8   $1,600.00\n" +
                                "03/09/2006  Architect, Ann S.            System Architect         8   $1,600.00\n" +
                                "03/10/2006  Architect, Ann S.            System Architect         8   $1,600.00\n" +
                                "03/11/2006  Architect, Ann S.            System Architect         8   $1,600.00\n" +
                                "\n" +
                                "Total:                                                           40   $8,000.00\n" +
                                "\n" +
                                "\n" +
                                "The Small Consulting Group                                            Page:   1\n" +
                                "===============================================================================\n" +
                                "\n";
        assertEquals(testInvoice.toReportString(), reportOutput);
    }
}
