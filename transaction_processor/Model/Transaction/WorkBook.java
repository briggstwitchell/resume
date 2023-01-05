/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.Transaction.WorkBook.java
 ******/

package Model.Transaction;
import Model.List.IList;
import Model.List.List;
import Model.Transaction.*;
import Model.CSVReader.CSVReader;
import Model.CSVReader.ChaseCSVReaderDebit;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents a workbook of transactions that can be sorted, filtered, and summarized in various ways
 *  to analyze a series of transactions obtained from a .csv file
 */
public class WorkBook implements Iterable<ITransaction> {

    private IList<ITransaction> transactions;
    private CSVReader reader;

    /**
     * Single argument constructor that establishes the type of CSVReader to be used and instantiates an empty list of transactions
     * @param reader
     */
    public WorkBook(CSVReader reader){
        this.transactions = new List<ITransaction>();
        this.reader = reader;
    }

    /**
     * No argument constructor that defaults the CSVReader to a Debit reader and instantiates an empty list of transactions
     */
    public WorkBook(){
        this.transactions = new List<ITransaction>();
        this.reader = new ChaseCSVReaderDebit();
    }



    /* **********************************************************
    BASIC METHODS
    ********************************************************** */
    /**
     * Utilizes the iterator within the transactions list to make a workbook iterable
     * @return an Iterator for a list of transactions
     */
    @Override
    public Iterator<ITransaction> iterator() {
        return transactions.iterator();
    }

    /**
     * Setter method to change the CSVReader being used
     * @param reader a CSVReader object that can parse a .csv file for transaction data
     */
    public void setReader(CSVReader reader){
        this.reader = reader;
    }

    /**
     * Populates the workbook's transaction list with the data contained in a .csv file
     * @param path a string for the path of the .csv file to be read and parsed
     * @throws IOException for an invalid .csv file
     */
    public void addTransactionsFromCSV(String path) throws IOException{
        this.transactions.addBack(reader.populate(path));
    }

    /**
     * Setter method for the transactions, used as a helper throughout various other methods
     * @param transactionList the list of transactions to replace the current transactionList
     */
    public void setTransactions(IList<ITransaction> transactionList){
        this.transactions = transactionList;
    }

    /**
     * Prints out all of the transactions in the workbook
     */
    public void printTransactions(){
        System.out.println("Transactions:");
        for(ITransaction t: this.transactions){
            System.out.println("\t"+t);
        }
    }

    /**
     * Converts all of the transactions in the workbook to a string, delimited by new-line characters
     * @return a string of all the transactions in the workbook
     */
    public String toString(){
        String output = "Transactions:\n";
        for(ITransaction t: this.transactions){
            output += "\n"+t.toString();
        }
        return output;
    }

    /**
     * Removes all the duplicate transactions in the workbook, if there are any
     */
    public void removeDuplicates(){
        while(this.removeDuplicatesHelper() > 0){}
        return;
    }

    /**
     * Helper method to remove any duplicates from the workbook
     * @return an integer to indicate whether or not there are any remaining duplicates in the workbook
     */
    private int removeDuplicatesHelper(){
        ArrayList<ITransaction> toRemove = new ArrayList();
        ArrayList<Integer> toRemoveIndex = new ArrayList();
        int i;
        int j;
        int count = this.transactions.count();
        ITransaction tempTransaction1;
        ITransaction tempTransaction2;

        for(i=0;i<count;i++){
            tempTransaction1 = this.transactions.get(i);
            for(j=0;j<count;j++){
                tempTransaction2 = this.transactions.get(j);
                if(tempTransaction1.equals(tempTransaction2) && i != j && !toRemove.contains(tempTransaction2)){
                    toRemove.add(tempTransaction2);
                    toRemoveIndex.add(j);
                }
            }
        }

        for(int k = toRemoveIndex.size()-1;k>=0;k--){
            this.transactions.removeIndex(toRemoveIndex.get(k));
        }

        if(toRemoveIndex.size() == 0){
            return 0;
        }
        else{
            return 1;
        }
    }

    /* **********************************************************
    FILTER METHODS
    ********************************************************** */

    /**
     * Filters the list of transactions by date (exclusive for from and to)
     * @param after a string for the date after which to include transactions (formatted as mm/dd/yy)
     * @param before a string for the date before which to include transactions (formatted as mm/dd/yy)
     * @return a new list of transactions within the specified date range
     */
    public IList<ITransaction> filterByDate(String after, String before) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
        try {
            Date fromDate = dateFormatter.parse(after);
            Date toDate = dateFormatter.parse(before);
            //
            IList<ITransaction> filteredList = this.transactions.filter(e -> e.getDate().after(fromDate)
                    && e.getDate().before(toDate));
            return filteredList;
        } catch (ParseException ex) {
            System.out.println(ex);
        }
        return null;
    }

    /**
     * Filters the list of transactions by amount
     * @param amount a double indicating the threshold for the transaction list to be filtered
     * @param greaterThan a boolean indicating whether or not the new transaction list will be be transactions above or below the threshold (true is above)
     * @return a new list of transactions above or below the amount threshold
     */
    public IList<ITransaction> filterByAmount(Double amount, boolean greaterThan) {
        IList<ITransaction> filteredList;
        if(greaterThan){
            filteredList = this.transactions.filter(e -> e.getAmount()>amount);
        }
        else {
            filteredList = this.transactions.filter(e -> e.getAmount()<amount);
        }
        return  filteredList;
    }

    /**
     * Filters the list of transactions by transaction category
     * @param category the TransactionCategory to be filtered to
     * @return a new list of transactions within the category specified
     */
    public IList<ITransaction> filterByCategory(TransactionCategory category) {
        IList<ITransaction> filteredList;
        filteredList = this.transactions.filter(e -> e.getTransactionCategory().equals(category));
        return  filteredList;
    }

    /**
     * Filters the list of transactions by transaction type (expense or income)
     * @param transactionType the Transaction type that the new list to be filtered to
     * @return a new list of transactions within the type specified (expense or income)
     */
    public IList<ITransaction> filterByTransactionType(String transactionType) {
        transactionType = transactionType.toLowerCase();
        IList<ITransaction> filteredList;

        switch (transactionType){
            case "expense":
                filteredList = this.transactions.filter(e-> e instanceof Expense);
                break;
            case "income":
                filteredList = this.transactions.filter(e-> e instanceof Income);
                break;
            default:
                throw new IllegalArgumentException("Argument must be 'expense' or 'income'");
        }
        return filteredList;
    }


    /* **********************************************************
    SUMMING METHODS
    ********************************************************** */
    /**
     * Returns a double for the sum of all transaction amounts in the workbook
     * @return a double for the sum of all transaction amounts in the workbook
     */
    public double sum(){
        return this.transactions.map(e->e.getAmount()).fold(0.0,(e1,e2)->e1+e2);
    }

    /**
     * Helper method to map each transaction to its amount member
     * @param list the list of transactions to be converted to amounts
     * @return a list of double representing the list of transaction amounts
     */
    private IList<Double> mapToAmount(IList<ITransaction> list) {
        return list.map(e->e.getAmount());
    }

    /**
     * Helper method to return a double for the sum of a list of doubles
     * @param listOfAmounts a list of doubles
     * @return a double for the sum of the list of doubles
     */
    private double sumAmounts(IList<Double> listOfAmounts) {
        return listOfAmounts.fold(0.0,(e1,e2)->e1+e2);
    }

    /**
     * Returns the sum of a list of transactions based on transaction type (income or expense)
     * @param transactionType a string indicating which type the user wants to sum
     * @return the sum of the transactions filtered by type
     */
    public double sum(String transactionType){
        transactionType = transactionType.toLowerCase();
        IList<ITransaction> filteredList = this.filterByTransactionType(transactionType);
        IList<Double> doubleList = this.mapToAmount(filteredList);
        return this.sumAmounts(doubleList);
    }

    /**
     * Sums the transactions within a certain date range (exclusive)
     * @param after a string for the date after which to include transactions (formatted as mm/dd/yy)
     * @param before a string for the date before which to include transactions (formatted as mm/dd/yy)
     * @return the sum of the transactions that fall within the date range
     */
    public double sum(String after, String before) {
        IList<ITransaction> filteredList = this.filterByDate(after, before);
        return filteredList.map(e->e.getAmount()).fold(0.0,(e1,e2)->e1+e2);
    }

    /**
     * Sums the transactions within a certain date range (exclusive) and of a certain category
     * @param after a string for the date after which to include transactions (formatted as mm/dd/yy)
     * @param before a string for the date before which to include transactions (formatted as mm/dd/yy)
     * @param category the TransactionCategory to be filtered to
     * @return the sum of the transactions that fall within the date range and are of a certain category
     */
    public double sum(String after, String before, String category){
        IList<ITransaction> filteredList = this.filterByDate(after,before);
        filteredList = filteredList.filter(e -> e.getTransactionCategory().toString().equals(category));

        IList<Double> doubleList = this.mapToAmount(filteredList);
        return Math.round(this.sumAmounts(doubleList) * 100.0) / 100.0;
    }

    /**
     * Sums transactions above or below a specified amount threshold
     * @param amount a double for the amount threshold
     * @param greaterThan a boolean for whether the sum will be for transactions greater or less than the threshold
     * @return the sum of the transactions above or below the threshold
     */
    public double sumGreaterThan(double amount, boolean greaterThan){
        IList<ITransaction> filteredList = this.filterByAmount(amount,greaterThan);
        IList<Double> doubleList = this.mapToAmount(filteredList);
        return Math.round(this.sumAmounts(doubleList) * 100.0) / 100.0;
    }

    /**
     * Sums transactions above or below a specified amount threshold
     * @param amount a double for the amount threshold
     * @param greaterThan a boolean for whether the sum will be for transactions greater or less than the threshold
     * @param category the TransactionCategory to be filtered to
     * @return the sum of the transactions above or below the threshold
     */
    public double sumGreaterThan(double amount, boolean greaterThan, TransactionCategory category){
        IList<ITransaction> filteredList = this.filterByAmount(amount,greaterThan);
        filteredList = filteredList.filter(e -> e.getTransactionCategory().equals(category));

        IList<Double> doubleList = this.mapToAmount(filteredList);
        return Math.round(this.sumAmounts(doubleList) * 100.0) / 100.0;
    }
    /* **********************************************************
    SORTING METHODS
    ********************************************************** */

    /**
     * Sorts the list by transaction amount
     * @param ascending a double indicating whether to sort the list in ascending or descending order
     */
    public void sortByAmount(boolean ascending){
        if(ascending) {
            this.transactions.sort((e1, e2) -> (int) (e1.getAmount() - e2.getAmount()));
        }
        else{
            this.transactions.sort((e1, e2) -> (int) (e2.getAmount() - e1.getAmount()));
        }
    }

    /**
     * Sorts the list by transaction number
     * @param ascending a double indicating whether to sort the list in ascending or descending order
     */
    public void sortByTransactionNumber(boolean ascending){
        if(ascending) {
            this.transactions.sort((e1, e2) -> (int) (e1.getTransactionNumber() - e2.getTransactionNumber()));
        }
        else{
            this.transactions.sort((e1, e2) -> (int) (e2.getTransactionNumber() - e1.getTransactionNumber()));
        }
    }

    /**
     * Sorts the list by date
     * @param earlierFirst a double indicating whether to sort the list in ascending or descending order
     */
    public void sortByDate(boolean earlierFirst){
        if(earlierFirst) {
            this.transactions.sort((e1, e2) -> (int) (e1.getDate().compareTo(e2.getDate())));
        }
        else{
            this.transactions.sort((e1, e2) -> (int) (e2.getDate().compareTo(e1.getDate())));
        }
    }

    /**
     * Sorts the transaction list alphabetically by transaction description
     * @param aTOz a double indicating whether to sort the list in ascending or descending order
     */
    public void sortByDescription(boolean aTOz){
        if(aTOz) {
            this.transactions.sort((e1, e2) -> (int) (e1.getDescription().compareTo(e2.getDescription())));
        }
        else{
            this.transactions.sort((e1, e2) -> (int) (e2.getDescription().compareTo(e1.getDescription())));
        }
    }

    /* **********************************************************
    OTHER METHODS
    ********************************************************** */
    /**
     * Returns a double for the average of all the transactions in the workbook
     * @return a double for the average of all the transactions in the workbook
     */
    public double getAvg(){
        int count = this.transactions.count();
        double sum = this.transactions.map(e->e.getAmount()).fold(0.0,(e1,e2)->e1+e2);
        return Math.round(sum/count * 100.0) / 100.0;
    }
}


