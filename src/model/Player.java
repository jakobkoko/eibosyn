package model;

import Helper.Utility;
import ddf.minim.AudioOutput;
import ddf.minim.ugens.Frequency;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Represents a Player
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public class Player {
	private SimpleMinim minim;
	private AudioOutput out;
	private ToneList toneList = new ToneList();
	private SimpleFloatProperty bpm;
	private SimpleObjectProperty<BeatType> beattype;
	private Looper looper;
	private boolean playing;

	/**
	 * Creates a default instance of Player
	 */
	public Player() {
		bpm = new SimpleFloatProperty();
		beattype = new SimpleObjectProperty<BeatType>();
		minim = new SimpleMinim(true);

		// Setting default tempo values
		bpm.set(120);
		beattype.set(BeatType.WHOLE);
		out = minim.getLineOut();
		out.setTempo(bpm.floatValue());

		// Istantiating and adding default tones to ToneList
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

		// Instatiate new Looper Thread
		looper = new Looper(toneList, out, bpm, beattype);

		// ChangeListener for bpm
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

		// ChangeListener for BeatType
		beattype.addListener(new ChangeListener<BeatType>() {
			@Override
			public void changed(ObservableValue<? extends BeatType> observable, BeatType oldValue, BeatType newValue) {
				looper.setBeattype(newValue);
			}
		});

	}

	/**
	 * Sets the Looper to playing. Also changes the boolean flag playing.
	 */
	public void play() {
		looper.setPlaying();
		playing = !playing;
	}

	/**
	 * 
	 * @return looping thread
	 */
	public Looper getLooper() {
		return looper;
	}

	/**
	 * 
	 * @return list of Tone Objects
	 */
	public ToneList getToneList() {
		return this.toneList;
	}
	
	/**
	 * 
	 * @param index
	 * @return tone from toneList at the specified index
	 */
    public Tone getToneFromToneList(int index) {
		return toneList.getList().get(index);
    }
    
    /**
     * 
     * @return SimpleMinim
     */
	public SimpleMinim getMinim() {
		return minim;
	}

	/**
	 * 
	 * @return AudioOutput
	 */
	public AudioOutput getOut() {
		return out;
	}

	/**
	 * Sets the AudioOutput to the right or the left channel
	 * @param f
	 */
	public void setBalance(float f) {
		out.setBalance(f);
	}

	/**
	 * Sets the bpm property
	 * @param bpm
	 */
	public void setBpm(float bpm) {
		this.bpm.set(bpm);
	}
	
	/**
	 * Sets the beattype property
	 * @param beattype
	 */
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
	
	/**
	 * Sets the frequency of a specified tone in toneList
	 * @param index
	 * @param note
	 */
	public void setToneFrequency(int index, String note) {
		toneList.getList().get(index).setFrequency(Frequency.ofPitch(note));
		Utility.debug(String.valueOf(toneList.getList().get(index).getFrequency().asHz()));
	}
	
	/**
	 * Indicates the value of the boolean flag playing 
	 * @return
	 */
	public boolean isPlaying() {
		return playing;
	}
	
	/**
	 * Quits the program
	 */
	public void quit() {
		out.close();
		System.exit(1);
	}
}
