package Windows;

import manager.Room;
import manager.SecurityManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class MainWindow extends JFrame {
    SecurityManager manager = new SecurityManager();
    private JPanel floorPanel;
    JPanel topPanel;
    JLabel floorLabel;
    JPanel containerPanel;
    JMenuBar menuBar = new JMenuBar();
    JMenu menuExit;
    JMenu menuTools;
    JMenu menuFloors;
    JMenu menuLog;
    JMenu menuSimulation;


    public MainWindow() {
        setTitle("Security-manager Simulator");
        setSize(800, 600);
        //setBackground(new Color(200, 220, 240));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(200, 220, 240)); // Колір фону головного вікна

        // Панель меню
        menuBar.setBackground(Color.decode("#DAEBF7"));
        menuBar.setFont(new Font("Inter", Font.PLAIN, 18));

        menuExit = new JMenu("File");
        menuTools = new JMenu("Tools");
        menuFloors = new JMenu("Floors");
        menuLog = new JMenu("Log");
        menuSimulation = new JMenu("Simulation");

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        menuExit.add(exitMenuItem);

        JMenuItem showLog = new JMenuItem("Show log");
        menuLog.add(showLog);
        showLog.addActionListener(e -> ShowLog());

        exitMenuItem.addActionListener(e -> CloseProgram());

        // Додавання підпунктів до Tools
        JMenuItem addFloorItem = new JMenuItem("Add new floor");
        menuTools.add(addFloorItem);

        addFloorItem.addActionListener(e -> AddFloor());
        // Додавання підпунктів до Simulation
        JMenuItem startSimulation = new JMenuItem("Start");
        JMenuItem stopSimulation = new JMenuItem("Stop");
        menuSimulation.add(startSimulation);
        menuSimulation.add(stopSimulation);

        menuBar.add(menuExit);
        menuBar.add(menuTools);
        menuBar.add(menuFloors);
        menuBar.add(menuLog);
        menuBar.add(menuSimulation);
        setJMenuBar(menuBar);

        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 220, 240));

        // Лейбл для підпису поверху

        floorLabel = new JLabel("  Floor 1", JLabel.LEFT);
        floorLabel.setFont(new Font("Inter", Font.PLAIN, 20));
        floorLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        topPanel.add(floorLabel, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);


        containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(Color.decode("#73A3C8"));

        // Ініціалізація floorPanel
        floorPanel = new JPanel();
        floorPanel.setLayout(new GridLayout(4, 3, 5, 5)); // 4 рядки, 3 колонки з відступами
        floorPanel.setBackground(new Color(200, 220, 240));

        // Створення кімнат з індивідуальним відображенням температури
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            JPanel roomPanel = new JPanel(new BorderLayout());
            roomPanel.setBackground(Color.decode("#DAEBF7"));
            roomPanel.setBorder(BorderFactory.createDashedBorder(Color.GRAY));

            // Випадкова температура між 10 і 30 градусами
            int temperature = 10 + random.nextInt(21);
            JLabel temperatureLabel = createTemperatureLabel(temperature);

            // Додати лейбл температури в центр панелі кімнати
            roomPanel.add(temperatureLabel, BorderLayout.CENTER);
            floorPanel.add(roomPanel);
        }

        // Встановити початковий розмір floorPanel
        floorPanel.setPreferredSize(new Dimension(
                (int) (getWidth() * 0.8),
                (int) (getHeight() * 0.7)
        ));

        containerPanel.add(floorPanel);
        containerPanel.setBackground(new Color(200, 220, 240)); // Колір фону позаду floorPanel
        add(containerPanel, BorderLayout.CENTER);

        // Додати слухача для зміни розміру вікна
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFloorPanelSize();
            }
        });

        // Показати вікно
        setVisible(true);

    }

    // Метод для зміни розміру floorPanel в залежності від розміру вікна
    private void adjustFloorPanelSize() {
        if (floorPanel != null) {
            floorPanel.setPreferredSize(new Dimension(
                    (int) (getWidth() * 0.8),
                    (int) (getHeight() * 0.7)
            ));
            floorPanel.revalidate(); // Оновлення компонента
        }
    }

    private JLabel createTemperatureLabel(int temperature) {
        JLabel label = new JLabel(temperature + " C");
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setVerticalAlignment(JLabel.TOP);
        label.setFont(new Font("Seven Segment", Font.PLAIN, 18));

        if (temperature >= 23 && temperature <= 31) {
            label.setForeground(Color.YELLOW);
        } else if (temperature >= 17 && temperature <= 22) {
            label.setForeground(Color.GREEN);
        } else if (temperature >= 10 && temperature <= 16) {
            label.setForeground(Color.BLUE);
        } else {
            label.setForeground(Color.BLACK); // Для всіх інших значень
        }

        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }


    public void AddFloor(){
        int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to add new floor?",
                "New floor confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            manager.addFloor();
            JMenu newFloor = new JMenu("Floor "+manager.getFloorCount());
            menuFloors.add(newFloor);
            JMenuItem newDeleteFloor = new JMenuItem("Delete Floor");
            JMenuItem addNewRoom = new JMenuItem("Add new Room");
            JMenuItem showFloor = new JMenuItem("Show Floor");

            newDeleteFloor.addActionListener(e -> DeleteFloor(newFloor));
            addNewRoom.addActionListener(e -> AddRoom(newFloor));
            // showFloor.addActionListener(e -> showFloorAction(newFloorMenu));*/

            newFloor.add(showFloor);
            newFloor.add(addNewRoom);
            newFloor.add(newDeleteFloor);
        }

    }

    public void DeleteFloor(JMenu floor) {
        int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete " + floor.getText() + "?",
                "Delete Floor Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String floorText = floor.getText();
                int floorNumber = Integer.parseInt(floorText.replace("Floor ", "").trim());

                manager.deleteFloor(manager.getFloors().get(floorNumber-1).getId());
                menuFloors.remove(floor);
                updateFloorNames();
                menuFloors.revalidate();
                menuFloors.repaint();


                JOptionPane.showMessageDialog(null,
                        floor.getText() + " has been successfully deleted.",
                        "Floor Deleted",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                // Обробка помилки, якщо номер поверху неможливо витягти
                JOptionPane.showMessageDialog(null,
                        "Failed to parse floor number: " + floor.getText(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Переписуємо назви поверхів після видалення поверху
    private void updateFloorNames() {
        for (int i = 0; i < menuFloors.getMenuComponentCount(); i++) {
            Component component = menuFloors.getMenuComponent(i);

            if (component instanceof JMenu) {
                JMenu floorMenu = (JMenu) component;
                floorMenu.setText("Floor " + (i + 1));
            }
        }
    }


    public void AddRoom(JMenuItem floor){
        SwingUtilities.invokeLater(() -> new AddRoom(this).setVisible(true));
    }

    public static void EditRoom(){

    }

    public static void DeleteRoom(){

    }

    public void ShowLog(){
        SwingUtilities.invokeLater(() -> new LogWindow(this));
    }

    public static void StartSimulation(){

    }

    public static void StopSimulation(){

    }

    public static void CloseProgram(){
        int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to close an application?",
                "Close Application",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void GenerateFloorView(){

    }

    public static void ChangeStateOfSensor(){

    }

    public static void OnMotionSensorTriggered(int a, int b, int c){
        //не пам'ятаю що там воно приймає як параметри як дойду поміняю
    }

    public static void OnTemperaturSensorTriggered(int a, int b, int c){

    }

    public static void OnOpenSensorTriggered(int a, int b, int c){

    }
}
