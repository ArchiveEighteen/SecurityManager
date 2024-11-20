package Windows;

import manager.SecurityManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;

public class MainWindow extends JFrame {
    SecurityManager manager = new SecurityManager();
    private JPanel floorPanel;
    JPanel topPanel;
    JLabel floorLabel;
    JPanel containerPanel;
    JMenuBar menuBar = new JMenuBar();
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

        JMenu menuExit = new JMenu("Exit");
        JMenu menuTools = new JMenu("Tools");
        JMenu menuFloors = new JMenu("Floors");
        JMenu menuLog = new JMenu("Log");
        JMenu menuSimulation = new JMenu("Simulation");


        // Додавання підпунктів до Tools
        JMenuItem addFloorItem = new JMenuItem("Add new floor");
        menuTools.add(addFloorItem);

        // Додавання підпунктів до Floors
        JMenuItem floor1 = new JMenuItem("Floor 1");
        JMenuItem floor2 = new JMenuItem("Floor 2");
        JMenuItem floor3 = new JMenuItem("Floor 3");
        menuFloors.add(floor1);
        menuFloors.add(floor2);
        menuFloors.add(floor3);

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
        floorLabel.setFont(new Font("Inter", Font.BOLD, 20));
        topPanel.add(floorLabel, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);

        // Панель-контейнер для центрованого floorPanel
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


    public static void AddFloor(){

    }

    public static void DeleteFloor(){

    }

    public static void AddRoom(){

    }

    public static void EditRoom(){

    }

    public static void DeleteRoom(){

    }

    public static void ShowLog(){

    }

    public static void StartSimulation(){

    }

    public static void StopSimulation(){

    }

    public static void CloseProgram(){

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
