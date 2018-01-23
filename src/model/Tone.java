package model;

import java.util.ArrayList;
import java.util.List;

import ddf.minim.AudioOutput;
import ddf.minim.UGen;
import ddf.minim.ugens.ADSR;
import ddf.minim.ugens.BitCrush;
import ddf.minim.ugens.Delay;
import ddf.minim.ugens.Frequency;
import ddf.minim.ugens.Instrument;
import ddf.minim.ugens.MoogFilter;
import ddf.minim.ugens.Oscil;
import ddf.minim.ugens.Waveform;
import ddf.minim.ugens.Waves;

public class Tone implements Instrument {

	private List<UGen> filters;
	private Frequency freq;
	private float amp;
	private Oscil osc;
	private ADSR adsr;
	private final float attTime = 0.001f;
	private float decTime;
	private final float relTime = 0.001f;
	private AudioOutput out;
	private ArrayList<UGen> filterList;
	private Delay delay;
	private MoogFilter moog;
	private BitCrush bitCrush;
	private UGen patchChain;

	public Tone(Frequency frequency, float amp, AudioOutput out) {
		this.freq = frequency;
		this.amp = amp;
		this.osc = new Oscil(freq, amp, Waves.SINE);
		this.adsr = new ADSR(1 ,attTime, decTime - attTime, 1);
		this.out = out;
		this.filterList = new ArrayList<>();
		this.delay = new Delay(0.2f, 0.1f, true);
		this.moog = new MoogFilter(2000, 0f, MoogFilter.Type.LP);
		this.bitCrush = new BitCrush(1, 44100);
		osc.patch(adsr);
		switchBitCrush();
	}

	public void updateFilters() {
		// Everytime updateFilters is called we repatch the tone;
		osc.unpatch(adsr);
		patchChain = osc;
		for(UGen filter : filterList) {
			System.out.println(1);
			patchChain.patch(filter);
			patchChain = filter;
		}
		patchChain.patch(adsr);
	}
	public void switchDelay() {
		if(!filterList.contains(delay)) filterList.add(delay);
		else filterList.remove(delay);
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
		decTime = 60 / bpm;

		switch(beatType) {
			case WHOLE: decTime *= 4;
				break;
			case HALF: decTime *= 2;
				break;
			case QUARTER: decTime *= 1;
				break;
			case EIGHTHS: decTime /= 2;
				break;
			case SIXTEENTHS: decTime /= 4;
				break;
			case THIRTY_SECONDS: decTime /= 8 ;
				break;
		}
		adsr.setParameters(1, attTime, decTime - attTime, 0, relTime, 0, 0);
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
		osc.unpatch(adsr);
		osc.setFrequency(frequency);
		updateFilters();
		osc.patch(adsr);

	}


}
