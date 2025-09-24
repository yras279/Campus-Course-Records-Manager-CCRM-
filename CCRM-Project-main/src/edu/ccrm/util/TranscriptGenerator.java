// edu/ccrm/util/TranscriptGenerator.java
package edu.ccrm.util;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Enrollment;

public class TranscriptGenerator {
    
    public static String generateTranscript(Student student) {
        StringBuilder transcript = new StringBuilder();
        
        transcript.append("OFFICIAL TRANSCRIPT\n");
        transcript.append("===================\n");
        transcript.append("Student ID: ").append(student.getId()).append("\n");
        transcript.append("Name: ").append(student.getFullName()).append("\n");
        transcript.append("Registration No: ").append(student.getRegNo()).append("\n");
        transcript.append("Email: ").append(student.getEmail()).append("\n");
        transcript.append("Status: ").append(student.getStatus()).append("\n");
        transcript.append("\n");
        
        transcript.append("COURSE HISTORY\n");
        transcript.append("==============\n");
        
        if (student.getEnrolledCourses().isEmpty()) {
            transcript.append("No courses enrolled.\n");
        } else {
            // Header
            transcript.append(String.format("%-10s %-30s %-8s %-6s %-10s\n", 
                    "Code", "Course", "Credits", "Grade", "Points"));
            transcript.append(String.format("%-10s %-30s %-8s %-6s %-10s\n", 
                    "----------", "------------------------------", "--------", "------", "----------"));
            
            // Courses
            double totalPoints = 0;
            int totalCredits = 0;
            
            for (Enrollment enrollment : student.getEnrolledCourses()) {
                String code = enrollment.getCourse().getCode();
                String title = enrollment.getCourse().getTitle();
                int credits = enrollment.getCourse().getCredits();
                String grade = enrollment.getGrade() != null ? enrollment.getGrade().name() : "N/A";
                double points = enrollment.getGrade() != null ? enrollment.getGrade().getPoints() : 0;
                
                transcript.append(String.format("%-10s %-30s %-8d %-6s %-10.2f\n", 
                        code, title, credits, grade, points));
                
                if (enrollment.getGrade() != null) {
                    totalPoints += points * credits;
                    totalCredits += credits;
                }
            }
            
            // GPA calculation
            if (totalCredits > 0) {
                double gpa = totalPoints / totalCredits;
                transcript.append("\n");
                transcript.append(String.format("Cumulative GPA: %.2f\n", gpa));
                transcript.append(String.format("Total Credits: %d\n", totalCredits));
            }
        }
        
        return transcript.toString();
    }
}