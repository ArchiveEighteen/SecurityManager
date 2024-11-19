package Windows;

import javax.swing.*;
import java.awt.*;

public class AddRoom extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner areaSpinner;
    private JSpinner windowsSpinner;
    private JSpinner doorsSpinner;
    private JCheckBox temperatureSensorCheckBox;
    private JSpinner motionSensorsSpinner;
    private JSpinner doorSensorsSpinner;
    private JSpinner windowSensorsSpinner;

    public AddRoom() {
        setTitle("Add Room");
        setSize(600, 400); // Set window size to 600x400
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main panel
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setBackground(new Color(200, 220, 240)); // Set background color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Font for labels
        Font labelFont = new Font("SansSerif", Font.BOLD, 14); // Increase font size

        // Row 1: Area of the room
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel areaLabel = new JLabel("Area of a room, m²");
        areaLabel.setFont(labelFont);
        contentPane.add(areaLabel, gbc);

        gbc.gridx = 1;
        areaSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        contentPane.add(areaSpinner, gbc);

        // Row 2: Number of windows
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel windowsLabel = new JLabel("Number of windows");
        windowsLabel.setFont(labelFont);
        contentPane.add(windowsLabel, gbc);

        gbc.gridx = 1;
        windowsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
        contentPane.add(windowsSpinner, gbc);

        // Row 3: Number of doors
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel doorsLabel = new JLabel("Number of doors");
        doorsLabel.setFont(labelFont);
        contentPane.add(doorsLabel, gbc);

        gbc.gridx = 1;
        doorsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        contentPane.add(doorsSpinner, gbc);

        // Row 4: Temperature sensor
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel temperatureLabel = new JLabel("Temperature sensor");
        temperatureLabel.setFont(labelFont);
        contentPane.add(temperatureLabel, gbc);

        gbc.gridx = 1;
        temperatureSensorCheckBox = new JCheckBox();
        temperatureSensorCheckBox.setBackground(new Color(200, 220, 240)); // Set background color for the checkbox area
        contentPane.add(temperatureSensorCheckBox, gbc);

        // Row 5: Motion sensors
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel motionLabel = new JLabel("Motion sensors");
        motionLabel.setFont(labelFont);
        contentPane.add(motionLabel, gbc);

        gbc.gridx = 1;
        motionSensorsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        contentPane.add(motionSensorsSpinner, gbc);

        // Row 6: Sensor for doors
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel doorSensorLabel = new JLabel("Sensor for doors");
        doorSensorLabel.setFont(labelFont);
        contentPane.add(doorSensorLabel, gbc);

        gbc.gridx = 1;
        doorSensorsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        contentPane.add(doorSensorsSpinner, gbc);

        // Row 7: Sensor for windows
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel windowSensorLabel = new JLabel("Sensor for Windows");
        windowSensorLabel.setFont(labelFont);
        contentPane.add(windowSensorLabel, gbc);

        gbc.gridx = 1;
        windowSensorsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        contentPane.add(windowSensorsSpinner, gbc);

        // Buttons panel
        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Add horizontal and vertical gaps
        buttonsPanel.setBackground(new Color(200, 220, 240));

        // Configure Cancel button
        buttonCancel = new RoundedButton("Cancel");
        buttonCancel.setBackground(Color.WHITE);
        buttonCancel.setForeground(Color.BLACK);

        // Configure Add Room button
        buttonOK = new RoundedButton("Add room");
        buttonOK.setBackground(Color.decode("#1875BB"));
        buttonOK.setForeground(Color.WHITE);

        buttonsPanel.add(buttonCancel);
        buttonsPanel.add(buttonOK);

        add(contentPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Button actions
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setModal(true);
        pack();
        setLocationRelativeTo(null); // Center on screen
    }

    private void onOK() {
        // Logic to handle room addition
        System.out.println("Room added!");
        dispose();
    }

    private void onCancel() {
        // Logic to handle cancel action
        dispose();
    }

    public static void main(String[] args) {
        AddRoom dialog = new AddRoom();
        dialog.setVisible(true);
        System.exit(0);
    }
}

// Custom class for rounded buttons
class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // Draw the button's text
        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(getText());
        int textHeight = fm.getAscent();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - 3;
        //g2.drawString(getText(), x, y);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
}
