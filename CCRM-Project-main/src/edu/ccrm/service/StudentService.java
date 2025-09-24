// edu/ccrm/service/StudentService.java
package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// import java.util.stream.Collectors;

public class StudentService {
    private final List<Student> students;
    
    public StudentService() {
        this.students = new ArrayList<>();
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }
    
    public List<Student> listStudents() {
        return new ArrayList<>(students);
    }
    
    public Optional<Student> findStudentById(String id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst();
    }
    
    public Optional<Student> findStudentByRegNo(String regNo) {
        return students.stream()
                .filter(s -> s.getRegNo().equals(regNo))
                .findFirst();
    }
    
    public void updateStudent(String id, String fullName, String email) {
        findStudentById(id).ifPresent(student -> {
            student.setFullName(fullName);
            student.setEmail(email);
        });
    }
    
    public void deactivateStudent(String id) {
        findStudentById(id).ifPresent(student -> student.setStatus("INACTIVE"));
    }
    
    public void enrollStudentInCourse(String studentId, Course course) 
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        
        Student student = findStudentById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        
        // Check if already enrolled
        boolean alreadyEnrolled = student.getEnrolledCourses().stream()
                .anyMatch(e -> e.getCourse().getCode().equals(course.getCode()));
        
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException("Student is already enrolled in this course");
        }
        
        // Check credit limit (max 18 credits per semester)
        int currentCredits = student.getEnrolledCourses().stream()
                .filter(e -> e.getCourse().getSemester() == course.getSemester())
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();
        
        if (currentCredits + course.getCredits() > 18) {
            throw new MaxCreditLimitExceededException("Credit limit exceeded for this semester");
        }
        
        student.enrollInCourse(course);
    }
    
    public void unenrollStudentFromCourse(String studentId, String courseCode) {
        Student student = findStudentById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        
        student.getEnrolledCourses().removeIf(e -> e.getCourse().getCode().equals(courseCode));
    }
}