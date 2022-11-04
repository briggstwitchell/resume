/******
 Name: Briggs Twitchell
 Assignment: Lab 02
 Date: 9/27/2022
 File: EmployeeTest.java
 ******/
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * This file tests class tests the Employee class
 */
class EmployeeTest {
    Employee e1;
    Paycheck p1;
    Employee e2;
    Paycheck p2;

    @BeforeEach
    void setUp() {
        e1 = new Employee("Briggs Twitchell",10.0f);
        p1 = new Paycheck(e1);

        e2 = new Employee("Gary Cantrell",20.0f);
        p2 = new Paycheck(e1);
    }

    @Test
    void employeeConstructor() {
        double expectedPayRate = 20.0f;
        String expectedName = "Gary Cantrell";
        assertEquals(expectedPayRate, e2.getPayRate());
        assertEquals(expectedName, e2.getName());
    }

    @Test
    void addHoursWorked() {
        double expected = 10.0f;
        e1.addHoursWorked(10.0f);
        assertEquals(expected, e1.getHours());
    }

    @org.junit.jupiter.api.Test
    void addHoursWorkedErrorLow() {
        assertThrows(IllegalArgumentException.class,()->e1.addHoursWorked(-10.0f));
    }

    @org.junit.jupiter.api.Test
    void addHoursWorkedErrorHigh() {
        assertThrows(IllegalArgumentException.class,()->e1.addHoursWorked(170.0f));
    }
    @org.junit.jupiter.api.Test
    void resetHoursWorked() {
        double expected = 0.0f;
        e1.resetHoursWorked();
        assertEquals(expected, e1.getHours());
    }

    @org.junit.jupiter.api.Test
    void getHours() {
        double expected = 41.0f;
        e1.addHoursWorked(41.0f);
        assertEquals(expected, e1.getHours());
    }

    @org.junit.jupiter.api.Test
    void setPayRate() {
        double expected = 20.0f;
        e1.setPayRate(20.0f);
        assertEquals(expected, e1.getPayRate());
    }

    @org.junit.jupiter.api.Test
    void setPayTooLow() {
        assertThrows(IllegalArgumentException.class,()->e1.setPayRate(-1.0f));
    }

    @org.junit.jupiter.api.Test
    void getPayRate() {
        double expected = 10.0f;
        assertEquals(expected, e1.getPayRate());
    }

    @org.junit.jupiter.api.Test
    void getId() {
        int expected = 16;
        assertEquals(expected, e2.getId());
    }

    @org.junit.jupiter.api.Test
    void getName() {
        String expected = "Briggs Twitchell";
        assertEquals(expected, e1.getName());
    }

    @org.junit.jupiter.api.Test
    void getWeeklyCheck() {
        double expected = 100.0f;
        e1.addHoursWorked(10.0f);
        assertEquals(expected, e1.getWeeklyCheck().getTotalPay());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        String expected = "Name: Briggs Twitchell";
        assertEquals(expected, "Name: "+ e1.getName());
    }

    @org.junit.jupiter.api.Test
    void getNumEmployees() {
        int expected = 10;
        assertEquals(expected,Employee.getNumEmployees());
    }
}