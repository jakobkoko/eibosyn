package model;

import ddf.minim.AudioOutput;

public class Looper extends Thread {

    private ToneList toneList;
    private AudioOutput out;

    public void run() {

        float i = 0;
        int var = 0;
        Tone currentTone;
        float bpm = 240;
        float beattypedur = BeatType.WHOLE.getValue();
        //                                             BPM
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


    }

    public Looper(ToneList toneList, AudioOutput out) {
        super();
        this.toneList = toneList;
        this.out = out;
        this.start();
    }

}
