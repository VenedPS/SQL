package ua.com.foxminded.task7sql.dao.sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.task7sql.dao.DBInitializer;
import ua.com.foxminded.task7sql.dao.connection.BasicConnectionPool;
import ua.com.foxminded.task7sql.dao.connection.ConnectionPool;
import ua.com.foxminded.task7sql.dao.exception.DAOException;
import ua.com.foxminded.task7sql.dto.Course;
import ua.com.foxminded.task7sql.dto.Student;

class StudentDaoSqlTest {
    private final static String POOL_IS_NULL_MESSAGE = "Connection pool is null";
    private final static String INPUT_IS_NULL_MESSAGE = "Input is null";
    
    private static ConnectionPool connectionPool;
    
    @BeforeAll
    public static void setUp() {
        Connection connection = null;
        try {
            connectionPool = new BasicConnectionPool();
            connection = connectionPool.getConnection();
            DBInitializer dBInitializer = new DBInitializer();
            dBInitializer.createTables(connection);
            StudentDaoSql studentDaoSql = new StudentDaoSql(connectionPool);
            studentDaoSql.addStudent(new Student(0, 0, "Test0", "Test0"));
            studentDaoSql.addStudent(new Student(1, 0, "Test1", "Test1"));
            studentDaoSql.addStudent(new Student(2,0,"Test2","Test2"));
            studentDaoSql.addStudentToCourse(2, 0);
            CourseDaoSql courseDaoSql = new CourseDaoSql(connectionPool);
            courseDaoSql.addCourse(new Course(0, "TestCourse0", "TestCourse0"));
        } catch (DAOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connectionPool.releaseConnection(connection);
            }
        }
    }
    
    @AfterAll
    public static void cleanUp() {
        try {
            connectionPool.shutdown();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }
    
  @Test
  void addStudent_shouldReturnMessage_whenConnectionPoolIsNull() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(null);
      
      String expected = POOL_IS_NULL_MESSAGE;
      
      String actual = studentDaoSql.addStudent(new Student());
      
      assertEquals(expected, actual);
  }
  
  @Test
  void addStudent_shouldReturnMessage_whenInputIsNull() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(connectionPool);
      
      String expected = INPUT_IS_NULL_MESSAGE;
      
      String actual = studentDaoSql.addStudent(null);
      
      assertEquals(expected, actual);
  }
  
  @Test
  void getStudentById_shouldreturnStudent_whenConnectionPoolIsNull() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(null);
      
      Student expected = new Student();
      
      Student actual = studentDaoSql.getStudentById(0);
      
      assertEquals(expected, actual);
  }
  
  @Test
  void addStudent_getStudentById_shoulReturnStudentFromDb_whenStudentCreated() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(connectionPool);
      
      Student expected = new Student(11, 1, "TestfirstName", "TestlastName");
      studentDaoSql.addStudent(expected);

      Student actual = new Student();
      actual = studentDaoSql.getStudentById(11);
      
      assertEquals(expected, actual);
  }
  
  @Test
  void addStudent_getStudentById_shoulReturnStudentsFromDb_whenFewStudentsCreated() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(connectionPool);
      
      Student expected1 = new Student(12, 0, "Test12", "Test12");
      Student expected2 = new Student(13, 0, "Test13", "Test13");
      studentDaoSql.addStudent(expected1);
      studentDaoSql.addStudent(expected2);

      Student actual1 = new Student();
      Student actual2 = new Student();
      actual1 = studentDaoSql.getStudentById(12);
      actual2 = studentDaoSql.getStudentById(13);

      assertEquals(expected1, actual1);
      assertEquals(expected2, actual2);
  }
  
  @Test
  void deleteStudentById_shouldReturnMessage_whenConnectionPoolIsNull() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(null);
      
      String expected = POOL_IS_NULL_MESSAGE;
      
      String actual = studentDaoSql.deleteStudentById(0);
      
      assertEquals(expected, actual);
  }
  
  @Test
  void deleteStudentById_shoulDeleteStudentFromDb_whenStudentCreated() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(connectionPool);
      
      Student expected = new Student();
      studentDaoSql.deleteStudentById(0);
      
      Student actual = new Student();
      actual = studentDaoSql.getStudentById(0);
      
      assertEquals(expected, actual);
  }

  @Test
  void addStudentToCourse_shouldReturnMessage_whenConnectionPoolIsNull() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(null);
      
      String expected = POOL_IS_NULL_MESSAGE;
      
      String actual = studentDaoSql.addStudentToCourse(0, 0);
      
      assertEquals(expected, actual);
  }
  
  @Test
  void searchStudentsInCouse_shouldReturnStudentsList_whenConnectionPoolIsNull() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(null);
      
      List<Student> expected = new ArrayList<>();
      
      List<Student> actual = studentDaoSql.searchStudentsInCourse("");
      
      assertEquals(expected, actual);
  }
  
  @Test
  void removeStudentFromCourse_shouldReturnMessage_whenConnectionPoolIsNull() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(null);
      
      String expected = POOL_IS_NULL_MESSAGE;
      
      String actual = studentDaoSql.removeStudentFromCourse(0, 0);
      
      assertEquals(expected, actual);
  }
  
  @Test
  void addStudentToCourse_searchStudentsInCouse_shoulReturnStudentsListFromDb_whenStudentCreated() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(connectionPool);

      List<Student> expected = new ArrayList<>();
      expected.add(new Student(1, 0, "Test1", "Test1"));
      studentDaoSql.addStudentToCourse(1, 0);

      List<Student> actual = new ArrayList<>();
      actual = studentDaoSql.searchStudentsInCourse("TestCourse0");
      
      assertEquals(expected, actual);
  }
  
  @Test
  void removeStudentFromCourse_shoulRemoveStudentFromCourseInDb_whenStudentCreated() {
      StudentDaoSql studentDaoSql = new StudentDaoSql(connectionPool);
      
      List<Student> expected = new ArrayList<>();
      studentDaoSql.removeStudentFromCourse(2, 0);

      List<Student> actual = new ArrayList<>();
      actual = studentDaoSql.searchStudentsInCourse("TestCourse0");

      assertEquals(expected, actual);
  }

}
