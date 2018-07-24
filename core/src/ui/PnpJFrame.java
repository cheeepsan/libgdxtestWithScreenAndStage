package ui;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * STOLEN FROM
 * https://stackoverflow.com/questions/15694107/how-to-layout-multiple-panels-on-a-jframe-java
 * @GameDroids
 */
public class PnpJFrame extends javax.swing.JFrame{

    // these are the components we need.
    private final JSplitPane splitPane;
    private final JPanel leftPanel;
    private final JPanel rightPanel;
    private final JTabbedPane tabPane;

//    private final JTable characterTable;

    public PnpJFrame(){

        setPreferredSize(new Dimension(400, 400));
        getContentPane().setLayout(new GridLayout());

        tabPane = new JTabbedPane();
        splitPane = new JSplitPane();

        leftPanel = new JPanel();
        rightPanel = new JPanel();

//        Vector<String> rowData = new Vector<String>();
//        Vector<String> columnNames = new Vector<String>();
//
//        rowData.add("1");
//        rowData.add("2");
//        columnNames.add("Slot");
//        columnNames.add("Equiped");
//        characterTable = new JTable(rowData, columnNames);



        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(200);
        splitPane.setTopComponent(leftPanel);
        splitPane.setBottomComponent(rightPanel);

        tabPane.addTab("Character", splitPane);

        getContentPane().add(tabPane);


        pack();
    }
}