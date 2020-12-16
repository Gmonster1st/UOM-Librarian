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

public class DetailsPanel extends JPanel {
    private Readable readable;
    private final boolean back;
    private GridBagConstraints constraints;

    private boolean edit = false;


    public DetailsPanel(Readable readable, boolean back) {
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

        Dimension minWindowSize = new Dimension(800, 300);
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
        JTextField title = getjTextField(GRIDX_START, "Title:", readable.getTitle(), edit, 1, 2);
        add(title, constraints);
        //endregion

        //region Create and add Year text field and label (editable)
        JTextField year = getjTextField(GRIDX_MIDDLE, "Year:", readable.getYear().toString(), edit, 1, 2);
        add(year, constraints);
        //endregion

        //region Create and add Reference Code text field and label
        JTextField reference = getjTextField(GRIDX_END, "Reference Code:", readable.getReferenceCode().getCode(), false, 1, 2);
        add(reference, constraints);
        //endregion

        //region Create and add Subject text field and label
        JTextField subject = getjTextField(GRIDX_START, "Subject:", readable.getSubject().getName(), false, 3, 4);
        add(subject, constraints);
        //endregion

        //region Create and add Pages text field and label (editable)
        JTextField pages = getjTextField(GRIDX_MIDDLE, "Pages:", String.valueOf(readable.getPages()), edit, 3, 4);
        add(pages, constraints);
        //endregion

        //region Create and add Lend status text field and label
        JTextField lend = getjTextField(GRIDX_END, "Lend:", String.valueOf(readable.isLend()), false, 3, 4);
        add(lend, constraints);
        //endregion

        if (readable instanceof Book) {
            var book = (Book) readable;

            //region Create and add ISBN text field and label
            JTextField isbn = getjTextField(GRIDX_START, "ISBN:", book.getISBN(), false, 5, 6);
            add(isbn, constraints);
            //endregion

            //region Create and add Publisher text field and label
            JTextField publisher = getjTextField(GRIDX_MIDDLE, "Publisher:", book.getPublisher().getName(), false, 5, 6);
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
            JTextField isbn = getjTextField(GRIDX_START, "ISBN:", periodical.getISBN(), false, 5, 6);
            add(isbn, constraints);
            //endregion

            //region Create and add Publisher text field and label
            JTextField publisher = getjTextField(GRIDX_MIDDLE, "Publisher:", periodical.getPublisher().getName(), false, 5, 6);
            add(publisher, constraints);
            //endregion

            //region Create and add Volume and Issue text field and label
            String volIssueValue = "Volume: " + periodical.getVolume() + " Issue: " + periodical.getIssue();
            JTextField volIssue = getjTextField(GRIDX_END, "Volume and Issue:", volIssueValue, false, 5, 6);
            add(volIssue, constraints);
            //endregion
        }

        if (readable instanceof Thesis) {
            var thesis = (Thesis) readable;

            //region Create and add Thesis type text field and label
            JTextField type = getjTextField(GRIDX_START, "Type:", thesis.getType().toString(), false, 5, 6);
            add(type, constraints);
            //endregion

            //region Create and add Author text field and label
            String authorValue = thesis.getAuthor().getName() + " " + thesis.getAuthor().getSurname();
            JTextField author = getjTextField(GRIDX_MIDDLE, "Author:", authorValue, false, 5, 6);
            add(author, constraints);
            //endregion

            //region Create and add Professor text field and label
            String professorValue = thesis.getSupervisor().getName() + " " + thesis.getSupervisor().getSurname();
            JTextField professor = getjTextField(GRIDX_END, "Professor:", professorValue, false, 5, 6);
            add(professor, constraints);
            //endregion

            //region Create and add University text field and label (editable)
            JTextField university = getjTextField(GRIDX_MIDDLE, "University:", thesis.getUniversity(), edit, 7, 8);
            add(university, constraints);
            //endregion (Editab

            //region Create and add Department text field and label (editable)
            JTextField department = getjTextField(GRIDX_END, "Department:", thesis.getDepartment(), edit, 7, 8);
            add(department, constraints);
            //endregion
        }

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

        footerPanel.add(deleteButton, footerConstrains);

        JButton editButton = new JButton("Edit");
        editButton.setBounds(65, 60, 120, 30);
        editButton.addActionListener(e -> {
            if (!back) {
                AddNewEditWindow editWindow = new AddNewEditWindow(Frame.getFrames()[0], "Edit readable");
                editWindow.setContentPane(new AddNewEditPanel(readable, false));
                editWindow.setVisible(true);
                dispose();
            }
        });

        footerConstrains.gridx = 4;
        footerConstrains.gridy = 1;
        footerPanel.add(editButton, footerConstrains);

        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.weighty = 0.5;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        add(footerPanel, constraints);
        //endregion
    }

    private JTextField getjTextField(int guixSpot, String label, String value, boolean edit, int labelRow, int textFieldRow) {
        JLabel titleLabel = new JLabel(label);
        JTextField title = new JTextField(value);
        title.setEditable(edit);
        constraints.gridx = guixSpot;
        constraints.gridy = labelRow;
        add(titleLabel, constraints);
        constraints.gridy = textFieldRow;
        return title;
    }

    private void dispose() {
        var parentWindow = (DetailsWindow) getRootPane().getParent();
        parentWindow.dispose();
    }

    private void goBack() {
        JPanel root = (JPanel) DetailsPanel.this.getParent();
        CardLayout cardLayout = (CardLayout) root.getLayout();
        cardLayout.show(root, "CopiesTable");
    }
}
