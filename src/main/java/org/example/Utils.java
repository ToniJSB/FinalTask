package org.example;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Utility class for various helper methods.
 */
public class Utils {

    /**
     * Returns a DateTimeFormatter with the specified format.
     *
     * @param format the format pattern
     * @return the DateTimeFormatter
     */
    public static DateTimeFormatter getDayFormatter(String format){
        return DateTimeFormatter.ofPattern(format);
    }

    /**
     * Obtains the JFrame from a given component.
     *
     * @param componente the component
     * @return the JFrame or null if not found
     */
    public static JFrame obtenerFrameDesdeComponente(Component componente) {
        // Recorrer la jerarquía de contenedores
        while (componente != null) {
            if (componente instanceof JFrame) {
                return (JFrame) componente; // Devolver el JFrame encontrado
            }
            componente = componente.getParent(); // Subir al siguiente contenedor
        }
        return null; // Si no se encuentra un JFrame
    }

    /**
     * Capitalizes the first letter of the given text.
     *
     * @param texto the text
     * @return the text with the first letter capitalized
     */
    public static String capitalizeFirtsLetter(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto; // Devuelve la cadena original si es nula o vacía
        }
        // Convierte la primera letra a mayúscula y concatena el resto
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    /**
     * Gets the logo image.
     *
     * @return the logo image
     */
    public static Image getLogo(){
        try{
            File file = new File("./src/main/resources/img-Photoroom.png");
            return new ImageIcon(ImageIO.read(file)).getImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets the map photo image.
     *
     * @return the map photo image
     */
    public static Image getFotoMap(){
        try{
            File file = new File("./src/main/resources/foto_mapa.png");
            return new ImageIcon(ImageIO.read(file)).getImage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validates the given email.
     *
     * @param email the email
     * @return true if the email is valid, false otherwise
     */
    public static boolean validarEmail(String email) {
        // Expresión regular para validar un email
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Utility class for date formatting and validation.
     */
    public static class DateFormat {
        private static final String RARE_REGEX_PARTIAL = "^[0-9]|/";
        private static final String DATE_REGEX_PARTIAL = "^(0[1-9]|[12][0-9]|3[01])2?/?([01][0-9])?/?((19|20)2?[0-9]{0,2})4?$";
        private static final String DATE_REGEX_FULL = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)\\d\\d)$";
        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
        private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm");

        /**
         * Validates a partial date string.
         *
         * @param partialDate the partial date string
         * @return true if the partial date is valid, false otherwise
         */
        public static boolean isPartialDateValid(String partialDate) {
            return Pattern.matches(RARE_REGEX_PARTIAL, partialDate);
        }

        /**
         * Validates a full date string.
         *
         * @param dateStr the full date string
         * @return true if the full date is valid, false otherwise
         */
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

        /**
         * Checks if the given day is valid for the specified month and year.
         *
         * @param day the day
         * @param month the month
         * @param year the year
         * @return true if the day is valid, false otherwise
         */
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

        /**
         * Checks if the given year is a leap year.
         *
         * @param year the year
         * @return true if the year is a leap year, false otherwise
         */
        public static boolean isLeapYear(int year) {
            // Un año es bisiesto si es divisible por 4, pero no por 100, a menos que también sea divisible por 400
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        }

        /**
         * Converts a LocalDate to a Date.
         *
         * @param localDate the LocalDate
         * @return the Date
         */
        public static Date asDate(LocalDate localDate) {
            return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        }

        /**
         * Converts a LocalDateTime to a Date.
         *
         * @param localDateTime the LocalDateTime
         * @return the Date
         */
        public static Date asDate(LocalDateTime localDateTime) {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        }

        /**
         * Converts a string representation of a date to a Date.
         *
         * @param stringDate the string representation of the date
         * @return the Date
         */
        public static Date asDate(String stringDate) {
            return new Date(Long.parseLong(stringDate));
        }

        /**
         * Converts a Date to a LocalDate.
         *
         * @param date the Date
         * @return the LocalDate
         */
        public static LocalDate asLocalDate(Date date) {
            return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        }

        /**
         * Converts a Date to a LocalDateTime.
         *
         * @param date the Date
         * @return the LocalDateTime
         */
        public static LocalDateTime asLocalDateTime(Date date) {
            return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }

        /**
         * Formats a Date as a string using the time format.
         *
         * @param date the Date
         * @return the formatted string
         */
        public static String dateAsStringTimeF(Date date) {
            return TIME_FORMAT.format(date);
        }

        /**
         * Formats a Date as a string using the date format.
         *
         * @param date the Date
         * @return the formatted string
         */
        public static String dateAsStringDateF(Date date) {
            return DATE_FORMAT.format(date);
        }

        /**
         * Converts a Date to a LocalTime.
         *
         * @param date the Date
         * @return the LocalTime
         */
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

    /**
     * Utility class for password encryption and verification.
     */
    public static class Password {

        /**
         * Encrypts the given password using SHA-256.
         *
         * @param password the password
         * @return the encrypted password
         */
        public static String encriptarPassword(String password) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hashedPassword = digest.digest(password.getBytes());
                return Base64.getEncoder().encodeToString(hashedPassword);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error al encriptar la contraseña", e);
            }
        }

        /**
         * Verifies the given password against the hashed password.
         *
         * @param password the password
         * @param hashedPassword the hashed password
         * @return true if the password matches, false otherwise
         */
        public static boolean verificarPassword(String password, String hashedPassword) {
            String nuevoHash = encriptarPassword(password);
            return nuevoHash.equals(hashedPassword);
        }
    }
}
