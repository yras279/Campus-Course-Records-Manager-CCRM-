// edu/ccrm/config/AppConfig.java
package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AppConfig {
    private static AppConfig instance;
    private Path dataDirectory;
    
    private AppConfig() {
        // Private constructor for singleton
    }
    
    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }
    
    public void loadConfig() {
        this.dataDirectory = Paths.get("data");
        // Create directory if it doesn't exist
        if (!java.nio.file.Files.exists(dataDirectory)) {
            try {
                java.nio.file.Files.createDirectories(dataDirectory);
            } catch (Exception e) {
                System.err.println("Error creating data directory: " + e.getMessage());
            }
        }
    }
    
    public Path getDataDirectory() {
        return dataDirectory;
    }
}