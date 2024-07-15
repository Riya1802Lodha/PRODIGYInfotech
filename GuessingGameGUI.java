import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class GuessingGameGUI {

    private JFrame frame;
    private JPanel mainPanel, inputPanel, outputPanel, historyPanel, rangePanel;
    private JTextField inputField, minRangeField, maxRangeField;
    private JLabel outputLabel, rangeLabel;
    private JTextArea historyArea;
    private Random random;
    private int randomNumber;
    private int attempts;
    private ArrayList<Integer> previousGuesses;
    private int minRange, maxRange;

    public GuessingGameGUI() {
        frame = new JFrame("Fun Guessing Game");
        frame.setSize(800, 600); // Set the frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        rangePanel = new JPanel();
        rangePanel.setLayout(new GridBagLayout());
        rangePanel.setBorder(BorderFactory.createTitledBorder("Set Range"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        rangeLabel = new JLabel("Enter the range for the random number:");
        rangeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        rangePanel.add(rangeLabel, gbc);

        minRangeField = new JTextField(5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        rangePanel.add(minRangeField, gbc);

        JLabel toLabel = new JLabel("to");
        toLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        rangePanel.add(toLabel, gbc);

        maxRangeField = new JTextField(5);
        gbc.gridx = 2;
        gbc.gridy = 1;
        rangePanel.add(maxRangeField, gbc);

        JButton setRangeButton = new JButton("Set Range");
        setRangeButton.setBackground(new Color(135, 206, 250));
        setRangeButton.setFont(new Font("Arial", Font.BOLD, 14));
        setRangeButton.addActionListener(new SetRangeButtonListener());
        gbc.gridx = 3;
        gbc.gridy = 1;
        rangePanel.add(setRangeButton, gbc);

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Make a Guess"));

        JLabel promptLabel = new JLabel("Enter your guess:");
        promptLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(promptLabel, gbc);

        inputField = new JTextField(10);
        inputField.setEnabled(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(inputField, gbc);

        JButton guessButton = new JButton("Guess");
        guessButton.setBackground(new Color(135, 206, 250));
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        guessButton.addActionListener(new GuessButtonListener());
        guessButton.setEnabled(false);
        gbc.gridx = 2;
        gbc.gridy = 0;
        inputPanel.add(guessButton, gbc);

        outputPanel = new JPanel();
        outputPanel.setLayout(new GridBagLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));

        outputLabel = new JLabel("Welcome to the Fun Guessing Game!");
        outputLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        outputPanel.add(outputLabel, gbc);

        historyPanel = new JPanel();
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("Previous Guesses"));

        historyArea = new JTextArea(10, 20);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        historyPanel.add(scrollPane);

        mainPanel.add(rangePanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(outputPanel, BorderLayout.SOUTH);
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(historyPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);

        random = new Random();
        attempts = 0;
        previousGuesses = new ArrayList<>();
    }

    private class SetRangeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                minRange = Integer.parseInt(minRangeField.getText());
                maxRange = Integer.parseInt(maxRangeField.getText());

                if (minRange >= maxRange) {
                    throw new NumberFormatException();
                }

                randomNumber = random.nextInt(maxRange - minRange + 1) + minRange;
                attempts = 0;
                previousGuesses.clear();
                historyArea.setText("");
                outputPanel.setBackground(null);
                outputLabel.setText("Range set! Enter your guess:");
                inputField.setEnabled(true);
                inputField.setText("");
                minRangeField.setEnabled(false);
                maxRangeField.setEnabled(false);
                ((JButton) e.getSource()).setEnabled(false);
                inputPanel.getComponent(2).setEnabled(true);  // Enable the Guess button
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid and correct range values.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(inputField.getText());

                if (guess < minRange || guess > maxRange) {
                    outputLabel.setText("Out of range! Enter a number between " + minRange + " and " + maxRange + ".");
                } else {
                    attempts++;

                    if (previousGuesses.contains(guess)) {
                        outputLabel.setText("You've already guessed " + guess + "! Try a new number.");
                    } else {
                        previousGuesses.add(guess);
                        historyArea.append(guess + "\n");

                        if (guess < randomNumber) {
                            outputLabel.setText("Too low! Try again.");
                        } else if (guess > randomNumber) {
                            outputLabel.setText("Too high! Try again.");
                        } else {
                            outputLabel.setText("Congratulations! You guessed the number.");
                            outputPanel.setBackground(Color.GREEN);
                            JOptionPane.showMessageDialog(frame, "It took you " + attempts + " attempts.", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
                            resetGame();
                        }
                    }
                }

                inputField.setText(""); // Clear input field after each guess
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void resetGame() {
        randomNumber = random.nextInt(maxRange - minRange + 1) + minRange;
        attempts = 0;
        previousGuesses.clear();
        historyArea.setText("");
        outputPanel.setBackground(null);
        outputLabel.setText("Range set! Enter your guess:");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GuessingGameGUI();
            }
        });
    }
}
