package studentprofiling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {


    public void addStudent(Student student) {

        String userQuery =
                "INSERT INTO users " +
                        "(id, name, email, password, role) " +
                        "VALUES (?, ?, ?, ?, 'STUDENT')";


        String studentQuery =
                "INSERT INTO students " +
                        "(student_id, department, semester) " +
                        "VALUES (?, ?, ?)";


        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement userStatement =
                         connection.prepareStatement(userQuery);
                 PreparedStatement studentStatement =
                         connection.prepareStatement(studentQuery)) {
                userStatement.setString(1, student.getId());
                userStatement.setString(2, student.getName());
                userStatement.setString(3, student.getEmail());
                userStatement.setString(4, student.getPassword());
                userStatement.executeUpdate();

                studentStatement.setString(1, student.getId());
                studentStatement.setString(2, student.getDepartment());
                studentStatement.setInt(3, student.getSemester());
                studentStatement.executeUpdate();

                connection.commit();
                System.out.println("Student added successfully.");
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Unable to add student: " + e.getMessage());
        }
    }


    public Student findStudent(
            String studentId) {

        String query =
                "SELECT users.*, students.department, " +
                        "students.semester " +

                        "FROM users " +

                        "JOIN students " +

                        "ON users.id = students.student_id " +

                        "WHERE students.student_id = ?";


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setString(
                    1,
                    studentId
            );


            ResultSet result =
                    statement.executeQuery();


            if (result.next()) {

                return new Student(

                        result.getString("id"),

                        result.getString("name"),

                        result.getString("email"),

                        result.getString("password"),

                        result.getString("department"),

                        result.getInt("semester")
                );
            }

        }

        catch (SQLException e) {

            e.printStackTrace();
        }


        return null;
    }


    public void displayAllStudents() {

        String query =
                "SELECT users.id, users.name, " +
                        "students.department, students.semester " +

                        "FROM users " +

                        "JOIN students " +

                        "ON users.id = students.student_id";


        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query);

                ResultSet result =
                        statement.executeQuery()
        ) {

            System.out.println(
                    "\n===== ALL STUDENTS ====="
            );


            while (result.next()) {

                System.out.println(

                        result.getString("id") +

                                " | " +

                                result.getString("name") +

                                " | " +

                                result.getString("department") +

                                " | Semester: " +

                                result.getInt("semester")
                );
            }

        }

        catch (SQLException e) {

            e.printStackTrace();
        }
    }
}