/******
 Name: Briggs Twitchell
 Assignment: Final Project
 Date: 12/04/2022
 File: Model.Transaction.TransactionCategory.java
 ******/

package Model.Transaction;
import java.util.HashSet;
import java.util.Set;

/**
 * Enumerates the possible category that a transaction could fall into
 */
public enum TransactionCategory {
    GROCERIES, TRAVEL, RESTAURANTS, GAS, SHOPPING, UTILITIES, GIFTS, OTHER;

    /**
     * Returns a transaction category for a string passed in by checking if its in a series of predefined sets. Sets to 'Other' if not found.
     * @param category a string to be categories into one of the enumerated transaction cateogy types
     * @return a TransactionCategory that the string passed in corresponds to
     */
    public static TransactionCategory getTransactionCategory(String category) {
        try{
            return TransactionCategory.valueOf(category);
        }
        catch (IllegalArgumentException e){
            //System.out.println(e);
        }

        catch (Exception e){
            System.out.println(e);
        }

        Set<String> restaurantsSet = new HashSet<String>();
        restaurantsSet.add("FOOD & DRINK");

        Set<String> billsSet = new HashSet<String>();
        billsSet.add("BILLS & UTILITIES");

        if(restaurantsSet.contains(category)){
            return TransactionCategory.RESTAURANTS;
        }
        else if(billsSet.contains(category)){
            return TransactionCategory.UTILITIES;
        }
        else{
            return TransactionCategory.OTHER;
        }
    }

    /**
     * Formats the category into a string and returns it
     * @return a formatted string of the TransactionCategory
     */
    @Override
    public String toString(){
        String str = super.toString().toLowerCase();
        String capitalFirst = str.substring(0,1).toUpperCase()+str.substring(1);
        return capitalFirst;
    }

}

