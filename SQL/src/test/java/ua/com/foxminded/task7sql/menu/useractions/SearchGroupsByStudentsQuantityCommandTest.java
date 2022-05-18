package ua.com.foxminded.task7sql.menu.useractions;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ua.com.foxminded.task7sql.dao.SchoolDao;
import ua.com.foxminded.task7sql.dto.Group;

class SearchGroupsByStudentsQuantityCommandTest {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final static String CONSOLE_INPUT_ERROR_MESSAGE = "Incorrect students quantity value entered!";
    
    @Test
    void execute_shouldReturnMessage_whenInputEmptyLine() {
        SchoolDao schoolDao = Mockito.mock(SchoolDao.class);
        Mockito.when(schoolDao.searchGroupsByStudentsQuantity(0)).thenReturn(new ArrayList<>());
        SearchGroupsByStudentsQuantityCommand searchGroupsByStudentsQuantityCommand = new SearchGroupsByStudentsQuantityCommand(schoolDao);
        
        String expected = CONSOLE_INPUT_ERROR_MESSAGE;
        
        String input = "";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        String actual = searchGroupsByStudentsQuantityCommand.execute();
        
        assertEquals(expected, actual);
    }
    
    @Test
    void execute_shouldReturnMessage_whenInputOneSpace() {
        SchoolDao schoolDao = Mockito.mock(SchoolDao.class);
        Mockito.when(schoolDao.searchGroupsByStudentsQuantity(0)).thenReturn(new ArrayList<>());
        SearchGroupsByStudentsQuantityCommand searchGroupsByStudentsQuantityCommand = new SearchGroupsByStudentsQuantityCommand(schoolDao);
        
        String expected = CONSOLE_INPUT_ERROR_MESSAGE;
        
        String input = " ";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        String actual = searchGroupsByStudentsQuantityCommand.execute();
        
        assertEquals(expected, actual);
    }
    
    @Test
    void execute_shouldReturnMessage_whenInputInvalidLine() {
        SchoolDao schoolDao = Mockito.mock(SchoolDao.class);
        Mockito.when(schoolDao.searchGroupsByStudentsQuantity(0)).thenReturn(new ArrayList<>());
        SearchGroupsByStudentsQuantityCommand searchGroupsByStudentsQuantityCommand = new SearchGroupsByStudentsQuantityCommand(schoolDao);
        
        String expected = CONSOLE_INPUT_ERROR_MESSAGE;
        
        String input = "Test Test";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        String actual = searchGroupsByStudentsQuantityCommand.execute();
        
        assertEquals(expected, actual);
    }
    
    @Test
    void execute_shouldReturnMessage_whenSchoolDaoMocked() {
        SchoolDao schoolDao = Mockito.mock(SchoolDao.class);
        
        List<Group> input = new ArrayList<>();
        input.add(new Group(0, "TE-00"));
        Mockito.when(schoolDao.searchGroupsByStudentsQuantity(1)).thenReturn(input);
        SearchGroupsByStudentsQuantityCommand searchGroupsByStudentsQuantityCommand = new SearchGroupsByStudentsQuantityCommand(schoolDao);
        
        String expected = "TE-00" + LINE_SEPARATOR;
        
        String inputString = "1";
        InputStream in = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(in);
        
        String actual = searchGroupsByStudentsQuantityCommand.execute();

        assertEquals(expected, actual);
    }

}
