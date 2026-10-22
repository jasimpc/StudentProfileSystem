package studentprofiling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcademicRecordDAO {


    public void addAcademicRecord(

            String studentId,
            String subject,
            int marks,
            double attendance
    ) {

        String query =

                "INSERT INTO academic_records " +

                        "(student_id, subject, marks, attendance) " +

                        "VALUES (?, ?, ?, ?)";


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

            statement.setString(
                    2,
                    subject
            );

            statement.setInt(
                    3,
                    marks
            );

            statement.setDouble(
                    4,
                    attendance
            );


            statement.executeUpdate();


            System.out.println(
                    "Academic record added successfully."
            );

        }

        catch (SQLException e) {

            e.printStackTrace();
        }
    }


    public void viewAcademicRecords(
            String studentId) {

        String query =

                "SELECT * FROM academic_records " +

                        "WHERE student_id = ?";


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


            System.out.println(
                    "\n----- ACADEMIC RECORDS -----"
            );


            boolean found = false;


            while (result.next()) {

                found = true;


                System.out.println(

                        "Subject: " +

                                result.getString("subject") +

                                " | Marks: " +

                                result.getInt("marks") +

                                " | Attendance: " +

                                result.getDouble("attendance") +

                                "%"
                );
            }


            if (!found) {

                System.out.println(
                        "No academic records available."
                );
            }

        }

        catch (SQLException e) {

            e.printStackTrace();
        }
    }
}