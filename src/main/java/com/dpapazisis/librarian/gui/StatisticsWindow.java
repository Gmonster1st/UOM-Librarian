/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.model.readable.ThesisType;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.awt.*;

public class StatisticsWindow extends JDialog {
    private final transient LibraryService libraryService = LibraryService.getInstance();

    public StatisticsWindow(Frame owner, String title) {
        super(owner, title);
        Dimension size = new Dimension(750, 150);
        setPreferredSize(size);
        setContentPane(createPanel());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridheight = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel totalItems = new JLabel("Total item: " + libraryService.getLibrary().size());
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(totalItems, constraints);

        JLabel totalBooks = new JLabel("Total Books: " + libraryService.getNumberOfBooks());
        constraints.gridx = 3;
        panel.add(totalBooks, constraints);

        JLabel totalPeriodical = new JLabel("Total Periodicals: " + libraryService.getNumberOfPeriodicals());
        constraints.gridx = 5;
        panel.add(totalPeriodical, constraints);

        JLabel totalThesis = new JLabel("Total Thesis: " + libraryService.getNumberOfThesis());
        constraints.gridx = 7;
        panel.add(totalThesis, constraints);

        var thesisMap = libraryService.getNumbersOfTypesOfThesis();
        JLabel totalGraduates = new JLabel("Total Graduate Thesis: " + thesisMap.get(ThesisType.GRADUATE));
        constraints.gridx = 3;
        constraints.gridy = 3;
        panel.add(totalGraduates, constraints);

        JLabel totalPostGraduates = new JLabel("Total Post Graduate Thesis: " + thesisMap.get(ThesisType.POST_GRADUATE));
        constraints.gridx = 5;
        panel.add(totalPostGraduates, constraints);

        JLabel totalDoctoral = new JLabel("Total Doctoral Thesis: " + thesisMap.get(ThesisType.DOCTORAL));
        constraints.gridx = 7;
        panel.add(totalDoctoral, constraints);


        return panel;
    }


}
