package ua.com.foxminded.task7sql.menu.useractions;

import java.util.List;

import ua.com.foxminded.task7sql.dao.SchoolDao;
import ua.com.foxminded.task7sql.dto.Group;
import ua.com.foxminded.task7sql.util.ConsoleReader;

public class SearchGroupsByStudentsQuantityCommand implements Command {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    
    private SchoolDao schoolDao;

    public SearchGroupsByStudentsQuantityCommand(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    @Override
    public String execute() {
        ConsoleReader consoleReader = new ConsoleReader();
        String inputLine = consoleReader.readFromConsole("Enter quantity of students: ");
        int quantity = 0;
        try {
            quantity = Integer.parseInt(inputLine);
        } catch (NumberFormatException e) {
            return "Incorrect students quantity value entered!";
        }
        List<Group> groups = schoolDao.searchGroupsByStudentsQuantity(quantity);
        StringBuilder result = new StringBuilder();
        groups.forEach(group -> result.append(group + LINE_SEPARATOR));
        return result.toString();
    }

}
