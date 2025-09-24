// edu/ccrm/domain/Student.java
package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String regNo;
    private String status;
    private final List<Enrollment> enrolledCourses;
    
    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = regNo;
        this.status = "ACTIVE";
        this.enrolledCourses = new ArrayList<>();
    }
    
    @Override
    public String getProfile() {
        return """
               Student Profile:
               ID: """ + id + "\n" +
               "Registration No: " + regNo + "\n" +
               "Name: " + fullName + "\n" +
               "Email: " + email + "\n" +
               "Status: " + status + "\n" +
               "Enrolled Courses: " + enrolledCourses.size();
    }
    
    // Getters and setters
    public String getRegNo() { return regNo; }
    public void setRegNo(String regNo) { this.regNo = regNo; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public List<Enrollment> getEnrolledCourses() { return enrolledCourses; }
    
    public void enrollInCourse(Course course) {
        Enrollment enrollment = new Enrollment(this, course);
        enrolledCourses.add(enrollment);
    }
    
    public void unenrollFromCourse(Course course) {
        enrolledCourses.removeIf(e -> e.getCourse().getCode().equals(course.getCode()));
    }
}