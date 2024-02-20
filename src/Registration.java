import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Registration extends JFrame {
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JTextField departureCityTextField;
    private JTextField arrivalCityTextField;
    private JComboBox<String> genderComboBox;
    private JTextField ageTextField;

    public Registration() {
        setTitle("Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the UI controls
        JLabel nameLabel = new JLabel("Name:");
        nameTextField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        emailTextField = new JTextField(20);

        JLabel departureCityLabel = new JLabel("Departure City:");
        departureCityTextField = new JTextField(20);

        JLabel arrivalCityLabel = new JLabel("Arrival City:");
        arrivalCityTextField = new JTextField(20);

        JLabel genderLabel = new JLabel("Gender:");
        String[] genders = {"Male", "Female"};
        genderComboBox = new JComboBox<>(genders);

        JLabel ageLabel = new JLabel("Age:");
        ageTextField = new JTextField(20);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                String email = emailTextField.getText();
                String departureCity = departureCityTextField.getText();
                String arrivalCity = arrivalCityTextField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                int age = Integer.parseInt(ageTextField.getText());

                registerUser(name, email, departureCity, arrivalCity, gender, age);
            }
        });

        // Create a layout and add the controls to it
        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(nameLabel);
        panel.add(nameTextField);
        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(departureCityLabel);
        panel.add(departureCityTextField);
        panel.add(arrivalCityLabel);
        panel.add(arrivalCityTextField);
        panel.add(genderLabel);
        panel.add(genderComboBox);
        panel.add(ageLabel);
        panel.add(ageTextField);
        panel.add(registerButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setVisible(true);

        // Establish the database connection
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://mysql-airport-adilmax014-53ae.a.aivencloud.com:21269/defaultdb?sslmode=require", "avnadmin", "AVNS_kEYdbZAAPnIvTqmjX5Y");

            // Create the users table if it doesn't exist
            createUsersTable(connection);
        } catch (SQLException ex) {
            System.out.println("Connection failure.");
            ex.printStackTrace();
        }
    }

    private void createUsersTable(Connection connection) {
        try {
            Statement statement = connection.createStatement();

            // Create the users table
            String createUserTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(50) NOT NULL," +
                    "email VARCHAR(50) NOT NULL," +
                    "departure_city VARCHAR(50) NOT NULL," +
                    "arrival_city VARCHAR(50) NOT NULL," +
                    "gender VARCHAR(10) NOT NULL," +
                    "age INT NOT NULL" +
                    ")";
            statement.executeUpdate(createUserTableQuery);

            statement.close();

            System.out.println("Users table created successfully.");
        } catch (SQLException e) {
            System.out.println("Error creating users table.");
            e.printStackTrace();
        }
    }

    private void registerUser(String name, String email, String departureCity, String arrivalCity, String gender, int age) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://mysql-airport-adilmax014-53ae.a.aivencloud.com:21269/defaultdb?sslmode=require", "avnadmin", "AVNS_kEYdbZAAPnIvTqmjX5Y");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, email, departure_city, arrival_city, gender, age) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, departureCity);
            statement.setString(4, arrivalCity);
            statement.setString(5, gender);
            statement.setInt(6, age);

            statement.executeUpdate();
            statement.close();
            connection.close();

            System.out.println("User registered successfully.");
        } catch (SQLException e) {
            System.out.println("Error registering user.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Registration();
    }
}