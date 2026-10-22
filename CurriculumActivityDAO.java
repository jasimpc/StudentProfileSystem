package studentprofiling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurriculumActivityDAO {

    public void addActivity(
            String studentId,
            String activity) {

        String query =
                "INSERT INTO curriculum_activities " +
                        "(student_id, activity) VALUES (?, ?)";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setString(1, studentId);
            statement.setString(2, activity);

            statement.executeUpdate();

            System.out.println(
                    "Curriculum activity added successfully."
            );

        } catch (SQLException e) {

            System.out.println(
                    "Error adding curriculum activity."
            );

            e.printStackTrace();
        }
    }


    public void viewActivities(
            String studentId) {

        String query =
                "SELECT * FROM curriculum_activities " +
                        "WHERE student_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setString(1, studentId);

            ResultSet result =
                    statement.executeQuery();

            System.out.println(
                    "\n===== CURRICULUM ACTIVITIES ====="
            );

            boolean found = false;

            while (result.next()) {

                found = true;

                System.out.println(

                        result.getInt(
                                "activity_id"
                        )

                                + ". "

                                + result.getString(
                                "activity"
                        )
                );
            }

            if (!found) {

                System.out.println(
                        "No curriculum activities available."
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    /**
     * Admin-only delete: removes an activity by its ID without
     * checking ownership.  Must NOT be called from student-facing code.
     */
    public void deleteActivity(
            int activityId) {

        String query =
                "DELETE FROM curriculum_activities " +
                        "WHERE activity_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setInt(1, activityId);

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Curriculum activity deleted successfully."
                );

            } else {

                System.out.println(
                        "Activity not found."
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    /**
     * Ownership-aware delete: removes an activity only when
     * the activity's student_id matches {@code ownerStudentId}.
     * Use this method whenever a student initiates the delete so
     * that no student can delete another student's activity.
     *
     * @param activityId      the activity to delete
     * @param ownerStudentId  the student_id of the requesting student
     */
    public void deleteActivity(
            int activityId,
            String ownerStudentId) {

        String query =
                "DELETE FROM curriculum_activities " +
                        "WHERE activity_id = ? AND student_id = ?";

        try (
                Connection connection =
                        DBConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(query)
        ) {

            statement.setInt(1, activityId);
            statement.setString(2, ownerStudentId);

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Curriculum activity deleted successfully."
                );

            } else {

                System.out.println(
                        "[ACCESS DENIED] Activity not found or you do " +
                                "not own this activity."
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}