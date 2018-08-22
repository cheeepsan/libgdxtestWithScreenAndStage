package ui;


import com.badlogic.gdx.utils.Array;
import pnpObject.PnpItem;
import pnpObject.PnpObject;
import pnpObject.PnpUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class PnpJFrame extends javax.swing.JFrame {


    private JSplitPane equipmentSplitPane;
    private JPanel equipmentLeftPanel;
    private JPanel equipmentRightPanel;

    private JSplitPane inventorySplitPane;
    private JPanel inventoryLeftPanel;
    private JPanel inventoryRightPanel;

    private JTabbedPane tabPane;

    private PnpObject currentObject = null;

    public PnpJFrame(PnpObject object) {
        this.currentObject = object;
        this.buildGUI();
        this.populateGUI();
    }

    protected void populateGUI() {
        switch (this.currentObject.getObjectType()) {
            case "unit":
                PnpUnit unit = (PnpUnit) this.currentObject;
                JList<JButton> eq = this.populateEquipment(unit);
                JList<JButton> inv = this.populateInventory(unit);

                eq.setFixedCellWidth(this.equipmentSplitPane.getDividerLocation());
                inv.setFixedCellWidth(this.inventorySplitPane.getDividerLocation());
                this.equipmentLeftPanel.add(eq);
                this.inventoryLeftPanel.add(inv);


                break;
            default:
                break;
        }
    }

    protected void buildGUI() {

        setPreferredSize(new Dimension(600, 600));
        getContentPane().setLayout(new GridLayout());

        tabPane = new JTabbedPane();
        equipmentSplitPane = new JSplitPane();
        equipmentLeftPanel = new JPanel();
        equipmentRightPanel = new JPanel();

        inventorySplitPane = new JSplitPane();
        inventoryLeftPanel = new JPanel();
        inventoryRightPanel = new JPanel();

        equipmentSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        equipmentSplitPane.setDividerLocation(300);
        equipmentSplitPane.setTopComponent(equipmentLeftPanel);
        equipmentSplitPane.setBottomComponent(equipmentRightPanel);

        inventorySplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        inventorySplitPane.setDividerLocation(300);
        inventorySplitPane.setTopComponent(inventoryLeftPanel);
        inventorySplitPane.setBottomComponent(inventoryRightPanel);


        tabPane.addTab("Equipment", equipmentSplitPane);
        tabPane.addTab("Inventory", inventorySplitPane);


        getContentPane().add(tabPane);

        pack();
    }

    /**
     *
     * EQUIPMENT
     *
     */

    protected JList populateEquipment(PnpUnit unit) {
        DefaultListModel<JButton> model = new DefaultListModel<JButton>();
        JList list = new JList(model);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new PnpListCellRenderer());

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                clickButtonAt(event.getPoint(), list);
            }
        });

        Array<PnpItem> itemList = unit.getInventory();

        for (PnpItem i : itemList) {
            System.out.println("Adding: " + i.name);
            JButton button = new JButton(i.name);
            button.addActionListener(new PnpInventoryButtonListener(this, i));
            model.addElement(button);
        }

        return list;
    }

    /**
     *
     * INVENTORY
     *
     */

    protected JList populateInventory(PnpUnit unit) {
        DefaultListModel<JButton> model = new DefaultListModel<JButton>();
        JList list = new JList(model);

        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new PnpListCellRenderer());

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                clickButtonAt(event.getPoint(), list);
            }
        });

        Array<PnpItem> itemList = unit.getInventory();

        for (PnpItem i : itemList) {
            System.out.println("Adding: " + i.name);
            JButton button = new JButton(i.name);
            button.addActionListener(new PnpInventoryButtonListener(this, i));
            model.addElement(button);
        }

        return list;
    }

    private void clickButtonAt(Point point, JList list) {
        int index = list.locationToIndex(point);
        JButton item = (JButton) list.getModel().getElementAt(index);
        item.doClick();
    }

    public void populateItemView(PnpItem item   ) {
        Label itemLabel = new Label(item.name);

        this.inventoryRightPanel.removeAll();
        this.inventoryRightPanel.add(itemLabel);
        this.inventoryRightPanel.revalidate();
    }

}