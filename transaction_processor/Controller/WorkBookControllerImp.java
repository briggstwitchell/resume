/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Controller.WorkBookController.java
 ******/
package Controller;
import Model.CSVReader.ChaseCSVReaderCredit;
import Model.List.IList;
import Model.Transaction.ITransaction;
import Model.Transaction.TransactionCategory;
import View.WorkBookView;
import Model.Transaction.WorkBook;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Links the WorkBookView and WorkBook model together
 */
public class WorkBookControllerImp implements WorkBookController{

    private Scanner in;
    private WorkBookView out;
    private WorkBook m;

    /**
     * Constructor to instantiate the controller to link the model with the view
     * @param m the model of the program containing the logic of a Workbook
     * @param in a stream of input determining the control flow of the program
     * @param out a WorkBookView that displays information back to the program user
     */
    public WorkBookControllerImp(WorkBook m, InputStream in, WorkBookView out){
        this.m = m;
        this.in = new Scanner(in);
        this.out = out;
        //out.setListeners(this,this);
    }

    /**
     * Links the WorkBookView and WorkBook model together and runs
     */
    @Override
    public void go() {
        Objects.requireNonNull(m);

        try {
            String input = "initial string";

            while (input != "0"){
                this.out.displayMainMenu();

                try {

                    input = in.next();
                    switch (input) {
                        case "1":
                            this.out.askForFileType();
                            input = in.next();
                            if(input == "1"){
                                m.setReader(new ChaseCSVReaderCredit());
                            }
                            else{
                                m.setReader(new ChaseCSVReaderCredit());
                            }
                            this.out.displayUserFileInput();
                            input = in.next();
                            m.addTransactionsFromCSV(input);
                            break;
                        case "2":
                            this.out.displayAllTransactions(m);
                            break;
                        case "3":
                            m.removeDuplicates();
                            this.out.displayDuplicatesRemoved();
                            break;
                        case "4":
                            this.out.displayUserSumSelection();
                            //input = scanner.next();
                            this.runSumSelection(m,in);
                            break;
                        case "5":
                            this.out.DisplayUserFilterSelection();
                            this.runFilterSelection();
                            break;
                        case "6":
                            this.out.displayUserSortSelection();
                            this.runSortSelection();
                            break;
                        case "7":
                            this.out.displayAverage(m.getAvg());
                            break;
                        case "8":
                            this.out.displayClearedTransactions();
                            m.setTransactions(new Model.List.List<ITransaction>());
                            break;
                        case "0":
                            this.out.displayProgramExit();
                            System.exit(0);
                            break;
                        default:
                            this.out.displayInvalidInputMessage();
                    }
                }
                catch(IOException e){
                    this.out.displayErrorMessage(e.getMessage());
                }

                catch (Exception e){
                    this.out.displayErrorMessage(e.getMessage());
                }
            }
        }
        catch (Exception e){this.out.displayErrorMessage(e.getMessage());}

    }

    /**
     * Helper method to link user input to the sum sub-menu options
     */
    private void runSumSelection(WorkBook m, Scanner scanner) {

        String input = scanner.next();
        String category;
        String from;
        String to;
        int amount;
        String greaterThan;
        try {
            switch (input) {

                case "1":
                    this.out.displaySum(m.sum());
                    break;
                case "2":
                    this.out.displayFromDateInput();
                    from = scanner.next();
                    this.out.displayToDateInput();
                    to = scanner.next();
                    this.out.displaySum(m.sum(from,to));
                    break;
                case "3":
                    this.out.displayFromDateInput();
                    from = scanner.next();
                    this.out.displayToDateInput();
                    to = scanner.next();
                    out.displayTransactionCategories();
                    category = scanner.next();
                    category = category.toUpperCase();
                    category = category.substring(0,1)+category.substring(1).toLowerCase();
                    this.out.displaySum(m.sum(from,to,category));
                    break;
                case "4":
                    this.out.displayUserThresholdEntry();
                    amount = scanner.nextInt();
                    this.out.displayUserThresholdEntry();
                    greaterThan = scanner.next();
                    if(greaterThan.equals("1")){
                        this.out.displaySum(m.sumGreaterThan(amount,true));
                    }
                    else{
                        this.out.displaySum(m.sumGreaterThan(amount,false));
                    }
                    break;
                case "0":
                    this.out.displayProgramExit();
                    System.exit(0);
                    break;
                default:
                    this.out.displayInvalidInputMessage();
                    break;
            }
        }
        catch (Exception e){
            this.out.displayErrorMessage(e.getMessage());
        };
    }


    private double convertStringToDouble(String number){
        double d = Double.valueOf(number);
        return d;
    }


    /**
     * Helper method to link user input to the filter sub-menu options
     */
    private void runFilterSelection() {

        String input = in.next();
        String category;
        String from;
        String to;
        double amount;
        String greaterThan;
        WorkBook temp;
        try {
            switch (input) {

                case "1":
                    this.out.displayFromDateInput();
                    from = in.next();
                    this.out.displayToDateInput();
                    to = in.next();
                    temp = new WorkBook();
                    temp.setTransactions(m.filterByDate(from,to));
                    this.out.displayAllTransactions(temp);
                    break;
                case "2":
                    this.out.displayUserThresholdEntry();
                    amount = in.nextInt();
                    this.out.displayUserInputGreaterOrLess();
                    greaterThan = in.next();

                    temp = new WorkBook();
                    if(greaterThan.equals("1")){
                        temp.setTransactions(m.filterByAmount(amount,true));
                    }
                    else{
                        temp.setTransactions(m.filterByAmount(amount,false));
                    }
                    this.out.displayAllTransactions(temp);
                    break;
                case "3":
                    this.out.displayTransactionCategories();
                    category = in.next();
                    category = category.toUpperCase();
                    category = category.substring(0,1)+category.substring(1).toLowerCase();

                    temp = new WorkBook();
                    temp.setTransactions(m.filterByCategory(TransactionCategory.getTransactionCategory(category)));
                    this.out.displayAllTransactions(temp);
                    break;
                case "4":
                    this.out.displayUserExpenseIncomeSelection();
                    String expense = in.next();
                    temp = new WorkBook();
                    if(expense.equals("1")){
                        temp.setTransactions(m.filterByTransactionType("expense"));
                    }
                    else{
                        temp.setTransactions(m.filterByTransactionType("income"));
                    }
                    this.out.displayAllTransactions(temp);
                    break;
                case "0":
                    this.out.displayProgramExit();
                    System.exit(0);
                    break;
                default:
                    this.out.displayInvalidInputMessage();
                    break;
            }
        }
        catch (Exception e){
            this.out.displayErrorMessage(e.getMessage());
        };
    }


    /**
     * Helper method to link user input to the sort sub-menu options
     */
    private void runSortSelection() {

        String input = in.next();
        String ascending;
        String greaterThan;
        try {
            switch (input) {
                case "1":
                    this.out.displayUserAscendingSelection();
                    ascending = in.next();
                    if(ascending.equals("1")){
                        m.sortByDate(true);
                    }
                    else{
                        m.sortByDate(false);}
                    this.out.displayAllTransactions(m);
                    break;
                case "2":
                    this.out.displayUserAscendingSelection();
                    ascending = in.next();
                    if(ascending.equals("1")){
                        m.sortByDescription(true);
                    }
                    else{
                        m.sortByDescription(false);}
                    this.out.displayAllTransactions(m);
                    break;
                case "3":
                    this.out.displayUserAscendingSelection();
                    ascending = in.next();
                    if(ascending.equals("1")){
                        m.sortByAmount(true);
                    }
                    else{
                        m.sortByAmount(false);}
                    this.out.displayAllTransactions(m);
                    break;
                case "0":
                    this.out.displayProgramExit();
                    System.exit(0);
                    break;
                default:
                    this.out.displayInvalidInputMessage();
                    break;
            }
        }
        catch (Exception e){
            this.out.displayErrorMessage(e.getMessage());
        };
    }

}
