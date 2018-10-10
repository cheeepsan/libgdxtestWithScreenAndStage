package ui;

import pnpObject.PnpItem;
import pnpObject.PnpUnit;

import javax.swing.*;
import java.awt.*;


public class PnpListCellRenderer extends JButton implements ListCellRenderer<JButton> {

    @Override
    public Component getListCellRendererComponent(JList<? extends JButton> list, JButton value, int index, boolean isSelected, boolean cellHasFocus) {
        value.setFocusPainted(false);
        value.setContentAreaFilled(false);
        return value;
    }

}
