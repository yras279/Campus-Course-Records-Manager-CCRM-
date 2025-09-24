# Campus Course & Records Manager (CCRM)

A comprehensive Java console application for managing students, courses, enrollments, and grades in an educational institution.

## Features

- Student Management (add, list, update, deactivate)
- Course Management (add, list, update, deactivate, search)
- Enrollment Management (enroll, unenroll with business rules)
- Grade Management (record marks, compute GPA)
- File Operations (import/export CSV, backup with NIO.2)
- Transcript Generation
- Backup and Recovery

## Evolution of Java

- 1995: Java 1.0 released by Sun Microsystems
- 1997: Java 1.1 with JDBC, RMI, and reflection
- 2000: Java 1.3 with HotSpot JVM
- 2004: Java 5 with generics, annotations, autoboxing
- 2014: Java 8 with lambdas, streams, new date/time API
- 2017: Java 9 with module system
- 2018: Java 11 as LTS version
- 2021: Java 17 as current LTS version
- 2023: Java 21 with virtual threads

## Java Platforms Comparison

| Platform | Full Name | Purpose | Key Features |
|----------|-----------|---------|--------------|
| Java SE | Standard Edition | Desktop and server applications | Core Java libraries, JVM, development tools |
| Java EE | Enterprise Edition | Enterprise applications | Servlets, JSP, EJB, JMS (now Jakarta EE) |
| Java ME | Micro Edition | Mobile and embedded devices | Limited libraries for constrained environments |

## Java Architecture

- **JDK (Java Development Kit)**: Development environment including compiler, debugger, and other tools
- **JRE (Java Runtime Environment)**: Runtime environment for executing Java applications
- **JVM (Java Virtual Machine)**: Executes Java bytecode, provides platform independence

The JDK contains the JRE, which contains the JVM. Developers use the JDK to create applications, which run on the JRE using the JVM.

## Installation on Windows

1. Download JDK from Oracle's website
2. Run the installer and follow the instructions
3. Set JAVA_HOME environment variable to JDK installation path
4. Add %JAVA_HOME%\bin to PATH environment variable
5. Verify installation with `java -version` in command prompt

## Eclipse IDE Setup

1. Download Eclipse IDE for Java Developers
2. Extract to a folder and run eclipse.exe
3. Create a new Java project
4. Configure JDK in project properties
5. Create packages and classes as needed

## How to Run

1. Clone the repository
2. Compile with `javac -d bin src/**/*.java`
3. Run with `java -cp bin edu.ccrm.Main`

## Enabling Assertions

Add `-ea` flag when running the application:
