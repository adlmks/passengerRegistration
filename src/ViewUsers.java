import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewUsers {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://mysql-airport-adilmax014-53ae.a.aivencloud.com:21269/defaultdb?sslmode=require", "avnadmin", "AVNS_kEYdbZAAPnIvTqmjX5Y");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String departureCity = resultSet.getString("departure_city");
                String arrivalCity = resultSet.getString("arrival_city");
                String gender = resultSet.getString("gender");
                int age = resultSet.getInt("age");

                System.out.println("ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Departure City: " + departureCity);
                System.out.println("Arrival City: " + arrivalCity);
                System.out.println("Gender: " + gender);
                System.out.println("Age: " + age);
                System.out.println("------------------------");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Error accessing the database.");
            ex.printStackTrace();
        }
    }
}