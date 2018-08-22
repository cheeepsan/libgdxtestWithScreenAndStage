package ui;

import pnpObject.PnpItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PnpInventoryButtonListener implements ActionListener {

    private PnpJFrame parentFrame;
    private PnpItem item;

    public PnpInventoryButtonListener(PnpJFrame frame, PnpItem item) {
        this.parentFrame = frame;
        this.item = item;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.parentFrame.populateItemView(this.item);
    }
}
