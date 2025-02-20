package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {
    public static DateTimeFormatter getDayFormatter(String format){
        return DateTimeFormatter.ofPattern(format);

    }
    public static Image getLogo(){
        try{
            File file = new File("./src/main/resources/img.png");
            return new ImageIcon(ImageIO.read(file)).getImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Image getFotoMap(){
        try{
            File file = new File("./src/main/resources/foto_mapa.png");
            return new ImageIcon(ImageIO.read(file)).getImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static class DateFormat{
        private static final String RARE_REGEX_PARTIAL = "^[0-9]|/";
        private static final String DATE_REGEX_PARTIAL = "^(0[1-9]|[12][0-9]|3[01])2?/?([01][0-9])?/?((19|20)2?[0-9]{0,2})4?$";
        private static final String DATE_REGEX_FULL = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)$";
        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm");



        // Validación incremental (carácter por carácter)
        public static boolean isPartialDateValid(String partialDate) {
            return Pattern.matches(RARE_REGEX_PARTIAL, partialDate);
        }

        // Validación completa
        public static boolean isFullDateValid(String dateStr) {
            if (!Pattern.matches(DATE_REGEX_FULL, dateStr)) {
                return false;
            }

            try {
                DATE_FORMAT.setLenient(false);
                Date date = DATE_FORMAT.parse(dateStr);
                Date currentDate = new Date();

                // Calcular la fecha de ayer
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                Date yesterday = calendar.getTime();

                // Calcular la fecha de 100 años atrás
                calendar.add(Calendar.YEAR, -100);
                Date hundredYearsAgo = calendar.getTime();

                // Validar que la fecha esté entre 100 años atrás y ayer
                if (date.before(hundredYearsAgo) || date.after(yesterday)) {
                    return false;
                }

                // Validar los días del mes según el mes y el año
                String[] parts = dateStr.split("/");
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);

                return isValidDayForMonth(day, month, year);
            } catch (ParseException e) {
                return false;
            }
        }

        public static boolean isValidDayForMonth(int day, int month, int year) {
            if (month < 1 || month > 12) {
                return false;
            }

            // Validar febrero y años bisiestos
            if (month == 2) {
                if (isLeapYear(year)) {
                    return day <= 29;
                } else {
                    return day <= 28;
                }
            }

            // Validar meses con 30 o 31 días
            if (month == 4 || month == 6 || month == 9 || month == 11) {
                return day <= 30;
            } else {
                return day <= 31;
            }
        }

        public static boolean isLeapYear(int year) {
            // Un año es bisiesto si es divisible por 4, pero no por 100, a menos que también sea divisible por 400
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        }

        public static Date asDate(LocalDate localDate) {
            return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }

        public static Date asDate(LocalDateTime localDateTime) {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        }
        public static Date asDate(String stringDate) {
            return new Date(Long.parseLong(stringDate));
        }

        public static LocalDate asLocalDate(Date date) {
            return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        }

        public static LocalDateTime asLocalDateTime(Date date) {
            return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        public static String dateAsStringTimeF(Date date) {
            return TIME_FORMAT.format(date);
        }
        public static String dateAsStringDateF(Date date) {
            return DATE_FORMAT.format(date);
        }

        public static LocalTime dateToLocalTime(Date date) {
            if (date == null) {
                throw new IllegalArgumentException("El objeto Date no puede ser nulo.");
            }

            // Convertir Date a Instant
            Instant instant = date.toInstant();

            // Convertir Instant a LocalTime usando la zona horaria del sistema

            return instant.atZone(ZoneId.systemDefault()).toLocalTime();
        }




    }


    public static class Password {


        // Encriptar la contraseña con SHA-256 y salt
        public static String encriptarPassword(String password) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashedPassword = digest.digest(password.getBytes());
                return Base64.getEncoder().encodeToString(hashedPassword);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error al encriptar la contraseña", e);
            }
        }

        // Verificar la contraseña
        public static boolean verificarPassword(String password, String hashedPassword) {
            String nuevoHash = encriptarPassword(password);
            return nuevoHash.equals(hashedPassword);
        }

    }


}
