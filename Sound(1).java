package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound(){
        soundURL[0] = getClass().getResource("/res/sounds/track-01.wav");
        soundURL[1] = getClass().getResource("/res/sounds/track-02.wav");
        soundURL[2] = getClass().getResource("/res/sounds/track-03.wav");
    }

    public void setFile(int i){

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            ais.close();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void play(){
        clip.start();

    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void stop(){
       if (clip != null && clip.isRunning()){
        clip.stop();
        }
    }
    
   public void playTrack(int trackIndex) {
        if(clip != null && clip.isRunning()){
            clip.stop();
        }
        setFile(trackIndex);
        loop();
    }
}
    
     
