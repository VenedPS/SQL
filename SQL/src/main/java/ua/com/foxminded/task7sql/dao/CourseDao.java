package ua.com.foxminded.task7sql.dao;

import ua.com.foxminded.task7sql.dto.Course;

public interface CourseDao {
    public String addCourse(Course input);
    public Course getCourseById(int input);
}
