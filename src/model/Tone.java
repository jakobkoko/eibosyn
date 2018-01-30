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

	public void updateFilters() {
		// Everytime updateFilters is called we repatch the tone;
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
	public void switchDelay() {
		if(!filterList.contains(delay)) filterList.add(delay);
		else filterList.remove(delay);
		updateFilters();
	}
	public void switchLowpass() {
		if(!filterList.contains(lowpass)) filterList.add(lowpass);
		else filterList.remove(lowpass);
		updateFilters();
	}
	public void switchHighpass() {
		if(!filterList.contains(highpass)) filterList.add(highpass);
		else filterList.remove(highpass);
		updateFilters();
	}
	public void switchMoog() {
		if(!filterList.contains(moog)) filterList.add(moog);
		else filterList.remove(moog);
		updateFilters();
	}
	public void switchBitCrush() {
		if(!filterList.contains(bitCrush)) filterList.add(bitCrush);
		else filterList.remove(bitCrush);
		updateFilters();
	}

	public void removeFilter(UGen filter) {
		filters.remove(filter);
	}

	public Frequency getFrequency() {
		return this.freq;
	}

	public void setFrequency(float hz) {
		this.osc.setFrequency(hz);
	}

	public void updateAmplitude(float newAmp) {
		this.osc.setAmplitude(newAmp);
	}

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

	public String toString() {
		return freq.toString();
	}

	public List<UGen> getFilters() {
		return filters;
	}

	public void setFilters(List<UGen> filters) {
		this.filters = filters;
	}

	public Oscil getOsc() {
		return osc;
	}

	public void setOsc(Oscil osc) {
		this.osc = osc;
	}

	public ADSR getAdsr() {
		return adsr;
	}

	public void setAdsr(ADSR adsr) {
		this.adsr = adsr;
	}

	public float getDecTime() {
		return decTime;
	}

	public void setDecTime(float decTime) {
		this.decTime = decTime;
	}

	public AudioOutput getOut() {
		return out;
	}

	public void setOut(AudioOutput out) {
		this.out = out;
	}

	public float getAttTime() {
		return attTime;
	}

	public float getRelTime() {
		return relTime;
	}

	public void setFrequency(Frequency frequency) {
		osc.setFrequency(frequency);
		if(filterList.size() > 0) {
			osc.unpatch(adsr);
			updateFilters();
		}
	}

	public void unmute() {
		updateAmplitude(1);
	}

	public void mute() {
		updateAmplitude(0);
	}
}
