package edu.ccrm.util;

import edu.ccrm.domain.Department;
import edu.ccrm.domain.Semester;

public class Validators {

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^.+@.+\\..+$");
    }

    public static boolean isValidDepartment(String deptStr) {
        try {
            Department.valueOf(deptStr.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isValidSemester(String semStr) {
        try {
            Semester.valueOf(semStr.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
