package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Sean Carberry
 * @version 4
 * @since 1/20/15
 */
public class ClientAccountTest {
    ClientAccount clientAccount, clientAccountTwo;

    @Before
    public void setUp() throws Exception {
        clientAccount =  new ClientAccount("Walrus & Carpenter", new Name("Turtle", "Mock"), new Address("1616 Index Ct.", "Redmond", StateCode.WA, "98055"));
        clientAccountTwo = new ClientAccount("Rise Up Bakery", new Name("Carberry", "Sean", "F"), new Address("1616 Index Ct.", "Redmond", StateCode.WA, "98055"));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetContact() throws Exception {
        String acctContact = clientAccount.getContact().toString();
        assertEquals(acctContact, "Turtle, Mock");

        acctContact = clientAccountTwo.getContact().toString();
        assertEquals(acctContact, "Carberry, Sean F");
    }

    @Test
    public void testSetContact() throws Exception {
        String acctContact = clientAccount.getContact().toString();
        assertEquals(acctContact, "Turtle, Mock");

        clientAccount.setContact(new Name("Hemlock", "Nigel", "Forteus"));

        acctContact = clientAccount.getContact().toString();
        assertEquals(acctContact, "Hemlock, Nigel Forteus");

    }

    @Test
    public void testGetName() throws Exception {
        String accountName = clientAccountTwo.getName();
        assertEquals(accountName, "Rise Up Bakery");

        accountName = clientAccount.getName();
        assertEquals(accountName, "Walrus & Carpenter");
    }

    @Test
    public void testIsBillable() throws Exception {
        assertTrue(clientAccount.isBillable());
        assertTrue(clientAccountTwo.isBillable());
    }

    @Test
    public void testCompare () throws Exception {
        assertEquals(clientAccount.compareTo(clientAccountTwo), 1);
        assertEquals(clientAccountTwo.compareTo(clientAccount), -1);

        assertEquals(clientAccount.compareTo(clientAccount), 0);
        assertEquals(clientAccountTwo.compareTo(clientAccountTwo), 0);
    }
}
