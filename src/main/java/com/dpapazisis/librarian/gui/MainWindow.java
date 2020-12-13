/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.gui.tablemodels.ReadableTableModel;
import com.dpapazisis.librarian.model.readable.Readable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This is the main window of the application
 * it is mostly designed for the proof of concept.
 * Definitely could be better
 */
public class MainWindow extends JFrame {
    public static final String FILE = "File";
    private final JPanel mainPanel;
    private final JButton button;
    private JMenuBar jMenuBar;
    private JTable myTable;
    private GridBagConstraints constraints;
    private ReadableTableModel data;


    public MainWindow(String title) {
        super(title);
        this.mainPanel = new JPanel();
        this.button = new JButton("ClickMe!");
        jMenuBar = new JMenuBar();
        setMainPanel();
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    private void setMainPanel() {
        GridBagLayout layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        Dimension minWindowSize = new Dimension(800, 600);
        mainPanel.setLayout(layout);
        mainPanel.setMinimumSize(minWindowSize);
        mainPanel.setPreferredSize(minWindowSize);
        setContent();
        mainPanel.setOpaque(true);
    }

    private void setContent() {
        setMenuBar();
        setMainTable();
        setButton();
    }

    private void setMainTable() {
        data = new ReadableTableModel();
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
                    Readable readable = data.getReadableAt(row);
                    JDialog details = new DetailsWindow(MainWindow.this, "Readable Details", readable);
                    details.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                    details.setSize(500, 500);
                    details.setResizable(false);
                    details.pack();
                    details.setLocationRelativeTo(MainWindow.this);
                    details.setVisible(true);
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
        mainPanel.add(scrollPane, constraints);
    }

    private void setMenuBar() {
        jMenuBar = new JMenuBar();
        JMenu fileMenu = createMenu(FILE, "File Operations", KeyEvent.VK_F);
        fileMenu.add(createMenuItem("AddNew", "AddNew Record", e -> newRecordDialog()));
        jMenuBar.add(fileMenu);
        setJMenuBar(jMenuBar);
    }

    private JMenu createMenu(String menuName, String menuDescription, int keyEvent) {
        JMenu menu = new JMenu(menuName);
        menu.setMnemonic(keyEvent);
        menu.getAccessibleContext().setAccessibleDescription(menuDescription);
        return menu;
    }

    private JMenuItem createMenuItem(String text, String description, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.getAccessibleContext().setAccessibleDescription(description);
        menuItem.addActionListener(listener);
        return menuItem;
    }

    private void newRecordDialog() {
        JDialog dialog = new AddNewWindow(MainWindow.this, "Add New Record");
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setSize(250, 250);
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(MainWindow.this);
        dialog.setVisible(true);
    }

    //    TODO:Remove or find a purpose for this button
    private void setButton() {
        button.addActionListener(e -> JOptionPane.showMessageDialog(mainPanel, "This is my Message"));
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.RELATIVE;
        constraints.gridheight = 1;
        constraints.insets = new Insets(10, 0, 10, 0);
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.PAGE_END;
        mainPanel.add(button, constraints);
    }

    public Dimension getMainPanelSize() {
        return mainPanel.getSize();
    }
}
