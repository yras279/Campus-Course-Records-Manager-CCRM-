// edu/ccrm/cli/CLI.java
package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.io.FileService;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.StudentService;
import edu.ccrm.util.TranscriptGenerator;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CLI {
    private final StudentService studentService;
    private final CourseService courseService;
    private final FileService fileService;
    private final Scanner scanner;
    
    public CLI() {
        this.studentService = new StudentService();
        this.courseService = new CourseService();
        this.fileService = new FileService();
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        boolean running = true;
        
        while (running) {
            printMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> manageStudents();
                case 2 -> manageCourses();
                case 3 -> manageEnrollments();
                case 4 -> manageGrades();
                case 5 -> importExportData();
                case 6 -> backupOperations();
                case 7 -> generateReports();
                case 8 -> printJavaPlatformInfo();
                case 9 -> {
                    running = false;
                    System.out.println("Thank you for using CCRM. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void printMainMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Manage Students");
        System.out.println("2. Manage Courses");
        System.out.println("3. Manage Enrollments");
        System.out.println("4. Manage Grades");
        System.out.println("5. Import/Export Data");
        System.out.println("6. Backup Operations");
        System.out.println("7. Generate Reports");
        System.out.println("8. Java Platform Info");
        System.out.println("9. Exit");
    }
    
    private void manageStudents() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n=== STUDENT MANAGEMENT ===");
            System.out.println("1. Add Student");
            System.out.println("2. List Students");
            System.out.println("3. Update Student");
            System.out.println("4. Deactivate Student");
            System.out.println("5. View Student Profile");
            System.out.println("6. Generate Transcript");
            System.out.println("7. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> listStudents();
                case 3 -> updateStudent();
                case 4 -> deactivateStudent();
                case 5 -> viewStudentProfile();
                case 6 -> generateTranscript();
                case 7 -> back = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void addStudent() {
        System.out.println("\n--- Add New Student ---");
        String id = getStringInput("Student ID: ");
        String regNo = getStringInput("Registration No: ");
        String fullName = getStringInput("Full Name: ");
        String email = getStringInput("Email: ");
        
        Student student = new Student(id, regNo, fullName, email);
        studentService.addStudent(student);
        System.out.println("Student added successfully!");
    }
    
    private void listStudents() {
        System.out.println("\n--- List of Students ---");
        List<Student> students = studentService.listStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (int i = 0; i < students.size(); i++) {
                System.out.println((i + 1) + ". " + students.get(i));
            }
        }
    }
    
    private void updateStudent() {
        System.out.println("\n--- Update Student ---");
        String id = getStringInput("Enter student ID to update: ");
        
        Optional<Student> studentOpt = studentService.findStudentById(id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            System.out.println("Current details: " + student);
            
            String fullName = getStringInput("New full name (press enter to keep current): ");
            String email = getStringInput("New email (press enter to keep current): ");
            
            if (!fullName.isEmpty()) student.setFullName(fullName);
            if (!email.isEmpty()) student.setEmail(email);
            
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student not found with ID: " + id);
        }
    }
    
    private void deactivateStudent() {
        System.out.println("\n--- Deactivate Student ---");
        String id = getStringInput("Enter student ID to deactivate: ");
        
        studentService.deactivateStudent(id);
        System.out.println("Student deactivated successfully!");
    }
    
    private void viewStudentProfile() {
        System.out.println("\n--- View Student Profile ---");
        String id = getStringInput("Enter student ID: ");
        
        Optional<Student> studentOpt = studentService.findStudentById(id);
        if (studentOpt.isPresent()) {
            System.out.println(studentOpt.get().getProfile());
        } else {
            System.out.println("Student not found with ID: " + id);
        }
    }
    
    private void generateTranscript() {
        System.out.println("\n--- Generate Transcript ---");
        String id = getStringInput("Enter student ID: ");
        
        Optional<Student> studentOpt = studentService.findStudentById(id);
        if (studentOpt.isPresent()) {
            System.out.println(TranscriptGenerator.generateTranscript(studentOpt.get()));
        } else {
            System.out.println("Student not found with ID: " + id);
        }
    }
    
    private void manageCourses() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n=== COURSE MANAGEMENT ===");
            System.out.println("1. Add Course");
            System.out.println("2. List Courses");
            System.out.println("3. Update Course");
            System.out.println("4. Deactivate Course");
            System.out.println("5. Search Courses by Instructor");
            System.out.println("6. Search Courses by Department");
            System.out.println("7. Search Courses by Semester");
            System.out.println("8. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> addCourse();
                case 2 -> listCourses();
                case 3 -> updateCourse();
                case 4 -> deactivateCourse();
                case 5 -> searchCoursesByInstructor();
                case 6 -> searchCoursesByDepartment();
                case 7 -> searchCoursesBySemester();
                case 8 -> back = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void addCourse() {
        System.out.println("\n--- Add New Course ---");
        String code = getStringInput("Course Code: ");
        String title = getStringInput("Course Title: ");
        int credits = getIntInput("Credits: ");
        String instructor = getStringInput("Instructor: ");
        
        System.out.println("Available Semesters:");
        for (Semester semester : Semester.values()) {
            System.out.println((semester.ordinal() + 1) + ". " + semester.getDisplayName());
        }
        int semesterChoice = getIntInput("Select semester: ");
        Semester semester = Semester.values()[semesterChoice - 1];
        
        String department = getStringInput("Department: ");
        
        Course course = new Course.Builder(code, title)
                .credits(credits)
                .instructor(instructor)
                .semester(semester)
                .department(department)
                .build();
        
        courseService.addCourse(course);
        System.out.println("Course added successfully!");
    }
    
    private void listCourses() {
        System.out.println("\n--- List of Courses ---");
        List<Course> courses = courseService.listCourses();
        
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            for (int i = 0; i < courses.size(); i++) {
                System.out.println((i + 1) + ". " + courses.get(i));
            }
        }
    }
    
    private void updateCourse() {
        // Implementation similar to updateStudent
        System.out.println("Course update functionality would be implemented here.");
    }
    
    private void deactivateCourse() {
        System.out.println("\n--- Deactivate Course ---");
        String code = getStringInput("Enter course code to deactivate: ");
        
        courseService.deactivateCourse(code);
        System.out.println("Course deactivated successfully!");
    }
    
    private void searchCoursesByInstructor() {
        System.out.println("\n--- Search Courses by Instructor ---");
        String instructor = getStringInput("Enter instructor name: ");
        
        List<Course> courses = courseService.searchCoursesByInstructor(instructor);
        displayCourses(courses, "Courses taught by " + instructor);
    }
    
    private void searchCoursesByDepartment() {
        System.out.println("\n--- Search Courses by Department ---");
        String department = getStringInput("Enter department: ");
        
        List<Course> courses = courseService.searchCoursesByDepartment(department);
        displayCourses(courses, "Courses in " + department + " department");
    }
    
    private void searchCoursesBySemester() {
        System.out.println("\n--- Search Courses by Semester ---");
        System.out.println("Available Semesters:");
        for (Semester semester : Semester.values()) {
            System.out.println((semester.ordinal() + 1) + ". " + semester.getDisplayName());
        }
        int semesterChoice = getIntInput("Select semester: ");
        Semester semester = Semester.values()[semesterChoice - 1];
        
        List<Course> courses = courseService.searchCoursesBySemester(semester);
        displayCourses(courses, "Courses offered in " + semester.getDisplayName());
    }
    
    private void displayCourses(List<Course> courses, String title) {
        System.out.println("\n--- " + title + " ---");
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            for (int i = 0; i < courses.size(); i++) {
                System.out.println((i + 1) + ". " + courses.get(i));
            }
        }
    }
    
    private void manageEnrollments() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n=== ENROLLMENT MANAGEMENT ===");
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. Unenroll Student from Course");
            System.out.println("3. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> enrollStudent();
                case 2 -> unenrollStudent();
                case 3 -> back = true;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void enrollStudent() {
        System.out.println("\n--- Enroll Student in Course ---");
        String studentId = getStringInput("Enter student ID: ");
        String courseCode = getStringInput("Enter course code: ");
        
        Optional<Course> courseOpt = courseService.findCourseByCode(courseCode);
        if (courseOpt.isPresent()) {
            try {
                studentService.enrollStudentInCourse(studentId, courseOpt.get());
                System.out.println("Student enrolled successfully!");
            } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
                System.out.println("Enrollment error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Course not found with code: " + courseCode);
        }
    }
    
    private void unenrollStudent() {
        System.out.println("\n--- Unenroll Student from Course ---");
        String studentId = getStringInput("Enter student ID: ");
        String courseCode = getStringInput("Enter course code: ");
        
        studentService.unenrollStudentFromCourse(studentId, courseCode);
        System.out.println("Student unenrolled successfully!");
    }
    
    private void manageGrades() {
        System.out.println("\n--- Grade Management ---");
        System.out.println("Grade management functionality would be implemented here.");
    }
    
    private void importExportData() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n=== IMPORT/EXPORT DATA ===");
            System.out.println("1. Export Students to CSV");
            System.out.println("2. Export Courses to CSV");
            System.out.println("3. Import Students from CSV");
            System.out.println("4. Import Courses from CSV");
            System.out.println("5. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                AppConfig config = AppConfig.getInstance();
                Path dataDir = config.getDataDirectory();
                
                switch (choice) {
                    case 1 -> {
                        fileService.exportStudentsToCsv(studentService.listStudents(), 
                                dataDir.resolve("students.csv"));
                        System.out.println("Students exported to " + dataDir.resolve("students.csv"));
                    }
                    case 2 -> {
                        fileService.exportCoursesToCsv(courseService.listCourses(), 
                                dataDir.resolve("courses.csv"));
                        System.out.println("Courses exported to " + dataDir.resolve("courses.csv"));
                    }
                    case 3 -> {
                        List<Student> importedStudents = fileService.importStudentsFromCsv(
                                dataDir.resolve("students.csv"));
                        importedStudents.forEach(studentService::addStudent);
                        System.out.println("Imported " + importedStudents.size() + " students");
                    }
                    case 4 -> {
                        List<Course> importedCourses = fileService.importCoursesFromCsv(
                                dataDir.resolve("courses.csv"));
                        importedCourses.forEach(courseService::addCourse);
                        System.out.println("Imported " + importedCourses.size() + " courses");
                    }
                    case 5 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("Error during file operation: " + e.getMessage());
            }
        }
    }
    
    private void backupOperations() {
        boolean back = false;
        
        while (!back) {
            System.out.println("\n=== BACKUP OPERATIONS ===");
            System.out.println("1. Create Backup");
            System.out.println("2. Calculate Backup Size");
            System.out.println("3. List Backup Files");
            System.out.println("4. Back to Main Menu");
            
            int choice = getIntInput("Enter your choice: ");
            
            try {
                AppConfig config = AppConfig.getInstance();
                Path dataDir = config.getDataDirectory();
                Path backupDir = dataDir.resolve("backups");
                
                switch (choice) {
                    case 1 -> {
                        Path backupPath = fileService.createBackup(dataDir, backupDir);
                        System.out.println("Backup created at: " + backupPath);
                    }
                    case 2 -> {
                        long size = fileService.calculateDirectorySize(backupDir);
                        System.out.println("Total backup size: " + size + " bytes");
                    }
                    case 3 -> {
                        int depth = getIntInput("Enter maximum depth: ");
                        fileService.listFilesByDepth(backupDir, depth);
                    }
                    case 4 -> back = true;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("Error during backup operation: " + e.getMessage());
            }
        }
    }
    
    private void generateReports() {
        System.out.println("\n--- Generate Reports ---");
        System.out.println("Report generation functionality would be implemented here.");
    }
    
    private void printJavaPlatformInfo() {
        System.out.println("\n=== JAVA PLATFORM INFORMATION ===");
        System.out.println("Java SE vs Java ME vs Java EE:");
        System.out.println("Java SE (Standard Edition): Core Java platform for desktop and server applications");
        System.out.println("Java ME (Micro Edition): For mobile and embedded devices");
        System.out.println("Java EE (Enterprise Edition): For enterprise applications, now Jakarta EE");
        System.out.println("\nJava Architecture:");
        System.out.println("JDK (Java Development Kit): Development environment for building Java applications");
        System.out.println("JRE (Java Runtime Environment): Runtime environment for executing Java applications");
        System.out.println("JVM (Java Virtual Machine): Virtual machine that executes Java bytecode");
        System.out.println("\nCurrent Java Version: " + System.getProperty("java.version"));
    }
    
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
            System.out.print(prompt);
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        return input;
    }
    
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}