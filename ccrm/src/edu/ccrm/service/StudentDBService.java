package edu.ccrm.service;

import edu.ccrm.db.StudentDAO;
import edu.ccrm.domain.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentDBService {

    private final StudentDAO dao = new StudentDAO();

    public StudentDBService() {
        try {
            dao.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudent(Student student) throws SQLException {
        dao.addStudent(student);
    }

    public Student getStudent(int id) throws SQLException {
        return dao.getStudent(id);
    }

    public List<Student> getAllStudents() throws SQLException {
        return dao.getAllStudents();
    }

    public void updateStudent(Student student) throws SQLException {
        dao.updateStudent(student);
    }

    public void deactivateStudent(int id) throws SQLException {
        dao.deactivateStudent(id);
    }
}
