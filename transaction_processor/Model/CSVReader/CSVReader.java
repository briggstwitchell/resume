/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.Transaction.CSVReader.java
 ******/

package Model.CSVReader;
import Model.List.IList;
import Model.Transaction.ITransaction;

import java.io.IOException;

/**
 * Defines the methods available to a class able to read in a CSV file and create a list of Transactions
 */
public interface CSVReader {

    /**
     * Returns an IList linked list of Transactions by reading a .csv file and parsing its data
     * @param path a string for the file path containing the .csv file
     * @return an IList linked list of transactions for the data contained in the .csv file passed in for the file path
     * @throws IOException for an invalid .csv file
     */
    IList<ITransaction> populate(String path)throws IOException;
}
