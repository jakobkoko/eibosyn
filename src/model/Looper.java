package model;

import ddf.minim.AudioOutput;

public class Looper extends Thread {

    private ToneList toneList;
    private AudioOutput out;

    public void run() {
        float i = 0;
        int var = 0;
        Tone currentTone;
        while (true) {
            currentTone = toneList.getList().get(var);
            out.playNote(0, BeatType.SIXTEENTHS.getValue(), currentTone);
            System.out.println(currentTone.toString() + " play");
            try {
                Thread.sleep((long) ((0.25) * 1000));
            } catch (InterruptedException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
            var += 1;
            if(var == 8) var = 0;
        }
    }

    public Looper(ToneList toneList, AudioOutput out) {
        super();
        this.toneList = toneList;
        this.out = out;
        this.start();
    }

}
