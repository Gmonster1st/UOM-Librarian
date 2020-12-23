/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.model.person.Author;

import javax.swing.*;
import java.awt.*;

public class AddAuthorsPanel extends JPanel {

    private JList<Author> bookAuthors;

    public AddAuthorsPanel() {
        setPanel();
    }

    private void setPanel() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;

        this.setLayout(layout);
        this.setOpaque(true);

        JLabel authorsLabel = new JLabel("Authors:");
        bookAuthors = new JList<>();
        bookAuthors.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane authorsScroller = new JScrollPane(bookAuthors);
        constraints.gridx = 1;
        constraints.gridy = 5;
        add(authorsLabel, constraints);
        constraints.gridy = 6;
        constraints.gridheight = 2;
        Dimension scrollerSize = new Dimension(0, 60);
        authorsScroller.setMinimumSize(scrollerSize);
        authorsScroller.setPreferredSize(scrollerSize);
        add(authorsScroller, constraints);
    }
}
