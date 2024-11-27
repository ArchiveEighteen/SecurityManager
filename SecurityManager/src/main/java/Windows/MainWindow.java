package Windows;


import Subscribers.MotionSubscriber;
import Subscribers.OpenSubscriber;
import Subscribers.TemperatureSubscriber;
import manager.Floor;
import manager.Room;
import manager.SecurityManager;
import manager.security.sensors.Sensor;
import manager.security.sensors.SensorNotification;
import manager.security.sensors.SensorType;
import manager.security.sensors.TemperatureSensor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

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
    TemperatureSubscriber subscriber1 = new TemperatureSubscriber("Temparature", this);
    MotionSubscriber subscriber2 = new MotionSubscriber("Motion", this);
    OpenSubscriber subscriber3 = new OpenSubscriber("Open", this);
    private Map<UUID, JPanel> sensorPanels = new HashMap<>();


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
        startSimulation.addActionListener(e -> StartSimulation());
        stopSimulation.addActionListener(e -> StopSimulation());

        menuBar.add(menuExit);
        menuBar.add(menuTools);
        menuBar.add(menuFloors);
        menuBar.add(menuLog);
        menuBar.add(menuSimulation);
        setJMenuBar(menuBar);

        topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 220, 240));


        floorLabel = new JLabel("", JLabel.LEFT);
        floorLabel.setFont(new Font("Inter", Font.PLAIN, 20));
        floorLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        topPanel.add(floorLabel, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);


        containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBackground(Color.decode("#73A3C8"));

        // Ініціалізація floorPanel
        floorPanel = new JPanel();

        // Додати слухача для зміни розміру вікна
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustFloorPanelSize();
            }
        });

        setVisible(true);

    }

    public void setColorOnTemperature(SensorNotification sn){
        for (Floor floor : manager.getFloors()) {
            for (Room room : floor.getRooms()) {
                    if (room.getSensors().getFirst().getId() == sn.getSensorId() && room.getSensors().getFirst().getType() == SensorType.TemperatureSensor) {
                        TemperatureSensor t = (TemperatureSensor) room.getSensors().getFirst();
                        JPanel sensorView = sensorPanels.get(t.getId());
                        if (sensorView != null) {
                            NumberFormat nf = NumberFormat.getNumberInstance();
                            nf.setMaximumFractionDigits(2);
                            nf.setRoundingMode(RoundingMode.DOWN);
                            JLabel temperatureLabel = (JLabel) sensorView.getComponent(0); // Припускаємо, що перший компонент — це етикетка температури
                            temperatureLabel.setText("" + nf.format(t.getCurrentTemperature()) + "°C");
                            Color fontColor = getColorBasedOnTemperature(t.getCurrentTemperature());
                            temperatureLabel.setForeground(fontColor);
                        }
                        return;
                    }
                }
            }
        }

    private Color getColorBasedOnTemperature(double temperature) {
        if (temperature < 0) {
            return new Color(0, 0, 255);
        } else if (temperature >= 0 && temperature < 10) {
            return new Color(0, 0, 139);
        } else if (temperature >= 10 && temperature < 20) {
            return new Color(34, 139, 34);
        } else if (temperature >= 20 && temperature < 30) {
            return new Color(255, 215, 0);
        } else if (temperature >= 30 && temperature < 40) {
            return new Color(255, 140, 0);
        } else {
            return new Color(255, 0, 0);
        }}

    public void setColorForOpen(SensorNotification sn){
        sensorPanels.get(sn.getSensorId()).setBackground(sn.getStatus()? new Color(181, 13, 21) : new Color(13, 12, 181));
    }

    public void setColorForMotion(SensorNotification sn){
        sensorPanels.get(sn.getSensorId()).setBackground(sn.getStatus()? new Color(12, 164, 181) : new Color(181, 13, 108));
    }

    public void GenerateFloorView(JMenu floor){
        floorPanel.removeAll();
        int floorIndex = Integer.parseInt(floor.getText().replace("Floor ", "")) - 1;
        Floor targetFloor = manager.getFloors().get(floorIndex);
        floorLabel.setText("Floor " + (floorIndex + 1));
        floorLabel.setHorizontalAlignment(SwingConstants.LEFT);
        floorLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 0, 0));

        // Налаштування розмітки для панелі поверху
        floorPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Отримуємо кімнати
        List<Room> rooms = targetFloor.getRooms();
        int maxWidth = getMaxLabelWidth(rooms);
        if (rooms.isEmpty()) {
            JLabel emptyLabel = new JLabel("No rooms available");
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            floorPanel.add(emptyLabel);
        } else {
            int columns = (int) Math.ceil(Math.sqrt(rooms.size()));

            // Додаємо кімнати
            for (int i = 0; i < rooms.size(); i++) {
                Room room = rooms.get(i);

                // Створюємо панель для кімнати
                JPanel roomPanel = createRoomPanel(room, i + 1, maxWidth);

                // Розміщуємо кімнату в сітці
                gbc.gridx = i % columns; // Номер колонки
                gbc.gridy = i / columns; // Номер рядка
                floorPanel.add(roomPanel, gbc);
            }
        }

        // Оновлюємо floorPanel
        floorPanel.revalidate();
        floorPanel.repaint();
        floorPanel.setBackground(new Color(200, 220, 240));
        containerPanel.add(floorPanel);
        containerPanel.setBackground(new Color(200, 220, 240)); // Колір фону позаду floorPanel
        add(containerPanel, BorderLayout.CENTER);
    }

    private JPanel createRoomPanel(Room room, int roomNumber, int fixedWidth) {
        int sensorCount = room.getSensors().size(); // Кількість сенсорів у кімнаті
        int baseHeight = 50; // Базова висота кімнати
        int sensorHeightIncrement = 30; // Додаткова висота за кожен сенсор

        int height = baseHeight + (sensorCount / 2 * sensorHeightIncrement);

        JPanel roomPanel = new JPanel();
        roomPanel.setLayout(new BorderLayout());
        roomPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Room " + roomNumber, // Заголовок кімнати
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12) // Шрифт заголовка
        ));
        roomPanel.setBackground(Color.decode("#DAEBF7"));
        roomPanel.setPreferredSize(new Dimension(fixedWidth, height));

        JPanel temper = new JPanel();
        temper.setBackground(Color.decode("#DAEBF7"));
        temper.add(sensorPanels.get(room.getSensors().getFirst().getId()));

        // Панель для сенсорів
        JPanel sensorPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // Дві колонки
        sensorPanel.setBackground(Color.WHITE);

        int sensorSize = fixedWidth / 2 - 10;
        // Додаємо сенсори до кімнати
        for (Sensor sensor : room.getSensors()) {
            JPanel sensorView = sensorPanels.get(sensor.getId());
            if (sensorView != null && sensor.getType() != SensorType.TemperatureSensor) {
                sensorView.setPreferredSize(new Dimension(sensorSize, sensorSize));
                sensorPanel.add(sensorView);
            }
        }

        // Додаємо панель сенсорів до кімнати
        JPanel allSensorsPanel = new JPanel();
        allSensorsPanel.setLayout(new BorderLayout());
        allSensorsPanel.add(temper, BorderLayout.NORTH);
        allSensorsPanel.add(sensorPanel, BorderLayout.CENTER);

        roomPanel.add(allSensorsPanel, BorderLayout.CENTER);
        return roomPanel;
    }


    private int getMaxLabelWidth(List<Room> rooms) {
        Font font = new Font("Arial", Font.BOLD, 12);
        FontMetrics metrics = getFontMetrics(font);

        int maxWidth = 0;
        for (int i = 0; i < rooms.size(); i++) {
            String label = "Room " + (i + 1);
            int labelWidth = metrics.stringWidth(label);
            maxWidth = Math.max(maxWidth, labelWidth);
        }
        return maxWidth + 25;
    }


    private void adjustFloorPanelSize() {
        if (floorPanel != null) {
            floorPanel.setPreferredSize(new Dimension(
                    (int) (getWidth() * 0.8),
                    (int) (getHeight() * 0.7)
            ));
            floorPanel.revalidate();
        }
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
            JMenu roomMenu = new JMenu("Rooms");

            newDeleteFloor.addActionListener(e -> DeleteFloor(newFloor));
            addNewRoom.addActionListener(e -> AddRoom(newFloor));
            showFloor.addActionListener(e -> GenerateFloorView(newFloor));

            newFloor.add(roomMenu);
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
                ((JMenu) component).setText("Floor " + (i + 1));
            }
        }
    }

    private void updateRoomNames(int floor) {
         for (int i = 0; i < ((JMenu) menuFloors.getMenuComponent(floor-1)).getMenuComponentCount(); i++) {
            Component component = ((JMenu) menuFloors.getMenuComponent(floor-1)).getMenuComponent(i);
            if (component instanceof JMenu) {
                ((JMenu) component).setText("Room " + (i + 1));
            }
        }
    }


    public void AddRoom(JMenuItem floor){
        int f = Integer.parseInt(floor.getText().replace("Floor ", ""));
        AddRoom newWindow = new AddRoom(this, f, null);
        newWindow.setVisible(true);
    }

    public void getAddRoomResult(int floor, int windows, int doors, double area){
        Room r = manager.getFloors().get(floor-1).addRoom(windows, doors, area);
        List<Sensor> rSensors = r.getSensors();
        for (Sensor s : rSensors){
            if(sensorPanels.putIfAbsent(s.getId(), new JPanel()) == null){
                switch(s.getType()){
                    case OpenSensor: sensorPanels.get(s.getId()).setBackground(new Color(13, 12, 181)); s.subscribe(subscriber3); break;
                    case MotionSensor: sensorPanels.get(s.getId()).setBackground(new Color(181, 13, 108)); s.subscribe(subscriber2); break;
                    case TemperatureSensor:
                        TemperatureSensor t = (TemperatureSensor) s;
                        sensorPanels.get(s.getId()).add(new JLabel(t.getCurrentTemperature()+"°C"));
                        Color fontColor = getColorBasedOnTemperature(t.getCurrentTemperature());
                        sensorPanels.get(s.getId()).setForeground(fontColor)
                        ;s.subscribe(subscriber1); break;}
            }
        }
        Floor targetFloor = manager.getFloors().get(floor - 1);
        int room = targetFloor.getRooms().size();
        JMenu floorMenu = (JMenu) menuFloors.getMenuComponent(floor - 1);
        JMenu floors = (JMenu) floorMenu.getMenuComponent(0);
        JMenu roomMenu = new JMenu("Room " + (targetFloor.getRooms().size()));
        JMenuItem editRoomItem = new JMenuItem("Edit Room");
        JMenuItem deleteRoomItem = new JMenuItem("Delete Room");

       // floorLabel.setText(manager.getFloors().get(floor-1).getRooms().toString());
        editRoomItem.addActionListener(e -> EditRoom(floor, r));
        deleteRoomItem.addActionListener(e -> DeleteRoom(floor, room));

        roomMenu.add(editRoomItem);
        roomMenu.add(deleteRoomItem);

        floors.add(roomMenu);

        floorMenu.revalidate();
        floorMenu.repaint();
    }

    public void getEditRoomResults(int floor, UUID room, double area, int windows, int doors ){
        for (Room r : manager.getFloors().get(floor - 1).getRooms()){
            if(r.getId().equals(room)){
                r.updateRoomParameters(windows, doors, area);
                List<Sensor> rSensors = r.getSensors();
                for (Sensor s : rSensors){
                    if(sensorPanels.putIfAbsent(s.getId(), new JPanel()) == null){
                        switch(s.getType()){
                            case OpenSensor: sensorPanels.get(s.getId()).setBackground(new Color(13, 12, 181)); s.subscribe(subscriber3); break;
                            case MotionSensor: sensorPanels.get(s.getId()).setBackground(new Color(181, 13, 108)); s.subscribe(subscriber2); break;
                            case TemperatureSensor:
                                TemperatureSensor t = (TemperatureSensor) s;
                                sensorPanels.get(s.getId()).add(new JLabel(t.getCurrentTemperature()+"C"));
                                Color fontColor = getColorBasedOnTemperature(t.getCurrentTemperature());
                                sensorPanels.get(s.getId()).setForeground(fontColor);
                                s.subscribe(subscriber1); break;}

                    }
                }
            }
        }
    }


    public void EditRoom(int floor, Room room){
        AddRoom newWindow = new AddRoom(this, floor, room);
        newWindow.setVisible(true);
    }

    public void DeleteRoom(int floor, int room){
        int confirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete this room?",
                "Delete Room Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            JMenu h = (JMenu) menuFloors.getMenuComponent(floor-1);
            UUID toRemove = manager.getFloors().get(floor-1).getRooms().get(room-1).getId();
            System.out.println(manager.deleteRoom(manager.getFloors().get(floor-1).getId(), manager.getFloors().get(floor-1).getRooms().get(room-1).getId()));
            ((JMenu) h.getMenuComponent(0)).remove(room-1);
            sensorPanels.remove(toRemove);
            updateRoomNames(floor);
            menuFloors.revalidate();
            menuFloors.repaint();
            // floorLabel.setText(manager.getFloors().get(floor-1).getRooms().toString());
            if(manager.getFloors().get(floor-1).getRooms().size()==((JMenu) menuFloors.getMenuComponent(floor-1)).getMenuComponentCount()){
            JOptionPane.showMessageDialog(null, "Room deleted successfully.");}
        }


    }

    public void ShowLog(){
        SwingUtilities.invokeLater(() -> new LogWindow(this));
    }

    public void StartSimulation(){
        manager.startSimulation();
    }

    public void StopSimulation(){
        manager.stopSimulation();
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

}
