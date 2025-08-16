package org.example.enums;

public enum Cuisine {
    ITALIAN("Italian"),
    CHINESE("Chinese"),
    INDIAN("Indian"),
    MEXICAN("Mexican"),
    JAPANESE("Japanese"),
    FRENCH("French"),
    GREEK("Greek"),
    SPANISH("Spanish"),
    THAI("Thai"),
    AMERICAN("American");

    private final String displayName;

    Cuisine(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
