/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.model.readable.Book;
import com.dpapazisis.librarian.model.readable.Periodical;
import com.dpapazisis.librarian.model.readable.Readable;
import com.dpapazisis.librarian.model.readable.Thesis;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.util.stream.Collectors;

public class AddNewEditPanel extends JPanel {
    private Readable readable;
    private final boolean back;
    private GridBagConstraints constraints;

    private boolean edit = false;

    public AddNewEditPanel() {
        super();
        back = false;
        setMainPanel(); //TODO: add parameter to use as indicator for add or edit
    }

    public AddNewEditPanel(Readable readable, boolean back) {
        super();
        this.readable = readable;
        this.back = back;
        setMainPanel();
    }

    public void setReadable(Readable readable) {
        this.readable = readable;
    }

    private void setMainPanel() {
        GridBagLayout layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;

        Dimension minWindowSize = new Dimension(800, 600);
        this.setLayout(layout);
        this.setMinimumSize(minWindowSize);
        this.setPreferredSize(minWindowSize);
        setComponents();
        this.setOpaque(true);
    }

    private void setComponents() {
        final int GRIDX_START = 2;
        final int GRIDX_MIDDLE = 4;
        final int GRIDX_END = 6;

        constraints.insets = new Insets(1, 1, 5, 1);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        //region Create back button and copyId label for the header
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            goBack();
        });
        JLabel copyIdLabel = new JLabel("copy: " + readable.getCopyId());
        //endregion

        //region Create header panel, add back button , copyId label and add the header to the GridBagLayout
        Dimension headerDimension = new Dimension(800, 30);
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setPreferredSize(headerDimension);
        headerPanel.setMinimumSize(headerDimension);
        headerPanel.setBorder(BorderFactory.createEtchedBorder());
        headerPanel.add(backButton, BorderLayout.LINE_START);
        headerPanel.add(copyIdLabel, BorderLayout.LINE_END);
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(headerPanel, constraints);
        backButton.setVisible(back);
        copyIdLabel.setVisible(back);
        //endregion

        constraints.gridwidth = 1;

        //region Create and add Title text field and label (editable)
        constraints.weightx = 1.0;
        JLabel titleLabel = new JLabel("Title:");
        JTextField title = new JTextField(readable.getTitle());
        title.setEditable(edit);
        constraints.gridx = GRIDX_START;
        constraints.gridy = 1;
        add(titleLabel, constraints);
        constraints.gridy = 2;
        add(title, constraints);
        //endregion

        //region Create and add Year text field and label (editable)
        JLabel yearLabel = new JLabel("Year:");
        JTextField year = new JTextField(readable.getYear().toString());
        year.setEditable(edit);
        constraints.gridx = GRIDX_MIDDLE;
        constraints.gridy = 1;
        add(yearLabel, constraints);
        constraints.gridy = 2;
        add(year, constraints);
        //endregion

        //region Create and add Reference Code text field and label
        JLabel referenceLabel = new JLabel("Reference Code:");
        JTextField reference = new JTextField(readable.getReferenceCode().getCode());
        reference.setEditable(false);
        constraints.gridx = GRIDX_END;
        constraints.gridy = 1;
        add(referenceLabel, constraints);
        constraints.gridy = 2;
        add(reference, constraints);
        //endregion

        //region Create and add Subject text field and label
        JLabel subjectLabel = new JLabel("Subject:");
        JTextField subject = new JTextField(readable.getSubject().getName());
        subject.setEditable(false);
        constraints.gridx = GRIDX_START;
        constraints.gridy = 3;
        add(subjectLabel, constraints);
        constraints.gridy = 4;
        add(subject, constraints);
        //endregion

        //region Create and add Pages text field and label (editable)
        JLabel pagesLabel = new JLabel("Pages:");
        JTextField pages = new JTextField(String.valueOf(readable.getPages()));
        pages.setEditable(edit);
        constraints.gridx = GRIDX_MIDDLE;
        constraints.gridy = 3;
        add(pagesLabel, constraints);
        constraints.gridy = 4;
        add(pages, constraints);
        //endregion

        //region Create and add Lend status text field and label
        JLabel lendLabel = new JLabel("Lend:");
        JTextField lend = new JTextField(String.valueOf(readable.isLend()));
        lend.setEditable(false);
        constraints.gridx = GRIDX_END;
        constraints.gridy = 3;
        add(lendLabel, constraints);
        constraints.gridy = 4;
        add(lend, constraints);
        //endregion

        if (readable instanceof Book) {
            var book = (Book) readable;

            //region Create and add ISBN text field and label
            JLabel isbnLabel = new JLabel("ISBN:");
            JTextField isbn = new JTextField(book.getISBN());
            isbn.setEditable(false);
            constraints.gridx = GRIDX_START;
            constraints.gridy = 5;
            add(isbnLabel, constraints);
            constraints.gridy = 6;
            add(isbn, constraints);
            //endregion

            //region Create and add Publisher text field and label
            JLabel publisherLabel = new JLabel("Publisher:");
            JTextField publisher = new JTextField(book.getPublisher().getName());
            publisher.setEditable(false);
            constraints.gridx = GRIDX_MIDDLE;
            constraints.gridy = 5;
            add(publisherLabel, constraints);
            constraints.gridy = 6;
            add(publisher, constraints);
            //endregion

            //region Create and add Authors list field and label
            JLabel authorsLabel = new JLabel("Authors:");
            JList<String> authors = new JList<>(
                    new Vector<>(
                            (book.getAuthors()
                                    .stream()
                                    .map(o -> o.getName() + " " + o.getSurname())
                                    .collect(Collectors.toList())
                            )
                    )
            );
            authors.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane authorsScroller = new JScrollPane(authors);
            constraints.gridx = GRIDX_END;
            constraints.gridy = 5;
            add(authorsLabel, constraints);
            constraints.gridy = 6;
            constraints.gridheight = 2;
            Dimension scrollerSize = new Dimension(0, 60);
            authorsScroller.setMinimumSize(scrollerSize);
            authorsScroller.setPreferredSize(scrollerSize);
            add(authorsScroller, constraints);
            //endregion
        }

        if (readable instanceof Periodical) {
            var periodical = (Periodical) readable;

            //region Create and add ISBN text field and label
            JLabel isbnLabel = new JLabel("ISBN:");
            JTextField isbn = new JTextField(periodical.getISBN());
            isbn.setEditable(false);
            constraints.gridx = GRIDX_START;
            constraints.gridy = 5;
            add(isbnLabel, constraints);
            constraints.gridy = 6;
            add(isbn, constraints);
            //endregion

            //region Create and add Publisher text field and label
            JLabel publisherLabel = new JLabel("Publisher:");
            JTextField publisher = new JTextField(periodical.getPublisher().getName());
            publisher.setEditable(false);
            constraints.gridx = GRIDX_MIDDLE;
            constraints.gridy = 5;
            add(publisherLabel, constraints);
            constraints.gridy = 6;
            add(publisher, constraints);
            //endregion

            //region Create and add Volume and Issue text field and label
            JLabel volIssueLabel = new JLabel("Volume and Issue:");
            JTextField volIssue = new JTextField("Volume: " + periodical.getVolume() + " Issue: " + periodical.getIssue());
            volIssue.setEditable(false);
            constraints.gridx = GRIDX_END;
            constraints.gridy = 5;
            add(volIssueLabel, constraints);
            constraints.gridy = 6;
            add(volIssue, constraints);
            //endregion
        }

        if (readable instanceof Thesis) {
            var thesis = (Thesis) readable;

            //region Create and add Thesis type text field and label
            JLabel typeLabel = new JLabel("Type:");
            JTextField type = new JTextField(thesis.getType().toString());
            type.setEditable(false);
            constraints.gridx = GRIDX_START;
            constraints.gridy = 5;
            add(typeLabel, constraints);
            constraints.gridy = 6;
            add(type, constraints);
            //endregion

            //region Create and add Author text field and label
            JLabel authorLabel = new JLabel("Author:");
            JTextField author = new JTextField(thesis.getAuthor().getName() + " " + thesis.getAuthor().getSurname());
            author.setEditable(false);
            constraints.gridx = GRIDX_MIDDLE;
            constraints.gridy = 5;
            add(authorLabel, constraints);
            constraints.gridy = 6;
            add(author, constraints);
            //endregion

            //region Create and add Professor text field and label
            JLabel professorLabel = new JLabel("Professor:");
            JTextField professor = new JTextField(thesis.getSupervisor().getName() + " " + thesis.getSupervisor().getSurname());
            professor.setEditable(false);
            constraints.gridx = GRIDX_END;
            constraints.gridy = 5;
            add(professorLabel, constraints);
            constraints.gridy = 6;
            add(professor, constraints);
            //endregion

            //region Create and add University text field and label (editable)
            JLabel universityLabel = new JLabel("University:");
            JTextField university = new JTextField(thesis.getUniversity());
            university.setEditable(edit);
            constraints.gridx = GRIDX_MIDDLE;
            constraints.gridy = 7;
            add(universityLabel, constraints);
            constraints.gridy = 8;
            add(university, constraints);
            //endregion (Editab

            //region Create and add Department text field and label (editable)
            JLabel departmentLabel = new JLabel("Department:");
            JTextField department = new JTextField(thesis.getDepartment());
            department.setEditable(edit);
            constraints.gridx = GRIDX_END;
            constraints.gridy = 7;
            add(departmentLabel, constraints);
            constraints.gridy = 8;
            add(department, constraints);
            //endregion
        }

        //region Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(65, 60, 120, 30);
        deleteButton.addActionListener(e -> {
            LibraryService.getInstance().removeReadable(readable);
            if (back) {
                goBack();
            } else {
                dispose();
            }
        });

        footerPanel.add(deleteButton);

        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.weighty = 1.0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        add(footerPanel, constraints);
        //endregion
    }

    private void dispose() {
        var parentWindow = (DetailsWindow) getRootPane().getParent();
        parentWindow.dispose();
    }

    private void goBack() {
        JPanel root = (JPanel) AddNewEditPanel.this.getParent();
        CardLayout cardLayout = (CardLayout) root.getLayout();
        cardLayout.show(root, "CopiesTable");
    }
}
