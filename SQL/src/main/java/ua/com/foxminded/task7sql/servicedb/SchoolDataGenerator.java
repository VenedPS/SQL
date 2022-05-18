package ua.com.foxminded.task7sql.servicedb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.com.foxminded.task7sql.dao.SchoolDao;
import ua.com.foxminded.task7sql.dto.Course;
import ua.com.foxminded.task7sql.dto.Group;
import ua.com.foxminded.task7sql.dto.Student;
import ua.com.foxminded.task7sql.util.FileParser;
import ua.com.foxminded.task7sql.util.FileReader;

public class SchoolDataGenerator {
    private static char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public void generateTestData() {
        List<Group> groups = generateGroup();
        List<Course> courses = generateCourse();
        List<Student> students = generateStudens();
        assignStudentsToGroups(groups, students);
        Map<Student, Set<Course>> studentsCourses = assignCoursesToStudents(students, courses);

        addGroupsToDB(groups);
        addCoursesToDB(courses);
        addStudentsToDB(students);
        addStudentsCoursesToDB(studentsCourses);

        System.out.println("Initial data was added to the database");
    }

    private List<Group> generateGroup() {
        List<Group> groups = new ArrayList<Group>();
        for (int i = 0; i < 10; i++) {
            StringBuilder groupName = new StringBuilder();
            groupName.append(ALPHABET[getRandomInt(0, ALPHABET.length - 1)]);
            groupName.append(ALPHABET[getRandomInt(0, ALPHABET.length - 1)]);
            groupName.append('-');
            groupName.append(getRandomInt(0, 9));
            groupName.append(getRandomInt(0, 9));
            Group group = new Group(i, groupName.toString());
            groups.add(group);
        }
        return groups;
    }

    private List<Course> generateCourse() {
        FileReader fileReader = new FileReader();
        List<String> courseFileData = fileReader.readFiles("/courses.txt");
        FileParser fileParser = new FileParser();
        List<Course> courses = fileParser.parseCoursesFile(courseFileData);
        return courses;
    }

    private List<Student> generateStudens() {
        FileReader fileReader = new FileReader();
        List<String> firstNames = fileReader.readFiles("/firstNames.txt");
        List<String> lastNames = fileReader.readFiles("/lastNames.txt");

        List<Student> students = new ArrayList<Student>();
        for (int i = 0; i < 200; i++) {
            String firstName = firstNames.get(getRandomInt(0, firstNames.size() - 1));
            String lastName = lastNames.get(getRandomInt(0, lastNames.size() - 1));
            Student student = new Student(i, 0, firstName, lastName);
            students.add(student);
        }
        return students;
    }

    private void assignStudentsToGroups(List<Group> groups, List<Student> students) {
        int studentIndex = 0;
        for (Group group : groups) {
            int groupSize = getRandomInt(10, 30);
            for (int i = 0; i < groupSize; i++) {
                if (studentIndex == students.size()) {
                    break;
                }
                Student student = students.get(studentIndex);
                student.setGroupId(group.getId());
                studentIndex++;
            }
        }
    }

    private Map<Student, Set<Course>> assignCoursesToStudents(List<Student> students, List<Course> courses) {
        Map<Student, Set<Course>> studentsCourses = new HashMap<>();
        for (Student student : students) {
            Set<Course> coursesForEachStudent = new HashSet<>();
            int coursesQuantity = getRandomInt(1, 3);
            for (int i = 0; i < coursesQuantity; i++) {
                int randomCourseIndex = getRandomInt(0, courses.size() - 1);
                Course randomCourse = courses.get(randomCourseIndex);
                coursesForEachStudent.add(randomCourse);
            }
            studentsCourses.put(student, coursesForEachStudent);
        }
        return studentsCourses;
    }

    private void addGroupsToDB(List<Group> groups) {
        SchoolDao schoolDao = new SchoolDao();
        for (Group group : groups) {
            schoolDao.addGroup(group);
        }
        System.out.println("Groups was added to DB");
    }

    private void addCoursesToDB(List<Course> courses) {
        SchoolDao schoolDao = new SchoolDao();
        for (Course course : courses) {
            schoolDao.addCourse(course);
        }
        System.out.println("Courses was added to DB");
    }

    private void addStudentsToDB(List<Student> students) {
        SchoolDao schoolDao = new SchoolDao();
        for (Student student : students) {
            schoolDao.addStudent(student);
        }
        System.out.println("Students was added to DB");
    }

    private void addStudentsCoursesToDB(Map<Student, Set<Course>> studentsCourses) {
        SchoolDao schoolDao = new SchoolDao();
        for (Map.Entry<Student, Set<Course>> entry : studentsCourses.entrySet()) {
            for (Course course : entry.getValue()) {
                schoolDao.addStudentToCourse(entry.getKey().getStudentId(), course.getId());
            }
        }
        System.out.println("Students was added to courses");
    }

    private int getRandomInt(int min, int max) {
        int result = 0;
        for (int i = 0; i < 100; i++) {
            result = min + (int) (Math.random() * ((max - min) + 1));
        }
        return result;
    }

}
