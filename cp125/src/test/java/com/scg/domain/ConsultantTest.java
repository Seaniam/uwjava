package com.scg.domain;

import com.scg.util.Name;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Sean Carberry
 * @version 4
 * @since 1/20/15
 */
public class ConsultantTest {
    Consultant consultant, consultantTwo, consultantThree;

    @Before
    public void setUp() throws Exception {
        Name consultantName = new Name("Coyote", "Wyle", "E");
        consultant = new Consultant(consultantName);

        Name consultantTwoName = new Name("Wolfe", "Virginia");
        consultantTwo = new Consultant(consultantTwoName);

        Name consultantThreeName = new Name();
        consultantThree = new Consultant(consultantThreeName);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {
        assertEquals(consultant.getName().getFirstName(), "Wyle");
        assertEquals(consultant.getName().getMiddleName(), "E");
        assertEquals(consultant.getName().getLastName(), "Coyote");

        assertEquals(consultantTwo.getName().getFirstName(), "Virginia");
        assertEquals(consultantTwo.getName().getMiddleName(), "");
        assertEquals(consultantTwo.getName().getLastName(), "Wolfe");

        assertEquals(consultantThree.getName().getFirstName(), "");
        assertEquals(consultantThree.getName().getMiddleName(), "");
        assertEquals(consultantThree.getName().getLastName(), "");
    }

    @Test
    public void testToString() throws Exception {
        String comparison = "Coyote, Wyle E";
        assertEquals(consultant.toString(), comparison);

        comparison = "Wolfe, Virginia";
        assertEquals(consultantTwo.toString(), comparison);

        comparison = "";
        assertEquals(consultantThree.toString(), comparison);
    }

    @Test
    public void testCompareTo() throws Exception {
        assertEquals(consultant.compareTo(consultantTwo), -1);
        assertEquals(consultant.compareTo(consultantThree), 1);
        assertEquals(consultantTwo.compareTo(consultant), 1);
        assertEquals(consultantTwo.compareTo(consultantThree), 1);
        assertEquals(consultantThree.compareTo(consultant), -1);
        assertEquals(consultantThree.compareTo(consultantTwo), -1);

        assertEquals(consultant.compareTo(consultant), 0);
        assertEquals(consultantTwo.compareTo(consultantTwo), 0);
        assertEquals(consultantThree.compareTo(consultantThree), 0);
    }
}
