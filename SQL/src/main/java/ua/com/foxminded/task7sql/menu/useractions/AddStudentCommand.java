package ua.com.foxminded.task7sql.menu.useractions;

import ua.com.foxminded.task7sql.dao.SchoolDao;
import ua.com.foxminded.task7sql.dto.Student;
import ua.com.foxminded.task7sql.util.ConsoleReader;

public class AddStudentCommand implements Command {
    SchoolDao schoolDao;

    public AddStudentCommand(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    @Override
    public String execute() {
        ConsoleReader consoleReader = new ConsoleReader();
        String inputLine = consoleReader.readFromConsole("Enter studentId, groupId, firstName and lastName separated by a space: ");

        String[] inputId = inputLine.split(" ");
        if (inputId.length < 4) {
            return "Invalid string entered! You must enter 2 numbers and 2 words.";
        }

        int studentId = 0;
        int groupId = 0;
        try {
            studentId = Integer.parseInt(inputId[0]);
        } catch (NumberFormatException e) {
            return "Incorrect studentId value entered!";
        }
        try {
            groupId = Integer.parseInt(inputId[1]);
        } catch (NumberFormatException e) {
            return "Incorrect groupId value entered!";
        }
       return schoolDao.addStudent(new Student(studentId,groupId,inputId[2],inputId[3])).toString();
    }

}
