package ua.samosfator.gmmtools;

import ua.samosfator.gmmtools.gui.ActionPicker;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    ActionPicker.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}