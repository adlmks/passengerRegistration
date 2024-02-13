import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AirplaneRegistrationApp extends JFrame {
    private JTextField fullNameField;
    private JTextField flightNumberField;
    private JTextField seatNumberField;
    private JTextField ageField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JComboBox<String> seatComboBox;
    private JButton registerButton;


    private static final String DB_URL = "jdbc:sqlite:passengers.db";

    public AirplaneRegistrationApp() {
        setTitle("Airplane Passenger Registration");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Full Name:"));
        fullNameField = new JTextField();
        panel.add(fullNameField);

        panel.add(new JLabel("Flight Number:"));
        flightNumberField = new JTextField();
        panel.add(flightNumberField);

        panel.add(new JLabel("Seat Number:"));
        seatNumberField = new JTextField();
        panel.add(seatNumberField);

        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);

        panel.add(new JLabel("Gender:"));
        JPanel genderPanel = new JPanel();
        ButtonGroup genderGroup = new ButtonGroup();
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        panel.add(genderPanel);

        panel.add(new JLabel("Seat Preference:"));
        String[] seatOptions = {"Window", "Aisle", "Middle"};
        seatComboBox = new JComboBox<>(seatOptions);
        panel.add(seatComboBox);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerPassenger();
            }
        });
        panel.add(new JLabel());
        panel.add(registerButton);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void registerPassenger() {
        String fullName = fullNameField.getText();
        String flightNumber = flightNumberField.getText();
        String seatNumber = seatNumberField.getText();
        String age = ageField.getText();
        String gender = maleRadioButton.isSelected() ? "Male" : "Female";
        String seatPreference = (String) seatComboBox.getSelectedItem();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS passengers (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "full_name TEXT," +
                    "flight_number TEXT," +
                    "seat_number TEXT," +
                    "age INTEGER," +
                    "gender TEXT," +
                    "seat_preference TEXT)";
            stmt.execute(createTableSQL);

            String insertSQL = "INSERT INTO passengers (full_name, flight_number, seat_number, age, gender, seat_preference) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertSQL);
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, flightNumber);
            preparedStatement.setString(3, seatNumber);
            preparedStatement.setInt(4, Integer.parseInt(age));
            preparedStatement.setString(5, gender);
            preparedStatement.setString(6, seatPreference);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(this, "Passenger registered:\nFull Name: " + fullName + "\nFlight Number: " + flightNumber + "\nSeat Number: " + seatNumber + "\nAge: " + age + "\nGender: " + gender + "\nSeat Preference: " + seatPreference);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering passenger.");
        }
    }

    public static void main(String[] args) {
        createDatabase();
        SwingUtilities.invokeLater(() -> new AirplaneRegistrationApp());
    }

    private static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            // Create table if not exists
            String createTableSQL = "CREATE TABLE IF NOT EXISTS passengers (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "full_name TEXT," +
                    "flight_number TEXT," +
                    "seat_number TEXT," +
                    "age INTEGER," +
                    "gender TEXT," +
                    "seat_preference TEXT)";
            stmt.execute(createTableSQL);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
