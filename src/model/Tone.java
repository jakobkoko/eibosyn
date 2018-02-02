package model;

import java.util.ArrayList;
import java.util.List;

import ddf.minim.AudioOutput;
import ddf.minim.UGen;
import ddf.minim.effects.HighPassSP;
import ddf.minim.effects.LowPassSP;
import ddf.minim.ugens.ADSR;
import ddf.minim.ugens.BitCrush;
import ddf.minim.ugens.Delay;
import ddf.minim.ugens.Frequency;
import ddf.minim.ugens.Instrument;
import ddf.minim.ugens.MoogFilter;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waves;
/**
 * Represents a Tone
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public class Tone implements Instrument {

	private List<UGen> filters;
	private Frequency freq;
	private float amp;
	private Oscil osc;
	private ADSR adsr;
	private final float attTime = 0.000001f;
	private float decTime;
	private final float relTime = 0.000001f;
	private AudioOutput out;
	private ArrayList<UGen> filterList;
	private Delay delay;
	private MoogFilter moog;
	private BitCrush bitCrush;
	private LowPassSP lowpass;
	private HighPassSP highpass;
	private UGen patchChain;
	private boolean filtered;

	/**
	 * Creates an instance of a Tone
	 * @param frequency
	 * @param amp
	 * @param out
	 */
	public Tone(Frequency frequency, float amp, AudioOutput out) {
		this.freq = frequency;
		this.amp = amp;
		this.osc = new Oscil(freq, amp, Waves.SINE);
		this.adsr = new ADSR(1 ,attTime, decTime - attTime, 1);
		this.out = out;
		this.filterList = new ArrayList<>();
		this.delay = new Delay( 0.4f, 0.5f, true, true );
		this.moog = new MoogFilter(2000, 0f, MoogFilter.Type.LP);
		this.bitCrush = new BitCrush(1, 44100);
		this.lowpass = new LowPassSP(100, out.sampleRate());
		this.highpass = new HighPassSP(freq.asHz(), out.sampleRate());
		osc.patch(adsr);
	}

	/**
	 * Repatching this Tone after effects were activated
	 */
	public void updateFilters() {
		osc.unpatch(adsr);
		if(filtered) patchChain.unpatch(adsr);
		patchChain = osc;
		for(UGen filter : filterList) {
			patchChain.patch(filter);
			patchChain = filter;
			filtered = true;
		}
		patchChain.patch(adsr);
	}
	
	/**
	 * Calls methods for patching effects on this Tone
	 * @param effectType
	 */
	public void switchFilters(EffectType effectType) {
		switch (effectType) {
			case DELAY:
				switchDelay();
				System.out.println("delay");
				break;
			case BITCRUSH:
				switchBitCrush();
				System.out.println("bitcrush");
				break;
			case MOOG:
				switchMoog();
				System.out.println("moog");
				break;
			case LOWPASS:
				switchLowpass();
				System.out.println("lowpass");
				break;
			case HIGHPASS:
				switchHighpass();
				System.out.println("highpass");
				break;
		}
	}
	
	/**
	 * Switches Delay on and off
	 */
	public void switchDelay() {
		if(!filterList.contains(delay)) filterList.add(delay);
		else filterList.remove(delay);
		updateFilters();
	}
	
	/**
	 * Switches LowPass on and off
	 */
	public void switchLowpass() {
		if(!filterList.contains(lowpass)) filterList.add(lowpass);
		else filterList.remove(lowpass);
		updateFilters();
	}
	
	/**
	 * Switches HighPass on and off
	 */
	public void switchHighpass() {
		if(!filterList.contains(highpass)) filterList.add(highpass);
		else filterList.remove(highpass);
		updateFilters();
	}
	
	/**
	 * Switches Moog on and off
	 */
	public void switchMoog() {
		if(!filterList.contains(moog)) filterList.add(moog);
		else filterList.remove(moog);
		updateFilters();
	}
	
	/**
	 * Switches BitCrush on and off
	 */
	public void switchBitCrush() {
		if(!filterList.contains(bitCrush)) filterList.add(bitCrush);
		else filterList.remove(bitCrush);
		updateFilters();
	}

	/**
	 * Unpatches the specified filter from this Tone
	 * @param filter
	 */
	public void removeFilter(UGen filter) {
		filters.remove(filter);
	}

	/**
	 * Gets the Frequency Object of this Tone
	 * @return
	 */
	public Frequency getFrequency() {
		return this.freq;
	}

	/**
	 * Sets the frequency of the Frequency Object of this Tone to the given float value in Hz
	 * @param hz
	 */
	public void setFrequency(float hz) {
		this.osc.setFrequency(hz);
		this.freq.setAsHz(hz);
		if(filterList.size() > 0) {
			osc.unpatch(adsr);
			updateFilters();
		}
	}
	
	/**
	 * Sets the frequency of the Frequency Object of this Tone to the given Frequency
	 * @param frequency
	 */
	public void setFrequency(Frequency frequency) {
		osc.setFrequency(frequency);
		this.freq.setAsHz(frequency.asHz());
		if(filterList.size() > 0) {
			osc.unpatch(adsr);
			updateFilters();
		}
	}

	/**
	 * Updates the float value amplitude
	 * @param newAmp
	 */
	public void updateAmplitude(float newAmp) {
		this.osc.setAmplitude(newAmp);
	}

	/**
	 * Updates the parameters of the ADSR Object to the given bpm and BeatType
	 * @param bpm
	 * @param beatType
	 */
	public void updateADSR_ToTempo(float bpm, BeatType beatType) {
		decTime = 60 / bpm; // = 1
		switch(beatType) {
			case WHOLE: decTime *= 4;
				break;
			case HALF: decTime *= 2;
				break;
			case QUARTER: decTime *= 1;
				break;
			case EIGHTHS: decTime /= 2;
				break;
			case SIXTEENTHS: decTime /= 4; // = 0.25
				break;
			case THIRTY_SECONDS: decTime /= 8 ;
				break;
		}
		adsr.setParameters(1, attTime, decTime - attTime - relTime, 0, relTime, 0, 0);
	}

	@Override
	public void noteOn(float duration) {
		adsr.noteOn();
		adsr.patch(out);
	}

	@Override
	public void noteOff() {
		adsr.noteOff();
		adsr.unpatchAfterRelease(out);
	}

	/**
	 * Gets the list of effects for this Tone
	 * @return
	 */
	public List<UGen> getFilters() {
		return filters;
	}

	/**
	 * Sets the list of effects for this Tone
	 * @param filters
	 */
	public void setFilters(List<UGen> filters) {
		this.filters = filters;
	}

	/**
	 * Gets the Oscillator Object of this Tone
	 * @return
	 */
	public Oscil getOsc() {
		return osc;
	}

	/**
	 * Sets the Oscillator Object of this Tone
	 * @param osc
	 */
	public void setOsc(Oscil osc) {
		this.osc = osc;
	}

	/**
	 * Gets the ADSR Object of this Tone
	 * @return
	 */
	public ADSR getAdsr() {
		return adsr;
	}

	/**
	 * Sets the ADSR Object of this Tone
	 * @return
	 */
	public void setAdsr(ADSR adsr) {
		this.adsr = adsr;
	}

	/**
	 * Gets the DecayTime of this Tone
	 * @return
	 */
	public float getDecTime() {
		return decTime;
	}

	/**
	 * Sets the DecayTime of this Tone
	 * @param decTime
	 */
	public void setDecTime(float decTime) {
		this.decTime = decTime;
	}

	/**
	 * Gets the AudioOutput of this Tone
	 * @return
	 */
	public AudioOutput getOut() {
		return out;
	}

	
	/**
	 * Sets the AudioOutput of this Tone
	 * @return
	 */
	public void setOut(AudioOutput out) {
		this.out = out;
	}

	/**
	 * Gets the AttackTime of this Tone
	 * @return
	 */
	public float getAttTime() {
		return attTime;
	}

	/**
	 * Gets the ReleaseTime of this Tone
	 * @return
	 */
	public float getRelTime() {
		return relTime;
	}
	
	/**
	 * Unmutes this Tone
	 */
	public void unmute() {
		updateAmplitude(1);
	}
	
	/**
	 * Mutes this Tone
	 */
	public void mute() {
		updateAmplitude(0);
	}
}
