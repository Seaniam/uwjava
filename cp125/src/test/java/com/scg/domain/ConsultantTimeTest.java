package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.Name;
import com.scg.util.StateCode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Sean Carberry
 * @version 4
 * @since 1/19/15
 */
public class ConsultantTimeTest {
    ConsultantTime consultantTime, otherConsultatntTime;

    Calendar calendar = new GregorianCalendar(2015, 0, 8);
    Date startDate = calendar.getTime();
    Date currentDay = startDate;

    final ClientAccount[] clients = {
            new ClientAccount("Beautiful Soup",
                    new Name("Turtle", "Mock"), new Address("1616 Index Ct.", "Redmond", StateCode.WA, "98055")),
            new ClientAccount("Croquet",
                    new Name("Des Coeurs", "Queen"), new Address("1616 Index Ct.", "Redmond", StateCode.WA, "98055"))
    };

    @Before
    public void setUp() throws Exception {
        consultantTime = new ConsultantTime(currentDay, clients[0], Skill.SOFTWARE_ENGINEER, 6);
        otherConsultatntTime = new ConsultantTime(currentDay, clients[1], Skill.SOFTWARE_TESTER, 4);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testEquals() throws Exception {
        ConsultantTime a = new ConsultantTime(currentDay, clients[0], Skill.SOFTWARE_ENGINEER, 6);
        ConsultantTime b = new ConsultantTime(currentDay, clients[0], Skill.SOFTWARE_ENGINEER, 6);
        assertTrue(a.equals(b) && b.equals(a));
    }

    @Test
    public void testHashCode() throws Exception {
        ConsultantTime c = new ConsultantTime(currentDay, clients[0], Skill.SOFTWARE_ENGINEER, 6);
        ConsultantTime d = new ConsultantTime(currentDay, clients[0], Skill.SOFTWARE_ENGINEER, 6);
        assertTrue(c.hashCode() == d.hashCode());
    }

    @Test
    public void testSetAccount() throws Exception {
        String accountName = consultantTime.getAccount().getName();
        assertEquals("Expect account to be set to 'Beautiful Soup'", accountName, "Beautiful Soup");

        consultantTime.setAccount(clients[1]);

        accountName = consultantTime.getAccount().getName();
        assertEquals("Expect account to now be set to 'Croquet'", accountName, "Croquet");
    }

    @Test
    public void testGetAccount() throws Exception {
        Account account = consultantTime.getAccount();
        String accountName = account.getName();
        boolean accountBillable = account.isBillable();

        assertEquals("Expect account to be set to 'Beautiful Soup'", accountName, "Beautiful Soup");
        assertTrue("Expect account to be billable", accountBillable);
    }

    @Test
    public void testSetDate() throws Exception {
        Date accountDate = consultantTime.getDate();
        Calendar newCalendar = new GregorianCalendar(2015, 2, 15);
        Date newStartDate = newCalendar.getTime();

        assertEquals("Expect account date to be start date.", accountDate, startDate);

        consultantTime.setDate(newStartDate);

        assertEquals("Expect account date to be new start date.", consultantTime.getDate(), newStartDate);
    }

    @Test
    public void testGetDate() throws Exception {
        Date accountDate = consultantTime.getDate();

        assertEquals("Expect account date to be start date.", accountDate, startDate);
    }

    @Test
    public void testSetSkillType() throws Exception {
        Skill skill = consultantTime.getSkillType();
        assertEquals("Expect consultant skill type to be 'Software Engineer'.", skill, Skill.SOFTWARE_ENGINEER);

        consultantTime.setSkillType(skill.UNKNOWN_SKILL);
        skill = consultantTime.getSkillType();

        assertEquals("Expect consultant skill type to be 'Unknown'.", skill, Skill.UNKNOWN_SKILL);
    }

    @Test
    public void testGetSkillType() throws Exception {
        assertEquals("Expect consultant skill type to be 'Software Engineer'.", consultantTime.getSkillType(), Skill.SOFTWARE_ENGINEER);
    }

    @Test
    public void testSetHours() throws Exception {
        int hours = consultantTime.getHours();
        assertEquals("Expect consultant hours to be 6.", hours, 6);

        consultantTime.setHours(8);

        assertEquals("Expect consultant hours to have been raised to 8.", consultantTime.getHours(), 8);
    }

    @Test
    public void testGetHours() throws Exception {
        assertEquals("Expect consultant hours to be 6.", consultantTime.getHours(), 6);
    }

    @Test
    public void testIsBillable() throws Exception {
        assertEquals("Expect consultant time to be billable.", consultantTime.isBillable(), true);
    }

    @Test
    public void testToString() throws Exception {
        Account account = consultantTime.getAccount();
        String compareCT = "ConsultantTime{date=Thu Jan 08 00:00:00 PST 2015, skillType=SOFTWARE_ENGINEER, hours=6, account="+ account +"}";
        assertEquals("Expect consultant time string output to include date, skill type, hours, and account.", consultantTime.toString(), compareCT);
    }
}
