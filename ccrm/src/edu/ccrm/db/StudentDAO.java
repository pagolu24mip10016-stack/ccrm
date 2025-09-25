package edu.ccrm.db;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Department;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "id INTEGER PRIMARY KEY," +
                "regNo TEXT UNIQUE NOT NULL," +
                "name TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "department TEXT NOT NULL," +
                "enrollmentDate TEXT NOT NULL," +
                "active INTEGER NOT NULL DEFAULT 1" +
                ")";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (id, regNo, name, email, department, enrollmentDate, active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, student.getId());
            pstmt.setString(2, student.getRegNo());
            pstmt.setString(3, student.getName());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getDepartment().name());
            pstmt.setString(6, student.getEnrollmentDate().toString());
            pstmt.setInt(7, student.isActive() ? 1 : 0);
            pstmt.executeUpdate();
        }
    }

    public Student getStudent(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String regNo = rs.getString("regNo");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Department dept = Department.valueOf(rs.getString("department"));
                LocalDate enrollmentDate = LocalDate.parse(rs.getString("enrollmentDate"));
                boolean active = rs.getInt("active") == 1;
                Student student = new Student(id, regNo, name, email, dept, enrollmentDate);
                if (!active) {
                    student.deactivate();
                }
                return student;
            } else {
                return null;
            }
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String regNo = rs.getString("regNo");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Department dept = Department.valueOf(rs.getString("department"));
                LocalDate enrollmentDate = LocalDate.parse(rs.getString("enrollmentDate"));
                boolean active = rs.getInt("active") == 1;
                Student student = new Student(id, regNo, name, email, dept, enrollmentDate);
                if (!active) {
                    student.deactivate();
                }
                students.add(student);
            }
        }
        return students;
    }

    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET regNo = ?, name = ?, email = ?, department = ?, enrollmentDate = ?, active = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, student.getRegNo());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getDepartment().name());
            pstmt.setString(5, student.getEnrollmentDate().toString());
            pstmt.setInt(6, student.isActive() ? 1 : 0);
            pstmt.setInt(7, student.getId());
            pstmt.executeUpdate();
        }
    }

    public void deactivateStudent(int id) throws SQLException {
        String sql = "UPDATE students SET active = 0 WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
