package ua.com.foxminded.task7sql.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ua.com.foxminded.task7sql.dto.Course;

class FileParserTest {

    private FileParser fileParser = new FileParser();
    
    @Test
    void parseCoursesFile_shouldThrowIllegalArgumentException_whenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            fileParser.parseCoursesFile(null);
        });
    }

    @Test
    void parseCoursesFile_shouldThrowIllegalArgumentException_whenInputIsEmpty() {
        List<String> input = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> {
            fileParser.parseCoursesFile(input);
        });
    }

    @Test
    void parseCoursesFile_shouldThrowIllegalArgumentException_whenInputEmptyFormat() {
        List<String> input = new ArrayList<>();
        input.add("");

        assertThrows(IllegalArgumentException.class, () -> {
            fileParser.parseCoursesFile(input);
        });
    }

    @Test
    void parseCoursesFile_shouldThrowIllegalArgumentException_whenWrongInputFormat() {
        List<String> input = new ArrayList<>();
        input.add("aaaa");

        assertThrows(IllegalArgumentException.class, () -> {
            fileParser.parseCoursesFile(input);
        });
    }

    @Test
    void parseCoursesFile_shouldReturnParsedData_whenOneInputLine() {
        List<String> input = new ArrayList<>();
        input.add("Biology_Biology");

        List<Course> expected = new ArrayList<>();
        expected.add(new Course(0,"Biology","Biology"));

        List<Course> actual = fileParser.parseCoursesFile(input);

        assertEquals(expected, actual);
    }

    @Test
    void parseCoursesFile_shouldReturnParsedData_whenFewInputLines() {
        List<String> input = new ArrayList<>();
        input.add("Biology_Biology");
        input.add("Chemistry_Chemistry");

        List<Course> expected = new ArrayList<>();
        expected.add(new Course(0,"Biology","Biology"));
        expected.add(new Course(1,"Chemistry","Chemistry"));

        List<Course> actual = fileParser.parseCoursesFile(input);

        assertEquals(expected, actual);
    }

}
