/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.services.LibraryService;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddNewAuthorPanel extends JPanel {
    private static final int GRIDX_LEFT = 0;
    private static final int GRIDX_RIGHT = 2;

    private final JTextField name;
    private final JTextField surname;
    private final DatePicker birthDate;
    private final JTextArea shortBio;
    private final JTextField email;

    public AddNewAuthorPanel() {
        Dimension size = new Dimension(300, 250);
        setMinimumSize(size);
        setPreferredSize(size);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name: ");
        name = new JTextField();

        constraints.gridy = 0;
        constraints.gridx = GRIDX_LEFT;
        add(nameLabel, constraints);
        constraints.gridx = GRIDX_RIGHT;
        add(name, constraints);

        JLabel surnameLabel = new JLabel("Surname: ");
        surname = new JTextField();
        constraints.gridy = 1;
        constraints.gridx = GRIDX_LEFT;
        add(surnameLabel, constraints);
        constraints.gridx = GRIDX_RIGHT;
        add(surname, constraints);

        JLabel birthDateLabel = new JLabel("Birth Date: ");
        birthDate = new DatePicker();

        constraints.gridy = 2;
        constraints.gridx = GRIDX_LEFT;
        add(birthDateLabel, constraints);
        constraints.gridx = GRIDX_RIGHT;
        add(birthDate, constraints);

        JLabel shortBioLabel = new JLabel("Short Bio: ");
        shortBio = new JTextArea(5, 20);
        shortBio.setLineWrap(true);
        constraints.gridy = 3;
        constraints.gridx = GRIDX_LEFT;
        add(shortBioLabel, constraints);
        constraints.gridx = GRIDX_RIGHT;
        constraints.fill = GridBagConstraints.BOTH;
        add(shortBio, constraints);

        JLabel emailLabel = new JLabel("Email: ");
        email = new JTextField();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 6;
        constraints.gridx = GRIDX_LEFT;
        add(emailLabel, constraints);
        constraints.gridx = GRIDX_RIGHT;
        add(email, constraints);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Author author = new Author(
                        name.getText(),
                        surname.getText(),
                        birthDate.getDate(),
                        shortBio.getText(),
                        email.getText()
                );
                LibraryService.getInstance().addAuthor(author);

                JDialog dialog = (JDialog) getRootPane().getParent();
                dialog.dispose();
            }
        });
        add(new JLabel());
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy = 7;
        add(addButton, constraints);
    }
}
