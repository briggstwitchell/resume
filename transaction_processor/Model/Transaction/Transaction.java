/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.Transaction.Transaction.java
 ******/
package Model.Transaction;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class represents an abstract transaction, implementing the methods that are common to all transaction types
 */
public abstract class Transaction implements ITransaction {
    protected Date date;
    protected double amount;
    protected String description;
    protected TransactionCategory category;


    /**
     * Single method constructor that sets the information associated with the transaction
     * @param date the transaction's date of occurrence
     * @param amount the amount of the transaction
     * @param description the description given to a transaction
     * @param category the category enumerated type of a transaction
     */
    public Transaction(Date date, double amount, String description, TransactionCategory category){
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.category = category;
    }

    /**
     * Returns the date associated with a transaction
     * @return
     */
    public Date getDate(){
        return this.date;
    }

    /**
     * Returns the amount of a transaction
     * @return
     */
    public double getAmount(){
        return this.amount;
    }

    /**
     * Returns the description associated with a transaction
     * @return
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * Returns a transaction's transaction category
     * @return
     */
    public TransactionCategory getTransactionCategory(){
        return this.category;
    }

    /**
     * Formats the transaction's data into a string
     * @return a string showing the transaction's data
     */
    public String toString(){
        DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        DecimalFormat amountFormatter = new DecimalFormat("0.00");

        return "\n\tAmount: $"+amountFormatter.format(amount)+", Date: "+dateFormatter.format(date)+", Category: "+this.category+", Description: "+this.description;
    }

}
