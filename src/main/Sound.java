package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.ArrayList;

public class Sound {

    Clip clip;
    File[] soundURL = new File[30];
    ArrayList<Clip> clips = new ArrayList<>();
    FloatControl fc;
    int volumeScale;
    float volume;

    public Sound() {
        soundURL[0] = new File("res/sound/TitleMusic.wav");
        soundURL[1] = new File("res/sound/cursor.wav");
        soundURL[2] = new File("res/sound/select.wav");
        soundURL[3] = new File("res/sound/dialogue.wav");
        soundURL[4] = new File("res/sound/lobbyMusic.wav");
        soundURL[5] = new File("res/sound/Stage1_Regular.wav");
        soundURL[6] = new File("res/sound/Stage1_Battle.wav");
        soundURL[7] = new File("res/sound/Stage2_Regular.wav");
        soundURL[8] = new File("res/sound/Stage2_Battle.wav");
        soundURL[9] = new File("res/sound/Boss.wav");
        soundURL[10] = new File("res/sound/Victory.wav");
        soundURL[11] = new File("res/sound/volumeSelect.wav");

    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            // Add the clip to a list of clips
            clips.add(clip);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
            System.out.println("FloatControl initialized: " + (fc != null));

        } catch(Exception e) {
            e.printStackTrace(System.out);
            System.out.println("What the audio doesn't work");
        }
    }
    public void play() {
        clip.start();
    }
    public void stop() {
        clip.stop();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // stop all sound effects
    public void stopAll() {
        for (Clip c : clips) {
            if (c.isRunning())
                c.stop();
        }
    }

    // Volume
    public void checkVolume() {
        if (fc != null) {
            switch (volumeScale) {
                case 0: volume = -80f; break;
                case 1: volume = -20f; break;
                case 2: volume = -12f; break;
                case 3: volume = -5f; break;
                case 4: volume = 1f; break;
                case 5: volume = 6f; break;

            }
            fc.setValue(volume);
        } else {
            System.out.println("FloatControl is not initialized. Cannot adjust volume.");
        }
    }
}
