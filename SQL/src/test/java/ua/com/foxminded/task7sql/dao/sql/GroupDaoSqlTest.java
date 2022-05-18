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
import ua.com.foxminded.task7sql.dto.Group;
import ua.com.foxminded.task7sql.dto.Student;

class GroupDaoSqlTest {
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
            GroupDaoSql groupDaoSql = new GroupDaoSql(connectionPool);
            groupDaoSql.addGroup(new Group(0, "Test0"));
            StudentDaoSql studentDaoSql = new StudentDaoSql(connectionPool);
            studentDaoSql.addStudent(new Student(0,0,"Test0","Test0"));
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
    void addGroup_shouldReturnMessage_whenConnectionPoolIsNull() {
        GroupDaoSql groupDaoSql = new GroupDaoSql(null);

        String expected = POOL_IS_NULL_MESSAGE;

        String actual = groupDaoSql.addGroup(new Group());

        assertEquals(expected, actual);
    }

    @Test
    void getGroupById_shouldReturnGroup_whenConnectionPoolIsNull() {
        GroupDaoSql groupDaoSql = new GroupDaoSql(null);

        Group expected = new Group();

        Group actual = groupDaoSql.getGroupById(0);

        assertEquals(expected, actual);
    }

    @Test
    void searchGroupsByStudentsQuantity_shouldreturnGroupList_whenConnectionPoolIsNull() {
        GroupDaoSql groupDaoSql = new GroupDaoSql(null);

        List<Group> expected = new ArrayList<>();

        List<Group> actual = groupDaoSql.searchGroupsByStudentsQuantity(1);

        assertEquals(expected, actual);
    }

    @Test
    void addGroup_shouldReturnMessage_whenInputIsNull() {
        GroupDaoSql groupDaoSql = new GroupDaoSql(connectionPool);

        String expected = INPUT_IS_NULL_MESSAGE;

        String actual = groupDaoSql.addGroup(null);

        assertEquals(expected, actual);
    }

    @Test
    void addGroup_getGroupById_shoulReturnGroupFromDb_whenGroupCreated() {
        GroupDaoSql groupDaoSql = new GroupDaoSql(connectionPool);
        Group expected = new Group(1, "Test1");
        groupDaoSql.addGroup(expected);

        Group actual = new Group();
        actual = groupDaoSql.getGroupById(1);

        assertEquals(expected, actual);
    }
  
  @Test
  void searchGroupsByStudentsQuantity_shoulReturnGoupListFromDb_whenGroupCreated() {
      GroupDaoSql groupDaoSql = new GroupDaoSql(connectionPool);
      List<Group> expected = new ArrayList<>();
      expected.add(new Group(0, "Test0"));

      List<Group> actual = new ArrayList<>();
      actual = groupDaoSql.searchGroupsByStudentsQuantity(1);
      
      assertEquals(expected, actual);
  }

}
