/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui.componentmodels;

import com.dpapazisis.librarian.model.person.Professor;
import com.dpapazisis.librarian.services.LibraryAction;
import com.dpapazisis.librarian.services.LibraryObserver;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorComboBoxModel extends AbstractListModel<Professor> implements ComboBoxModel<Professor>, LibraryObserver {
    private final transient LibraryService libraryService = LibraryService.getInstance();
    private List<Professor> professors;
    private Professor selection;

    public ProfessorComboBoxModel() {
        libraryService.addObserver(this);
        getData();
    }

    private void getData() {
        professors = new ArrayList<>(libraryService.getProfessors());
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (Professor) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public int getSize() {
        return professors.size();
    }

    @Override
    public Professor getElementAt(int index) {
        return professors.get(index);
    }

    @Override
    public void update(LibraryAction action) {
        getData();
        fireContentsChanged(this, 0, getSize());
    }
}
