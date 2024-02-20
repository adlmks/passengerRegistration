import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class cleardata {
    public static void main(String[] args) {
        String url = "jdbc:mysql://mysql-airport-adilmax014-53ae.a.aivencloud.com:21269/defaultdb?sslmode=require"; // Замените db_name на имя вашей базы данных
        String username = "avnadmin";
        String password = "AVNS_kEYdbZAAPnIvTqmjX5Y";

        String deleteQuery = "DELETE FROM users";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            int rowsDeleted = statement.executeUpdate(deleteQuery);
            System.out.println("Удалено " + rowsDeleted + " строк из таблицы users.");

        } catch (SQLException e) {
            System.out.println("Ошибка при удалении данных пользователей.");
            e.printStackTrace();
        }
    }
}