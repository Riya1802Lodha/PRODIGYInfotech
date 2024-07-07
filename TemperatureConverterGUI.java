import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TemperatureConverterGUI {

    private JFrame frame;
    private JPanel mainPanel, inputPanel, outputPanel;
    private JTextField inputField;
    private JLabel outputLabel;
    private JRadioButton celsiusButton, fahrenheitButton, kelvinButton; 

    public TemperatureConverterGUI() {
        frame = new JFrame("Temperature Converter");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel promptLabel = new JLabel("Enter temperature:");
        inputPanel.add(promptLabel);

        inputField = new JTextField(10);
        inputPanel.add(inputField);

        ButtonGroup unitGroup = new ButtonGroup();

        celsiusButton = new JRadioButton("Celsius"); 
        fahrenheitButton = new JRadioButton("Fahrenheit");
        kelvinButton = new JRadioButton("Kelvin");

        unitGroup.add(celsiusButton);
        unitGroup.add(fahrenheitButton);
        unitGroup.add(kelvinButton);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(3, 1));
        radioPanel.add(celsiusButton);
        radioPanel.add(fahrenheitButton);
        radioPanel.add(kelvinButton);

        inputPanel.add(radioPanel);

        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ConvertButtonListener());
        inputPanel.add(convertButton);

        outputPanel = new JPanel();
        outputPanel.setLayout(new FlowLayout());

        outputLabel = new JLabel("Converted temperatures will appear here.");
        outputPanel.add(outputLabel);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private class ConvertButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                double temperature = Double.parseDouble(inputField.getText());

                if (celsiusButton.isSelected()) { 
                    double fahrenheit = celsiusToFahrenheit(temperature);
                    double kelvin = celsiusToKelvin(temperature);
                    updateOutput(temperature, fahrenheit, kelvin);
                } else if (fahrenheitButton.isSelected()) {
                    double celsius = fahrenheitToCelsius(temperature);
                    double kelvin = fahrenheitToKelvin(temperature);
                    updateOutput(celsius, temperature, kelvin);
                } else if (kelvinButton.isSelected()) {
                    double celsius = kelvinToCelsius(temperature);
                    double fahrenheit = kelvinToFahrenheit(temperature);
                    updateOutput(celsius, fahrenheit, temperature);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a unit (Celsius, Fahrenheit, or Kelvin).");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid temperature value.");
            }
        }
    }

    private void updateOutput(double celsius, double fahrenheit, double kelvin) {
        outputLabel.setText("<html>Celsius: " + String.format("%.2f", celsius) + "<br>"
                + "Fahrenheit: " + String.format("%.2f", fahrenheit) + "<br>"
                + "Kelvin: " + String.format("%.2f", kelvin) + "</html>");
    }

    private double celsiusToFahrenheit(double celsius) {
        return (celsius * 9 / 5) + 32;
    }

    private double celsiusToKelvin(double celsius) {
        return celsius + 273.15;
    }

    private double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }

    private double fahrenheitToKelvin(double fahrenheit) {
        return (fahrenheit - 32) * 5 / 9 + 273.15;
    }

    private double kelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    private double kelvinToFahrenheit(double kelvin) {
        return (kelvin - 273.15) * 9 / 5 + 32;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TemperatureConverterGUI();
            }
        });
    }
}
