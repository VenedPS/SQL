package ua.com.foxminded.task7sql.dao.connection;

import java.sql.Connection;

import ua.com.foxminded.task7sql.dao.exception.DAOException;

public interface ConnectionPool {
    
    Connection getConnection() throws DAOException;    
    boolean releaseConnection(Connection connection);    
    void shutdown() throws DAOException;
    
}
