package org.jabref.gui.entryeditor;

import org.jabref.model.entry.field.Field;

public class FieldManager {
    private static FieldManager instance;
    private Field currentField;

    private FieldManager() {} // Private constructor

    public static FieldManager getInstance() {
        if (instance == null) {
            instance = new FieldManager();
        }
        return instance;
    }

    public void setCurrentField(Field field) {
        currentField = field;
    }

    public Field getCurrentField() {
        return currentField;
    }
}


