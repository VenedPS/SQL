package ua.com.foxminded.task7sql.dao.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ua.com.foxminded.task7sql.dao.exception.DAOException;
import ua.com.foxminded.task7sql.util.FileReader;

public class BasicConnectionPool implements ConnectionPool {
    private static final int INITIAL_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 20;
    private static final int MAX_TIMEOUT = 5;
    private static final String PROPERTY_FILE = "/config.properties";
    
    private final String url;
    private final String user;
    private final String password;
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();

    public BasicConnectionPool() throws DAOException {
        FileReader fileReader = new FileReader();
        Properties property = fileReader.readPropertiesFiles(PROPERTY_FILE);
        this.url = property.getProperty("dbUrl");
        this.user = property.getProperty("dbUsername");
        this.password = property.getProperty("dbPassword");
        
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                pool.add(createConnection(url, user, password));
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        }
        this.connectionPool = pool;
    }

    @Override
    public Connection getConnection() throws DAOException {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                try {
                    connectionPool.add(createConnection(url, user, password));
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            } else {
                throw new DAOException("Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);

        try {
            if(!connection.isValid(MAX_TIMEOUT)){
                try {
                    connection = createConnection(url, user, password);
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        } catch (SQLException | DAOException e) {
            throw new DAOException(e.getMessage());
        }

        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void shutdown() throws DAOException {
        for (Connection usedConnection : usedConnections) {
            try {
                usedConnection.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        }
        usedConnections.clear();
        for (Connection connection : connectionPool) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        }
        connectionPool.clear();
    }
    
}
