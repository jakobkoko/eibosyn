package model;

import ddf.minim.AudioOutput;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Looper extends Thread {

    private ToneList toneList;
    private AudioOutput out;
    private BeatType beattype;
    private float bpm;
    private SimpleIntegerProperty curToneIndex;
    private boolean playing;

    public void run() {
        while(!interrupted()) {
            float i = 0;
            int var = 0;
            float beattypedur = beattype.getValue();
            while (playing) {
                out.playNote(i, beattypedur, toneList.getList().get(var));
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

    public void setBpm(Number bpm) {
        this.bpm = bpm.floatValue();
    }

    public void setBeattype() {
        for(Tone t : toneList) {
            t.updateADSR_ToTempo(out.getTempo(), beattype);
        }
    }

    public void setPlaying() {
        this.playing = !playing;
    }

    public SimpleIntegerProperty getCurToneIndex() {
        return curToneIndex;
    }

    public boolean getPlaying() {
        return playing;
    }
}
