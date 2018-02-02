package model;

import Helper.Utility;
import ddf.minim.AudioOutput;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a Looper Thread
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public class Looper extends Thread {

    private ToneList toneList;
    private AudioOutput out;
    private BeatType beattype;
    private float bpm;
    private SimpleIntegerProperty curToneIndex;
    private boolean playing;

    /**
     * Creates a Thread that pushs notes into the AudioOutput queue depending on the set tempo in Beats per minute
     * @param toneList
     * @param out
     * @param bpm
     * @param beattype
     */
    public Looper(ToneList toneList, AudioOutput out, SimpleFloatProperty bpm, SimpleObjectProperty<BeatType> beattype) {
        super();
        this.toneList = toneList;
        this.out = out;
        this.bpm = bpm.floatValue();
        this.beattype = beattype.getValue();
        this.curToneIndex = new SimpleIntegerProperty();
        curToneIndex.set(0);
        this.start();
    }
    
    public void run() {
        while(!interrupted()) {
            float i = 0;
            int var = 0;
            while (playing) {
                float beattypedur = beattype.getValue();
                out.playNote(i, beattypedur, toneList.getList().get(var));
                Utility.debug("play: " + String.valueOf(toneList.getList().get(var).getFrequency().asHz()));
                long now = System.currentTimeMillis();
                long goal = now + (long) (((float) 60 / bpm) * 4 * 1000);
                while (now < goal) {
                    now = System.currentTimeMillis();
                }
                var++;
                curToneIndex.set(var);
                if (var == 8) {
                    var = 0;
                    curToneIndex.set(var);
                }
            }
        }
    }

    /**
     * Sets bpm as a float value
     * @param bpm
     */
    public void setBpm(Number bpm) {
        this.bpm = bpm.floatValue();
    }

    /**
     * Sets beattype
     * @param beattype
     */
    public void setBeattype(BeatType beattype) {
        this.beattype = beattype;
        for(Tone t : this.toneList.getList()) {
            t.updateADSR_ToTempo(out.getTempo(), beattype);
        }
    }

    /**
     * Sets the boolean flag playing
     */
    public void setPlaying() {
        this.playing = !playing;
    }

    /**
     * Getter for CurToneIndex Property
     * @return
     */
    public SimpleIntegerProperty getCurToneIndex() {
        return curToneIndex;
    }

    /**
     * Indicates if the Thread pushs notes to AudioOutput
     * @return
     */
    public boolean getPlaying() {
        return playing;
    }
}
