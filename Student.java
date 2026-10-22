package studentprofiling;

public class Student extends User {

    private String department;
    private int semester;


    public Student(
            String id,
            String name,
            String email,
            String password,
            String department,
            int semester) {

        super(
                id,
                name,
                email,
                password,
                "STUDENT"
        );

        this.department = department;
        this.semester = semester;
    }


    public String getDepartment() {
        return department;
    }


    public int getSemester() {
        return semester;
    }


    @Override
    public void accessStudentDetails() {

        System.out.println(
                "Student " + name +
                        " is accessing their profile."
        );
    }
}