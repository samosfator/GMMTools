package ua.samosfator.gmm.tools;

import ua.samosfator.gmm.tools.gui.ActionPicker;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                ActionPicker.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}