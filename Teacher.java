package studentprofiling;

public class Teacher extends User {

    private String department;


    public Teacher(
            String id,
            String name,
            String email,
            String password,
            String department) {

        super(
                id,
                name,
                email,
                password,
                "TEACHER"
        );

        this.department = department;
    }


    @Override
    public void accessStudentDetails() {

        System.out.println(
                "Teacher " + name +
                        " can access student details."
        );
    }
}
