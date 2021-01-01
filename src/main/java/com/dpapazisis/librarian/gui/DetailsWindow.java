/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.model.readable.Readable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DetailsWindow extends JDialog {
    private final Readable readable;
    private final JPanel mainPanel;
    private final long copiesCount;

    public DetailsWindow(Frame owner, String title, Readable readable, long copiesCount) {
        super(owner, title);
        this.readable = readable;
        this.copiesCount = copiesCount;
        this.mainPanel = new JPanel(new CardLayout(5, 5));
        Dimension minSize = new Dimension(700, 350);
        mainPanel.setMinimumSize(minSize);
        mainPanel.setPreferredSize(minSize);
        setPreferredSize(minSize);
        setPanels();
        setContentPane(mainPanel);
        pack();
    }

    private void setPanels() {
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        if (copiesCount > 1) {
            CopiesTablePanel copiesTablePanel = new CopiesTablePanel(readable);
            mainPanel.add(copiesTablePanel, "CopiesTable");
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    copiesTablePanel.clean();
                    super.windowClosed(e);
                }
            });
        } else {
            DetailsPanel detailsPanel = new DetailsPanel(readable, false);
            mainPanel.add(detailsPanel, "Details");
            cardLayout.show(mainPanel, "Details");
        }
    }
}
