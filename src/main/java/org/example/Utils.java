package org.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Utils {
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
}
