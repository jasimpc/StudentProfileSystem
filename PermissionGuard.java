package studentprofiling;

/**
 * PermissionGuard – centralised ownership / role checks.
 *
 * Rules enforced here:
 *  - A STUDENT may only read or modify records whose student_id
 *    matches their own login id.
 *  - A TEACHER may read any student record but may NOT modify
 *    student-owned data (activities).
 *  - An ADMINISTRATOR has unrestricted access.
 */
public class PermissionGuard {

    /**
     * Returns true when the acting user is allowed to modify
     * a record that belongs to {@code targetStudentId}.
     *
     * @param actingUser      the currently logged-in User
     * @param targetStudentId the student_id of the record being modified
     * @return true if access is permitted, false otherwise
     */
    public static boolean canModifyStudentRecord(
            User actingUser,
            String targetStudentId) {

        if (actingUser == null) {
            return false;
        }

        String role = actingUser.getRole();

        // Administrators may modify any record
        if ("ADMIN".equalsIgnoreCase(role)) {
            return true;
        }

        // Teachers may NOT modify student-owned records
        if ("TEACHER".equalsIgnoreCase(role)) {
            return false;
        }

        // Students may only modify their own records
        if ("STUDENT".equalsIgnoreCase(role)) {
            return actingUser.getId().equals(targetStudentId);
        }

        // Unknown role – deny by default
        return false;
    }

    /**
     * Convenience method: prints an ACCESS DENIED message and
     * returns false when the acting user is not allowed to modify
     * the target record.  Returns true silently when permitted.
     *
     * @param actingUser      the currently logged-in User
     * @param targetStudentId the student_id of the record being modified
     * @return true if access is permitted, false (+ message) otherwise
     */
    public static boolean checkAndDeny(
            User actingUser,
            String targetStudentId) {

        if (canModifyStudentRecord(actingUser, targetStudentId)) {
            return true;
        }

        System.out.println(
                "[ACCESS DENIED] You do not have permission to " +
                        "modify records for student ID: " +
                        targetStudentId
        );

        return false;
    }
}
