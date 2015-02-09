package com.scg.domain;

import com.scg.util.Name;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author Sean Carberry
 * @version 4
 * @since 1/31/15
 */
public class InvoiceLineItemTest {
    InvoiceLineItem testLineItem;

    @Before
    public void setUp() throws Exception {
        //InvoiceLineItem(Date date, Consultant consultant, Skill skill, int hours)
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DATE, 20);
        cal.set(Calendar.YEAR, 2015);
        Date itemDate = cal.getTime();

        Name consultantName = new Name("Doe", "John");
        Consultant itemConsultant = new Consultant(consultantName);

        testLineItem = new InvoiceLineItem(itemDate, itemConsultant, Skill.SOFTWARE_TESTER, 8);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetConsultant() throws Exception {
        Consultant consultant = testLineItem.getConsultant();
        assertEquals(consultant.getName().toString(), "Doe, John");
    }

    @Test
    public void testGetSkill() throws Exception {
        Skill skill = testLineItem.getSkill();
        assertEquals(skill.getSkillType().toString(), "Software Tester");
    }

    @Test
    public void testGetHours() throws Exception {
        int hours = testLineItem.getHours();
        assertEquals(hours, 8);
    }

    @Test
    public void testGetCharge() throws Exception {
        Skill skill = testLineItem.getSkill();
        int hours = testLineItem.getHours();
        int charge = skill.getRate() * hours;
        assertEquals(testLineItem.getCharge(), charge);
    }

    @Test
    public void testCurrencyFormat() throws Exception {
        String formatted = testLineItem.currencyFormat(1300);
        assertEquals(formatted, "$1,300.00");
    }

    @Test
    public void testToString() throws Exception {
        String lineItemOutput = "01/20/2015  Doe, John                    Software Tester          8     $800.00\n";
        assertEquals(testLineItem.toString(), lineItemOutput);
    }
}
