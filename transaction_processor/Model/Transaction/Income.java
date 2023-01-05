/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.Transaction.Income.java
 ******/

package Model.Transaction;
import java.util.Date;

/**
 * This class represents an Income transaction, differentiated by a transaction number starting with 1. Income is always
 * positive.
 */
public class Income extends Transaction {
    private int transactionNumber;
    private static int count;

    /**
     * Constructor method to set the transactions data, as well as its transaction number in relation to all the transactions
     *  instantiated so far.
     * @param date the transaction's date of occurrence
     * @param amount the amount of the transaction
     * @param description the description given to a transaction
     * @param category the category enumerated type of a transaction
     * @throws IllegalArgumentException for a negative amount argument
     */
    public Income(Date date, double amount, String description, TransactionCategory category) throws IllegalArgumentException {
        super(date, amount, description, category);
        if (amount < 0) {
            throw new IllegalArgumentException("Income amount cannot be < 0");
        }
        this.transactionNumber = 100000+count;
        count ++;
    }

    /**
     * Returns a transaction's transaction number (Income transactions always start with 1)
     * @return a transaction's transaction number (Income transactions always start with 1)
     */
    @Override
    public int getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * Formats the transaction's data into a string
     * @return a string showing the transaction's data, including its transaction number
     */
    @Override
    public String toString(){
        return "INCOME:"+super.toString()+"Model.Transaction#: "+transactionNumber;
    }

    /**
     * Equals override to indicate when to transactions equal each other -- they equal if all of their data (excluding
     *  transaction number) are the same
     * @param other the transaction being compared to this transaction
     * @return a boolean indicating whether the transaction passed in is equal to this transaction
     */
    @Override
    public boolean equals(Object other){
        if(other == this){
            return true;
        }
        if(!(other instanceof Expense)){
            return false;
        }
        return  (((Expense) other).getDate().equals(this.date)
                && ((Expense) other).getAmount() == this.amount
                && ((Expense) other).getDescription() == this.description
                && ((Expense) other).getTransactionCategory().equals(this.category)
                && ((Expense) other).getTransactionNumber() == this.transactionNumber);
    }
}
