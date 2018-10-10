package ui;

import pnpObject.PnpObject;
import pnpObject.PnpUnit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PnpInventoryMouseAdapter extends MouseAdapter {

    private JList list;
    private PnpUnit unit;
    private PnpJFrame parent;

    public PnpInventoryMouseAdapter(JList list, PnpUnit unit, PnpJFrame parent){
        this.unit = unit;
        this.parent = parent;
        this.list = list;
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        switch (event.getButton()) {
            case 1: //left
                clickButtonAt(event.getPoint(), list);
                break;
            case 3: //right
                popMenu(event.getPoint(), event, list);
                break;
            default:
                break;
        }
    }

    private void clickButtonAt(Point point, JList list) {
        int index = list.locationToIndex(point);
        JButton item = (PnpInventoryJButton) list.getModel().getElementAt(index);
        item.doClick();
    }

    private void popMenu(Point point, MouseEvent e, JList list){
        int index = list.locationToIndex(point);
        JButton itemButton = (PnpInventoryJButton) list.getModel().getElementAt(index);
        PnpObject item = ((PnpInventoryJButton) itemButton).getItem();
        PnpJPopMenu menu = new PnpJPopMenu(item, unit, parent);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}
