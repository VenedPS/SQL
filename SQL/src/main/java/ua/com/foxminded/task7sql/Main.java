package ua.com.foxminded.task7sql;

import ua.com.foxminded.task7sql.dao.DBInitializer;
import ua.com.foxminded.task7sql.servicedb.SchoolDataGenerator;
import ua.com.foxminded.task7sql.util.CommandSelector;

public class Main {

    public static void main(String[] args) {
        DBInitializer dBInitializer = new DBInitializer();
        dBInitializer.initializeDatabase();
        SchoolDataGenerator schoolDataGenerator = new SchoolDataGenerator();
        schoolDataGenerator.generateTestData();

        CommandSelector commandSelector = new CommandSelector();
        commandSelector.selectCommand();
    }

}
