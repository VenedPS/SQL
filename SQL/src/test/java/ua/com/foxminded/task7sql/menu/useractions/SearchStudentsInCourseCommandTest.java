package ua.com.foxminded.task7sql.menu.useractions;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.com.foxminded.task7sql.dao.SchoolDao;
import ua.com.foxminded.task7sql.dto.Student;

class SearchStudentsInCourseCommandTest {
    
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final static String CONSOLE_INPUT_ERROR_MESSAGE = "Invalid string entered! Course name can't be empty!";
    
    @Test
    void execute_shouldReturnMessage_whenInputEmptyLine() {
        SchoolDao schoolDao = Mockito.mock(SchoolDao.class);
        Mockito.when(schoolDao.searchStudentsInCourse("")).thenReturn(new ArrayList<>());
        SearchStudentsInCourseCommand searchStudentsInCourseCommand = new SearchStudentsInCourseCommand(schoolDao);
        
        String expected = CONSOLE_INPUT_ERROR_MESSAGE;
        
        String input = "";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        String actual = searchStudentsInCourseCommand.execute();
        
        assertEquals(expected, actual);
    }
    
    @Test
    void execute_shouldReturnMessage_whenInputOneSpace() {
        SchoolDao schoolDao = Mockito.mock(SchoolDao.class);
        Mockito.when(schoolDao.searchStudentsInCourse(" ")).thenReturn(new ArrayList<>());
        SearchStudentsInCourseCommand searchStudentsInCourseCommand = new SearchStudentsInCourseCommand(schoolDao);
        
        String expected = CONSOLE_INPUT_ERROR_MESSAGE;
        
        String input = " ";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        String actual = searchStudentsInCourseCommand.execute();
        
        assertEquals(expected, actual);
    }
    
    @Test
    void execute_shouldReturnMessage_whenSchoolDaoMocked() {
        SchoolDao schoolDao = Mockito.mock(SchoolDao.class);
        
        List<Student> input = new ArrayList<>();
        input.add(new Student(0, 0, "Test", "Test"));
        Mockito.when(schoolDao.searchStudentsInCourse("0 0 Test Test")).thenReturn(input);
        SearchStudentsInCourseCommand searchStudentsInCourseCommand = new SearchStudentsInCourseCommand(schoolDao);
        
        String expected = "Test Test" + LINE_SEPARATOR;
        
        String inputString = "0 0 Test Test";
        InputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        
        String actual = searchStudentsInCourseCommand.execute();

        assertEquals(expected, actual);
    }

}
