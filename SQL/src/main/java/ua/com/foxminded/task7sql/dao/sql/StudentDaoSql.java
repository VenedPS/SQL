package ua.com.foxminded.task7sql.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task7sql.dao.StudentDao;
import ua.com.foxminded.task7sql.dao.connection.ConnectionPool;
import ua.com.foxminded.task7sql.dao.exception.DAOException;
import ua.com.foxminded.task7sql.dto.Student;

public class StudentDaoSql implements StudentDao {
    private final static String ADD_STUDENT_QUERY = "INSERT INTO students (student_id, group_id, first_name, last_name) VALUES(?,?,?,?)";
    private final static String GET_STUDENT_BY_ID_QUERY = "SELECT student_id, group_id, first_name, last_name FROM students WHERE student_id=?";
    private final static String DELETE_STUDENT_BY_ID_QUERY = "DELETE FROM students WHERE student_id=?";
    private final static String ADD_STUDENT_TO_COURSE_QUERY = "INSERT INTO students_courses (student_id, course_id) VALUES(?,?)";
    private final static String REMOVE_STUDENT_FROM_COURSE_QUERY = "DELETE FROM students_courses WHERE student_id=? AND course_id=?";
    private final static String SEARCH_STUDENTS_IN_COUSE_QUERY = 
            "SELECT students.student_id, students.group_id, students.first_name, students.last_name " +
            "FROM students_courses " + "JOIN courses ON students_courses.course_id = courses.course_id " +
            "JOIN students ON students_courses.student_id = students.student_id " + 
            "WHERE courses.course_name = ?";
    private final static String POOL_IS_NULL_MESSAGE = "Connection pool is null";
    private final static String INPUT_IS_NULL_MESSAGE = "Input is null";
    
    private ConnectionPool connectionPool;
    
    public StudentDaoSql(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
    
    @Override
    public String addStudent(Student input) {
        if (connectionPool == null) {
            return POOL_IS_NULL_MESSAGE;
        } else if (input == null) {
            return INPUT_IS_NULL_MESSAGE;
        }
        Connection connection;
        try {
            connection = connectionPool.getConnection();
        } catch (DAOException e) {
            return e.getMessage();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_STUDENT_QUERY)) {
            preparedStatement.setInt(1, input.getStudentId());
            preparedStatement.setInt(2, input.getGroupId());
            preparedStatement.setString(3, input.getFirstName());
            preparedStatement.setString(4, input.getLastName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Student was added";
    }
    
    @Override
    public Student getStudentById(int input) {
        Student student = new Student();
        if (connectionPool == null) {
            System.out.println(POOL_IS_NULL_MESSAGE);
            return student;
        }
        Connection connection;
        try {
            connection = connectionPool.getConnection();
        } catch (DAOException e) {
            e.printStackTrace();
            return student;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_STUDENT_BY_ID_QUERY)) {
            preparedStatement.setInt(1, input);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                student.setStudentId(resultSet.getInt("student_id"));
                student.setGroupId(resultSet.getInt("group_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public String deleteStudentById(int id) {
        if (connectionPool == null) {
            return POOL_IS_NULL_MESSAGE;
        }
        Connection connection;
        try {
            connection = connectionPool.getConnection();
        } catch (DAOException e) {
            return e.getMessage();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_STUDENT_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Student was deleted";
    }

    @Override
    public String addStudentToCourse(int studentId, int courseId) {
        if (connectionPool == null) {
            return POOL_IS_NULL_MESSAGE;
        }
        Connection connection;
        try {
            connection = connectionPool.getConnection();
        } catch (DAOException e) {
            return e.getMessage();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_STUDENT_TO_COURSE_QUERY)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Student was added to Course";
    }

    @Override
    public String removeStudentFromCourse(int studentId, int courseId) {
        if (connectionPool == null) {
            return POOL_IS_NULL_MESSAGE;
        }
        Connection connection;
        try {
            connection = connectionPool.getConnection();
        } catch (DAOException e) {
            return e.getMessage();
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_STUDENT_FROM_COURSE_QUERY)) {
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Student was removed from course";
    }

    @Override
    public List<Student> searchStudentsInCourse(String courseName) {
        List<Student> studentlist = new ArrayList<>();
        if (connectionPool == null) {
            System.out.println(POOL_IS_NULL_MESSAGE);
            return studentlist;
        }
        Connection connection;
        try {
            connection = connectionPool.getConnection();
        } catch (DAOException e) {
            e.printStackTrace();
            return studentlist;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_STUDENTS_IN_COUSE_QUERY)) {
            preparedStatement.setString(1, courseName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("student_id"));
                student.setGroupId(resultSet.getInt("group_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                studentlist.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentlist;
    }

}
