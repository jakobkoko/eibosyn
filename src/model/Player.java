package model;

import ddf.minim.AudioOutput;
import ddf.minim.ugens.Frequency;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Player {
	private SimpleMinim minim;
	private AudioOutput out;
	private ToneList toneList = new ToneList();
	private Tone currentTone;
	private SimpleFloatProperty bpm;
	private SimpleObjectProperty<BeatType> beattype;
	private Looper looper;

	public void setToneList(int index, String note) {
		//toneList.setTone(index, note);
		toneList.getList().get(index).setFrequency(Frequency.ofPitch(note));
	}

	public Player() {
		bpm = new SimpleFloatProperty();
		beattype = new SimpleObjectProperty<BeatType>();

		minim = new SimpleMinim(true);

		bpm.set(120);
		beattype.set(BeatType.WHOLE);
		out = minim.getLineOut();
		out.setTempo(bpm.floatValue());

		//currentTone = new Tone(Frequency.ofHertz(440), 0, out);

		Tone tone1 = new Tone(Frequency.ofPitch("C"), 0, out);
		Tone tone2 = new Tone(Frequency.ofPitch("E"), 0, out);
		Tone tone3 = new Tone(Frequency.ofPitch("G"), 0, out);
		Tone tone4 = new Tone(Frequency.ofPitch("B"), 0, out);
		Tone tone5 = new Tone(Frequency.ofPitch("C5"), 0, out);
		Tone tone6 = new Tone(Frequency.ofPitch("B"), 0, out);
		Tone tone7 = new Tone(Frequency.ofPitch("G"), 0, out);
		Tone tone8 = new Tone(Frequency.ofPitch("E"), 0, out);

		toneList.addTone(tone1);
		toneList.addTone(tone2);
		toneList.addTone(tone3);
		toneList.addTone(tone4);
		toneList.addTone(tone5);
		toneList.addTone(tone6);
		toneList.addTone(tone7);
		toneList.addTone(tone8);

		looper = new Looper(toneList, out, bpm, beattype);

		bpm.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				out.setTempo(newValue.floatValue());
				for (Tone t : toneList.getList()) {
					t.updateADSR_ToTempo(newValue.floatValue(), beattype.getValue());
				}
				looper.setBpm(newValue);
			}
		});

		beattype.addListener(new ChangeListener<BeatType>() {
			@Override
			public void changed(ObservableValue<? extends BeatType> observable, BeatType oldValue, BeatType newValue) {
				looper.setBeattype(newValue);
			}
		});

	}

	public void play() {
		looper.setPlaying();
	}

	public void setBpm(float bpm) {
		this.bpm.set(bpm);
	}

	public Looper getLooper() {
		return looper;
	}

	public void setBalance(float f) {
		out.setBalance(f);
	}

	public void quit() {
		out.close();
		System.exit(1);
	}

    public Tone getToneFromToneList(int index) {
		return toneList.getList().get(index);
    }

	public void setBeattype(int beattype) {
		switch(beattype) {
			case 0:
				this.beattype.set(BeatType.WHOLE);
				break;
			case 1:
				this.beattype.set(BeatType.HALF);
				break;
			case 2:
				this.beattype.set(BeatType.QUARTER);
				break;
			case 3:
				this.beattype.set(BeatType.EIGHTHS);
				break;
			case 4:
				this.beattype.set(BeatType.SIXTEENTHS);
				break;
			case 5:
				this.beattype.set(BeatType.THIRTY_SECONDS);
				break;
		}
	}

	public SimpleMinim getMinim() {
		return minim;
	}

	public AudioOutput getOut() {
		return out;
	}
}
