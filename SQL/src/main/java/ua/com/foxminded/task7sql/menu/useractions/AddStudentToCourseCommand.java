package ua.com.foxminded.task7sql.menu.useractions;

import ua.com.foxminded.task7sql.dao.SchoolDao;
import ua.com.foxminded.task7sql.util.ConsoleReader;

public class AddStudentToCourseCommand implements Command {
    SchoolDao schoolDao;
    
    public AddStudentToCourseCommand(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    @Override
    public String execute() {
        ConsoleReader consoleReader = new ConsoleReader();
        String inputLine = consoleReader.readFromConsole("Enter studentId and courseId separated by a space: ");
        String[] inputId = inputLine.split(" ");
        if (inputId.length < 2) {
            return "Invalid string entered! You must enter 2 numbers.";
        }

        int studentId = 0;
        int courseId = 0;
        try {
            studentId = Integer.parseInt(inputId[0]);
        } catch (NumberFormatException e) {
            return "Incorrect studentId value entered!";
        }
        try {
            courseId = Integer.parseInt(inputId[1]);
        } catch (NumberFormatException e) {
            return "Incorrect courseId value entered!";
        }
        return schoolDao.addStudentToCourse(studentId, courseId).toString();        
    }

}
