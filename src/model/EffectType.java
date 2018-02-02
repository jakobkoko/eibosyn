package model;

/**
 * Representation of accessible effects
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public enum EffectType {
	LOWPASS(0, "LowPass"), HIGHPASS(1, "HighPass"), BITCRUSH(2, "BitCrush"), 
	DELAY(3, "Delay"), MOOG(4, "Moog");
	
	private final int effectNumber;
	private String name;
	
	/**
	 * Creates an instance of EffectType
	 * @param effectNumber
	 * @param name
	 */
	private EffectType(int effectNumber, String name) {
		this.effectNumber = effectNumber;
		this.name = name;
	}
	
	public int getEffectNumber() {
		return this.effectNumber;
	}
	
	public String toString() {
		return this.name;
	}
}
