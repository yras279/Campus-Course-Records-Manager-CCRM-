// edu/ccrm/io/FileService.java
package edu.ccrm.io;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {
    
    public void exportStudentsToCsv(List<Student> students, Path filePath) throws IOException {
        List<String> lines = students.stream()
                .map(s -> String.join(",",
                    s.getId(),
                    s.getRegNo(),
                    s.getFullName(),
                    s.getEmail(),
                    s.getStatus(),
                    s.getDateCreated().toString()))
                .collect(Collectors.toList());
        
        lines.add(0, "ID,RegNo,FullName,Email,Status,DateCreated");
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
    public void exportCoursesToCsv(List<Course> courses, Path filePath) throws IOException {
        List<String> lines = courses.stream()
                .map(c -> String.join(",",
                    c.getCode(),
                    c.getTitle(),
                    String.valueOf(c.getCredits()),
                    c.getInstructor(),
                    c.getSemester().name(),
                    c.getDepartment(),
                    c.getStatus()))
                .collect(Collectors.toList());
        
        lines.add(0, "Code,Title,Credits,Instructor,Semester,Department,Status");
        Files.write(filePath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
    
    public List<Student> importStudentsFromCsv(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines.skip(1) // Skip header
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length >= 6)
                    .map(parts -> {
                        Student student = new Student(parts[0], parts[1], parts[2], parts[3]);
                        if (parts.length > 4 && "INACTIVE".equals(parts[4])) {
                            student.setStatus("INACTIVE");
                        }
                        return student;
                    })
                    .collect(Collectors.toList());
        }
    }
    
    public List<Course> importCoursesFromCsv(Path filePath) throws IOException {
        try (Stream<String> lines = Files.lines(filePath)) {
            return lines.skip(1) // Skip header
                    .map(line -> line.split(","))
                    .filter(parts -> parts.length >= 7)
                    .map(parts -> {
                        try {
                            Semester semester = Semester.valueOf(parts[4]);
                            return new Course.Builder(parts[0], parts[1])
                                    .credits(Integer.parseInt(parts[2]))
                                    .instructor(parts[3])
                                    .semester(semester)
                                    .department(parts[5])
                                    .build();
                        } catch (NumberFormatException e) {
                            System.err.println("Error parsing course: " + String.join(",", parts));
                            return null;
                        }
                    })
                    .filter(course -> course != null)
                    .collect(Collectors.toList());
        }
    }
    
    public Path createBackup(Path sourceDir, Path backupDir) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        Path backupPath = backupDir.resolve("backup_" + timestamp);
        
        Files.createDirectories(backupPath);
        
        try (Stream<Path> paths = Files.walk(sourceDir)) {
            paths.filter(Files::isRegularFile)
                 .forEach(source -> {
                     try {
                         Path target = backupPath.resolve(sourceDir.relativize(source));
                         Files.createDirectories(target.getParent());
                         Files.copy(source, target);
                     } catch (IOException e) {
                         System.err.println("Error copying file: " + e.getMessage());
                     }
                 });
        }
        
        return backupPath;
    }
    
    public long calculateDirectorySize(Path directory) throws IOException {
        try (Stream<Path> paths = Files.walk(directory)) {
            return paths.filter(Files::isRegularFile)
                       .mapToLong(path -> {
                           try {
                               return Files.size(path);
                           } catch (IOException e) {
                               System.err.println("Error getting file size: " + e.getMessage());
                               return 0L;
                           }
                       })
                       .sum();
        }
    }
    
    public void listFilesByDepth(Path directory, int maxDepth) throws IOException {
        try (Stream<Path> paths = Files.walk(directory, maxDepth)) {
            paths.forEach(path -> {
                try {
                    if (Files.isRegularFile(path)) {
                        System.out.println(path + " - " + Files.size(path) + " bytes");
                    }
                } catch (IOException e) {
                    System.err.println("Error accessing file: " + e.getMessage());
                }
            });
        }
    }
}