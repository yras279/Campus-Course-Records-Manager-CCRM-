# Usage Guide

## Running the Application

1. Ensure you have Java JDK 8 or later installed
2. Clone the repository
3. Navigate to the project directory
4. Compile: `javac -d bin src/**/*.java`
5. Run: `java -cp bin edu.ccrm.Main`

## Sample Operations

### Adding a Student
1. Select "Manage Students" from main menu
2. Choose "Add Student"
3. Enter student details when prompted

### Adding a Course
1. Select "Manage Courses" from main menu
2. Choose "Add Course"
3. Enter course details and select semester

### Enrolling a Student
1. Select "Manage Enrollments" from main menu
2. Choose "Enroll Student in Course"
3. Enter student ID and course code

### Generating a Transcript
1. Select "Manage Students" from main menu
2. Choose "Generate Transcript"
3. Enter student ID

### Importing Data
1. Place CSV files in the data directory
2. Select "Import/Export Data" from main menu
3. Choosef the import option

### Creating Backups
1. Select "Backup Operations" from main menu
2. Choose "Create Backup"
3. Backups will be stored in timestamped folders
