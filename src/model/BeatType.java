package model;

public enum BeatType {
	WHOLE(4f), HALF(2f), QUARTER(1f), EIGHTHS(0.5f), SIXTEENTHS(0.25f), THIRTY_SECONDS(0.125f);
	
	private final float value;
	
	private BeatType(float value) {
		this.value = value;
	}
	
	public float getValue() {
		return this.value;
	}
}
