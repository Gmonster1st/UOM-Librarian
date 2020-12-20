/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui.componentmodels;

import com.dpapazisis.librarian.model.publisher.Publisher;
import com.dpapazisis.librarian.services.LibraryAction;
import com.dpapazisis.librarian.services.LibraryObserver;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PublisherComboBoxModel extends AbstractListModel<Publisher> implements ComboBoxModel<Publisher>, LibraryObserver {
    private final transient LibraryService libraryService = LibraryService.getInstance();
    private List<Publisher> publishers;
    private Publisher selection;

    public PublisherComboBoxModel() {
        libraryService.addObserver(this);
        getData();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (Publisher) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public int getSize() {
        return publishers.size();
    }

    @Override
    public Publisher getElementAt(int index) {
        return publishers.get(index);
    }

    @Override
    public void update(LibraryAction action) {
        getData();
        fireContentsChanged(this, 0, getSize());
    }

    private void getData() {
        publishers = new ArrayList<>(libraryService.getPublishers());
    }
}
