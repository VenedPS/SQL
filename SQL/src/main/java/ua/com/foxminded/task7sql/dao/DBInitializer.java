package ua.com.foxminded.task7sql.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import ua.com.foxminded.task7sql.util.FileReader;

public class DBInitializer {
    private static final String DB_SERVER_URL = "jdbc:postgresql://localhost/";
    private static final String DB_URL = "jdbc:postgresql://localhost/task7";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";

    public void initializeDatabase() {
        Connection connectionToServer = null;
        try {
            connectionToServer = DriverManager.getConnection(DB_SERVER_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        createDataBase(connectionToServer);
        System.out.println("DB was created");
        if (connectionToServer != null) {
            try {
                connectionToServer.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        createTables(connection);
        System.out.println("Tables was created");
        if (connectionToServer != null) {
            try {
                connectionToServer.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createDataBase(Connection connection) {
        StringBuilder dbCreatingScript = new StringBuilder();
        FileReader fileReader = new FileReader();
        for (String line : fileReader.readFiles("/dbCreating")) {
            dbCreatingScript.append(line);
        }
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(dbCreatingScript.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables(Connection connection) {
        StringBuilder tableCreatingScript = new StringBuilder();
        FileReader fileReader = new FileReader();
        for (String line : fileReader.readFiles("/tablesCreating")) {
            tableCreatingScript.append(line);
        }
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(tableCreatingScript.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
