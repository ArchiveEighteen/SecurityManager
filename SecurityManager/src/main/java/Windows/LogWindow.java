package Windows;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LogWindow extends JFrame {

    public LogWindow() {
        setTitle("Event Log");
        setSize(900, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель меню, яка буде такою ж, як у головному вікні
        JMenuBar menuBar = new JMenuBar();
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

        // Панель заголовку
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(200, 220, 240));

        JLabel logLabel = new JLabel("  Event Log", JLabel.LEFT);
        logLabel.setFont(new Font("Inter", Font.BOLD, 20));
        topPanel.add(logLabel, BorderLayout.NORTH);

        add(topPanel, BorderLayout.NORTH);

        // Панель для фільтра
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setBackground(new Color(200, 220, 240));

        JLabel filterLabel = new JLabel("Filter by");
        JComboBox<String> filterComboBox = new JComboBox<>(new String[]{"Date", "Floor", "Room", "Event", "System Reaction", "Sensor"});
        filterPanel.add(filterLabel);
        filterPanel.add(filterComboBox);

        topPanel.add(filterPanel, BorderLayout.EAST);

        // Панель для таблиці з відступами
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Відступи з усіх сторін
        tablePanel.setBackground(new Color(200, 220, 240)); // Колір фону панелі

        // Модель таблиці для зберігання логів
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Date");
        tableModel.addColumn("Floor");
        tableModel.addColumn("Room");
        tableModel.addColumn("Event");
        tableModel.addColumn("System Reaction");
        tableModel.addColumn("Sensor");

        // JTable для відображення логів
        JTable logTable = new JTable(tableModel) {
            // Налаштування закруглених кутів
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Фон таблиці з закругленими кутами
                g2.setColor(Color.decode("#DAEBF7"));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);

                super.paintComponent(g);
                g2.dispose();
            }
        };

        logTable.setFillsViewportHeight(true);
        logTable.setFont(new Font("Inter", Font.PLAIN, 14));
        logTable.setRowHeight(25);
        logTable.setGridColor(Color.WHITE); // Білий колір меж таблиці

        // Налаштування кольорів заголовків таблиці
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Color.decode("#1875BB")); // Фон заголовків
        headerRenderer.setForeground(Color.WHITE); // Колір тексту заголовків
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < logTable.getColumnModel().getColumnCount(); i++) {
            logTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Встановлення ширини колонок
        logTable.getColumnModel().getColumn(0).setPreferredWidth(100); // Date
        logTable.getColumnModel().getColumn(1).setPreferredWidth(50);  // Floor
        logTable.getColumnModel().getColumn(2).setPreferredWidth(50);  // Room
        logTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Event
        logTable.getColumnModel().getColumn(4).setPreferredWidth(200); // System Reaction
        logTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Sensor

        // Встановлення кольору фону таблиці
        logTable.setBackground(Color.decode("#DAEBF7"));

        // Додавання таблиці в JScrollPane для прокрутки
        JScrollPane scrollPane = new JScrollPane(logTable) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Фон JScrollPane з закругленими кутами
                g2.setColor(Color.decode("#DAEBF7"));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        scrollPane.getViewport().setBackground(new Color(240, 250, 255)); // Колір фону для області перегляду
        scrollPane.setBorder(BorderFactory.createEmptyBorder());


        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Додавання панелі таблиці до вікна
        add(tablePanel, BorderLayout.CENTER);

        // Показати вікно
        setVisible(true);
    }

    public static void main(String[] args) {
        // Запуск LogWindow
        SwingUtilities.invokeLater(LogWindow::new);
    }
}
