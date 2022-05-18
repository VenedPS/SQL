package ua.com.foxminded.task7sql.dao.sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ua.com.foxminded.task7sql.dao.DBInitializer;
import ua.com.foxminded.task7sql.dao.connection.BasicConnectionPool;
import ua.com.foxminded.task7sql.dao.connection.ConnectionPool;
import ua.com.foxminded.task7sql.dao.exception.DAOException;
import ua.com.foxminded.task7sql.dto.Course;

class CourseDaoSqlTest {
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
    void addCourse_shouldReturnMessage_whenConnectionPoolIsNull() {
        CourseDaoSql courseDaoSql = new CourseDaoSql(null);
        
        String expected = POOL_IS_NULL_MESSAGE;
        
        String actual = courseDaoSql.addCourse(new Course());
        
        assertEquals(expected, actual);
    }
    
    @Test
    void getCourseById_shouldReturnCourse_whenConnectionPoolIsNull() {
        CourseDaoSql courseDaoSql = new CourseDaoSql(null);
        
        Course expected = new Course();
        
        Course actual = courseDaoSql.getCourseById(0);
        
        assertEquals(expected, actual);
    }
    
  @Test
  void addCourse_shouldReturnMessage_whenInputIsNull() {
      CourseDaoSql courseDaoSql = new CourseDaoSql(connectionPool);
      
      String expected = INPUT_IS_NULL_MESSAGE;
      
      String actual = courseDaoSql.addCourse(null);
      
      assertEquals(expected, actual);
  }
  
  @Test
  void addCourse_getCourseById_shoulReturnCourseFromDb_whenCouseCreated() {
      CourseDaoSql courseDaoSql = new CourseDaoSql(connectionPool);
      
      Course expected = new Course(1, "Test1", "test1");
      courseDaoSql.addCourse(expected);
      
      Course actual = new Course();
      actual = courseDaoSql.getCourseById(1);

      assertEquals(expected, actual);
  }
  
  @Test
  void addCourse_getCourseById_shoulReturnCourseFromDb_whenFewCousesCreated() {
      CourseDaoSql courseDaoSql = new CourseDaoSql(connectionPool);
      
      Course expected1 = new Course(2, "Test2", "test2");
      Course expected2 = new Course(3, "Test3", "test3");
      courseDaoSql.addCourse(expected1);
      courseDaoSql.addCourse(expected2);

      Course actual1 = new Course();
      Course actual2 = new Course();
      actual1 = courseDaoSql.getCourseById(2);
      actual2 = courseDaoSql.getCourseById(3);
      
      assertEquals(expected1, actual1);
      assertEquals(expected2, actual2);
  }

}
