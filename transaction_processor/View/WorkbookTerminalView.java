/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: View.WorkBookTerminalView.java
 ******/

package View;
import Model.Transaction.TransactionCategory;
import Model.Transaction.WorkBook;

/**
 * This class implements a view for a workbook interactive program through the terminal window
 */
public class WorkbookTerminalView implements WorkBookView{


    /**
     * Shows the user the main menu selections
     */
    public void displayMainMenu(){
        String output = "\nPlease select one of the options below (enter 0 to quit program):\n";
        output += "\t1) add transactions to your workbook\n";
        output += "\t2) print current transactions in workbook\n";
        output += "\t3) remove any duplicates\n";
        output += "\t4) sum transactions\n";
        output += "\t5) filter transactions\n";
        output += "\t6) sort transactions\n";
        output += "\t7) get average of all transactions\n";
        output += "\t8) clear all current transactions\n";
        output += "\n\t> ";

        System.out.print(output);
    }

    /**
     * Displays all the transactions currently in the user's workbook
     */
    public void displayAllTransactions(WorkBook w){
        System.out.println(w.toString());
    }

    /**
     * Displays the menu of sum options available to the user
     */
    public void displayUserSumSelection(){
        String output = "Please select one of the options below (enter 0 to quit program):\n";
        output += "\t1) Sum all transactions\n";
        output += "\t2) Sum by date range (exclusive from and to)\n";
        output += "\t3) Sum for date range (exclusive from and to) and transaction category\n";
        output += "\t4) Sum for transactions above or below an amount threshold\n";
        output += "\n\t> ";
        System.out.print(output);
    }

    /**
     * Tells the user that duplicates have been removed from the workbook
     */
    public void displayDuplicatesRemoved(){
        System.out.print("All potential duplicates removed...\n");
    }

    /**
     * Displays the sum of a list of transactions to the user
     * @param sum the sum to be displayed
     */
    public void displaySum(double sum){
        System.out.print("sum = "+sum+"\n");
    }

    /**
     * Displays the average of all the transactions to the user
     * @param average the average to be displayed
     */
    public void displayAverage(double average){
        System.out.print("average = "+average+"\n");
    }

    /**
     * Shows the user where/how to input the from date for a transaction filter/sort
     */
    public void displayFromDateInput() {
        System.out.print("From date ('mm/dd/yy' format):\n\t> ");
    }

    /**
     * Shows the user where/how to input the to date for a transaction filter/sort
     */
    public void displayToDateInput() {
        System.out.print("To date ('mm/dd/yy' format):\n\t> ");
    }

    /**
     * Tells the user that their input was invalid
     */
    public void displayInvalidInputMessage(){
        System.out.println("INVALID INPUT");
    }

    /**
     * Tells the user that the program is being exited
     */
    public void displayProgramExit(){
        System.out.println("exiting program...\n");
    }

    /**
     * Displays all the transaction categories available for the use to filter/sum by
     */
    public void displayTransactionCategories(){
        System.out.println("Categories:");
        for (TransactionCategory t : TransactionCategory.values()) {
            System.out.println("\t"+t.toString());
        }
        System.out.println("Please input one of the categories as listed\n\t> ");
    }

    /**
     * Tells the user to enter a file name
     */
    public void displayUserFileInput(){
        System.out.print("Please enter a .csv file name for which you'd like to create a transaction workbook\n(must be in current director)\n\n\t> ");
    }

    /**
     * Displays the menu of filter options to the user
     */
    public void DisplayUserFilterSelection(){
        String output = "Please select one of the options below (enter 0 to quit program):\n";
        output += "\t1) Filter by date range (exclusive from and to)\n";
        output += "\t2) Filter by transaction threshold\n";
        output += "\t3) Filter for specific transaction category\n";
        output += "\t4) Filter for transaction type (income or expense)\n";
        output += "\n\t> ";
        System.out.println(output);
    }

    /**
     * Displays the input for filtering/summing transactions by type (income or expense)
     */
    public void displayUserExpenseIncomeSelection(){
        System.out.println("Filter for expenses or income\n(1 for expenses, anything else for income):\n\t> ");
    }

    /**
     * Prompts the user to indicate whether they want return amounts greater or less than the threshold they defined
     */
    public void displayUserInputGreaterOrLess() {
        System.out.print("Would you like to filter for transactions greater or less than your threshold?\n(1 for greater or anything else for less):\n\t> ");
    }

    /**
     * Displays the sort of filter options to the user
     */
    public void displayUserSortSelection(){
        String output = "Please select one of the options below (enter 0 to quit program):\n";
        output += "\t1) Sort by date\n";
        output += "\t2) Sort by alphabetically by description\n";
        output += "\t3) Sort by transaction amount\n";
        output += "\n\t> ";
        System.out.print(output);
    }

    /**
     * Prompts the user to indicate whether they want to sort in ascending or descending order
     */
    public void displayUserAscendingSelection(){
        System.out.print("Sort ascending or descending? (enter 1 for ascending and anything else for descending):\n\t> ");
    }

    /**
     * Prompts the user to indicate an amount threshold for an amount filter
     */
    public void displayUserThresholdEntry() {
        System.out.print("Threshold amount:\n\t> ");
    }

    /**
     * Displays a message when an error occurs
     */
    public void displayErrorMessage(String message){
        System.out.println(message);
    }

    /**
     * Asks the user for the file type (bank statement or credit card)
     */
    public void askForFileType(){
        String output = "What type of file is this? (enter 1 for credit-card statement or anything else for bank statement):\n";
        output += "\n\t> ";
        System.out.print(output);
    }

    /**
     * Indicates to the user that all transactions have been cleared from the workbook
     */
    public void displayClearedTransactions(){
        System.out.print("clearing transacctions...\n");
    }
}
