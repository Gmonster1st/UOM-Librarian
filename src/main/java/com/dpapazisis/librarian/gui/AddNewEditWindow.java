/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.model.publisher.Publisher;
import com.dpapazisis.librarian.model.readable.Periodical;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Year;

public class AddNewEditWindow extends JDialog {
    private final JPanel mainPanel;

    public AddNewEditWindow(Frame owner, String title) {
        super(owner, title);
        this.mainPanel = new AddNewEditPanel(setTestBook(), false);
        Dimension minSize = new Dimension(700, 550);
        mainPanel.setMinimumSize(minSize);
        mainPanel.setPreferredSize(minSize);
        setPreferredSize(minSize);
//        setPanels();
        setContentPane(mainPanel);
        pack();
    }

    //    TODO:Remove this method
    private Periodical setTestBook() {
        Year year = Year.parse("2020");
        int pages = 500;
        URL url = null;
        try {
            url = new URL("http://www.example.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Publisher publisher = new Publisher("Editions", url, "edit@myeditions.com");

        Subject subject = new Subject("ComputerScience", "100");

        return new Periodical.Builder("TestPeriodical", year, pages, subject)
                .withISBN("978-3-16-148410-0")  // Make Step builder
                .isVolume(8)
                .isIssue(18)
                .andPublisher(publisher)
                .build();
    }

//    private void setPanels() {
//        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
//        if (copiesCount > 1) {
//            CopiesTablePanel copiesTablePanel = new CopiesTablePanel(readable);
//            mainPanel.add(copiesTablePanel, "CopiesTable");
//        } else {
//            DetailsPanel detailsPanel = ;
//            mainPanel.add(detailsPanel, "Details");
//            cardLayout.show(mainPanel, "Details");
//        }
//    }
}
