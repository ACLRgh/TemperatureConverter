package com.mycompany.temperatureconverter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TemperatureConverter extends JFrame {
    private final List<String> units = Arrays.asList("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)");
    private JComboBox<String> fromUnitSelection;
    private JComboBox<String> toUnitSelection;
    private JTextField inputValue;
    private JLabel resultLabel = new JLabel("");


    public TemperatureConverter() {
        setTitle("Temperature Converter");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER,50,20));

        JLabel welcome = new JLabel("Welcome to Temperature Converter with Java Swing!");
        add(welcome);
        
        //initial options
        JLabel fromUnitLabel = new JLabel("Choose which unit to convert the temperature from:");
        add(fromUnitLabel);
        fromUnitSelection = new JComboBox<>(new Vector<>(units));
        add(fromUnitSelection);

        JLabel toUnitLabel = new JLabel("Choose which unit to convert the temperature to:");
        add(toUnitLabel);
        List<String> toUnits = new ArrayList<>(units);
        toUnits.remove(0);
        toUnitSelection = new JComboBox<>(new Vector<>(toUnits));
        add(toUnitSelection);


        //update the second selection according to first selection
        fromUnitSelection.addActionListener(e -> {
            List<String> updateToUnits = new ArrayList<>(units);
            updateToUnits.remove(fromUnitSelection.getSelectedItem().toString());
            toUnitSelection.setModel(new DefaultComboBoxModel<>( new Vector<>(updateToUnits) ));
            checkInputs();
        });

        toUnitSelection.addActionListener(e -> {
            checkInputs();
        });

        //prompt for value
        JLabel promptValue = new JLabel("Enter your temperature value:");
        add(promptValue);
        inputValue = new JTextField(15);
        add(inputValue);

        //trigger action everytime you type or delete
        inputValue.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {checkInputs();}
            @Override
            public void removeUpdate(DocumentEvent e) {checkInputs();}
            @Override
            public void changedUpdate(DocumentEvent e) {checkInputs();}
        });

        add(resultLabel);
    }

    //check inputs and display results
    void checkInputs() {
        try {
            String textInput = inputValue.getText();
            if (textInput.isEmpty()) {
                resultLabel.setText("");
                return;
            }
            double input = Double.parseDouble(textInput);
            String fromUnit = fromUnitSelection.getSelectedItem().toString();
            String toUnit = toUnitSelection.getSelectedItem().toString();
            double result = convert(input, fromUnit, toUnit);

           resultLabel.setText("Result: " + result + " " + toUnit);
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input");
        }
    }

    double convert(double input, String fromUnit, String toUnit) {
        double result = input;
        //convert all to Celsius
        if (fromUnit.equals("Fahrenheit (°F)")) {result = (result - 32) * 5 / 9;}
        if (fromUnit.equals("Kelvin (K)")) {result -= 273.15;}

        //convert to selected unit
        if (toUnit.equals("Fahrenheit (°F)")) {return result * 9 / 5 + 32;}
        if (toUnit.equals("Kelvin (K)")) {return result + 273.15;}
        return result;
    }


    public static void main(String[] args) {
        new TemperatureConverter().setVisible(true);
    }
}