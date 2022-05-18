package ua.com.foxminded.task7sql.dao;

import java.util.List;

import ua.com.foxminded.task7sql.dto.Student;

public interface StudentDao {
    public String addStudent(Student input);
    public Student getStudentById(int id);
    public String deleteStudentById(int id);
    public String addStudentToCourse(int studentId, int courseId);
    public String removeStudentFromCourse(int studentId, int courseId);
    public List<Student> searchStudentsInCourse(String courseName);

}
