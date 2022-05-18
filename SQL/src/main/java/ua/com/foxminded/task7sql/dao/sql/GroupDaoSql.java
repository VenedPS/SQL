package ua.com.foxminded.task7sql.dao.sql;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ua.com.foxminded.task7sql.dao.GroupDao;
import ua.com.foxminded.task7sql.dao.connection.ConnectionPool;
import ua.com.foxminded.task7sql.dao.exception.DAOException;
import ua.com.foxminded.task7sql.dto.Group;

public class GroupDaoSql implements GroupDao {
    private final static String ADD_GROUP_QUERY = "INSERT INTO groups (group_id, group_name) VALUES(?,?)";
    private final static String GET_GROUP_BY_ID_QUERY = "SELECT group_id, group_name FROM groups WHERE group_id=?";
    private final static String SEARCH_GROUPS_BY_STUDENTS_QUANTITY_QUERY = "SELECT groups.group_id, groups.group_name, COUNT(1) as Quantity " 
            + "FROM students "
            + "INNER JOIN groups ON students.group_id = groups.group_id "
            + "GROUP BY groups.group_id, groups.group_name "
            + "HAVING COUNT(1) >= ?";
    private final static String POOL_IS_NULL_MESSAGE = "Connection pool is null";
    private final static String INPUT_IS_NULL_MESSAGE = "Input is null";
    
    private ConnectionPool connectionPool;
    
    public GroupDaoSql(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }
    
    @Override
    public String addGroup(Group input) {
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
        try(PreparedStatement preparedStatement = connection.prepareStatement(ADD_GROUP_QUERY)) {
            preparedStatement.setInt(1, input.getId());
            preparedStatement.setString(2, input.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Group was added";
    }
    
    @Override
    public Group getGroupById(int input) {
        Group group = new Group();
        if (connectionPool == null) {
            System.out.println(POOL_IS_NULL_MESSAGE);
            return group;
        }
        Connection connection;
        try {
            connection = connectionPool.getConnection();
        } catch (DAOException e) {
            e.printStackTrace();
            return group;
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_GROUP_BY_ID_QUERY)) {
            preparedStatement.setInt(1, input);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                group.setId(resultSet.getInt("group_id"));
                group.setName(resultSet.getString("group_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group;
    }

    @Override
    public List<Group> searchGroupsByStudentsQuantity(int quantity) {
        List<Group> groupsList = new ArrayList<>();
        if (connectionPool == null) {
            System.out.println(POOL_IS_NULL_MESSAGE);
            return groupsList;
        }
        Connection connection;
        try {
            connection = connectionPool.getConnection();
        } catch (DAOException e) {
            e.printStackTrace();
            return groupsList;
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_GROUPS_BY_STUDENTS_QUANTITY_QUERY)) { 
            preparedStatement.setInt(1, quantity);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("group_id"));
                group.setName(resultSet.getString("group_name"));
                groupsList.add(group);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupsList;
    }

}
