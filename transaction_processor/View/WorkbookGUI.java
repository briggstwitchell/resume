package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WorkbookGUI extends JFrame implements ActionListener{
    JButton addTransactionsButton;
    JButton printCurrentTransactionsButton;

    ActionEvent event;

    public WorkbookGUI(){
        super("Workbook");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(750,750);
        this.setLayout(new FlowLayout());
        this.pack();
        this.setVisible(true);
    }

    public void displayMainMenu(){
        JLabel yourWorkbookLabel = new JLabel();
        yourWorkbookLabel.setText("Menu");
        yourWorkbookLabel.setBounds(100,50,75,75);
        //yourWorkbookLabel.setVisible(true);

        //1) add transactions button
        this.addTransactionsButton = new JButton("1) Add Transactions");
        addTransactionsButton.setBounds(100,100,150,50);
        addTransactionsButton.addActionListener(this);
        addTransactionsButton.setActionCommand("1");
        //this.next(addTransactionsButton.getActionCommand());

        //2)print transactions button
        this.printCurrentTransactionsButton = new JButton("2) Print Transactions");
        this.printCurrentTransactionsButton.setBounds(100,160,150,50);
        printCurrentTransactionsButton.setActionCommand("2");
        printCurrentTransactionsButton.addActionListener(this);
        //this.next(printCurrentTransactionsButton.getActionCommand());

        this.add(yourWorkbookLabel);
        this.add(addTransactionsButton);
        this.add(printCurrentTransactionsButton);
    }

    public String next(String actionCommand){
        System.out.println(actionCommand);
        return actionCommand;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.next(e.getActionCommand());
    }
}
