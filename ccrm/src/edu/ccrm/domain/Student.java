package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
    private final int id;
    private final String regNo;
    private String name;
    private String email;
    private Department department;
    private boolean isActive;
    private LocalDate enrollmentDate;
    private List<Course> enrolledCourses;

    public Student(int id, String regNo, String name, String email, Department department, LocalDate enrollmentDate) {
        this.id = id;
        this.regNo = regNo;
        this.name = name;
        this.email = email;
        this.department = department;
        this.enrollmentDate = enrollmentDate;
        this.isActive = true;
        this.enrolledCourses = new ArrayList<>();
    }

    // Getters and setters for mutable fields
    public int getId() {
        return id;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public List<Course> getEnrolledCourses() {
        return new ArrayList<>(enrolledCourses);
    }

    // Enrollment controls
    public void enrollCourse(Course course) {
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        }
    }

    public void unenrollCourse(Course course) {
        enrolledCourses.remove(course);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", regNo='" + regNo + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department=" + department +
                ", active=" + isActive +
                ", enrollmentDate=" + enrollmentDate +
                ", enrolledCourses=" + enrolledCourses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id == student.id && regNo.equals(student.regNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regNo);
    }
}
