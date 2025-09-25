package edu.ccrm.io;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Department;
import edu.ccrm.domain.Semester;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ImportExportService {

    public List<Student> importStudents(Path csvFile) throws IOException {
        List<String[]> records = CsvUtils.readCsv(csvFile);
        List<Student> students = new ArrayList<>();

        for (String[] record : records) {
            if (record.length < 6) continue; // skip incomplete lines
            try {
                int id = Integer.parseInt(record[0]);
                String regNo = record[1];
                String name = record[2];
                String email = record[3];
                Department dept = Department.valueOf(record[4]);
                LocalDate enrollmentDate = LocalDate.parse(record[5]);

                Student student = new Student(id, regNo, name, email, dept, enrollmentDate);
                students.add(student);
            } catch (Exception e) {
                // skip invalid record
            }
        }

        return students;
    }

    public List<Course> importCourses(Path csvFile) throws IOException {
        List<String[]> records = CsvUtils.readCsv(csvFile);
        List<Course> courses = new ArrayList<>();

        for (String[] record : records) {
            if (record.length < 6) continue;
            try {
                int courseId = Integer.parseInt(record[0]);
                String name = record[1];
                int credits = Integer.parseInt(record[2]);
                String instructor = record[3];
                Semester semester = Semester.valueOf(record[4]);
                Department department = Department.valueOf(record[5]);

                Course course = new Course(courseId, name, credits, instructor, semester, department);
                courses.add(course);
            } catch (Exception e) {
                // skip invalid record
            }
        }

        return courses;
    }

    public void exportStudents(List<Student> students, Path csvFile) throws IOException {
        List<String[]> data = new ArrayList<>();
        for (Student s : students) {
            String[] record = new String[]{
                    String.valueOf(s.getId()),
                    s.getRegNo(),
                    s.getName(),
                    s.getEmail(),
                    s.getDepartment().name(),
                    s.getEnrollmentDate().toString()
            };
            data.add(record);
        }
        CsvUtils.writeCsv(csvFile, data);
    }

    public void exportCourses(List<Course> courses, Path csvFile) throws IOException {
        List<String[]> data = new ArrayList<>();
        for (Course c : courses) {
            String[] record = new String[]{
                    String.valueOf(c.getCourseId()),
                    c.getCourseName(),
                    String.valueOf(c.getCredits()),
                    c.getInstructor(),
                    c.getSemester().name(),
                    c.getDepartment().name()
            };
            data.add(record);
        }
        CsvUtils.writeCsv(csvFile, data);
    }
}
