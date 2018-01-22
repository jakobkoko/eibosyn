package model;

import ddf.minim.AudioOutput;
import ddf.minim.ugens.Frequency;
import ddf.minim.ugens.GranulateSteady;
import ddf.minim.ugens.Waves;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;

public class Player {
	private SimpleMinim minim;
	private AudioOutput out;
	private ToneList toneList = new ToneList();
	private float bpm = 100;
	private Tone currentTone;

	public Player() {
		// Instantiate new minim
		minim = new SimpleMinim(true);

		// use the getLineOut method of the Minim object to get an AudioOutput
		// object
		out = minim.getLineOut();

		// create a sine wave Oscil, set to 440 Hz, at 0.5 amplitude

		currentTone = new Tone(Frequency.ofHertz(440), 1, out);

		Tone tone1 = new Tone(Frequency.ofPitch("C"), 1, out);
		Tone tone2 = new Tone(Frequency.ofPitch("E"), 1, out);
		Tone tone3 = new Tone(Frequency.ofPitch("G"), 1, out);
		Tone tone4 = new Tone(Frequency.ofPitch("B"), 1, out);
		Tone tone5 = new Tone(Frequency.ofPitch("C5"), 1, out);
		Tone tone6 = new Tone(Frequency.ofPitch("B"), 1, out);
		Tone tone7 = new Tone(Frequency.ofPitch("G"), 1, out);
		Tone tone8 = new Tone(Frequency.ofPitch("E"), 1, out);

		toneList.addTone(tone1);
		toneList.addTone(tone2);
		toneList.addTone(tone3);
		toneList.addTone(tone4);
		toneList.addTone(tone5);
		toneList.addTone(tone6);
		toneList.addTone(tone7);
		toneList.addTone(tone8);

		out.setTempo(bpm);

		for (Tone t : toneList.getList()) {
			t.updateADSR_ToTempo(out.getTempo(), BeatType.SIXTEENTHS);
		}

		float i = 0;
		for(int h = 0; h < 100; h++) {
			for (Tone t : toneList.getList()) {

				out.playNote(i, BeatType.SIXTEENTHS.getValue(), t);
				i += BeatType.SIXTEENTHS.getValue();

				// try {
				// Thread.sleep((long) ((t.getDecTime() + t.getAttTime()) *
				// 1000));
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
	}

	// int var = 0;
	// while (true) {
	//
	// currentTone = toneList.getList().get(var);
	// i = 0;
	// out.playNote(i, BeatType.SIX.getValue(), currentTone);
	// try {
	// Thread.sleep((long) ((BeatType.THIRTY_SECONDS.getValue()) * 1000));
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// var += 1;
	// if(var == 4) var = 0;
	// }

	// for(int j = 0; j < 17; j++) {
	// out.playNote(j, 1, toneList.getNextTone());
	// }

	// currentTone = toneList.getCurrentTone();
	//
	// GranulateSteady a = new GranulateSteady(0.25f, 0.25f, 0.1f);
	//
	// currentTone.patch(a);
	//
	// a.patch(out);

	// TimeThread timeThread = new TimeThread("lol", out, currentTone,
	// toneList);
	// timeThread.setPriority(Thread.MAX_PRIORITY);
	// timeThread.start();
}
