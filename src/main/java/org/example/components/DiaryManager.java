package org.example.components;
import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DiaryManager {
    private static final LocalTime START_TIME = LocalTime.of(9, 0);
    private static final LocalTime END_TIME = LocalTime.of(18, 0);
    private static final Duration APPOINTMENT_DURATION = Duration.ofMinutes(30);


    // Clase interna para representar intervalos de tiempo
    private static class TimeInterval {
        LocalTime start;
        LocalTime end;

        TimeInterval(LocalTime start) {
            this.start = start;
            this.end = start.plus(APPOINTMENT_DURATION);
        }

        boolean overlaps(TimeInterval other) {
            return !this.end.isBefore(other.start) && !this.start.isAfter(other.end);
        }
    }

    // Generar todos los slots disponibles del día
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
