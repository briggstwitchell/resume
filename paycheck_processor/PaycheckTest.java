/******
 Name: Briggs Twitchell
 Assignment: Lab 02
 Date: 9/27/2022
 File: PaycheckTest.java
 ******/
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This file tests class tests the Paycheck class
 */
class PaycheckTest {
    Paycheck p1;
    Employee e1;
    Paycheck p2;

    Employee e3;

    @BeforeEach
    void setUp(){
        e1 = new Employee("Briggs Twitchell",10.0f);
        e1.addHoursWorked(10.0f);
        p1 = new Paycheck(e1);
        p2 = new Paycheck("Gary Cantrell",123,20.0f,20.0f);

        e3 = new Employee("John",10.0f);
        e3.addHoursWorked(50);
        e3.getWeeklyCheck();
    }

    @Test
    void oneArgPaycheckConstructor() {
        String expectedName = "Briggs Twitchell";
        double expectedPayRate = 10.0f;
        assertEquals(expectedName,p1.getName());
        assertEquals(expectedPayRate,p1.getRate());
    }

    @Test
    void twoArgPaycheckConstructor() {
        String expectedName = "Gary Cantrell";
        double expectedPayRate = 20.0f;
        assertEquals(expectedName,p2.getName());
        assertEquals(expectedPayRate,p2.getRate());
    }

    @Test
    void getTotalPay() {
        double expected = 100.0f;
        assertEquals(expected,p1.getTotalPay());
    }

    @Test
    void getName() {
        String expected = "Briggs Twitchell";
        assertEquals(expected, p1.getName());
    }

    @Test
    void getRate() {
        double expected = 10.0f;
        assertEquals(expected,p1.getRate());
    }

    @Test
    void getHours() {
        double expected = 20.0f;
        assertEquals(expected,p2.getHours());
    }

    @Test
    void testToString() {
        String expected = "Total pay: $400.00";
        assertEquals(expected,p2.toString());
    }

    @Test
    void getOverTimePay() {
        double expected = 150;
        assertEquals(expected,e3.getWeeklyCheck().getTotalPay()-40*10);
    }

    @Test
    void getId() {
        //e3.addHoursWorked(50);
        int expected = 10;
        assertEquals(expected,e3.getId());
    }
}