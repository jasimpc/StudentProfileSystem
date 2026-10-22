package studentprofiling;

class AcademicRecord {

    private String subject;
    private int marks;
    private double attendance;

    public AcademicRecord(
            String subject,
            int marks,
            double attendance) {

        this.subject = subject;
        this.marks = marks;
        this.attendance = attendance;
    }

    public void displayRecord() {

        System.out.println(
                "Subject: " + subject +
                        " | Marks: " + marks +
                        " | Attendance: " +
                        attendance + "%"
        );
    }
}
