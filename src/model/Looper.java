package model;

import ddf.minim.AudioOutput;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Looper extends Thread {

    private ToneList toneList;
    private AudioOutput out;
    private float bpm;
    private SimpleIntegerProperty curToneIndex;

    public void run() {

        /*
        float i = 0;
        int var = 0;
        Tone currentTone;
        float bpm = 4000;
        float beattypedur = BeatType.WHOLE.getValue();
        float loopduration = 2 * 4 * ((float)60/(float)bpm) * 4 * 1000;
        while(true) {
            out.playNote(i, beattypedur, toneList.getList().get(var));
            out.playNote(i+=4, beattypedur, toneList.getList().get(++var));
            out.playNote(i+=4, beattypedur, toneList.getList().get(++var));
            out.playNote(i+=4, beattypedur, toneList.getList().get(++var));
            out.playNote(i+=4, beattypedur, toneList.getList().get(++var));
            out.playNote(i+=4, beattypedur, toneList.getList().get(++var));
            out.playNote(i+=4, beattypedur, toneList.getList().get(++var));
            out.playNote(i+=4, beattypedur, toneList.getList().get(++var));
            long now = System.currentTimeMillis();
            long goal = now+(long)loopduration;
            while(now < goal){
                now = System.currentTimeMillis();
            }
            var = 0;
            i = 0;
        }
        */

        float i = 0;
        int var = 0;
        Tone currentTone;
        float beattypedur = BeatType.WHOLE.getValue();
        //
        float loopduration = ((float)60/bpm) * 4 * 1000;
        while(true) {
            //System.out.println(i);
            //System.out.println(beattypedur);
            out.playNote(i, beattypedur, toneList.getList().get(var));
            long now = System.currentTimeMillis();
            long goal = now+(long)(((float)60/bpm) * 4 * 1000);
            //System.out.println(now);
            while(now < goal){
                now = System.currentTimeMillis();
            }
            //System.out.println(goal);
            var++;
            curToneIndex.set(var);
            if(var == 8) {
                var = 0;
                curToneIndex.set(var);
            }
        }


    }

    public Looper(ToneList toneList, AudioOutput out, SimpleFloatProperty bpm) {
        super();
        this.toneList = toneList;
        this.out = out;
        this.bpm = bpm.floatValue();
        this.curToneIndex = new SimpleIntegerProperty();
        curToneIndex.set(0);
        this.start();
    }

    public void setBpm(Number bpm) {
        this.bpm = bpm.floatValue();
    }

    public SimpleIntegerProperty getCurToneIndex() {
        return curToneIndex;
    }
}
