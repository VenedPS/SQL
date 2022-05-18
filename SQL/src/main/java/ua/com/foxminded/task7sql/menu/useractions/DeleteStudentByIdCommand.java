package ua.com.foxminded.task7sql.menu.useractions;

import ua.com.foxminded.task7sql.dao.SchoolDao;
import ua.com.foxminded.task7sql.util.ConsoleReader;

public class DeleteStudentByIdCommand implements Command {
    SchoolDao schoolDao;

    public DeleteStudentByIdCommand(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    @Override
    public String execute() {
        ConsoleReader consoleReader = new ConsoleReader();
        String inputLine = consoleReader.readFromConsole("Enter studentId: ");
        int studentId = 0;
        try {
            studentId = Integer.parseInt(inputLine);
        } catch (NumberFormatException e) {
            return "Incorrect studentId value entered!";
        }
        return schoolDao.deleteStudentById(studentId).toString();
    }

}
