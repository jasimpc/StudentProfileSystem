package studentprofiling;

import java.sql.SQLException;
import java.util.Scanner;

public class StudentProfilingSystem {

    static CurriculumActivityDAO curriculumDAO =
            new CurriculumActivityDAO();

    static NonCurriculumActivityDAO nonCurriculumDAO =
            new NonCurriculumActivityDAO();

    static Scanner scanner =
            new Scanner(System.in);

    static UserDAO userDAO =
            new UserDAO();

    static StudentDAO studentDAO =
            new StudentDAO();

    static AcademicRecordDAO academicDAO =
            new AcademicRecordDAO();


    public static void main(
            String[] args) {

        boolean running = true;


        while (running) {

            System.out.println(
                    "\n=============================="
            );

            System.out.println(
                    " STUDENT PROFILING SYSTEM"
            );

            System.out.println(
                    "=============================="
            );

            System.out.println(
                    "1. Login"
            );

            System.out.println(
                    "2. Exit"
            );


            System.out.print(
                    "Enter choice: "
            );


            int choice = readInt();


            switch (choice) {

                case 1:

                    login();

                    break;


                case 2:

                    running = false;

                    System.out.println(
                            "Application closed."
                    );

                    break;


                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }


    public static void login() {

        System.out.println(
                "\n===== LOGIN ====="
        );


        System.out.print(
                "User ID: "
        );

        String id =
                scanner.nextLine();


        System.out.print(
                "Password: "
        );

        String password =
                scanner.nextLine();


        User user;
        try {
            user = userDAO.login(id, password);
        } catch (SQLException e) {
            System.err.println("Unable to log in: " + e.getMessage());
            return;
        }


        if (user == null) {

            System.out.println(
                    "Invalid login credentials."
            );

            return;
        }


        System.out.println(
                "Login successful!"
        );


        if (user instanceof Student) {

            studentMenu(
                    (Student) user
            );

        }


        else if (user instanceof Teacher) {

            teacherMenu(
                    (Teacher) user
            );

        }


        else if (user instanceof Administrator) {

            adminMenu(
                    (Administrator) user
            );
        }
    }


    public static void studentMenu(
            Student student) {

        boolean active = true;

        while (active) {

            System.out.println(
                    "\n===== STUDENT MENU ====="
            );

            System.out.println(
                    "1. View My Profile"
            );

            System.out.println(
                    "2. View Academic Records"
            );

            System.out.println(
                    "3. View Curriculum Activities"
            );

            System.out.println(
                    "4. Add Curriculum Activity"
            );

            System.out.println(
                    "5. Delete My Curriculum Activity"
            );

            System.out.println(
                    "6. View Non-Curriculum Activities"
            );

            System.out.println(
                    "7. Add Non-Curriculum Activity"
            );

            System.out.println(
                    "8. Delete My Non-Curriculum Activity"
            );

            System.out.println(
                    "9. Update My Profile"
            );

            System.out.println(
                    "10. Logout"
            );

            System.out.print(
                    "Enter choice: "
            );

            int choice = readInt();


            switch (choice) {

                case 1:

                    viewStudentDetails(
                            student.getId()
                    );

                    break;


                case 2:

                    academicDAO.viewAcademicRecords(
                            student.getId()
                    );

                    break;


                case 3:

                    curriculumDAO.viewActivities(
                            student.getId()
                    );

                    break;


                case 4:

                    // Guard: student may only add to their own record
                    if (PermissionGuard.checkAndDeny(
                            student,
                            student.getId())) {

                        addCurriculumActivity(
                                student.getId()
                        );
                    }

                    break;


                case 5:

                    // Guard: student may only delete their own activity
                    if (PermissionGuard.checkAndDeny(
                            student,
                            student.getId())) {

                        deleteMyCurriculumActivity(
                                student.getId()
                        );
                    }

                    break;


                case 6:

                    nonCurriculumDAO.viewActivities(
                            student.getId()
                    );

                    break;


                case 7:

                    // Guard: student may only add to their own record
                    if (PermissionGuard.checkAndDeny(
                            student,
                            student.getId())) {

                        addNonCurriculumActivity(
                                student.getId()
                        );
                    }

                    break;


                case 8:

                    // Guard: student may only delete their own activity
                    if (PermissionGuard.checkAndDeny(
                            student,
                            student.getId())) {

                        deleteMyNonCurriculumActivity(
                                student.getId()
                        );
                    }

                    break;


                case 9:

                    // Guard: student may only update their own profile
                    if (PermissionGuard.checkAndDeny(
                            student,
                            student.getId())) {

                        updateMyProfile(
                                student
                        );
                    }

                    break;


                case 10:

                    active = false;

                    System.out.println(
                            "Logged out successfully."
                    );

                    break;


                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    public static void teacherMenu(
            Teacher teacher) {

        boolean active = true;

        while (active) {

            System.out.println(
                    "\n===== TEACHER MENU ====="
            );

            System.out.println(
                    "1. View Student Profile"
            );

            System.out.println(
                    "2. Add Academic Record"
            );

            System.out.println(
                    "3. View Curriculum Activities"
            );

            System.out.println(
                    "4. View Non-Curriculum Activities"
            );

            System.out.println(
                    "5. Logout"
            );

            System.out.print(
                    "Enter choice: "
            );

            int choice = readInt();


            switch (choice) {

                case 1:

                    viewStudent();

                    break;


                case 2:

                    addAcademicRecord();

                    break;


                case 3:

                    viewCurriculumActivities();

                    break;


                case 4:

                    viewNonCurriculumActivities();

                    break;


                case 5:

                    active = false;

                    System.out.println(
                            "Logged out successfully."
                    );

                    break;


                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    public static void adminMenu(
            Administrator admin) {

        boolean active = true;

        while (active) {

            System.out.println(
                    "\n===== ADMIN MENU ====="
            );

            System.out.println(
                    "1. Add Student"
            );

            System.out.println(
                    "2. View All Students"
            );

            System.out.println(
                    "3. View Student Profile"
            );

            System.out.println(
                    "4. Add Curriculum Activity"
            );

            System.out.println(
                    "5. Delete Curriculum Activity"
            );

            System.out.println(
                    "6. Add Non-Curriculum Activity"
            );

            System.out.println(
                    "7. Delete Non-Curriculum Activity"
            );

            System.out.println(
                    "8. Logout"
            );

            System.out.print(
                    "Enter choice: "
            );

            int choice = readInt();


            switch (choice) {

                case 1:

                    addStudent();

                    break;


                case 2:

                    studentDAO.displayAllStudents();

                    break;


                case 3:

                    viewStudent();

                    break;


                case 4:

                    addCurriculumActivityByAdmin(admin);

                    break;


                case 5:

                    deleteCurriculumActivity();

                    break;


                case 6:

                    addNonCurriculumActivityByAdmin(admin);

                    break;


                case 7:

                    deleteNonCurriculumActivity();

                    break;


                case 8:

                    active = false;

                    System.out.println(
                            "Logged out successfully."
                    );

                    break;


                default:

                    System.out.println(
                            "Invalid choice."
                    );
            }
        }
    }

    public static void addStudent() {

        System.out.print(
                "Student ID: "
        );

        String id =
                scanner.nextLine();


        System.out.print(
                "Name: "
        );

        String name =
                scanner.nextLine();


        System.out.print(
                "Email: "
        );

        String email =
                scanner.nextLine();


        System.out.print(
                "Password: "
        );

        String password =
                scanner.nextLine();


        System.out.print(
                "Department: "
        );

        String department =
                scanner.nextLine();


        System.out.print(
                "Semester: "
        );

        int semester = readIntInRange(1, 12);


        Student student =
                new Student(

                        id,
                        name,
                        email,
                        password,
                        department,
                        semester
                );


        studentDAO.addStudent(
                student
        );
    }


    public static void viewStudent() {

        System.out.print(
                "Enter Student ID: "
        );

        String studentId =
                scanner.nextLine();


        viewStudentDetails(
                studentId
        );
    }

    public static void viewStudentDetails(
            String studentId) {

        Student student =
                studentDAO.findStudent(
                        studentId
                );


        if (student == null) {

            System.out.println(
                    "Student not found."
            );

            return;
        }


        System.out.println(
                "\n================================"
        );

        System.out.println(
                "       STUDENT PROFILE"
        );

        System.out.println(
                "================================"
        );


        System.out.println(
                "ID         : " +
                        student.getId()
        );


        System.out.println(
                "Name       : " +
                        student.getName()
        );


        System.out.println(
                "Email      : " +
                        student.getEmail()
        );


        System.out.println(
                "Department : " +
                        student.getDepartment()
        );


        System.out.println(
                "Semester   : " +
                        student.getSemester()
        );


        academicDAO.viewAcademicRecords(
                studentId
        );


        curriculumDAO.viewActivities(
                studentId
        );


        nonCurriculumDAO.viewActivities(
                studentId
        );
    }


    public static void addAcademicRecord() {

        System.out.print(
                "Student ID: "
        );


        String studentId =
                scanner.nextLine();


        System.out.print(
                "Subject: "
        );


        String subject =
                scanner.nextLine();


        System.out.print(
                "Marks: "
        );


        int marks = readIntInRange(0, 100);


        System.out.print(
                "Attendance: "
        );


        double attendance = readDoubleInRange(0, 100);


        academicDAO.addAcademicRecord(

                studentId,

                subject,

                marks,

                attendance
        );
    }
    public static void addCurriculumActivity(
            String studentId) {

        System.out.print(
                "Enter Curriculum Activity: "
        );

        String activity =
                scanner.nextLine();

        curriculumDAO.addActivity(
                studentId,
                activity
        );
    }


    /**
     * Student-facing delete: uses the ownership-aware DAO overload
     * so the SQL WHERE clause enforces student_id = ownerStudentId.
     */
    public static void deleteMyCurriculumActivity(
            String ownerStudentId) {

        curriculumDAO.viewActivities(ownerStudentId);

        System.out.print(
                "\nEnter Activity ID to delete: "
        );

        int activityId = readInt();

        // Ownership enforced inside the DAO
        curriculumDAO.deleteActivity(
                activityId,
                ownerStudentId
        );
    }
    public static void addCurriculumActivityByAdmin(
            Administrator admin) {

        // Guard: only ADMIN role may use this path
        if (!PermissionGuard.checkAndDeny(admin, null)) {
            return;
        }

        System.out.print(
                "Enter Student ID: "
        );

        String studentId =
                scanner.nextLine();


        Student student =
                studentDAO.findStudent(
                        studentId
                );


        if (student == null) {

            System.out.println(
                    "Student not found."
            );

            return;
        }


        System.out.print(
                "Enter Curriculum Activity: "
        );

        String activity =
                scanner.nextLine();


        curriculumDAO.addActivity(
                studentId,
                activity
        );
    }
    public static void addNonCurriculumActivity(
            String studentId) {

        System.out.print(
                "Enter Non-Curriculum Activity: "
        );

        String activity =
                scanner.nextLine();


        nonCurriculumDAO.addActivity(
                studentId,
                activity
        );
    }


    /**
     * Student-facing delete: uses the ownership-aware DAO overload
     * so the SQL WHERE clause enforces student_id = ownerStudentId.
     */
    public static void deleteMyNonCurriculumActivity(
            String ownerStudentId) {

        nonCurriculumDAO.viewActivities(ownerStudentId);

        System.out.print(
                "\nEnter Activity ID to delete: "
        );

        int activityId = readInt();

        // Ownership enforced inside the DAO
        nonCurriculumDAO.deleteActivity(
                activityId,
                ownerStudentId
        );
    }
    public static void addNonCurriculumActivityByAdmin(
            Administrator admin) {

        // Guard: only ADMIN role may use this path
        if (!PermissionGuard.checkAndDeny(admin, null)) {
            return;
        }

        System.out.print(
                "Enter Student ID: "
        );

        String studentId =
                scanner.nextLine();


        Student student =
                studentDAO.findStudent(
                        studentId
                );


        if (student == null) {

            System.out.println(
                    "Student not found."
            );

            return;
        }


        System.out.print(
                "Enter Non-Curriculum Activity: "
        );

        String activity =
                scanner.nextLine();


        nonCurriculumDAO.addActivity(
                studentId,
                activity
        );
    }
    public static void deleteCurriculumActivity() {

        System.out.print(
                "Enter Student ID: "
        );

        String studentId =
                scanner.nextLine();


        curriculumDAO.viewActivities(
                studentId
        );


        System.out.print(
                "\nEnter Activity ID to delete: "
        );

        int activityId = readInt();


        curriculumDAO.deleteActivity(
                activityId,
                studentId
        );
    }
    public static void deleteNonCurriculumActivity() {

        System.out.print(
                "Enter Student ID: "
        );

        String studentId =
                scanner.nextLine();


        nonCurriculumDAO.viewActivities(
                studentId
        );


        System.out.print(
                "\nEnter Activity ID to delete: "
        );

        int activityId = readInt();


        nonCurriculumDAO.deleteActivity(
                activityId,
                studentId
        );
    }
    public static void viewCurriculumActivities() {

        System.out.print(
                "Enter Student ID: "
        );

        String studentId =
                scanner.nextLine();


        curriculumDAO.viewActivities(
                studentId
        );
    }
    public static void viewNonCurriculumActivities() {

        System.out.print(
                "Enter Student ID: "
        );

        String studentId =
                scanner.nextLine();


        nonCurriculumDAO.viewActivities(
                studentId
        );
    }


    /**
     * Allows a student to update ONLY their own profile fields
     * (email and password).  The WHERE clause uses the session
     * student's ID so a student can never change another student's data.
     */
    public static void updateMyProfile(
            Student student) {

        System.out.println(
                "\n===== UPDATE MY PROFILE ====="
        );

        System.out.println(
                "Leave a field blank to keep the current value."
        );


        System.out.print(
                "New Email (current: " + student.getEmail() + "): "
        );

        String newEmail =
                scanner.nextLine().trim();


        System.out.print(
                "New Password: "
        );

        String newPassword =
                scanner.nextLine().trim();


        // Build the update only for fields the student actually filled in
        if (newEmail.isEmpty() && newPassword.isEmpty()) {

            System.out.println(
                    "No changes made."
            );

            return;
        }


        StringBuilder sql =
                new StringBuilder(
                        "UPDATE users SET "
                );

        boolean needsComma = false;


        if (!newEmail.isEmpty()) {

            sql.append("email = ?");
            needsComma = true;
        }


        if (!newPassword.isEmpty()) {

            if (needsComma) {
                sql.append(", ");
            }

            sql.append("password = ?");
        }


        // Critical: WHERE clause is bound to the LOGGED-IN student's ID
        sql.append(" WHERE id = ?");


        try (

                java.sql.Connection connection =
                        DBConnection.getConnection();

                java.sql.PreparedStatement statement =
                        connection.prepareStatement(
                                sql.toString()
                        )
        ) {

            int paramIndex = 1;


            if (!newEmail.isEmpty()) {

                statement.setString(
                        paramIndex++,
                        newEmail
                );
            }


            if (!newPassword.isEmpty()) {

                statement.setString(
                        paramIndex++,
                        newPassword
                );
            }


            // Bind to the session student's ID — not a user-supplied ID
            statement.setString(
                    paramIndex,
                    student.getId()
            );


            int rows =
                    statement.executeUpdate();


            if (rows > 0) {

                System.out.println(
                        "Profile updated successfully."
                );

            } else {

                System.out.println(
                        "Update failed."
                );
            }

        } catch (java.sql.SQLException e) {

            System.err.println(
                    "Unable to update profile: " + e.getMessage()
            );
        }
    }

    private static int readInt() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid whole number: ");
            }
        }
    }

    private static int readIntInRange(int minimum, int maximum) {
        while (true) {
            int value = readInt();
            if (value >= minimum && value <= maximum) {
                return value;
            }
            System.out.print(
                    "Enter a value from " + minimum + " to " + maximum + ": "
            );
        }
    }

    private static double readDoubleInRange(double minimum, double maximum) {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (Double.isFinite(value)
                        && value >= minimum
                        && value <= maximum) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // Prompt again below.
            }
            System.out.print(
                    "Enter a number from " + minimum + " to " + maximum + ": "
            );
        }
    }

}