package ua.com.foxminded.task7sql.util;

import ua.com.foxminded.task7sql.menu.Menu;

public class CommandSelector {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    public void selectCommand() {
        ConsoleReader consoleReader = new ConsoleReader();
        String inputLine = consoleReader.readFromConsole(
                "Enter command number: " + LINE_SEPARATOR + 
                "1: Find all groups with less or equals student count" + LINE_SEPARATOR + 
                "2: Find all students related to course with given name" + LINE_SEPARATOR + 
                "3: Add new student" + LINE_SEPARATOR + 
                "4: Delete student by STUDENT_ID" + LINE_SEPARATOR + 
                "5: Add a student to the course (from a list)" + LINE_SEPARATOR + 
                "6: Remove the student from one of his or her courses");
        
        Menu menu = new Menu();
        menu.execute(inputLine);
        consoleReader.closeScanner();
    }

}
