/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.gui.componentmodels.CopiesTableModel;
import com.dpapazisis.librarian.model.readable.Readable;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CopiesTablePanel extends JPanel {
    private final Readable readable;
    private JTable myTable;
    private GridBagConstraints constraints;
    private CopiesTableModel data;
    private CopiesTableModel copiesTableModel;

    public CopiesTablePanel(Readable readable) {
        this.readable = readable;
        setMainPanel();
    }

    private void setMainPanel() {
        GridBagLayout layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        Dimension minWindowSize = new Dimension(800, 600);
        this.setLayout(layout);
        this.setMinimumSize(minWindowSize);
        this.setPreferredSize(minWindowSize);
        setMainTable();
        this.setOpaque(true);
    }

    private void setMainTable() {
        copiesTableModel = new CopiesTableModel(readable);
        data = copiesTableModel;
        data.addTableModelListener(e -> myTable.repaint());
        myTable = new JTable(data);
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
                    Readable selectedReadable = data.getReadableAt(row);
                    JPanel root = (JPanel) getParent();
                    DetailsPanel detailsPanel = new DetailsPanel(selectedReadable, true);
                    root.add(detailsPanel, "Details");
                    CardLayout cardLayout = (CardLayout) root.getLayout();
                    cardLayout.show(root, "Details");
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(myTable);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridheight = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.BOTH;
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        this.add(scrollPane, constraints);

        JButton editButton = new JButton("Edit");
        editButton.setBounds(65, 60, 120, 30);
        editButton.addActionListener(e -> {
            MainWindow mainWindow = (MainWindow) Frame.getFrames()[0];
            AddNewEditWindow editWindow = new AddNewEditWindow(mainWindow, "Edit readable");
            var editPanel = new AddNewEditPanel(readable, false);
            editPanel.setMultiCopyFunction(true);
            editWindow.setContentPane(editPanel);
            editWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            editWindow.setLocationRelativeTo(mainWindow);
            editWindow.setVisible(true);
        });

        constraints.gridy = 1;
        constraints.weightx = 0;
        constraints.weighty = 0;
        constraints.fill = GridBagConstraints.NONE;
        this.add(editButton, constraints);
    }


    /**
     * Clean Up resources
     */
    public void clean() {
        if (copiesTableModel != null) {
            LibraryService.getInstance().removeObserver(copiesTableModel);
        }
    }
}
