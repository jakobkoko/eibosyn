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
	private Frequency frequency;
	private Oscil osc;
	private ADSR adsr;
	private final float attTime = 0.001f;
	private float decTime;
	private final float relTime = 0.001f;
	private AudioOutput out;
	private Delay delay;
	private BitCrush bitCrush;
	private MoogFilter moog;
	
	public Tone(Frequency freq, float amp, AudioOutput out) {
		this.osc = new Oscil(freq, amp, Waves.QUARTERPULSE);
		this.adsr = new ADSR(1 ,attTime, decTime - attTime, 1);
		this.out = out;
		filters = new ArrayList<UGen>();
		delay = new Delay(0.2f, 0.1f, true);
		bitCrush = new BitCrush(1, 44100);
		moog = new MoogFilter(2000, 0f, MoogFilter.Type.LP);
		osc.patch(moog);
		moog.patch(bitCrush);
	}
	
	public void addFilter(UGen filter) {
		filters.add(filter);
	}

	public void removeFilter(UGen filter) {
		filters.remove(filter);
	}
	
	public Frequency getFrequency() {
		return this.frequency;
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
		bitCrush.patch(adsr);
		adsr.noteOn();
		adsr.patch(out);
	}

	@Override
	public void noteOff() {
		adsr.noteOff();
		bitCrush.unpatch(adsr);
		adsr.unpatchAfterRelease(out);
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
		this.frequency = frequency;
	}
	
	
}
