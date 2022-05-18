package ua.com.foxminded.task7sql.menu;

import java.util.HashMap;
import java.util.Map;

import ua.com.foxminded.task7sql.dao.SchoolDao;
import ua.com.foxminded.task7sql.menu.useractions.AddStudentCommand;
import ua.com.foxminded.task7sql.menu.useractions.AddStudentToCourseCommand;
import ua.com.foxminded.task7sql.menu.useractions.Command;
import ua.com.foxminded.task7sql.menu.useractions.DeleteStudentByIdCommand;
import ua.com.foxminded.task7sql.menu.useractions.RemoveStudentFromCourseCommand;
import ua.com.foxminded.task7sql.menu.useractions.SearchGroupsByStudentsQuantityCommand;
import ua.com.foxminded.task7sql.menu.useractions.SearchStudentsInCourseCommand;

public class Menu {
    private Map<String, Command> commands = new HashMap<>();

    public Menu() {
        SchoolDao schoolDao = new SchoolDao();
        commands.put("1", new SearchGroupsByStudentsQuantityCommand(schoolDao));
        commands.put("2", new SearchStudentsInCourseCommand(schoolDao));
        commands.put("3", new AddStudentCommand(schoolDao));
        commands.put("4", new DeleteStudentByIdCommand(schoolDao));
        commands.put("5", new AddStudentToCourseCommand(schoolDao));
        commands.put("6", new RemoveStudentFromCourseCommand(schoolDao));
    }
    
    public void execute(String commandNumber) {
        Command chusenCommand = commands.get(commandNumber);
        if (chusenCommand != null) {
            System.out.println(chusenCommand.execute());
        } else {
            System.out.println("Command #" + commandNumber + " not found.");
        }
    }

}
