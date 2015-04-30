package Interval;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Is responsible for calling sounds.
 */
public class Signal{
    File file;

    /**
     * It gives value to file class field, according to received Sounds parameter
     * @param sounds enum argument used to choose sound
     * @return this, it allows to chaining methods
     */
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

    /**
     * It make sounds.
     */
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
