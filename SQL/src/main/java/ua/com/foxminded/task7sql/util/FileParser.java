package ua.com.foxminded.task7sql.util;

import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.task7sql.dto.Course;

public class FileParser {
    private static final String SPLIT_SYMBOL = "_";
    int iteration = 0;

    public List<Course> parseCoursesFile(List<String> input) {
        if (input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Data not found!");
        }

        List<Course> courses = new ArrayList<>();
        input.stream().map(line -> line.split(SPLIT_SYMBOL)).forEach(line -> {
            if (line.length == 2) {
                courses.add(new Course(iteration++, line[0], line[1]));
            } else {
                throw new IllegalArgumentException("Wrong data format!");
            }
        });
        return courses;
    }
}