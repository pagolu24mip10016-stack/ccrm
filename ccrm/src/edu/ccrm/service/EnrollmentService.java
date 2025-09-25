package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.MaxCreditExceededException;

import java.util.List;

public class EnrollmentService {
    private static final int MAX_CREDITS_PER_SEMESTER = 24;

    public void enrollStudentInCourse(Student student, Course course) throws MaxCreditExceededException {
        int currentCredits = student.getEnrolledCourses().stream()
                .mapToInt(Course::getCredits)
                .sum();
        if (currentCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditExceededException("Enrollment exceeds max allowed credits per semester");
        }
        student.enrollCourse(course);
    }

    public void unenrollStudentFromCourse(Student student, Course course) {
        student.unenrollCourse(course);
    }

    public List<Course> getStudentEnrolledCourses(Student student) {
        return student.getEnrolledCourses();
    }
}
