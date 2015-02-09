package com.scg.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Sean Carberry
 * @version 4
 * @since 1/21/15
 */
public class NonBillableAccountTest {
    @Test
    public void testToString() throws Exception {
        assertEquals("Expect 'Sick Leave' to be returned as a string value.", NonBillableAccount.SICK_LEAVE.toString(), "Sick Leave");
        assertEquals("Expect 'Vacation' to be returned as a string value.", NonBillableAccount.VACATION.toString(), "Vacation");
        assertEquals("Expect 'Business Development' to be returned as a string value.", NonBillableAccount.BUSINESS_DEVELOPMENT.toString(), "Business Development");
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Expect 'Sick Leave' to be returned as a string value.", NonBillableAccount.SICK_LEAVE.getName(), "Sick Leave");
        assertEquals("Expect 'Vacation' to be returned as a string value.", NonBillableAccount.VACATION.getName(), "Vacation");
        assertEquals("Expect 'Business Development' to be returned as a string value.", NonBillableAccount.BUSINESS_DEVELOPMENT.getName(), "Business Development");
    }

    @Test
    public void testValueOf() throws Exception {
        assertTrue("Expect 'BUSINESS_DEVELOPMENT' enum constant to be returned as string value.", NonBillableAccount.valueOf("BUSINESS_DEVELOPMENT").toString().equals("Business Development"));
        assertTrue("Expect 'VACATION' enum constant to be returned as string value.", NonBillableAccount.valueOf("VACATION").toString().equals("Vacation"));
        assertTrue("Expect 'SICK_LEAVE' enum constant to be returned as string value.", NonBillableAccount.valueOf("SICK_LEAVE").toString().equals("Sick Leave"));
    }

    @Test
    public void testValues() throws Exception {
        NonBillableAccount[] types = NonBillableAccount.values();
        assertTrue(types[0].toString().equals("Sick Leave"));
        assertTrue(types[1].toString().equals("Vacation"));
        assertTrue(types[2].toString().equals("Business Development"));
    }

    @Test
    public void testIsBillable() throws Exception {
        assertFalse(NonBillableAccount.BUSINESS_DEVELOPMENT.isBillable());
        assertFalse(NonBillableAccount.VACATION.isBillable());
        assertFalse(NonBillableAccount.SICK_LEAVE.isBillable());
    }
}
