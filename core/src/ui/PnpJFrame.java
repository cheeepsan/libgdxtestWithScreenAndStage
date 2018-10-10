package ui;


import pnpObject.*;
import pnpObject.pnpTypes.ItemType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class PnpJFrame extends javax.swing.JFrame {


    private JSplitPane equipmentSplitPane;
    private JPanel equipmentContainer;
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
            case UNIT:
                PnpUnit unit = (PnpUnit) this.currentObject;
                JList<JButton> eq = this.populateEquipment(unit);
                JList<JButton> inv = this.populateInventory(unit);
                JTable eqTable = this.getEquipmentTable(unit);

                eq.setFixedCellWidth(this.equipmentSplitPane.getDividerLocation());
                inv.setFixedCellWidth(this.inventorySplitPane.getDividerLocation());

                this.equipmentLeftPanel.add(eq);
                this.inventoryLeftPanel.add(inv);
                this.equipmentRightPanel.add(eqTable);


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
        equipmentContainer = new JPanel();
        equipmentLeftPanel = new JPanel();
        equipmentRightPanel = new JPanel();

        inventorySplitPane = new JSplitPane();
        inventoryLeftPanel = new JPanel();
        inventoryRightPanel = new JPanel();

        equipmentSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        equipmentSplitPane.setDividerLocation(300);
        equipmentSplitPane.setTopComponent(equipmentLeftPanel);
        equipmentSplitPane.setBottomComponent(equipmentContainer);

        inventorySplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        inventorySplitPane.setDividerLocation(300);
        inventorySplitPane.setTopComponent(inventoryLeftPanel);
        inventorySplitPane.setBottomComponent(inventoryRightPanel);

        equipmentContainer.add(equipmentRightPanel);

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

        list.addMouseListener(new PnpInventoryMouseAdapter(list, unit, this));

        ArrayList<PnpItem> itemList = unit.getInventory().stream().filter(eq -> eq.getItemType() == ItemType.EQUIPMENT).collect(Collectors.toCollection(ArrayList::new));

        for (PnpItem i : itemList) {
            JButton button = new PnpInventoryJButton(i);
            button.addActionListener(new PnpInventoryButtonListener(this, i));
            model.addElement(button);
        }

        return list;
    }

    public void reloadEquipment(PnpUnit unit) {

        this.equipmentRightPanel.removeAll();
        this.equipmentRightPanel.add(this.getEquipmentTable(unit));
        this.equipmentRightPanel.revalidate();
    }

    public JTable getEquipmentTable(PnpUnit unit) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn(new Object[]{"Slotname"});
        model.addColumn(new Object[]{"Slotdata"});
        JTable table = new JTable(model);

        Map<PnpUnitSlot, PnpItem> eqMap = unit.getEquipment();

        eqMap.forEach((name, slot) -> {
            String slotValue = "EMPTY";
            if ( slot != null && slot.name != null) {
                slotValue = slot.name;
            }
            System.out.println("Adding to slot: " + name.type);
            model.addRow(new Object[]{
                    PnpUnitSlot.getSlotName(name.type),
                    slotValue
            });
        });

        return table;
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

        list.addMouseListener(new PnpInventoryMouseAdapter(list, unit, this));

        ArrayList<PnpItem> itemList = unit.getInventory();

        for (PnpItem i : itemList) {
            JButton button = new PnpInventoryJButton(i);
            button.addActionListener(new PnpInventoryButtonListener(this, i));
            model.addElement(button);
        }

        return list;
    }



    public void populateItemView(PnpItem item   ) {
        Label itemLabel = new Label(item.name);
        Label itemDescription = new Label(item.description);

        this.inventoryRightPanel.removeAll();
        this.inventoryRightPanel.add(itemLabel);
        this.inventoryRightPanel.add(itemDescription);
        this.inventoryRightPanel.revalidate();
    }

    /**
     *
     *
     * OTHER
     *
     */

}