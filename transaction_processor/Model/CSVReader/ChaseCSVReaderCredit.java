/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.Transaction.ChaseCSVReaderCredit.java
 ******/

package Model.CSVReader;
import Model.List.*;
import Model.Transaction.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a class that can read in a .csv file formatted in the specific .csv file generated for a ChaseBank credit card statement
 */
public class ChaseCSVReaderCredit implements CSVReader{

    /**
     * Returns an IList linked list of Transactions by reading a .csv file and parsing its data
     * @param path a string for the file path containing the .csv file
     * @return an IList linked list of transactions for the data contained in the .csv file passed in for the file path
     * @throws IOException for an invalid .csv file
     */
    public IList<ITransaction> populate(String path)throws IOException{

        //checks that the file type is valid
        if(!path.substring(path.length()-4,path.length()).trim().strip().replace("\n","").equals(".csv")){
            throw new IOException("Invalid file -- file must end with '.csv'\n");
        }

        IList<ITransaction> transactionList = new List<ITransaction>();

            String line;
            ArrayList<String> stringList = new ArrayList<String>();

            //initializing transaction variables
            Double amount;
            Date date;
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
            String description;
            TransactionCategory category;


                BufferedReader bufferedReader = new BufferedReader(new FileReader(path));

                //reads through each line in the file and creates a transaction if no parsing errors are encountered
                //      (adds the transaction to a list of transactions)
                while ((line = bufferedReader.readLine()) != null) {
                    try {
                        stringList.addAll(java.util.List.of(line.split(",")));

                        //parsing data for each line
                        amount = Double.parseDouble(stringList.get(5));
                        date = dateFormatter.parse(stringList.get(1));
                        description = stringList.get(2);
                        category = TransactionCategory.getTransactionCategory(stringList.get(3).toUpperCase());

                        if (amount > 0) {
                            transactionList.addBack(new Income(date, amount, description, category));
                        } else {
                            transactionList.addBack(new Expense(date, amount, description, category));
                        }
                    }
                    catch (NumberFormatException e) {
                        //System.out.println(e);
                    }
/*
                    catch (IOException e) {
                        System.out.println(e);
                    }
                    */

                    catch (Exception e) {
                        //System.out.println(e);
                    }
                    finally {
                        stringList.clear();
                    }
                }

        return transactionList;
    }



}
