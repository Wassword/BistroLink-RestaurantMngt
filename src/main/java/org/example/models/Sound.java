package org.example.models;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import java.net.URL;

public class Sound {

    public void playSound(String soundFileName) {
        try {
            // Load the sound file using ClassLoader
            URL soundFileUrl = getClass().getClassLoader().getResource(soundFileName);

            if (soundFileUrl == null) {
                System.err.println("Sound file not found: " + soundFileName);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFileUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // Play the sound

            // Optional: Wait for the sound to finish playing
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Sound soundManager = new Sound();
        soundManager.playSound("OneRepublic_-_Nobody_from_Kaiju_No_8.wav");  // Use the file name only
    }
}
