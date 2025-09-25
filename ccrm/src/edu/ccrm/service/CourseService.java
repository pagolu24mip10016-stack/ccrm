package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Department;
import edu.ccrm.domain.Semester;
import edu.ccrm.exception.CourseNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class CourseService {
    private final Map<Integer, Course> courseMap = new HashMap<>();

    public void addCourse(Course course) {
        courseMap.put(course.getCourseId(), course);
    }

    public void updateCourse(Course course) throws CourseNotFoundException {
        if (!courseMap.containsKey(course.getCourseId())) {
            throw new CourseNotFoundException("Course not found with ID: " + course.getCourseId());
        }
        courseMap.put(course.getCourseId(), course);
    }

    public void deactivateCourse(int courseId) throws CourseNotFoundException {
        Course course = getCourse(courseId);
        course.deactivate();
    }

    public Course getCourse(int courseId) throws CourseNotFoundException {
        Course course = courseMap.get(courseId);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with ID: " + courseId);
        }
        return course;
    }

    public List<Course> listAllCourses() {
        return new ArrayList<>(courseMap.values());
    }

    public List<Course> searchCoursesByInstructor(String instructor) {
        String lower = instructor.toLowerCase();
        return courseMap.values().stream()
                .filter(c -> c.isActive() && c.getInstructor().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Course> searchCoursesByDepartment(Department department) {
        return courseMap.values().stream()
                .filter(c -> c.isActive() && c.getDepartment() == department)
                .collect(Collectors.toList());
    }

    public List<Course> searchCoursesBySemester(Semester semester) {
        return courseMap.values().stream()
                .filter(c -> c.isActive() && c.getSemester() == semester)
                .collect(Collectors.toList());
    }
}
