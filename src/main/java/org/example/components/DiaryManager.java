package org.example.components;
import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * The DiaryManager class manages the diary and generates available time slots.
 */
public class DiaryManager {
    private static final LocalTime START_TIME = LocalTime.of(9, 0);
    private static final LocalTime END_TIME = LocalTime.of(18, 0);
    private static final Duration APPOINTMENT_DURATION = Duration.ofMinutes(30);


    /**
     * Generates all available time slots for the day.
     *
     * @return the list of available time slots
     */
    public List<LocalTime> generateAllTimeSlots() {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime current = START_TIME;

        while (current.plus(APPOINTMENT_DURATION).isBefore(END_TIME)
                || current.plus(APPOINTMENT_DURATION).equals(END_TIME)) {
            slots.add(current);
            current = current.plus(APPOINTMENT_DURATION);
        }
        return slots;
    }


}
