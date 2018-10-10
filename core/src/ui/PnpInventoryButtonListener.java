package ui;

import pnpObject.PnpItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

    /**
     *
     *
     @Override
     public void mouseClicked(MouseEvent mouseEvent) {
     System.out.println("1");
     this.parentFrame.populateItemView(this.item);

     }

     @Override
     public void mousePressed(MouseEvent mouseEvent) {
     System.out.println("2");
     }

     @Override
     public void mouseReleased(MouseEvent mouseEvent) {
     System.out.println("3");
     }

     @Override
     public void mouseEntered(MouseEvent mouseEvent) {
     System.out.println("4");
     }

     @Override
     public void mouseExited(MouseEvent mouseEvent) {
     System.out.println("5");
     }
     */
}
