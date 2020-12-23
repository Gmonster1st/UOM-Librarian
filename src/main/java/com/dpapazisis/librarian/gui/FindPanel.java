/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.gui.componentmodels.SearchTableModel;
import com.dpapazisis.librarian.model.readable.Readable;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FindPanel extends JPanel {
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String KEY_WORD = "keyWord";
    private final transient LibraryService libraryService = LibraryService.getInstance();
    private SearchTableModel data;
    private final GridBagConstraints constraints;

    public FindPanel() {
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        setMainTable();
    }

    private void setMainTable() {
        data = new SearchTableModel();
        Dimension headerDimension = new Dimension(770, 40);
        JPanel header = new JPanel();
        header.setPreferredSize(headerDimension);
        header.setBorder(BorderFactory.createEtchedBorder());
        JTextField search = new JTextField();
        JButton find = new JButton("Find");
        search.setColumns(20);
        header.add(search);
        header.add(find);

        JRadioButton byTitle = new JRadioButton("By Title");
        byTitle.setActionCommand(TITLE);
        byTitle.setSelected(true);
        JRadioButton byAuthor = new JRadioButton("By Author");
        byAuthor.setActionCommand(AUTHOR);
        JRadioButton byKeyWord = new JRadioButton("By Key Word");
        byKeyWord.setActionCommand(KEY_WORD);

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(byTitle);
        radioGroup.add(byAuthor);
        radioGroup.add(byKeyWord);

        find.addActionListener(e -> {
            String searchTerm = search.getText();
            switch (radioGroup.getSelection().getActionCommand()) {
                case TITLE:
                    data.searchTableByTitle(searchTerm);
                    break;
                case AUTHOR:
                    data.searchTableByAuthor(searchTerm);
                    break;
                case KEY_WORD:
                    data.searchTableByKeyWord(searchTerm);
                    break;
                default:
            }
        });

        header.add(byTitle);
        header.add(byAuthor);
        header.add(byKeyWord);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(header, constraints);
        JTable myTable = new JTable(data);
        myTable.setAutoCreateRowSorter(true);
        myTable.setPreferredScrollableViewportSize(new Dimension(800, 600));
        myTable.setFillsViewportHeight(true);
        myTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = (JTable) e.getSource();
                int viewRow = table.getSelectedRow();
                int row = table.convertRowIndexToModel(viewRow);
                if (e.getClickCount() == 2 && row >= 0) {
                    Readable readable = data.getReadableAt(row);
                    DetailsWindow details = new DetailsWindow(
                            Frame.getFrames()[0],
                            "Readable Details",
                            readable,
                            data.getCopiesCount(row)
                    );
                    details.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    details.setResizable(false);
                    details.pack();
                    details.setLocationRelativeTo(Frame.getFrames()[0]);
                    details.setVisible(true);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(myTable);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.PAGE_END;
        scrollPane.setPreferredSize(new Dimension(400, 250));
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        add(scrollPane, constraints);
    }
}
