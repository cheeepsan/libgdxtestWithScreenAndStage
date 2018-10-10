package ui;

import pnpObject.PnpEquipment;
import pnpObject.PnpItem;
import pnpObject.PnpObject;
import pnpObject.PnpUnit;
import pnpObject.pnpTypes.ObjectType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PnpJPopMenu extends JPopupMenu {

    private PnpObject object;
    private PnpUnit unit;
    private PnpJFrame parent;

    public PnpJPopMenu(PnpObject object, PnpUnit unit, PnpJFrame parent) {
        this.object = object;
        this.unit = unit;
        this.parent = parent;
        this.init();
    }

    private void init() {
        switch (object.getObjectType()) {
            case ITEM:
                this.initItem();
                break;
            default:
                break;
        }
    }

    private void initItem() {
        PnpItem item = (PnpItem)this.object;
        switch (item.getItemType()) {
            case EQUIPMENT:
                PnpEquipment equipment = (PnpEquipment)item;
                JMenuItem equipButton = new JMenuItem("Equip");
                equipButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        unit.getEquipment().put(equipment.getSlot(), equipment);
                        parent.reloadEquipment(unit);
                    }
                });
                super.add(equipButton);
        }
    }

}
