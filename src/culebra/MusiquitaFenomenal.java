/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culebra;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusiquitaFenomenal {

    private static Clip clip;

    public static void reproducir(String ubicacion, boolean bucle) {
        // Crear un hilo separado para ejecutar el método reproducir
        new Thread(() -> {
            try {
                File audioFile = new File(ubicacion);
                if (audioFile.exists()) {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();
                    if (bucle == true) {
                        clip.loop(Clip.LOOP_CONTINUOUSLY);
                        while (true) {
                            Thread.sleep(1000);
                        }
                    }

                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }).start(); // Iniciar el hilo
    }

    public static void cerrar() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }

    private MusiquitaFenomenal() {
    }
}
