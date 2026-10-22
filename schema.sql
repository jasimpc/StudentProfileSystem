CREATE DATABASE IF NOT EXISTS student_profiling_system
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE student_profiling_system;

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(50) PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(254) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(16) NOT NULL,
    CONSTRAINT chk_users_role CHECK (role IN ('STUDENT', 'TEACHER', 'ADMIN'))
);

CREATE TABLE IF NOT EXISTS students (
    student_id VARCHAR(50) PRIMARY KEY,
    department VARCHAR(120) NOT NULL,
    semester TINYINT UNSIGNED NOT NULL,
    CONSTRAINT chk_students_semester CHECK (semester BETWEEN 1 AND 12),
    CONSTRAINT fk_students_user FOREIGN KEY (student_id)
        REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS academic_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL,
    subject VARCHAR(120) NOT NULL,
    marks TINYINT UNSIGNED NOT NULL,
    attendance DECIMAL(5, 2) NOT NULL,
    CONSTRAINT chk_academic_marks CHECK (marks BETWEEN 0 AND 100),
    CONSTRAINT chk_academic_attendance CHECK (attendance BETWEEN 0 AND 100),
    CONSTRAINT fk_academic_student FOREIGN KEY (student_id)
        REFERENCES students (student_id) ON DELETE CASCADE,
    INDEX idx_academic_student (student_id)
);

CREATE TABLE IF NOT EXISTS curriculum_activities (
    activity_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL,
    activity VARCHAR(255) NOT NULL,
    CONSTRAINT fk_curriculum_student FOREIGN KEY (student_id)
        REFERENCES students (student_id) ON DELETE CASCADE,
    INDEX idx_curriculum_student (student_id)
);

CREATE TABLE IF NOT EXISTS non_curriculum_activities (
    activity_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id VARCHAR(50) NOT NULL,
    activity VARCHAR(255) NOT NULL,
    CONSTRAINT fk_non_curriculum_student FOREIGN KEY (student_id)
        REFERENCES students (student_id) ON DELETE CASCADE,
    INDEX idx_non_curriculum_student (student_id)
);
