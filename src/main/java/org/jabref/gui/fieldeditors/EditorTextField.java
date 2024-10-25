package org.jabref.gui.fieldeditors;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.input.KeyEvent;

import org.jabref.gui.ClipBoardManager;
import org.jabref.gui.entryeditor.EntryEditor;
import org.jabref.gui.fieldeditors.contextmenu.EditorContextAction;
import org.jabref.gui.keyboard.KeyBindingRepository;
import org.jabref.model.entry.field.Field;

public class EditorTextField extends TextField implements Initializable, ContextMenuAddable {

    private final ContextMenu contextMenu = new ContextMenu();
    public static TabPane tabs;
    public Field currentField;

    public void setCurrentField(Field field){
        currentField = field;
    }
    private Runnable additionalPasteActionHandler = () -> {
        // No additional paste behavior
    };



    public EditorTextField() {

        this("");

        // Add an event filter to handle key press events
        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            // If the TAB key is pressed and the TextArea is empty
             String keyText = event.getText();

                 if ("\t".equals(keyText) && EntryEditor.checkLastTextField(tabs, this)) {
                     tabs.getSelectionModel().selectNext();


                // Consume the event to prevent further processing of the TAB key
                event.consume();
            }
        });
    }




    public EditorTextField(final String text) {
        super(text);

        // Always fill out all the available space
        setPrefHeight(Double.POSITIVE_INFINITY);
        HBox.setHgrow(this, Priority.ALWAYS);

        ClipBoardManager.addX11Support(this);
    }

    @Override
    public void initContextMenu(final Supplier<List<MenuItem>> items, KeyBindingRepository keyBindingRepository) {
        setOnContextMenuRequested(event -> {
            contextMenu.getItems().setAll(EditorContextAction.getDefaultContextMenuItems(this));
            contextMenu.getItems().addAll(0, items.get());
            contextMenu.show(this, event.getScreenX(), event.getScreenY());
        });
    }

    public static void entryContext(TabPane tab){
        tabs = tab;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // not needed
    }

    public void setAdditionalPasteActionHandler(Runnable handler) {
        Objects.requireNonNull(handler);
        this.additionalPasteActionHandler = handler;
    }

    @Override
    public void paste() {
        super.paste();
        additionalPasteActionHandler.run();
    }
}
