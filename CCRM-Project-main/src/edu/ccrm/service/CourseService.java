// edu/ccrm/service/CourseService.java
package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CourseService {
    private final List<Course> courses;
    
    public CourseService() {
        this.courses = new ArrayList<>();
    }
    
    public void addCourse(Course course) {
        courses.add(course);
    }
    
    public List<Course> listCourses() {
        return new ArrayList<>(courses);
    }
    
    public Optional<Course> findCourseByCode(String code) {
        return courses.stream()
                .filter(c -> c.getCode().equals(code))
                .findFirst();
    }
    
    public void updateCourse(String code, String title, int credits, String instructor, 
                           Semester semester, String department) {
        findCourseByCode(code).ifPresent(course -> {
            // In a real implementation, we would update these fields
            // For simplicity, we're just noting that updates would happen here
        });
    }
    
    public void deactivateCourse(String code) {
        findCourseByCode(code).ifPresent(course -> course.setStatus("INACTIVE"));
    }
    
    public List<Course> searchCoursesByInstructor(String instructor) {
        return courses.stream()
                .filter(c -> c.getInstructor().equalsIgnoreCase(instructor))
                .collect(Collectors.toList());
    }
    
    public List<Course> searchCoursesByDepartment(String department) {
        return courses.stream()
                .filter(c -> c.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }
    
    public List<Course> searchCoursesBySemester(Semester semester) {
        return courses.stream()
                .filter(c -> c.getSemester() == semester)
                .collect(Collectors.toList());
    }
}