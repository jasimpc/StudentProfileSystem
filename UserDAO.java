package studentprofiling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {


    public User login(String id, String password) throws SQLException {
        String query =
                "SELECT * FROM users " +
                        "WHERE id = ? AND password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.setString(2, password);

            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    return null;
                }
                String role =
                        result.getString("role");
                String name =
                        result.getString("name");
                String email =
                        result.getString("email");

                if (role.equals("STUDENT")) {
                    return new Student(
                            id,
                            name,
                            email,
                            password,
                            "",
                            0
                    );
                }

                if (role.equals("TEACHER")) {
                    return new Teacher(
                            id,
                            name,
                            email,
                            password,
                            ""
                    );
                }

                if (role.equals("ADMIN")) {
                    return new Administrator(
                            id,
                            name,
                            email,
                            password
                    );
                }
                return null;
            }
        }
    }
}