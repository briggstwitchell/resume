/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.Transaction.ITransaction.java
 ******/

package Model.Transaction;

import java.util.Date;

/**
 * This interface defines the methods that each transaction should contain
 */
public interface ITransaction {

    /**
     * Returns the date associated with a transaction
     * @return
     */
    Date getDate();

    /**
     * Returns the amount of a transaction
     * @return
     */
    double getAmount();

    /**
     * Returns the description associated with a transaction
     * @return
     */
    String getDescription();

    /**
     * Returns a transaction's transaction number
     * @return
     */
    int getTransactionNumber();

    /**
     * Returns a transaction's transaction category
     * @return
     */
    TransactionCategory getTransactionCategory();

}
