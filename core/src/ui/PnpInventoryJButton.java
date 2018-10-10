package ui;

import pnpObject.PnpItem;
import pnpObject.PnpObject;

import javax.swing.*;

public class PnpInventoryJButton extends JButton {

    private PnpItem item;

    public PnpInventoryJButton(PnpItem item) {
        super(item.name);
        this.item = item;
    }

    public PnpObject getItem() {
        return this.item;
    }
}
