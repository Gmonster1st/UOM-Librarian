/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian;

import com.dpapazisis.librarian.gui.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 * UOM-Librarian application
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::run);

    }

    private static void run() {
        MainWindow app = new MainWindow("UOM-Librarian");
        Point screenCenter = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        Point computedCenter = new Point(
                (int) (screenCenter.getX() - (app.getMainPanelSize().getWidth() / 2)),
                (int) (screenCenter.getY() - (app.getMainPanelSize().getHeight() / 2)));
        app.setLocation(computedCenter);
        app.setVisible(true);
    }
}
