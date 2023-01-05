/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: View.WorkBookView.java
 ******/

package View;
import Model.Transaction.WorkBook;

/**
 * Interface that defines the methods available to a view displaying the workbook information to a program user
 *      (NOTE: only one view was created for this program)
 */
public interface WorkBookView {

        /**
         * Shows the user the main menu selections
         */
        void displayMainMenu();

        /**
         * Tells the user to enter a file name
         */
        void displayUserFileInput();

        /**
         * Displays all the transactions currently in the user's workbook
         */
        void displayAllTransactions(WorkBook w);

        /**
         * Displays the menu of sum options available to the user
         */
        void displayUserSumSelection();

        /**
         * Tells the user that duplicates have been removed from the workbook
         */
        void displayDuplicatesRemoved();

        /**
         * Displays the sum of a list of transactions to the user
         * @param sum the sum to be displayed
         */
        void displaySum(double sum);

        /**
         * Displays the average of all the transactions to the user
         * @param average the average to be displayed
         */
        void displayAverage(double average);

        /**
         * Shows the user where/how to input the from date for a transaction filter/sort
         */
        void displayFromDateInput();

        /**
         * Shows the user where/how to input the to date for a transaction filter/sort
         */
        void displayToDateInput();


        /**
         * Tells the user that their input was invalid
         */
        void displayInvalidInputMessage();

        /**
         * Tells the user that the program is being exited
         */
        void displayProgramExit();

        /**
         * Displays all the transaction categories available for the use to filter/sum by
         */
        void displayTransactionCategories();

        /**
         * Displays the menu of filter options to the user
         */
        void DisplayUserFilterSelection();

        /**
         * Displays the input for filtering/summing transactions by type (income or expense)
         */
        void displayUserExpenseIncomeSelection();

        /**
         * Prompts the user to indicate whether they want return amounts greater or less than the threshold they defined
         */
        void displayUserInputGreaterOrLess();

        /**
         * Displays the sort of filter options to the user
         */
        void displayUserSortSelection();

        /**
         * Prompts the user to indicate whether they want to sort in ascending or descending order
         */
        void displayUserAscendingSelection();

        /**
         * Prompts the user to indicate an amount threshold for an amount filter
         */
        void displayUserThresholdEntry();

        /**
         * Displays a message when an error occurs
         */
        void displayErrorMessage(String message);

        /**
         * Asks the user for the file type (bank statement or credit card)
         */
        void askForFileType();

        /**
         * Indicates to the user that all transactions have been cleared
         */
        void displayClearedTransactions();

    }
