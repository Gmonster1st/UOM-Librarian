/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.gui.componentmodels.AuthorListModel;
import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.readable.Book;
import com.dpapazisis.librarian.model.readable.Readable;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyEvent;

public class EditAuthorsPanel extends JPanel {
    private static final int GRIDX_LEFT = 0;
    private static final int GRIDX_CENTER = 2;
    private static final int GRIDX_RIGHT = 4;

    private final JButton addButton;
    private final JButton removeButton;
    private final LibraryService library = LibraryService.getInstance();
    private final JList<Author> bookAuthors;
    private final AuthorListModel allAuthorListModel;
    private AuthorListModel bookAuthorListModel;

    public EditAuthorsPanel(Readable readable, AuthorListModel readableModel) {
        Book book;
        if (readable instanceof Book) {
            book = (Book) readable;
        } else {
            throw new IllegalArgumentException();
        }
        Dimension size = new Dimension(500, 300);
        setMinimumSize(size);
        setPreferredSize(size);
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        //region Create and add Library Authors list field and label
        JLabel libraryAuthorsLabel = new JLabel("Library Authors:");
        allAuthorListModel = new AuthorListModel();
        JList<Author> allAuthors = new JList<>(allAuthorListModel);
        allAuthors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane allAuthorsScroller = new JScrollPane(allAuthors);
        constraints.gridx = GRIDX_LEFT;
        constraints.gridy = 0;
        add(libraryAuthorsLabel, constraints);
        constraints.gridy = 1;
        constraints.gridheight = GridBagConstraints.RELATIVE;
        Dimension allAuthorsScrollerSize = new Dimension(150, 200);
        allAuthorsScroller.setMinimumSize(allAuthorsScrollerSize);
        allAuthorsScroller.setPreferredSize(allAuthorsScrollerSize);
        add(allAuthorsScroller, constraints);
        //endregion

        addButton = new JButton(">>");
        addButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var selectedIndex = allAuthors.getSelectedIndex();
                if (selectedIndex >= 0) {
                    bookAuthorListModel.addAuthorToView(allAuthorListModel.getElementAt(selectedIndex));
                }
            }
        });

        constraints.gridheight = 1;
        constraints.gridy = 5;
        constraints.gridx = GRIDX_CENTER;
        add(addButton, constraints);

        removeButton = new JButton("<<");
        removeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var selectedIndex = bookAuthors.getSelectedIndex();
                if (selectedIndex >= 0) {
                    bookAuthorListModel.removeAuthorFromView(selectedIndex);
                }
            }
        });
        constraints.gridy = 6;
        constraints.gridx = GRIDX_CENTER;
        add(removeButton, constraints);

        //region Create and add Book Authors list field and label
        JLabel bookAuthorsLabel = new JLabel("Book Authors:  << Max: 5 >>");
        bookAuthorListModel = new AuthorListModel(book);
        bookAuthors = new JList<>(bookAuthorListModel);
        bookAuthors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane bookAuthorsScroller = new JScrollPane(bookAuthors);
        constraints.gridx = GRIDX_RIGHT;
        constraints.gridy = 0;
        add(bookAuthorsLabel, constraints);
        constraints.gridy = 1;
        constraints.gridheight = GridBagConstraints.RELATIVE;
        Dimension bookScrollerSize = new Dimension(150, 200);
        bookAuthorsScroller.setMinimumSize(bookScrollerSize);
        bookAuthorsScroller.setPreferredSize(bookScrollerSize);
        add(bookAuthorsScroller, constraints);
        //endregion

        JButton addNewButton = new JButton("Add");
        addNewButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog((Dialog) EditAuthorsPanel.this.getRootPane().getParent());
                dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                dialog.addHierarchyListener(hierarchyEvent -> {
                    if (hierarchyEvent.getChangeFlags() == HierarchyEvent.SHOWING_CHANGED && !dialog.isShowing()) {
                        dialog.dispose();
                    }
                });
                dialog.setTitle("Add Author");
                dialog.setContentPane(new AddNewAuthorPanel());
                dialog.setResizable(false);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        add(new JLabel());
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = GRIDX_CENTER;
        constraints.gridy = 7;
        add(addNewButton, constraints);

        //region Footer
        JPanel footerPanel = new JPanel(new GridBagLayout());
        var footerConstrains = new GridBagConstraints();
        footerConstrains.gridx = 1;
        footerConstrains.gridy = 1;
        footerConstrains.fill = GridBagConstraints.RELATIVE;
        footerConstrains.gridheight = 1;
        footerConstrains.insets = new Insets(10, 50, 10, 50);
        footerConstrains.weighty = 1.0;
        footerConstrains.anchor = GridBagConstraints.CENTER;

        footerPanel.setBorder(BorderFactory.createEtchedBorder());

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(65, 60, 120, 30);
        saveButton.addActionListener(e -> {
            readableModel.setAuthorsInView(bookAuthorListModel.getAuthors());
            dispose();
        });

        footerPanel.add(saveButton, footerConstrains);

        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.weighty = 0.5;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        add(footerPanel, constraints);
        //endregion
    }


    public void dispose() {
        library.removeObserver(allAuthorListModel);
        library.removeObserver(bookAuthorListModel);
        JDialog dialog = (JDialog) getRootPane().getParent();
        dialog.dispose();
    }
}
