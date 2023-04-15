package com.asteroids.javaasteroidsgame;

import javafx.scene.media.AudioClip;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Sounds {
    private Map<String, AudioClip> sounds;


    public Sounds() {
        sounds = new HashMap<>();
        loadSounds("large", "/sounds/bangLarge.mp3");
        loadSounds("medium", "/sounds/bangMedium.mp3");
        loadSounds("small", "/sounds/bangSmall.mp3");
        loadSounds("fire", "/sounds/fire.mp3");
        loadSounds("beat", "/sounds/beat1.mp3");
        loadSounds("saucer", "/sounds/saucerBig.mp3");
        loadSounds("thrust", "/sounds/thrust.mp3");
        // Add more audio clips here
    }

    private void loadSounds(String key, String path) {
        URL soundUrl = getClass().getResource(path);
        AudioClip sound = new AudioClip(soundUrl.toString());
        sounds.put(key, sound);
    }

    public void playSound(String key) {
        AudioClip sound = sounds.get(key);
        sound.play();
    }
}