/******
 Name: Briggs Twitchell
 Assignment: Lab 02
 Date: 9/27/2022
 File: Paycheck.java
 ******/

/**
 * Paycheck class with fields for employee id, rate, hours, and total pay
 */
public class Paycheck {

    private int id;

    private String name;
    private double rate;
    private double hours;
    private double totalPay;

    private double overTimePay;
    private static int maxHours = 168;

    /**
     * Constructor method to create a paycheck taking in EE name, ID, pay rate, and hours
     * @param employeeName String for employee's name
     * @param employeeId int for employee's ID
     * @param employeeRate double for employee's rate of pay
     * @param employeeHours double for employee's total hours worked
     */
    public Paycheck(String employeeName, int employeeId, double employeeRate, double employeeHours ){
        this.name = employeeName;
        this.id = employeeId;
        setRate(employeeRate);
        setHours(employeeHours);
        setTotalPay(this.getHours());
    }

    /**
     * Paycheck constructor to create a new paycheck based on Employee field data
     * @param employee Employee object to populate name, id, rate, hours, and totalPay
     */
    public Paycheck(Employee employee){
        this.name = employee.getName();
        this.id = employee.getId();
        this.rate = employee.getPayRate();
        setHours(employee.getHours());
        setTotalPay(this.getHours());
    }

    /**
     * Calculates and sets the total pay based on an employee's hours worked
     * @param hours hours worked by an employee as a double
     */
    private void setTotalPay(double hours){
        if(hours <=40){this.totalPay =  this.rate*hours;}
        else {this.totalPay = (40*this.getRate() + (hours - 40)*1.5*this.getRate());}

        //sets overtime pay if hours >40
        if(this.hours > 40){
            this.overTimePay = (this.hours-40)*1.5*this.getRate();
        }
    }

    /**
     * Sets the numbers of hours for a paycheck
     * @param hours double for the hours an employee worked
     * @throws IllegalArgumentException for hours <0 or >168
     */
    private void setHours(double hours)throws IllegalArgumentException{
        if(hours < 0 || hours > this.maxHours){
            throw new IllegalArgumentException("setHours ERROR -- hours argument <0 or >168.");
        }
        else{this.hours = hours;}
    }

    /**
     * Sets the payrate for a paycheck
     * @param rate double for the employee's rate of pay
     * @throws IllegalArgumentException for a rate <0
     */
    private void setRate(double rate)throws IllegalArgumentException{
        if(rate < 0){
            throw new IllegalArgumentException("setRate ERROR -- rate argument <0.");
        }
        else{this.rate = rate;}
    }

    /**
     * returns a double for the paycheck's total based on the current hours worked
     * @return a double Paycheck's total pay for the pay period
     */
    public double getTotalPay(){
        setTotalPay(this.getHours());
        return this.totalPay;}

    /**
     * returns a string for the name associated with the paycheck
     * @return a string for name associated with the paycheck
     */
    public String getName(){return this.name;}

    /**
     * returns an integer for the ID associated with the paycheck
     * @return an integer for the ID associated with the paycheck
     */
    private int getId(){return this.id;}

    /**
     * returns a double for the pay rate associated with the paycheck
     * @return a double for the pay rate associated with the paycheck
     */
    public double getRate(){return this.rate;}

    /**
     * returns a double for the hours associated with the paycheck
     * @return a double for the hours associated with the paycheck
     */
    public double getHours(){return this.hours;}

    /**
     * Formats and returns a string with an paycheck's name, id, payrate, hours worked, and total pay
     * @return a string with an paycheck's name, id, payrate, hours worked, and total pay
     */
    @Override
    public String toString(){
        return String.format("Total pay: $%.2f", this.totalPay);
        /*
        * String str = "Name: " + this.name + "\nID: " + this.id +
        *      "\nPay rate: " + this.rate + "\nHours: "+ this.hours + totalPay;
        * String str = "Total pay: " + this.totalPay;
        * return str;
        */
    }


}
