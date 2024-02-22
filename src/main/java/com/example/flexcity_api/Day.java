package com.example.flexcity_api;

import java.time.DayOfWeek;

public enum Day {

    Monday(2),
    Tuesday(3),
    Wednesday(4),
    Thursday(5),
    Friday(6),
    Saturday(7),
    Sunday(1);


    public final int day;

    Day(int d) {
        this.day = d;
    }

    // Getter method for the associated int value
    public int getValue() {
        return day;
    }

    public static String getByValue(int value) {
        for (Day day : values()) {
            if (day.getValue() == value) {
                return day.toString();
            }
        }
        throw new IllegalArgumentException("No matching day for value: " + value);
    }
}
