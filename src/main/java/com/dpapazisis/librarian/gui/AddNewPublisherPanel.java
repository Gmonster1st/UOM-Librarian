/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.model.publisher.Publisher;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class AddNewPublisherPanel extends JPanel {
    private static final int GRIDX_LEFT = 0;
    private static final int GRIDX_RIGHT = 2;

    private final JTextField name;
    private final JTextField website;
    private final JTextField email;

    public AddNewPublisherPanel() {
        Dimension size = new Dimension(300, 150);
        setMinimumSize(size);
        setPreferredSize(size);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name: ");
        name = new JTextField();
        name.setColumns(20);

        constraints.gridy = 0;
        constraints.gridx = GRIDX_LEFT;
        add(nameLabel, constraints);
        constraints.gridx = GRIDX_RIGHT;
        add(name, constraints);

        JLabel websiteLabel = new JLabel("Website: ");
        website = new JTextField();
        constraints.gridy = 1;
        constraints.gridx = GRIDX_LEFT;
        add(websiteLabel, constraints);
        constraints.gridx = GRIDX_RIGHT;
        add(website, constraints);

        JLabel emailLabel = new JLabel("Email: ");
        email = new JTextField();
        constraints.gridy = 2;
        constraints.gridx = GRIDX_LEFT;
        add(emailLabel, constraints);
        constraints.gridx = GRIDX_RIGHT;
        add(email, constraints);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                URL url = null;
                try {
                    url = new URL(website.getText());
                } catch (MalformedURLException malformedURLException) {
                    malformedURLException.printStackTrace();
                }

                Publisher publisher = new Publisher(
                        name.getText(),
                        url,
                        email.getText()
                );
                LibraryService.getInstance().addPublisher(publisher);

                JDialog dialog = (JDialog) getRootPane().getParent();
                dialog.dispose();
            }
        });
        add(new JLabel());
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy = 3;
        add(addButton, constraints);
    }
}
