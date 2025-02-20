package org.example.components;
import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DiaryManager {
    private static final LocalTime START_TIME = LocalTime.of(9, 0);
    private static final LocalTime END_TIME = LocalTime.of(18, 0);
    private static final Duration APPOINTMENT_DURATION = Duration.ofMinutes(30);

    private List<TimeInterval> appointments = new ArrayList<>();

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

    // Agregar nueva cita
    public boolean addAppointment(LocalTime startTime) {
        TimeInterval newAppointment = new TimeInterval(startTime);

        // Validar horario
        if (startTime.isBefore(START_TIME) || newAppointment.end.isAfter(END_TIME)) {
            return false;
        }

        // Verificar solapamientos
        for (TimeInterval existing : appointments) {
            if (existing.overlaps(newAppointment)) {
                return false;
            }
        }

        appointments.add(newAppointment);
        return true;
    }

    // Calcular tiempo total de citas
    public Duration calculateTotalAppointmentsTime() {
        return Duration.ofMinutes(appointments.size() * 30);
    }

    // Mostrar citas existentes
    public void printAppointments() {
        System.out.println("Citas agendadas:");
        appointments.forEach(appt ->
                System.out.printf("%s - %s\n", appt.start, appt.end));
    }

    // Mostrar slots disponibles
    public void printAvailableSlots() {
        List<LocalTime> allSlots = generateAllTimeSlots();
        System.out.println("\nHorarios disponibles:");

        allSlots.forEach(slot -> {
            TimeInterval temp = new TimeInterval(slot);
            boolean available = appointments.stream()
                    .noneMatch(appt -> appt.overlaps(temp));

            if (available) {
                System.out.printf("%s - %s\n", slot, slot.plus(APPOINTMENT_DURATION));
            }
        });
    }

}
