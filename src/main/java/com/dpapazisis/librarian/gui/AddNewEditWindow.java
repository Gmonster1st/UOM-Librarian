/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import javax.swing.*;
import java.awt.*;

public class AddNewEditWindow extends JDialog {

    public AddNewEditWindow(Frame owner, String title) {
        super(owner, title);
        Dimension minSize = new Dimension(750, 320);
        setMinimumSize(minSize);
        setPreferredSize(minSize);
        setResizable(false);
        pack();
    }
}
