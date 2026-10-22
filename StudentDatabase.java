package studentprofiling;

import java.util.ArrayList;

class StudentDatabase {

    private ArrayList<Student> students;
    private ArrayList<Teacher> teachers;
    private ArrayList<Administrator> administrators;

    public StudentDatabase() {

        students = new ArrayList<>();
        teachers = new ArrayList<>();
        administrators = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void addAdministrator(
            Administrator administrator) {

        administrators.add(administrator);
    }

    public Student findStudent(String id) {

        for (Student student : students) {

            if (student.getId().equals(id)) {
                return student;
            }
        }

        return null;
    }

    public User login(
            String id,
            String password) {

        for (Student student : students) {

            if (student.getId().equals(id) &&
                    student.getPassword().equals(password)) {

                return student;
            }
        }

        for (Teacher teacher : teachers) {

            if (teacher.getId().equals(id) &&
                    teacher.getPassword().equals(password)) {

                return teacher;
            }
        }

        for (Administrator admin : administrators) {

            if (admin.getId().equals(id) &&
                    admin.getPassword().equals(password)) {

                return admin;
            }
        }

        return null;
    }

    public void displayAllStudents() {

        System.out.println(
                "\n========== ALL STUDENTS =========="
        );

        if (students.isEmpty()) {

            System.out.println(
                    "No students available."
            );

        } else {

            for (Student student : students) {

                System.out.println(
                        student.getId() +
                                " - " +
                                student.getName()
                );
            }
        }
    }
}