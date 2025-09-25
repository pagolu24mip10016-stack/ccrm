package edu.ccrm;

import edu.ccrm.domain.*;
import edu.ccrm.exception.*;
import edu.ccrm.service.*;
import edu.ccrm.io.*;
import edu.ccrm.util.*;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static final GradeService gradeService = new GradeService();
    private static final TranscriptService transcriptService = new TranscriptService();
    private static final ImportExportService importExportService = new ImportExportService();

    public static void main(String[] args) {
        ConfigSingleton config = ConfigSingleton.getInstance();
        System.out.println("Welcome to Campus Course Records Manager (CCRM).");

        boolean exit = false;
        while (!exit) {
            showMainMenu();
            int choice = readInt();
            switch (choice) {
                case 1:
                    manageStudents();
                    break;
                case 2:
                    manageCourses();
                    break;
                case 3:
                    manageEnrollments();
                    break;
                case 4:
                    manageGradesAndTranscripts();
                    break;
                case 5:
                    fileOperations();
                    break;
                case 0:
                    System.out.println("Exiting CCRM. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments");
        System.out.println("4. Manage Grades and Transcripts");
        System.out.println("5. File Operations");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }

    private static void manageStudents() {
        System.out.println("\nStudent Management Menu:");
        System.out.println("1. Add Student");
        System.out.println("2. Update Student");
        System.out.println("3. Deactivate Student");
        System.out.println("4. List All Students");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter choice: ");
        int choice = readInt();
        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                updateStudent();
                break;
            case 3:
                deactivateStudent();
                break;
            case 4:
                listAllStudents();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addStudent() {
        try {
            System.out.print("Enter ID: ");
            int id = readInt();

            System.out.print("Enter Registration Number: ");
            String regNo = scanner.nextLine();

            System.out.print("Enter Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            if (!Validators.isValidEmail(email)) {
                System.out.println("Invalid email format.");
                return;
            }

            System.out.print("Enter Department (e.g., COMPUTER_SCIENCE): ");
            String deptStr = scanner.nextLine().toUpperCase();
            if (!Validators.isValidDepartment(deptStr)) {
                System.out.println("Invalid department.");
                return;
            }
            Department department = Department.valueOf(deptStr);

            System.out.print("Enter Enrollment Date (yyyy-MM-dd): ");
            String dateStr = scanner.nextLine();
            LocalDate enrollmentDate = DateTimeUtil.parseDate(dateStr);
            if (enrollmentDate == null) {
                System.out.println("Invalid date format.");
                return;
            }

            Student student = new Student(id, regNo, name, email, department, enrollmentDate);
            studentService.addStudent(student);
            System.out.println("Student added successfully.");

        } catch (Exception e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    private static void updateStudent() {
        try {
            System.out.print("Enter Student ID to update: ");
            int id = readInt();

            Student student = studentService.getStudent(id);

            System.out.print("Enter new Name (blank to skip): ");
            String name = scanner.nextLine();
            if (!name.isBlank()) {
                student.setName(name);
            }

            System.out.print("Enter new Email (blank to skip): ");
            String email = scanner.nextLine();
            if (!email.isBlank()) {
                if (!Validators.isValidEmail(email)) {
                    System.out.println("Invalid email format.");
                    return;
                }
                student.setEmail(email);
            }

            System.out.print("Enter new Department (blank to skip): ");
            String deptStr = scanner.nextLine();
            if (!deptStr.isBlank()) {
                if (!Validators.isValidDepartment(deptStr)) {
                    System.out.println("Invalid department.");
                    return;
                }
                student.setDepartment(Department.valueOf(deptStr.toUpperCase()));
            }

            studentService.updateStudent(student);
            System.out.println("Student updated successfully.");

        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deactivateStudent() {
        try {
            System.out.print("Enter Student ID to deactivate: ");
            int id = readInt();

            studentService.deactivateStudent(id);
            System.out.println("Student deactivated.");

        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listAllStudents() {
        List<Student> students = studentService.listAllStudents();
        students.forEach(s -> System.out.println(s));
    }

    private static void manageCourses() {
        System.out.println("\nCourse Management Menu:");
        System.out.println("1. Add Course");
        System.out.println("2. Update Course");
        System.out.println("3. Deactivate Course");
        System.out.println("4. List All Courses");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter choice: ");
        int choice = readInt();
        switch (choice) {
            case 1:
                addCourse();
                break;
            case 2:
                updateCourse();
                break;
            case 3:
                deactivateCourse();
                break;
            case 4:
                listAllCourses();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addCourse() {
        try {
            System.out.print("Enter Course ID: ");
            int id = readInt();

            System.out.print("Enter Course Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Credits: ");
            int credits = readInt();

            System.out.print("Enter Instructor Name: ");
            String instructor = scanner.nextLine();

            System.out.print("Enter Semester (e.g., SPRING): ");
            String semStr = scanner.nextLine().toUpperCase();
            if (!Validators.isValidSemester(semStr)) {
                System.out.println("Invalid semester.");
                return;
            }
            Semester semester = Semester.valueOf(semStr);

            System.out.print("Enter Department (e.g., COMPUTER_SCIENCE): ");
            String deptStr = scanner.nextLine().toUpperCase();
            if (!Validators.isValidDepartment(deptStr)) {
                System.out.println("Invalid department.");
                return;
            }
            Department department = Department.valueOf(deptStr);

            Course course = new Course(id, name, credits, instructor, semester, department);
            courseService.addCourse(course);
            System.out.println("Course added successfully.");

        } catch (Exception e) {
            System.out.println("Error adding course: " + e.getMessage());
        }
    }

    private static void updateCourse() {
        try {
            System.out.print("Enter Course ID to update: ");
            int id = readInt();

            Course course = courseService.getCourse(id);

            System.out.print("Enter new Course Name (blank to skip): ");
            String name = scanner.nextLine();
            if (!name.isBlank()) {
                course.setCourseName(name);
            }

            System.out.print("Enter new Credits (blank to skip): ");
            String creditsStr = scanner.nextLine();
            if (!creditsStr.isBlank()) {
                int credits = Integer.parseInt(creditsStr);
                course.setCredits(credits);
            }

            System.out.print("Enter new Instructor Name (blank to skip): ");
            String instructor = scanner.nextLine();
            if (!instructor.isBlank()) {
                course.setInstructor(instructor);
            }

            System.out.print("Enter new Semester (blank to skip): ");
            String semStr = scanner.nextLine();
            if (!semStr.isBlank()) {
                if (!Validators.isValidSemester(semStr)) {
                    System.out.println("Invalid semester.");
                    return;
                }
                course.setSemester(Semester.valueOf(semStr.toUpperCase()));
            }

            System.out.print("Enter new Department (blank to skip): ");
            String deptStr = scanner.nextLine();
            if (!deptStr.isBlank()) {
                if (!Validators.isValidDepartment(deptStr)) {
                    System.out.println("Invalid department.");
                    return;
                }
                course.setDepartment(Department.valueOf(deptStr.toUpperCase()));
            }

            courseService.updateCourse(course);
            System.out.println("Course updated successfully.");

        } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deactivateCourse() {
        try {
            System.out.print("Enter Course ID to deactivate: ");
            int id = readInt();

            courseService.deactivateCourse(id);
            System.out.println("Course deactivated.");

        } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listAllCourses() {
        List<Course> courses = courseService.listAllCourses();
        courses.forEach(c -> System.out.println(c));
    }

    private static void manageEnrollments() {
        System.out.println("\nEnrollment Management Menu:");
        System.out.println("1. Enroll Student in Course");
        System.out.println("2. Unenroll Student from Course");
        System.out.println("3. List Enrolled Courses for Student");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter choice: ");
        int choice = readInt();
        switch (choice) {
            case 1:
                enrollStudentInCourse();
                break;
            case 2:
                unenrollStudentFromCourse();
                break;
            case 3:
                listEnrolledCourses();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void enrollStudentInCourse() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = readInt();
            Student student = studentService.getStudent(studentId);

            System.out.print("Enter Course ID: ");
            int courseId = readInt();
            Course course = courseService.getCourse(courseId);

            enrollmentService.enrollStudentInCourse(student, course);
            System.out.println("Student enrolled in course.");

        } catch (StudentNotFoundException | CourseNotFoundException | MaxCreditExceededException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void unenrollStudentFromCourse() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = readInt();
            Student student = studentService.getStudent(studentId);

            System.out.print("Enter Course ID: ");
            int courseId = readInt();
            Course course = courseService.getCourse(courseId);

            enrollmentService.unenrollStudentFromCourse(student, course);
            System.out.println("Student unenrolled from course.");

        } catch (StudentNotFoundException | CourseNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listEnrolledCourses() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = readInt();
            Student student = studentService.getStudent(studentId);

            List<Course> courses = enrollmentService.getStudentEnrolledCourses(student);
            if (courses.isEmpty()) {
                System.out.println("Student has no enrolled courses.");
            } else {
                courses.forEach(c -> System.out.println(c));
            }
        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void manageGradesAndTranscripts() {
        System.out.println("\nGrades and Transcripts Menu:");
        System.out.println("1. Generate Transcript for Student");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter choice: ");
        int choice = readInt();
        switch (choice) {
            case 1:
                generateTranscript();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void generateTranscript() {
        try {
            System.out.print("Enter Student ID: ");
            int studentId = readInt();
            Student student = studentService.getStudent(studentId);

            String transcript = transcriptService.generateTranscript(student);
            System.out.println("\n" + transcript);

        } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void fileOperations() {
        System.out.println("\nFile Operations Menu:");
        System.out.println("1. Import Students from CSV");
        System.out.println("2. Import Courses from CSV");
        System.out.println("3. Export Students to CSV");
        System.out.println("4. Export Courses to CSV");
        System.out.println("5. Backup Data Directory");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter choice: ");
        int choice = readInt();
        switch (choice) {
            case 1:
                importStudents();
                break;
            case 2:
                importCourses();
                break;
            case 3:
                exportStudents();
                break;
            case 4:
                exportCourses();
                break;
            case 5:
                backupDataDirectory();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void importStudents() {
        try {
            System.out.print("Enter path to students CSV file: ");
            String path = scanner.nextLine();
            List<Student> students = importExportService.importStudents(Paths.get(path));
            for (Student s : students) {
                studentService.addStudent(s);
            }
            System.out.println(students.size() + " students imported.");
        } catch (Exception e) {
            System.out.println("Error importing students: " + e.getMessage());
        }
    }

    private static void importCourses() {
        try {
            System.out.print("Enter path to courses CSV file: ");
            String path = scanner.nextLine();
            List<Course> courses = importExportService.importCourses(Paths.get(path));
            for (Course c : courses) {
                courseService.addCourse(c);
            }
            System.out.println(courses.size() + " courses imported.");
        } catch (Exception e) {
            System.out.println("Error importing courses: " + e.getMessage());
        }
    }

    private static void exportStudents() {
        try {
            System.out.print("Enter path to export students CSV file: ");
            String path = scanner.nextLine();
            importExportService.exportStudents(studentService.listAllStudents(), Paths.get(path));
            System.out.println("Students exported.");
        } catch (Exception e) {
            System.out.println("Error exporting students: " + e.getMessage());
        }
    }

    private static void exportCourses() {
        try {
            System.out.print("Enter path to export courses CSV file: ");
            String path = scanner.nextLine();
            importExportService.exportCourses(courseService.listAllCourses(), Paths.get(path));
            System.out.println("Courses exported.");
        } catch (Exception e) {
            System.out.println("Error exporting courses: " + e.getMessage());
        }
    }

    private static void backupDataDirectory() {
        try {
            System.out.print("Enter source directory path for backup: ");
            String sourceDir = scanner.nextLine();

            System.out.print("Enter target directory path for backup: ");
            String targetDir = scanner.nextLine();

            FileBackupUtil.backupDirectory(Paths.get(sourceDir), Paths.get(targetDir));
            System.out.println("Backup completed successfully.");
        } catch (Exception e) {
            System.out.println("Backup failed: " + e.getMessage());
        }
    }

    private static int readInt() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number, please try again: ");
            }
        }
    }
}
