package com.scg.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Sean Carberry
 * @version 4
 * @since 1/31/15
 */
public class AddressTest {
    Address testAddr;

    @Before
    public void setUp() throws Exception {
        testAddr = new Address("1234 Somewhere Street", "Portland", StateCode.OR, "98001");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetStreetNumber() throws Exception {
        String sn = testAddr.getStreetNumber();
        assertEquals(sn, "1234 Somewhere Street");
    }

    @Test
    public void testGetCity() throws Exception {
        String city = testAddr.getCity();
        assertEquals(city, "Portland");
    }

    @Test
    public void testGetState() throws Exception {
        String sc = testAddr.getState().toString();
        assertEquals(sc, "OR");
    }

    @Test
    public void testGetPostalCode() throws Exception {
        String zip = testAddr.getPostalCode();
        assertEquals(zip, "98001");
    }

    @Test
    public void testEquals() throws Exception {
        Address a = new Address("4567 Somewhere Street", "Salem", StateCode.OR, "98001");
        Address b = new Address("4567 Somewhere Street", "Salem", StateCode.OR, "98001");

        assertTrue(a.equals(b) && b.equals(a));
    }

    @Test
    public void testHashCode() throws Exception {
        Address c = new Address("4567 Somewhere Street", "Salem", StateCode.OR, "98001");
        Address d = new Address("4567 Somewhere Street", "Salem", StateCode.OR, "98001");

        assertTrue(c.hashCode() == d.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        String addrOutput = "1234 Somewhere Street\n" +
                            "Portland, OR 98001";
        assertEquals(testAddr.toString(), addrOutput);
    }
}
