package ua.com.foxminded.task7sql.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ua.com.foxminded.task7sql.dao.CourseDao;
import ua.com.foxminded.task7sql.dao.connection.ConnectionPool;
import ua.com.foxminded.task7sql.dao.exception.DAOException;
import ua.com.foxminded.task7sql.dto.Course;

public class CourseDaoSql implements CourseDao {
    private final static String ADD_COURSE_QUERY = "INSERT INTO courses (course_id, course_name, course_description) VALUES(?,?,?)";
    private final static String GET_COURSE_BY_ID_QUERY = "SELECT course_id, course_name, course_description FROM courses WHERE course_id=?";
    private final static String POOL_IS_NULL_MESSAGE = "Connection pool is null";
    private final static String INPUT_IS_NULL_MESSAGE = "Input is null";
    
    private ConnectionPool connectionPool;
    
    public CourseDaoSql(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public String addCourse(Course input) {
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
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_COURSE_QUERY)) {
            preparedStatement.setInt(1, input.getId());
            preparedStatement.setString(2, input.getName());
            preparedStatement.setString(3, input.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Course was added";
    }

    @Override
    public Course getCourseById(int input) {
        Course course = new Course();
        if (connectionPool == null) {
            System.out.println(POOL_IS_NULL_MESSAGE);
            return course;
        }
        Connection connection;
        try {
            connection = connectionPool.getConnection();
        } catch (DAOException e) {
            e.printStackTrace();
            return course;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_COURSE_BY_ID_QUERY)) {
            preparedStatement.setInt(1, input);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                course.setId(resultSet.getInt("course_id"));
                course.setName(resultSet.getString("course_name"));
                course.setDescription(resultSet.getString("course_description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

}
