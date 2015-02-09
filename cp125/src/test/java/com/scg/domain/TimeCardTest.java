package com.scg.domain;

import com.scg.util.Address;
import com.scg.util.ListFactory;
import com.scg.util.Name;
import com.scg.util.StateCode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Sean Carberry
 * @version 4
 * @since 1/19/15
 */
public class TimeCardTest {
    TimeCard timeCard = null;
    final Consultant programmer = new Consultant(new Name("Tester", "Tom"));
    Calendar calendar = new GregorianCalendar(2015, 0, 8);
    Date startDate = calendar.getTime();
    final ClientAccount[] clients = {
            new ClientAccount("Beautiful Soup",
                    new Name("Turtle", "Mock"), new Address("1616 Index Ct.", "Redmond", StateCode.WA, "98055")),
            new ClientAccount("Croquet",
                    new Name("Des Coeurs", "Queen"), new Address("1616 Index Ct.", "Redmond", StateCode.WA, "98055"))
    };

    Date currentDay = startDate;

    @Before
    public void setUp() throws Exception {
        timeCard = new TimeCard(programmer, startDate);

        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[0],
                Skill.SOFTWARE_TESTER, 8));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[0],
                Skill.SOFTWARE_TESTER, 8));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[1],
                Skill.SOFTWARE_TESTER, 8));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[1],
                Skill.SOFTWARE_TESTER, 8));
        calendar.add(Calendar.DATE, 1);
        currentDay = calendar.getTime();
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[1],
                Skill.SOFTWARE_TESTER, 8));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testAddConsultantTime() throws Exception {
        assertEquals(timeCard.getBillableHoursForClient("Beautiful Soup").size(), 2);
        assertEquals(timeCard.getBillableHoursForClient("Croquet").size(), 3);

        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[0],
                Skill.SOFTWARE_TESTER, 6));
        timeCard.addConsultantTime(new ConsultantTime(currentDay, clients[1],
                Skill.SOFTWARE_TESTER, 2));

        assertEquals(timeCard.getBillableHoursForClient("Beautiful Soup").size(), 3);
        assertEquals(timeCard.getBillableHoursForClient("Croquet").size(), 4);
    }

    @Test
    public void testGetBillableHoursForClient() throws Exception {
        List<ConsultantTime> billableHours = timeCard.getBillableHoursForClient("Beautiful Soup");
        ConsultantTime bs = billableHours.get(0);

        assertEquals("Expect date started to be current day.", bs.getDate(), startDate);
        assertEquals("Expect skill type to be software engineer", bs.getSkillType(), Skill.SOFTWARE_TESTER);
        assertEquals("Expect hours worked to be 8", bs.getHours(), 8);
        assertEquals("Expect skill type to be software engineer", bs.getSkillType(), Skill.SOFTWARE_TESTER);
        assertEquals("Expect account to be Beautiful Soup", bs.getAccount().getName(), "Beautiful Soup");
    }

    @Test
    public void testGetConsultant() throws Exception {
        Consultant consultant = timeCard.getConsultant();
        assertEquals("Expect consultant's first name to be Tom", consultant.getName().getFirstName(), "Tom");
        assertEquals("Expect consultant's middle name to be Null", consultant.getName().getMiddleName(), null);
        assertEquals("Expect consultant's last name to be Tester", consultant.getName().getLastName(), "Tester");
    }

    @Test
    public void testGetTotalBillableHours() throws Exception {
        int totalBillable = timeCard.getTotalBillableHours();
        assertEquals("Expect total billable hours to be 40", totalBillable, 40);
    }

    @Test
    public void testGetTotalHours() throws Exception {
        int totalHours = timeCard.getTotalHours();
        assertEquals("Expect total hours to be 40", totalHours, 40);
    }

    @Test
    public void testGetTotalNonBillableHours() throws Exception {
        int totalNonBillable = timeCard.getTotalNonBillableHours();
        assertEquals("Expect total non-billable hours to be 0", totalNonBillable, 0);
    }

    @Test
    public void testGetWeekStartingDay() throws Exception {
        Date sd = timeCard.getWeekStartingDay();
        assertEquals("Expect week starting day to be same as start date entered", sd, startDate);
    }

    @Test
    public void testCompare () throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2007, Calendar.FEBRUARY, 4, 2, 2, 0);
        Consultant programmer = new Consultant(new Name("Coder", "Carl"));
        Consultant systemAnalyst = new Consultant(new Name("Architect", "Ann", "S."));

        TimeCard timeCard1 = new TimeCard(programmer, startDate);

        calendar.set(2007, Calendar.FEBRUARY, 18, 2, 2, 0);
        Date startDate = calendar.getTime();
        TimeCard timeCard2 = new TimeCard(systemAnalyst, startDate);

        calendar.set(2007, Calendar.FEBRUARY, 11, 2, 2, 0);
        startDate = calendar.getTime();
        TimeCard timeCard3 = new TimeCard(programmer, startDate);

        assertEquals(timeCard1.compareTo(timeCard2), -1);
        assertEquals(timeCard1.compareTo(timeCard3), -1);
        assertEquals(timeCard2.compareTo(timeCard1), 1);
        assertEquals(timeCard2.compareTo(timeCard3), -1);
        assertEquals(timeCard3.compareTo(timeCard1), 1);
        assertEquals(timeCard3.compareTo(timeCard2), 1);
        assertEquals(timeCard1.compareTo(timeCard1), 0);
        assertEquals(timeCard2.compareTo(timeCard2), 0);
        assertEquals(timeCard3.compareTo(timeCard3), 0);
    }

    @Test
    public void testToReportString() throws Exception {
        String report = timeCard.toReportString();
        String comparison = "====================================================================\n" +
                "Consultant: Tester, Tom                  Week Starting: Jan 08, 2015\n" +
                "\n" +
                "Billable Time:\n" +
                "Account                      Date        Hours  Skill               \n" +
                "---------------------------  ----------  -----  --------------------\n" +
                "Beautiful Soup               01/08/2015      8  Software Tester     \n" +
                "Beautiful Soup               01/09/2015      8  Software Tester     \n" +
                "Croquet                      01/10/2015      8  Software Tester     \n" +
                "Croquet                      01/11/2015      8  Software Tester     \n" +
                "Croquet                      01/12/2015      8  Software Tester     \n" +
                "\n" +
                "Non-billable Time:\n" +
                "Account                      Date        Hours  Skill               \n" +
                "---------------------------  ----------  -----  --------------------\n" +
                "\n" +
                "Summary:\n" +
                "Total Billable:                             40\n" +
                "Total Non-billable:                          0\n" +
                "Total Hours:                                40\n" +
                "====================================================================\n";
        assertEquals("Expect report string to be formatted correctly", report, comparison);
    }

    @Test
    public void testToString() throws Exception {
        String tcString = timeCard.toString();
        String comparison = "TimeCard{consultant=Tester, Tom, weekStartingDay=Thu Jan 08 00:00:00 PST 2015}";
        assertEquals("Expect object to string to be set with consultant and week starting day", tcString, comparison);
    }
}
