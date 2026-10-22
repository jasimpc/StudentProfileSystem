# Student Profiling System — Technical Report

## 1. Project overview

The Student Profiling System is a Java console application backed by MySQL. It provides role-based workflows for students, teachers, and administrators to manage student profiles, academic records, and co-curricular activities.

## 2. Objectives and features

- Authenticate users and direct them to a role-specific menu.
- Let students view and update their own profile, review academic records and activities, and manage their own activities.
- Let teachers view student profiles and record marks and attendance.
- Let administrators add and list students, view profiles, and manage activities for students.
- Store data persistently in a relational database and enforce ownership when students delete their activities.

## 3. Architecture

The application uses a simple layered design:

- `User`, `Student`, `Teacher`, and `Administrator` represent domain users.
- `StudentProfilingSystem` provides the command-line menus and coordinates workflows.
- `UserDAO`, `StudentDAO`, `AcademicRecordDAO`, `CurriculumActivityDAO`, and `NonCurriculumActivityDAO` perform parameterized JDBC queries.
- `DBConnection` reads database configuration from environment variables.
- `PermissionGuard` centralizes role and student-record ownership checks.

The schema is supplied in [`schema.sql`](./schema.sql). Its five tables are `users`, `students`, `academic_records`, `curriculum_activities`, and `non_curriculum_activities`. Foreign keys cascade related-record deletion; constraints validate roles, semesters, marks, and attendance.

## 4. Requirements

- JDK 9 or later (the Maven build targets Java 8 APIs)
- Maven 3.6 or later
- MySQL 8.0.16 or later

## 5. Setup and execution

1. Create the schema by running `schema.sql` with a MySQL account allowed to create databases and tables.
2. Create the first administrator account manually. For example, run the following after replacing the sample identity, email, and password:

   ```sql
   USE student_profiling_system;
   INSERT INTO users (id, name, email, password, role)
   VALUES ('admin1', 'System Administrator', 'admin@example.com',
           'REPLACE_WITH_A_STRONG_PASSWORD', 'ADMIN');
   ```

3. Set the connection variables in the shell. Defaults are `DB_HOST=localhost`, `DB_PORT=3306`, `DB_NAME=student_profiling_system`, `DB_USER=root`, and an empty `DB_PASSWORD`.

   ```powershell
   $env:DB_HOST = "localhost"
   $env:DB_PORT = "3306"
   $env:DB_NAME = "student_profiling_system"
   $env:DB_USER = "root"
   $env:DB_PASSWORD = "your-local-mysql-password"
   ```

4. From the project directory, build and run:

   ```powershell
   mvn clean compile
   mvn exec:java
   ```

The MySQL JDBC driver is declared in `pom.xml` and is resolved by Maven.

## 6. Validation and error handling

Menu choices and numeric fields are read as complete input lines; invalid integer or decimal input is rejected and prompted again. Prepared statements are used for database values. Student activity deletions include both the activity ID and logged-in student ID in the delete condition.

## 7. Limitations and future work

This is an educational command-line prototype, not a production student information system. Passwords are currently stored and compared as plain text; replace this with salted password hashing before real use. The application also needs stronger field validation, automated tests against a disposable MySQL database, account management for teachers and administrators, and a graphical or web interface for broader deployment.
