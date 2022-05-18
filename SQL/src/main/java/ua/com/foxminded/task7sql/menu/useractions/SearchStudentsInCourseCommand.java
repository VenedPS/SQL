package ua.com.foxminded.task7sql.menu.useractions;

import java.util.List;

import ua.com.foxminded.task7sql.dao.SchoolDao;
import ua.com.foxminded.task7sql.dto.Student;
import ua.com.foxminded.task7sql.util.ConsoleReader;

public class SearchStudentsInCourseCommand implements Command {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    
    private SchoolDao schoolDao;

    public SearchStudentsInCourseCommand(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    @Override
    public String execute() {
        ConsoleReader consoleReader = new ConsoleReader();
        String inputLine = consoleReader.readFromConsole("Enter a course name: ");
        if (inputLine == "" || inputLine == " ") {
            return "Invalid string entered! Course name can't be empty!";
        }
        List<Student> students = schoolDao.searchStudentsInCourse(inputLine);
        StringBuilder result = new StringBuilder();
        students.forEach(student -> result.append(student + LINE_SEPARATOR));
        return result.toString();
    }

}
