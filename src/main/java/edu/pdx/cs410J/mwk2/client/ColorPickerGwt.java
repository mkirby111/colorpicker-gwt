package edu.pdx.cs410J.mwk2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A basic GWT class that takes user input for a color in a given text format (RGB, CMYK, or Hex)
 * and returns the color in all formats, including a visual representation.
 */
public class ColorPickerGwt implements EntryPoint {

    private DockPanel appPanel = new DockPanel();
    private SimplePanel colorPanel = new SimplePanel();
    private VerticalPanel centerPanel = new VerticalPanel();
    private StackPanel colorFormatPanel = new StackPanel();
    private Grid colorPickerFormHex = new Grid(2, 3);
    private Grid colorPickerFormName = new Grid(1, 3);
    private TextBox colorFieldHex = new TextBox();
    private TextBox colorFieldName = new TextBox();
    private ListBox colorListBox = new ListBox();
    private Label colorResults = new Label("aqua");
    private String colorName = "";
    private ArrayList<String> colorList = new ArrayList<>();

    public void onModuleLoad() {

        Label header = new Label("Color Picker");
        header.setStyleName("header");

        colorPanel.setStyleName("gwt-DecoratorPanel");
        colorPanel.getElement().getStyle().setBackgroundColor("aqua");

        Label colorLabelHex = new Label("Enter Hex color: ");
        Label hexExample = new Label("Example: #0000FF");
        Button colorButtonHex = new Button("Set Color");
        colorPickerFormHex.setWidget(0, 0, colorLabelHex);
        colorPickerFormHex.setWidget(0, 1, colorFieldHex);
        colorPickerFormHex.setWidget(0, 2, colorButtonHex);
        colorPickerFormHex.setWidget(1, 0, hexExample);
        colorPickerFormHex.setStyleName("colorPickerForm");

        colorList = Colors.getColorList();
        for (String str: colorList) {
            colorListBox.addItem(str);
        }
        colorListBox.setVisibleItemCount(1);

        Label colorLabelName = new Label("Select color name: ");
        Button colorButtonName = new Button("Set Color");
        colorPickerFormName.setWidget(0, 0, colorLabelName);
        colorPickerFormName.setWidget(0, 1, colorListBox);
        colorPickerFormName.setWidget(0, 2, colorButtonName);
        colorPickerFormName.setStyleName("colorPickerForm");

        colorFormatPanel.add(colorPickerFormHex, "Set Hex Color");
        colorFormatPanel.add(colorPickerFormName, "Set Name Color");
        colorFormatPanel.setPixelSize(400, 100);


        centerPanel.add(colorPanel);
        centerPanel.add(colorResults);
        centerPanel.add(colorFormatPanel);
        centerPanel.setStyleName("gwt-DecoratorPanel");

        colorResults.setStyleName("colorResults");

        // Listen for mouse events on the Set Hex Color button.
        colorButtonHex.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                getHexColor();
            }
        });

        // Listen for keyboard events in the input box.
        colorFieldHex.addKeyDownHandler(new KeyDownHandler() {
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    getHexColor();
                }
            }
        });

        // Listen for mouse events on the Set Name Color button.
        colorButtonName.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent clickEvent) {
                getNameColor();
            }
        });

        // Listen for keyboard events in the input box.
        colorListBox.addKeyDownHandler(new KeyDownHandler() {
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    getNameColor();
                }
            }
        });

        RootPanel rootPanel = RootPanel.get();
        appPanel.add(header, DockPanel.NORTH);
        appPanel.add(centerPanel, DockPanel.CENTER);
        appPanel.setStyleName("gwt-appPanel");
        rootPanel.add(appPanel);
    }

    private void getHexColor() {
        final String colorHex = colorFieldHex.getText();

        ColorPickerAsync async = GWT.create(ColorPicker.class);
        async.setHexColor(colorHex, new AsyncCallback<String>() {

            public void onFailure(Throwable ex) {
                if (ex instanceof ValidateInputException) {
                    Window.alert("Invalid input: " + ex.getMessage());
                } else {
                    Window.alert("onFailure: " + ex.toString());
                }
            }

            public void onSuccess(String colors) {
                colorPanel.getElement().getStyle().setBackgroundColor(colorHex);
                colorResults.setText(colors);
            }
        });
    }

    private void getNameColor() {
        colorName = colorListBox.getSelectedItemText();

        ColorPickerAsync async = GWT.create(ColorPicker.class);
        async.setNameColor(colorName, new AsyncCallback<String>() {

            public void onFailure(Throwable ex) {
                if (ex instanceof ValidateInputException) {
                    Window.alert("Invalid input: " + ex.getMessage());
                } else {
                    Window.alert("onFailure: " + ex.toString());
                }
            }

            public void onSuccess(String colors) {
                colorPanel.getElement().getStyle().setBackgroundColor(colorName);
                colorResults.setText(colors);
            }
        });
    }
}
