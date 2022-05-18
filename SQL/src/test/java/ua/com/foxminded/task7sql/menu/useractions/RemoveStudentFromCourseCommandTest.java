package ua.com.foxminded.task7sql.menu.useractions;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.com.foxminded.task7sql.dao.SchoolDao;

class RemoveStudentFromCourseCommandTest {
    private final static String CONSOLE_INPUT_ERROR_MESSAGE = "Invalid string entered! You must enter 2 numbers.";
    
    @Test
    void execute_shouldReturnMessage_whenInputEmptyLine() {
        SchoolDao schoolDao = Mockito.mock(SchoolDao.class);
        Mockito.when(schoolDao.removeStudentFromCourse(0, 0)).thenReturn("");
        RemoveStudentFromCourseCommand removeStudentFromCourseCommand = new RemoveStudentFromCourseCommand(schoolDao);
        
        String expected = CONSOLE_INPUT_ERROR_MESSAGE;
        
        String input = "";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        String actual = removeStudentFromCourseCommand.execute();
        
        assertEquals(expected, actual);
    }
    
    @Test
    void execute_shouldReturnMessage_whenInputOneSpace() {
        SchoolDao schoolDao = Mockito.mock(SchoolDao.class);
        Mockito.when(schoolDao.removeStudentFromCourse(0, 0)).thenReturn("");
        RemoveStudentFromCourseCommand removeStudentFromCourseCommand = new RemoveStudentFromCourseCommand(schoolDao);
        
        String expected = CONSOLE_INPUT_ERROR_MESSAGE;
        
        String input = " ";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        String actual = removeStudentFromCourseCommand.execute();
        
        assertEquals(expected, actual);
    }
    
    @Test
    void execute_shouldReturnMessage_whenInputInvalidLine() {
        SchoolDao schoolDao = Mockito.mock(SchoolDao.class);
        Mockito.when(schoolDao.removeStudentFromCourse(0, 0)).thenReturn("");
        RemoveStudentFromCourseCommand removeStudentFromCourseCommand = new RemoveStudentFromCourseCommand(schoolDao);
        
        String expected = CONSOLE_INPUT_ERROR_MESSAGE;
        
        String input = "Test Test";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        String actual = removeStudentFromCourseCommand.execute();
        
        assertEquals(expected, actual);
    }
    
    @Test
    void execute_shouldReturnMessage_whenSchoolDaoMocked() {
        SchoolDao schoolDao = Mockito.mock(SchoolDao.class);
        
        String expected = "added";
        Mockito.when(schoolDao.removeStudentFromCourse(0, 0)).thenReturn(expected);
        RemoveStudentFromCourseCommand removeStudentFromCourseCommand = new RemoveStudentFromCourseCommand(schoolDao);
        
        String input = "0 0";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        String actual = removeStudentFromCourseCommand.execute();

        assertEquals(expected, actual);
    }

}
