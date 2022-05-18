package ua.com.foxminded.task7sql.dao;

import java.util.List;

import ua.com.foxminded.task7sql.dao.connection.BasicConnectionPool;
import ua.com.foxminded.task7sql.dao.connection.ConnectionPool;
import ua.com.foxminded.task7sql.dao.exception.DAOException;
import ua.com.foxminded.task7sql.dao.sql.CourseDaoSql;
import ua.com.foxminded.task7sql.dao.sql.GroupDaoSql;
import ua.com.foxminded.task7sql.dao.sql.StudentDaoSql;
import ua.com.foxminded.task7sql.dto.Course;
import ua.com.foxminded.task7sql.dto.Group;
import ua.com.foxminded.task7sql.dto.Student;

public class SchoolDao {
    
    private ConnectionPool connectionPool;
    
    public SchoolDao() {
        try {
            this.connectionPool = new BasicConnectionPool();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    
    public String addStudent(Student input) {
        StudentDao studentDao = new StudentDaoSql(connectionPool);
        String result = studentDao.addStudent(input);
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public Student getStudentById(int id) {
        StudentDao studentDao = new StudentDaoSql(connectionPool);
        Student student = studentDao.getStudentById(id);
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return student;
    }

    public String deleteStudentById(int id) {
        StudentDao studentDao = new StudentDaoSql(connectionPool);
        String result = studentDao.deleteStudentById(id);
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String addStudentToCourse(int studentId, int courseId) {
        StudentDao studentDao = new StudentDaoSql(connectionPool);
        String result = studentDao.addStudentToCourse(studentId, courseId);
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String removeStudentFromCourse(int studentId, int courseId) {
        StudentDao studentDao = new StudentDaoSql(connectionPool);
        String result = studentDao.removeStudentFromCourse(studentId, courseId);
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Student> searchStudentsInCourse(String courseName) {
        StudentDao studentDao = new StudentDaoSql(connectionPool);
        List<Student> students = studentDao.searchStudentsInCourse(courseName);
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return students;
    }

    public String addGroup(Group input) {
        GroupDao groupDao = new GroupDaoSql(connectionPool);
        String result = groupDao.addGroup(input);
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Group getGroupById(int input) {
        GroupDao groupDao = new GroupDaoSql(connectionPool);
        Group group = groupDao.getGroupById(input);
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return group;
    }
    
    public List<Group> searchGroupsByStudentsQuantity(int quantity) {
        GroupDao groupDao = new GroupDaoSql(connectionPool);
        List<Group> groups = groupDao.searchGroupsByStudentsQuantity(quantity);
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return groups;
    }

    public String addCourse(Course input) {
        CourseDao courseDao = new CourseDaoSql(connectionPool);
        String result = courseDao.addCourse(input);
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Course getCourseById(int input) {
        CourseDao courseDao = new CourseDaoSql(connectionPool);
        Course course = courseDao.getCourseById(input);
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return course;
    }
    
}
