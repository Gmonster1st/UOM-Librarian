/*
 * Copyright (c) 2020.
 * Dimitrios Papazisis
 * This Software is to be used for educational purposes only.
 * All rights Reserved.
 */

package com.dpapazisis.librarian.gui;

import com.dpapazisis.librarian.categories.Classifier;
import com.dpapazisis.librarian.categories.Subject;
import com.dpapazisis.librarian.gui.componentmodels.*;
import com.dpapazisis.librarian.model.person.Author;
import com.dpapazisis.librarian.model.person.Professor;
import com.dpapazisis.librarian.model.publisher.Publisher;
import com.dpapazisis.librarian.model.readable.Readable;
import com.dpapazisis.librarian.model.readable.*;
import com.dpapazisis.librarian.services.LibraryAction;
import com.dpapazisis.librarian.services.LibraryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.util.concurrent.Callable;

import static java.lang.Integer.parseInt;
import static java.time.Year.parse;
import static java.util.stream.Collectors.groupingBy;

public class AddNewEditPanel extends JPanel {
    private static final int GRIDX_START = 2;
    private static final int GRIDX_MIDDLE = 4;
    private static final int GRIDX_END = 6;

    private final transient LibraryService libraryService = LibraryService.getInstance();
    private final PublisherComboBoxModel publisherComboBoxModel = new PublisherComboBoxModel();
    private Readable readable;
    private final boolean back;
    private GridBagConstraints constraints;
    private String type = "";
    private JTextField title;
    private JTextField year;
    private JComboBox<Subject> subject;
    private JTextField pages;
    private JCheckBox lend;
    private JTextField isbn;
    private JComboBox<Publisher> publisher;
    private JList<Author> bookAuthors;
    private JTextField volume;
    private JTextField issue;
    private JComboBox<ThesisType> thesisType;
    private JComboBox<Author> thesisAuthor;
    private JComboBox<Professor> professor;
    private JTextField university;
    private JTextField department;
    private AuthorComboBoxModel authorComboBoxModel;
    private ProfessorComboBoxModel professorComboBoxModel;
    private AuthorListModel authorListModel;
    private boolean multiCopyOperation;
    private AuthorListModel readableAuthorListModel;

    public AddNewEditPanel(String type) {
        super();
        this.type = type;
        back = false;
        setMainPanel();
    }

    public AddNewEditPanel(Readable readable, boolean back) {
        super();
        this.readable = readable;
        this.back = back;
        setMainPanel();
    }

    public void setReadable(Readable readable) {
        this.readable = readable;
    }

    public void setMultiCopyFunction(boolean state) {
        multiCopyOperation = state;
    }

    private void setMainPanel() {
        GridBagLayout layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.PAGE_START;

        this.setLayout(layout);
        setComponents();
        this.setOpaque(true);
    }

    private void setComponents() {
        constraints.insets = new Insets(1, 1, 5, 1);
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridwidth = 1;

        //region Create and add Title text field and label (editable)
        constraints.weightx = 1.0;
        Dimension titleSize = new Dimension(300, 20);
        title = setTextField(GRIDX_START, titleSize, "Title:", 1, 2);
        //endregion

        //region Create and add Year text field and label (editable)
        Dimension yearSize = new Dimension(50, 20);
        year = setTextField(GRIDX_MIDDLE, yearSize, "Year:", 1, 2);
        //endregion

        //region Create and add Reference Code text field and label
        Dimension referenceSize = new Dimension(100, 20);
        JLabel referenceLabel = new JLabel("Reference Code:");
        JTextField reference = new JTextField();
        reference.setMinimumSize(referenceSize);
        reference.setPreferredSize(referenceSize);
        reference.setEditable(false);
        constraints.gridx = GRIDX_END;
        constraints.gridy = 1;
        add(referenceLabel, constraints);
        constraints.gridy = 2;
        add(reference, constraints);
        //endregion

        //region Create and add Subject ComboBox and label
        JLabel subjectLabel = new JLabel("Subject:");
        subject = new JComboBox<>(new SubjectComboBoxModel());
        constraints.gridx = GRIDX_START;
        constraints.gridy = 3;
        add(subjectLabel, constraints);
        constraints.gridy = 4;
        add(subject, constraints);
        //endregion

        //region Create and add Pages text field and label (editable)
        Dimension pagesSize = new Dimension(50, 20);
        pages = setTextField(GRIDX_MIDDLE, pagesSize, "Pages:", 3, 4);
        //endregion

        //region Create and add Lend status CheckBox and label
        lend = new JCheckBox("is Lend");
        constraints.gridx = GRIDX_END;
        constraints.gridy = 4;
        add(lend, constraints);
        //endregion

        setReadableValuesForEdit(reference);

        Dimension isbnSize = new Dimension(130, 20);
        Dimension publisherSize = new Dimension(130, 20);
        Dimension fullNameSize = new Dimension(150, 20);

        setBookFields(isbnSize, publisherSize);

        setPeriodicalFields(isbnSize, publisherSize);

        setThesisFields(fullNameSize);

        if (isbn != null) {
            isbn.setToolTipText("<html>10-digit ISBN format ex. \"0-19-852663-6\"<br> 13-digit ISBN format ex. \"978-3-16-148410-0\"</html>");
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

        JSpinner numOfCopies = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        numOfCopies.setPreferredSize(new Dimension(60, 20));

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(65, 60, 120, 30);
        saveButton.addActionListener(e -> {
            switch (type) {
                case Classifier.BOOK:
                    newBook((Integer) numOfCopies.getValue());
                    break;
                case Classifier.PERIODICAL:
                    newPeriodical((Integer) numOfCopies.getValue());
                    break;
                case Classifier.THESIS:
                    newThesis((Integer) numOfCopies.getValue());
                    break;
                default:
                    saveToLibrary();
                    return;
            }
            dispose();
        });

        if (readable != null) {
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
            footerConstrains.gridx = 4;
            footerConstrains.gridy = 1;
        }

        footerPanel.add(saveButton, footerConstrains);

        if (!type.equals("")) {
            JLabel copiesLabel = new JLabel("Number of Copies: ");
            footerConstrains.gridx = 5;
            footerConstrains.insets = new Insets(0, 0, 0, 0);
            footerConstrains.anchor = GridBagConstraints.LINE_END;
            footerPanel.add(copiesLabel, footerConstrains);
            footerConstrains.gridx = 6;
            footerConstrains.anchor = GridBagConstraints.LINE_START;
            footerPanel.add(numOfCopies, footerConstrains);
        }

        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.weighty = 0.5;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        add(footerPanel, constraints);
        //endregion
    }

    private void saveToLibrary() {
        if (multiCopyOperation) {
            var copies = libraryService.getLibrary()
                    .stream()
                    .collect(groupingBy(Readable::isCopy))
                    .get(readable.isCopy());
            for (var item : copies) {
                saveReadable(item);
            }
        } else {
            saveReadable();
        }
    }

    private void setThesisFields(Dimension fullNameSize) {
        if (readable instanceof Thesis || type.equals(Classifier.THESIS)) {
            assert readable instanceof Thesis;
            var thesis = (Thesis) readable;

            //region Create and add Thesis type ComboBox and label
            Dimension typeSize = new Dimension(100, 20);
            JLabel typeLabel = new JLabel("Type:");
            thesisType = new JComboBox<>(ThesisType.values());
            thesisType.setMinimumSize(typeSize);
            thesisType.setPreferredSize(typeSize);
            constraints.gridx = GRIDX_START;
            constraints.gridy = 5;
            add(typeLabel, constraints);
            constraints.gridy = 6;
            add(thesisType, constraints);
            //endregion

            //region Create and add Author ComboBox and label
            JLabel authorLabel = new JLabel("Author:");
            authorComboBoxModel = new AuthorComboBoxModel();
            thesisAuthor = new JComboBox<>(authorComboBoxModel);
            thesisAuthor.setMinimumSize(fullNameSize);
            thesisAuthor.setPreferredSize(fullNameSize);
            constraints.gridx = GRIDX_MIDDLE;
            constraints.gridy = 5;
            add(authorLabel, constraints);
            constraints.gridy = 6;
            add(thesisAuthor, constraints);
            addEditInfoDialog(GRIDX_MIDDLE, "New Author", new AddNewAuthorPanel(), "Add", () -> null);
            //endregion

            //region Create and add Professor ComboBox and label
            JLabel professorLabel = new JLabel("Professor:");
            professorComboBoxModel = new ProfessorComboBoxModel();
            professor = new JComboBox<>(professorComboBoxModel);
            professor.setMinimumSize(fullNameSize);
            professor.setPreferredSize(fullNameSize);
            constraints.gridx = GRIDX_END;
            constraints.gridy = 5;
            add(professorLabel, constraints);
            constraints.gridy = 6;
            add(professor, constraints);
            addEditInfoDialog(GRIDX_END, "New Professor", new AddNewProfessorPanel(), "Add", () -> null);
            //endregion

            //region Create and add University text field and label
            university = setTextField(GRIDX_MIDDLE, fullNameSize, "University:", 7, 8);
            //endregion

            //region Create and add Department text field and label
            department = setTextField(GRIDX_END, fullNameSize, "Department:", 7, 8);
            //endregion

            //region Set Thesis Values for edit
            if (readable != null) {
                thesisAuthor.setSelectedItem(thesis.getAuthor());
                thesisType.setSelectedItem(thesis.getType());
                professor.setSelectedItem(thesis.getSupervisor());
                university.setText(thesis.getUniversity());
                department.setText(thesis.getDepartment());
            }
            //endregion
        }
    }

    private void setPeriodicalFields(Dimension isbnSize, Dimension publisherSize) {
        if (readable instanceof Periodical || type.equals(Classifier.PERIODICAL)) {
            assert readable instanceof Periodical;
            var periodical = (Periodical) readable;

            //region Create and add ISBN text field and label
            isbn = setTextField(GRIDX_START, isbnSize, "ISBN:", 5, 6);
            //endregion

            //region Create and add Publisher ComboBox and label
            JLabel publisherLabel = new JLabel("Publisher:");
            publisher = new JComboBox<>(publisherComboBoxModel);
            publisher.setMinimumSize(publisherSize);
            publisher.setPreferredSize(publisherSize);
            constraints.gridx = GRIDX_MIDDLE;
            constraints.gridy = 5;
            add(publisherLabel, constraints);
            constraints.gridy = 6;
            add(publisher, constraints);
            addEditInfoDialog(GRIDX_MIDDLE, "New Publisher", new AddNewPublisherPanel(), "Add", () -> null);
            //endregion

            //region Create and add Volume and Issue text field and label
            Dimension volIssueSize = new Dimension(50, 20);
            JLabel volIssueLabel = new JLabel("Volume and Issue:");
            volume = new JTextField();
            issue = new JTextField();
            volume.setMinimumSize(volIssueSize);
            issue.setMinimumSize(volIssueSize);
            volume.setPreferredSize(volIssueSize);
            issue.setPreferredSize(volIssueSize);
            constraints.gridx = GRIDX_END;
            constraints.gridy = 5;
            add(volIssueLabel, constraints);
            constraints.gridy = 6;
            add(volume, constraints);
            constraints.anchor = GridBagConstraints.CENTER;
            add(issue, constraints);
            //endregion

            //region Set Periodical Values for edit
            if (readable != null) {
                isbn.setText(periodical.getISBN());
                publisher.setSelectedItem(periodical.getPublisher());
                volume.setText(String.valueOf(periodical.getVolume()));
                issue.setText(String.valueOf(periodical.getIssue()));
            }
            //endregion
        }
    }

    private void setBookFields(Dimension isbnSize, Dimension publisherSize) {
        if (readable instanceof Book || type.equals(Classifier.BOOK)) {
            assert readable instanceof Book;
            var book = (Book) readable;

            //region Create and add ISBN text field and label
            isbn = setTextField(GRIDX_START, isbnSize, "ISBN:", 5, 6);
            //endregion

            //region Create and add Publisher ComboBox and label
            JLabel publisherLabel = new JLabel("Publisher:");
            publisher = new JComboBox<>(publisherComboBoxModel);
            publisher.setMinimumSize(publisherSize);
            publisher.setPreferredSize(publisherSize);
            constraints.gridx = GRIDX_MIDDLE;
            constraints.gridy = 5;
            add(publisherLabel, constraints);
            constraints.gridy = 6;
            add(publisher, constraints);
            addEditInfoDialog(GRIDX_MIDDLE, "Add Publisher", new AddNewPublisherPanel(), "Add", () -> null);
            //endregion

            //region Create and add Authors list field and label
            JLabel authorsLabel = new JLabel("Authors:");
            bookAuthors = new JList<>();
            bookAuthors.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane authorsScroller = new JScrollPane(bookAuthors);
            constraints.gridx = GRIDX_END;
            constraints.gridy = 5;
            add(authorsLabel, constraints);
            constraints.gridy = 6;
            constraints.gridheight = 2;
            Dimension scrollerSize = new Dimension(160, 60);
            authorsScroller.setMinimumSize(scrollerSize);
            authorsScroller.setPreferredSize(scrollerSize);
            add(authorsScroller, constraints);
            //endregion

            //region Set Book Values for edit
            if (readable != null) {
                isbn.setText(book.getISBN());
                publisher.setSelectedItem(book.getPublisher());
                readableAuthorListModel = new AuthorListModel(book);
                bookAuthors.setModel(readableAuthorListModel);
                EditAuthorsPanel editAuthorsPanel = new EditAuthorsPanel(readable, readableAuthorListModel);
                addEditInfoDialog(GRIDX_END, "Edit Authors", editAuthorsPanel, "Edit", () -> {
                    editAuthorsPanel.dispose();
                    return null;
                });
            } else {
                authorListModel = new AuthorListModel();
                bookAuthors.setModel(authorListModel);
                addEditInfoDialog(GRIDX_END, "Add Authors", new AddNewAuthorPanel(), "Add", () -> null);
            }
            //endregion
        }
    }

    private void addEditInfoDialog(int relativePosition, String title, JPanel panel, String buttonText, Callable<Void> method) {
        constraints.gridx = relativePosition + 1;
        JButton button = new JButton(buttonText);
        button.addActionListener(actionEvent -> {
            JDialog dialog = new JDialog((Dialog) AddNewEditPanel.this.getRootPane().getParent());
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.addHierarchyListener(hierarchyEvent -> {
                if (hierarchyEvent.getChangeFlags() == HierarchyEvent.SHOWING_CHANGED && !dialog.isShowing()) {
                    try {
                        method.call();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    dialog.dispose();
                }
            });
            dialog.setTitle(title);
            dialog.setContentPane(panel);
            dialog.setResizable(false);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
        add(button, constraints);
    }

    private void setReadableValuesForEdit(JTextField reference) {
        if (readable != null) {
            title.setText(readable.getTitle());
            year.setText(readable.getYear().toString());
            pages.setText(String.valueOf(readable.getPages()));
            reference.setText(readable.getReferenceCode().getCode());
            lend.setSelected(readable.isLend());
            subject.setSelectedItem(readable.getSubject());
        } else {
            subject.setSelectedIndex(0);
        }
    }

    private void saveReadable() {
        if (readable != null) {
            readable.setTitle(title.getText());
            readable.setYear(parse(year.getText()));
            readable.setPages(parseInt(pages.getText()));
            readable.setLendStatus(lend.isSelected());
            readable.setSubject((Subject) subject.getSelectedItem());

            if (readable instanceof Book) {
                var book = (Book) readable;
                book.setISBN(isbn.getText());
                book.setPublisher((Publisher) publisher.getSelectedItem());
                book.setAuthors(readableAuthorListModel.getAuthors());
            }

            if (readable instanceof Periodical) {
                var periodical = (Periodical) readable;
                periodical.setISBN(isbn.getText());
                periodical.setPublisher((Publisher) publisher.getSelectedItem());
                periodical.setVolume(parseInt(volume.getText()));
                periodical.setIssue(parseInt(issue.getText()));
            }

            if (readable instanceof Thesis) {
                var thesis = (Thesis) readable;
                thesis.setType((ThesisType) thesisType.getSelectedItem());
                thesis.setAuthor((Author) thesisAuthor.getSelectedItem());
                thesis.setSupervisor((Professor) professor.getSelectedItem());
                thesis.setUniversity(university.getText());
                thesis.setDepartment(department.getText());
            }
            libraryService.notifyObservers(LibraryAction.EDIT);
            dispose();
        }
    }

    private void saveReadable(Readable readable) {
        if (readable != null) {
            readable.setTitle(title.getText());
            readable.setYear(parse(year.getText()));
            readable.setPages(parseInt(pages.getText()));
            readable.setLendStatus(lend.isSelected());
            readable.setSubject((Subject) subject.getSelectedItem());

            if (readable instanceof Book) {
                var book = (Book) readable;
                book.setISBN(isbn.getText());
                book.setPublisher((Publisher) publisher.getSelectedItem());
                book.setAuthors(readableAuthorListModel.getAuthors());
            }

            if (readable instanceof Periodical) {
                var periodical = (Periodical) readable;
                periodical.setISBN(isbn.getText());
                periodical.setPublisher((Publisher) publisher.getSelectedItem());
                periodical.setVolume(parseInt(volume.getText()));
                periodical.setIssue(parseInt(issue.getText()));
            }

            if (readable instanceof Thesis) {
                var thesis = (Thesis) readable;
                thesis.setType((ThesisType) thesisType.getSelectedItem());
                thesis.setAuthor((Author) thesisAuthor.getSelectedItem());
                thesis.setSupervisor((Professor) professor.getSelectedItem());
                thesis.setUniversity(university.getText());
                thesis.setDepartment(department.getText());
            }
            libraryService.notifyObservers(LibraryAction.EDIT);
            dispose();
        }
    }

    private void newThesis(int copies) {
        var thesis = new Thesis.Builder(
                title.getText(),
                parse(year.getText()),
                parseInt(pages.getText()),
                (Subject) subject.getSelectedItem()
        )
                .ofType((ThesisType) thesisType.getSelectedItem())
                .withAuthor((Author) thesisAuthor.getSelectedItem())
                .withSupervisor((Professor) professor.getSelectedItem())
                .fromUniversity(university.getText())
                .andDepartment(department.getText());
        if (copies < 2) {
            libraryService.addNewReadable(thesis.build());
        } else {
            libraryService.addMultiCopyReadable(thesis.build(copies));
        }
    }

    private void newPeriodical(int copies) {
        var periodical = new Periodical.Builder(
                title.getText(),
                parse(year.getText()),
                parseInt(pages.getText()),
                (Subject) subject.getSelectedItem()
        )
                .withISBN(isbn.getText())
                .andPublisher((Publisher) publisher.getSelectedItem())
                .isVolume(parseInt(volume.getText()))
                .isIssue(parseInt(issue.getText()));
        if (copies < 2) {
            libraryService.addNewReadable(periodical.build());
        } else {
            libraryService.addMultiCopyReadable(periodical.build(copies));
        }
    }

    private void newBook(int copies) {
        var book = new Book.Builder(
                title.getText(),
                parse(year.getText()),
                parseInt(pages.getText()),
                (Subject) subject.getSelectedItem()
        )
                .withISBN(isbn.getText())
                .andPublisher((Publisher) publisher.getSelectedItem())
                .withAuthors(bookAuthors.getSelectedValuesList());
        if (copies < 2) {
            libraryService.addNewReadable(book.build());
        } else {
            libraryService.addMultiCopyReadable(book.build(copies));
        }
    }

    private JTextField setTextField(int xPosition, Dimension size, String label, int labelRow, int textFieldRow) {
        JLabel jLabel = new JLabel(label);
        JTextField textField = new JTextField();
        textField.setMinimumSize(size);
        textField.setPreferredSize(size);
        constraints.gridx = xPosition;
        constraints.gridy = labelRow;
        add(jLabel, constraints);
        constraints.gridy = textFieldRow;
        add(textField, constraints);
        return textField;
    }

    private void dispose() {
        if (authorComboBoxModel != null) {
            libraryService.removeObserver(authorComboBoxModel);
        }
        if (professorComboBoxModel != null) {
            libraryService.removeObserver(professorComboBoxModel);
        }
        if (authorListModel != null) {
            libraryService.removeObserver(authorListModel);
        }
        libraryService.removeObserver(publisherComboBoxModel);
        var parentWindow = (AddNewEditWindow) getRootPane().getParent();
        parentWindow.dispose();
    }

    private void goBack() {
        JPanel root = (JPanel) AddNewEditPanel.this.getParent();
        CardLayout cardLayout = (CardLayout) root.getLayout();
        cardLayout.show(root, "CopiesTable");
    }
}
