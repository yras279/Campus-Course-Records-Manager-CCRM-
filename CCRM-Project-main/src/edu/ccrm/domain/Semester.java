// edu/ccrm/domain/Semester.java
package edu.ccrm.domain;

public enum Semester {
    SPRING("Spring"), 
    SUMMER("Summer"), 
    FALL("Fall");
    
    private final String displayName;
    
    Semester(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}