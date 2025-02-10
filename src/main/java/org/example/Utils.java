package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

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
