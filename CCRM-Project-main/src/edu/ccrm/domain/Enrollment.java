// edu/ccrm/domain/Enrollment.java
package edu.ccrm.domain;

import java.time.LocalDate;

public class Enrollment {
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private Double marks;
    private Grade grade;
    
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDate.now();
    }
    
    public void recordMarks(double marks) {
        this.marks = marks;
        this.grade = Grade.fromMarks(marks);
    }
    
    public double calculateGradePoints() {
        return grade != null ? grade.getPoints() : 0.0;
    }
    
    // Getters
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public Double getMarks() { return marks; }
    public Grade getGrade() { return grade; }
    
    @Override
    public String toString() {
        return course.getCode() + " - " + course.getTitle() + 
               (marks != null ? " | Marks: " + marks + " | Grade: " + grade : " | Not graded");
    }
}