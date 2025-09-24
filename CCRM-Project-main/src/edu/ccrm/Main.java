// Main.java
package edu.ccrm;

import edu.ccrm.cli.CLI;
import edu.ccrm.config.AppConfig;

public class Main {
    public static void main(String[] args) {
        System.out.println("Campus Course & Records Manager (CCRM)");
        System.out.println("======================================");
        
        // Initialize singleton configuration
        AppConfig config = AppConfig.getInstance();
        config.loadConfig();
        
        // Start CLI interface
        CLI cli = new CLI();
        cli.start();
    }
}