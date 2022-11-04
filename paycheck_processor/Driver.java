/******
 Name: Briggs Twitchell
 Assignment: Lab 02
 Date: 9/27/2022
 File: Driver.java
 ******/

public class Driver {
    /**
     * This driver demonstrates the functionality of the Employee and Paycheck classes
     */
    public static void main(String[] args){

        Employee e1 = new Employee("Jill",23.0f);
        Employee e2 = new Employee("John",18.23f);
        Employee e3 = new Employee("Eban",22.78f);

        //formatting the list of employees for the demo
        String line = "===============================================================\n";
        String line2 = "===============================================================";
        String listOfEEs1 = line+e1.toString2()+line+e2.toString2()+line+e3.toString2();

        System.out.println("1) Demonstrating constructors:");
        System.out.println(listOfEEs1);

        //adding 10 hours to demonstrate their total pay to be their rate x 10
        double hours1 = 10.0f;
        e1.addHoursWorked(hours1);
        e2.addHoursWorked(hours1);
        e3.addHoursWorked(hours1);

        String listOfEEs2 = line+e1.toString2()+line+e2.toString2()+line+e3.toString2();

        System.out.println(line2);
        System.out.println("2) Demonstrating updating of hours but no pay since no checks were generated:");
        System.out.println(listOfEEs2);

        //adding 40 hours to demonstrate that overtime pay will be factored in
        double hours2 = 40.0f;
        e1.addHoursWorked(hours2);
        e1.getWeeklyCheck();
        e2.addHoursWorked(hours2);
        e2.getWeeklyCheck();
        e3.addHoursWorked(hours2);
        e3.getWeeklyCheck();

        String listOfEEs3 = line+e1.toString2()+line+e2.toString2()+line+e3.toString2();

        System.out.println(line2);
        System.out.println("3) Demonstrating updating of hours and output of pay with overtime since checks were generated:");
        System.out.println(listOfEEs3);

        //resetting hours to 0 and generate
        e1.resetHoursWorked();
        e2.resetHoursWorked();
        e3.resetHoursWorked();

        String listOfEEs4 = line+e1.toString2()+line+e2.toString2()+line+e3.toString2();

        System.out.println(line2);
        System.out.println("4) Demonstrating reset of hours and printing of most recent pay:");
        System.out.println(listOfEEs4);

        //Demonstrating input of negative hours, which should generate an error
        try{
            double hours3 = -40.0f;
            e1.addHoursWorked(hours3);
            e2.addHoursWorked(hours3);
            e3.addHoursWorked(hours3);

            String listOfEEs5 = line+e1.toString2()+line+e2.toString2()+line+e3.toString2();

            System.out.println(line2);
            System.out.println("5) This line should not print because an error was thrown");
            System.out.println(listOfEEs5);
        }
        catch (IllegalArgumentException e){
            System.out.println(line2);
            System.out.println("5) This line should print because an error was thrown");
            System.out.println("Error = "+e);
            System.out.println(line2);

        }
        catch (Exception e){}

        //showing new paycheck amounts based on a new set of hours
        double hours4 = 10.0f;
        e1.addHoursWorked(hours4);
        e1.getWeeklyCheck();
        e2.addHoursWorked(hours4);
        e2.getWeeklyCheck();
        e3.addHoursWorked(hours4);
        e3.getWeeklyCheck();

        String listOfEEs6 = line+e1.toString2()+line+e2.toString2()+line+e3.toString2();
        System.out.println("6) This shows new paycheck totals generated (based on 10 hours) because the .getWeeklyCheck() method was called.");
        System.out.println(listOfEEs6);

        //showing the array of paychecks that e1 has
        double[] e1PayCheckArray = e1.getPayChecksArray();

        System.out.println(line2);
        System.out.println("7) This shows the list of paychecks that have been generated for e1.");
        System.out.println(line2);
        System.out.println(e1);
        for (int i=0;i<e1.getPayChecksArray().length;i++){
            System.out.println("Paycheck #"+ (i+1) + ": $" + e1PayCheckArray[i]);
        }

    }
}
