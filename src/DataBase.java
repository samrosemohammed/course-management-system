import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
    // Replace with your actual database URL, username, and password
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/login";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static int getTeacherCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM teacher_login";
        return getCount(query);
    }

    public static int getStudentCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM student_login";
        return getCount(query);
    }

    public static int getCourseCount() throws SQLException {
        String query = "SELECT COUNT(DISTINCT course) FROM student_login";
        return getCount(query);
    }

    private static int getCount(String query) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0; // Return 0 if there's an issue or no results
    }
}
