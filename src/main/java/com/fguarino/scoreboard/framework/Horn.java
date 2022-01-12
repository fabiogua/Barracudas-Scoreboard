package com.fguarino.scoreboard.framework;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;

public class Horn {


    MediaPlayer mediaPlayer;
    String musicFile = "/com/fguarino/audio/buzzer.mp3";
    AudioClip buzzer;

    public Horn(){
            buzzer = new AudioClip(getClass().getResource(musicFile).toExternalForm());
    }
    public void play(){
        buzzer.play();
    }

    public void stop(){
        buzzer.stop();
    }
    
}
