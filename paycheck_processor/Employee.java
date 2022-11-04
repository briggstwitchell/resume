/******
 Name: Briggs Twitchell
 Assignment: Lab 02
 Date: 9/27/2022
 File: Employee.java
 ******/
import java.util.Arrays;

/**
 * Class representing an Employee
 */
public class Employee {
    private String name;
    private int id;
    private double hours;
    private double payRate;
    //private double weeklyTotalPay;
    private static int numEmployees;
    private static int maxHours = 168;

    private int numChecks;
    private double[] payChecksArray;//TODO -- finish this part


    /**
     * Constructor method to create an Employee object
     * @param name the employee's full name (String)
     * @param payRate the employee's pay rate (double)
     */
    public Employee(String name, double payRate){
        this.name = name;
        Employee.addEmployee();
        this.id = getNumEmployees();
        resetHoursWorked();
        setPayRate(payRate);
        this.numChecks = 0;
        this.payChecksArray = new double[0];
    }

    /**
     * sets the Employee's rate of pay
     * @param payRate double representing the employee's rate of pay
     * @throws IllegalArgumentException for doubles less than 0
     */
    public void setPayRate(double payRate)
            throws IllegalArgumentException{
        if (payRate < 0) {
            throw new IllegalArgumentException("setPayRate ERROR -- argument payRate must be > 0.");
        }
        else {
            this.payRate = payRate;
        }
    }

    /**
     * Sets employee's hours to 0
     */
    public void resetHoursWorked(){
        this.hours = 0;
    }

    /**
     * Adds hours to an employee's hours
     * @param hours number of additional hours worked
     * @throws IllegalArgumentException for hours below 0 or above 168.
     */
    public void addHoursWorked(double hours)
            throws IllegalArgumentException{
        if(hours < 0 || hours > this.maxHours){throw new IllegalArgumentException("addHoursWorked ERROR -- argument hours can't be <0 or >168.");}
        else{this.hours += hours;
        }
    }

    public double getHours(){return this.hours;}

    /**
     * Returns a double for an employee's rate of pay
     * @return a double for an employee's rate of pay
     */
    public double getPayRate(){return this.payRate;}

    /**
     * Returns an integer for an employee's ID
     * @return integer for an employee's ID
     */
    public int getId(){return this.id;}

    /**
     * Returns a String for an employee's name
     * @return String for an employee's name
     */
    public String getName() {return this.name;}

    /**
     * Constructs and returns a new paycheck object based on an employee's current hours worked
     * @return a paycheck object based on the employee's hours worked
     */
    public Paycheck getWeeklyCheck(){
        Paycheck employeePaycheck = new Paycheck(this);
        this.numChecks ++;
        addToPayCheckArray(employeePaycheck.getTotalPay());
        return employeePaycheck;
    }

    /**
     * Formats and returns a string with an employee's name, id, payrate, and hours worked
     * @return a string formatted with an employee's name, id, payrate, and hours worked
     */
    @Override
    public String toString(){
        //String str = "Name: " + this.name + "\nID: "+this.id + "Rate of Pay: "+ this.payRate+ "Current hours work: "+this.hours;
        String str = "Name: "+ this.name;
        return str;
    }
    public String toString2(){
        String formattedPay = String.format("$%.2f",this.getPayRate());
        String formattedHours = String.format("%.2f",this.getHours());
        String formattedTotalPay;
        if (this.numChecks == 0){formattedTotalPay ="$0.00";}
        else{formattedTotalPay = String.format("$%.2f",this.payChecksArray[numChecks-1]);}

        String str = "Name: " + this.name + "\nID: "+this.id + "\nRate of Pay: "+
                formattedPay + "\nCurrent hours work: "+ formattedHours+
                "\nMost recent paycheck generated: "+formattedTotalPay+"\n";
        return str;
    }
    /**
     * Adds an additional employee to the number of employees so far
     */
    private static void addEmployee(){
        Employee.numEmployees ++;
    }

    /**
     * Returns an integer for the current number of employees constructed
     * @return an integer for the current number of employees constructed
     */
    public static int getNumEmployees(){return Employee.numEmployees;}

    private void addToPayCheckArray(double checkValue){
        double[] newCheckArray = Arrays.copyOf(this.payChecksArray,this.payChecksArray.length+1);
        newCheckArray[(newCheckArray.length) -1] = checkValue;
        this.payChecksArray = newCheckArray;

    }

    /**
     * Function returns all a double array for all the paychecks generated for an employee
     * @return a double array reflecting all of the Employee's paychecks generated
     */
    public double[] getPayChecksArray(){return this.payChecksArray;}

    /**
     * Function returns the number of paychecks that have been generated for an employee
     * @return
     */
    public int getNumChecks(){return this.numChecks;}

}
