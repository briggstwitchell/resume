import Controller.WorkBookControllerImp;
import Model.Transaction.ITransaction;
import Model.Transaction.TransactionCategory;
import Model.CSVReader.ChaseCSVReaderCredit;
import Model.CSVReader.ChaseCSVReaderDebit;
import Model.Transaction.WorkBook;
import View.WorkbookTerminalView;


public class Main {
    public static void main(String[] args) {

        //interactive program demo
        WorkBook model = new WorkBook(new ChaseCSVReaderDebit());
        Controller.WorkBookController c = new WorkBookControllerImp(model,System.in,new WorkbookTerminalView());
        c.go();

        //simple demo
        //Main.programDemo1();
    }

    private static void programDemo1(){
        String currentDirectory = System.getProperty("user.dir") + "/";
        String demoCsv = "chasecredit.csv";

        WorkBook workBook = new WorkBook(new ChaseCSVReaderCredit());
        try {
            workBook.addTransactionsFromCSV(demoCsv);

            System.out.println("\nDemo #1: sum = " + workBook.sum());

            System.out.println("\nDemo #2.1: sum by transaction type (expense) = " + workBook.sum("expense"));
            System.out.println("\nDemo #2.2: sum by transaction type (income) = " + workBook.sum("income"));

            System.out.println("\nDemo #3: sum by date range = " + workBook.sum("10/14/22", "10/19/22"));

            System.out.println("\nDemo #4: sum after 10/14/22 but before 10/19/22 for Food & Drink = " + workBook.sum("10/14/22", "10/19/22", "Restaurants"));

            System.out.println("\nDemo #5: sum transactions less than $-20 = " + workBook.sumGreaterThan(-20, false));

            System.out.println("\nDemo #6: sum gas transactions less than $-20 = " + workBook.sumGreaterThan(-20, false, TransactionCategory.GAS));

            System.out.println("\nDemo #7: printing list sorted by descending amount:");
            workBook.sortByAmount(true);
            for (ITransaction t : workBook) {
                System.out.println(t.getAmount());
            }

            System.out.println("\nDemo #8: printing list sorted by reverse transaction number:");
            workBook.sortByTransactionNumber(false);
            for (ITransaction t : workBook) {
                System.out.println(t.getTransactionNumber());
            }

            System.out.println("\nDemo #9: printing list sorted by earlier date:");
            workBook.sortByDate(true);
            for (ITransaction t : workBook) {
                System.out.println(t.getDate());
            }

            System.out.println("\nDemo #10: printing list sorted by transaction description A to Z:");
            workBook.sortByDescription(true);
            for (ITransaction t : workBook) {
                System.out.println(t.getDescription());
            }

            System.out.println("\nDemo #11: average of all transactions = " + workBook.getAvg());


            System.out.println("\nDemo #12: showing removing of duplicate:");
            workBook.addTransactionsFromCSV("chasesmallercredit.csv");
            System.out.println("BEFORE DUPLICATE REMOVAL");
            WorkBook filteredWorkbook = new WorkBook(new ChaseCSVReaderCredit());
            filteredWorkbook.setTransactions(workBook.filterByAmount(-4.0, true));
            filteredWorkbook.sortByAmount(false);

            filteredWorkbook.printTransactions();
            System.out.println("AFTER DUPLICATE REMOVAL");
            filteredWorkbook.removeDuplicates();
            filteredWorkbook.printTransactions();
        }
        catch (Exception e){System.out.println(e);}
    }

}
