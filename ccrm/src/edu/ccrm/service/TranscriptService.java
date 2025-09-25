package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.service.GradeService;

import java.util.List;

public class TranscriptService {

    private final GradeService gradeService = new GradeService();

    public String generateTranscript(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for Student: ").append(student.getName()).append("\n");
        sb.append("Registration No: ").append(student.getRegNo()).append("\n");
        sb.append("Department: ").append(student.getDepartment()).append("\n\n");

        List<Course> courses = student.getEnrolledCourses();

        double totalPoints = 0.0;
        int totalCredits = 0;

        for (Course course : courses) {
            int marks = getMarksForCourse(student, course); // Assume a method or map to get marks
            double gpa = gradeService.computeGPA(marks);
            sb.append(course.getCourseName())
              .append(" - Credits: ").append(course.getCredits())
              .append(", Marks: ").append(marks)
              .append(", GPA: ").append(String.format("%.2f", gpa))
              .append("\n");

            totalPoints += gpa * course.getCredits();
            totalCredits += course.getCredits();
        }

        double cumulativeGPA = totalCredits > 0 ? (totalPoints / totalCredits) : 0.0;
        sb.append("\nCumulative GPA: ").append(String.format("%.2f", cumulativeGPA)).append("\n");

        return sb.toString();
    }

    // Dummy implementation; in real, grades would be stored and retrieved properly
    private int getMarksForCourse(Student student, Course course) {
        return 75; // Default fixed marks for demo purpose
    }
}
