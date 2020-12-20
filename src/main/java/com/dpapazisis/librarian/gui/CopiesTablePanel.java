/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.gui.componentmodels.CopiesTableModel;
import com.dpapazisis.librarian.model.readable.Readable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CopiesTablePanel extends JPanel {
    private final Readable readable;
    private JTable myTable;
    private GridBagConstraints constraints;
    private CopiesTableModel data;

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
        CopiesTableModel copiesTableModel = new CopiesTableModel(readable);
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
                int row = table.getSelectedRow();
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
    }
    //TODO: Adjust the edit function for multiple copies to be available in the CopiesTablePanel
}
