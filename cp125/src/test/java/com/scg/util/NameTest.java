package com.scg.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * @author Sean Carberry
 * @version 4
 * @since 1/20/15
 */
public class NameTest {
    Name noName, twoName, threeName;

    @Before
    public void setUp() throws Exception {
        noName = new Name();
        twoName = new Name("Testre", "Tina");
        threeName = new Name("Tester", "Tyler", "Threename");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetFirstName() throws Exception {
        assertEquals(noName.getFirstName(), "");
        assertEquals(twoName.getFirstName(), "Tina");
        assertEquals(threeName.getFirstName(), "Tyler");
    }

    @Test
    public void testSetFirstName() throws Exception {
        noName.setFirstName("Tricky");
        assertEquals(noName.getFirstName(), "Tricky");
    }

    @Test
    public void testGetMiddleName() throws Exception {
        assertEquals(noName.getMiddleName(), "");
        assertEquals(twoName.getMiddleName(), "");
        assertEquals(threeName.getMiddleName(), "Threename");
    }

    @Test
    public void testSetMiddleName() throws Exception {
        noName.setMiddleName("Tongue");
        twoName.setMiddleName("Twoname");
        assertEquals(noName.getMiddleName(), "Tongue");
        assertEquals(twoName.getMiddleName(), "Twoname");
    }

    @Test
    public void testGetLastName() throws Exception {
        assertEquals(noName.getLastName(), "");
        assertEquals(twoName.getLastName(), "Testre");
        assertEquals(threeName.getLastName(), "Tester");
    }

    @Test
    public void testSetLastName() throws Exception {
        noName.setLastName("Twister");
        assertEquals(noName.getLastName(), "Twister");
    }

    @Test
    public void testEquals() throws Exception {
        Name a = new Name("Smiley", "Travis");
        Name b = new Name("Smiley", "Travis");
        assertTrue(a.equals(b) && b.equals(a));
    }

    @Test
    public void testHashCode() throws Exception {
        Name c = new Name("Smiley", "Travis");
        Name d = new Name("Smiley", "Travis");
        assertTrue(c.hashCode() == d.hashCode());
    }

    @Test
    public void testToString() throws Exception {
        String testNameComparison = "Testre, Tina";
        String testName = twoName.toString();
        assertEquals(testName, testNameComparison);
    }
}
