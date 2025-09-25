package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Department;
import edu.ccrm.exception.StudentNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class StudentService {
    private final Map<Integer, Student> studentMap = new HashMap<>();

    public void addStudent(Student student) {
        studentMap.put(student.getId(), student);
    }

    public void updateStudent(Student student) throws StudentNotFoundException {
        if (!studentMap.containsKey(student.getId())) {
            throw new StudentNotFoundException("Student not found with ID: " + student.getId());
        }
        studentMap.put(student.getId(), student);
    }

    public void deactivateStudent(int studentId) throws StudentNotFoundException {
        Student student = getStudent(studentId);
        student.deactivate();
    }

    public Student getStudent(int studentId) throws StudentNotFoundException {
        Student student = studentMap.get(studentId);
        if (student == null) {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }
        return student;
    }

    public List<Student> listAllStudents() {
        return new ArrayList<>(studentMap.values());
    }

    public List<Student> searchStudentsByDepartment(Department department) {
        return studentMap.values().stream()
                .filter(s -> s.getDepartment() == department && s.isActive())
                .collect(Collectors.toList());
    }

    public List<Student> searchStudentsByName(String name) {
        String lower = name.toLowerCase();
        return studentMap.values().stream()
                .filter(s -> s.isActive() && s.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }
}
