package studentprofiling;

public class Administrator extends User {

    public Administrator(
            String id,
            String name,
            String email,
            String password) {

        super(
                id,
                name,
                email,
                password,
                "ADMIN"
        );
    }


    @Override
    public void accessStudentDetails() {

        System.out.println(
                "Administrator " + name +
                        " has full system access."
        );
    }
}
