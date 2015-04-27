package Interval;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Signal implements Runnable{
    File file;

    public Signal getSound(Sounds sounds){
         switch (sounds){
             case FINISH:
                 file = new File("src/Interval/FINISH.wav");
                 break;
             case TRANSITION:
                 file = new File("src/Interval/TRANSITION.wav");
                 break;
         }
        return this;
    }

    @Override
    public void run() {
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
        }
    }
}
