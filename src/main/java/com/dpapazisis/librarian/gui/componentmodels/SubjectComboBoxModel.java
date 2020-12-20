/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui.componentmodels;

import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectComboBoxModel extends AbstractListModel<Subject> implements ComboBoxModel<Subject> {
    private final List<Subject> subjects;
    private Subject selection;

    public SubjectComboBoxModel() {
        LibraryService libraryService = LibraryService.getInstance();
        this.subjects = new ArrayList<>(libraryService.getSubjects());
    }

    @Override
    public int getSize() {
        return subjects.size();
    }

    @Override
    public Subject getElementAt(int index) {
        return subjects.get(index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (Subject) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }
}
