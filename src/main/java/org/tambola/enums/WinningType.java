package org.tambola.enums;

public enum WinningType {
    TOP_ROW("topRow"),
    MIDDLE_ROW("middleRow"),
    BOTTOM_ROW("bottomRow"),
    FULL_HOUSE("fullHouse"),
    EARLY_FIVE("earlyFive");

    private final String value;

    WinningType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static WinningType fromString(String value) {
        for (WinningType type : WinningType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid WinningType: " + value);
    }
}
