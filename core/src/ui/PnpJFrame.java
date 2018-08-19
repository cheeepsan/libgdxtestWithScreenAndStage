package ui;

import com.badlogic.gdx.utils.Array;
import pnpObject.PnpItem;
import pnpObject.PnpObject;
import pnpObject.PnpUnit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PnpJFrame extends javax.swing.JFrame{


    private JSplitPane equipmentSplitPane;
    private JPanel equipmentLeftPanel;
    private JPanel equipmentRightPanel;

    private JSplitPane inventorySplitPane;
    private JPanel inventoryLeftPanel;
    private JPanel inventoryRightPanel;

    private JTabbedPane tabPane;

    private PnpObject currentObject = null;
    public PnpJFrame(PnpObject object){
        this.currentObject = object;
        this.buildGUI();
        this.populateGUI();
    }

    protected JList populateEquipment(PnpUnit unit) {
        JList<JButton> list = new JList<JButton>();

        return list;
    }
    protected JList populateInventory(PnpUnit unit) {
        DefaultListModel<JButton> model = new DefaultListModel<JButton>();
        JList list = new JList(model);
        Array<PnpItem> itemList = unit.getInventory();

        for (PnpItem i : itemList) {
            System.out.println("Adding: " + i.name);
            model.addElement(new JButton(i.name));
        }
        System.out.println("LIST: " + list.toString());

        list.setDragEnabled(true);

        return list;
    }

    protected void populateGUI() {
        switch (this.currentObject.getObjectType()) {
            case "unit":
                PnpUnit unit = (PnpUnit)this.currentObject;
                JList<JButton> eq = this.populateEquipment(unit);
                JList<JButton> inv = this.populateInventory(unit);

                this.equipmentLeftPanel.add(eq);
                this.inventoryLeftPanel.add(inv);
                break;
            default: break;
        }
    }

    protected void buildGUI() {

        setPreferredSize(new Dimension(600 , 600));
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
}