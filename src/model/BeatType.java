package model;

/**
 * Representation of different beattypes
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public enum BeatType {
	WHOLE(4f), HALF(2f), QUARTER(1f), EIGHTHS(0.5f), SIXTEENTHS(0.25f), THIRTY_SECONDS(0.125f);
	
	private final float value;
	
	/**
	 * Creates an instance of BeatType
	 * @param value
	 */
	private BeatType(float value) {
		this.value = value;
	}
	
	/**
	 * 
	 * @return value
	 */
	public float getValue() {
		return this.value;
	}
}
