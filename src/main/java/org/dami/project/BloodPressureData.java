package org.dami.project;

public class BloodPressureData {

	private int min;
	private int max;
	private int median;

	public BloodPressureData(int min, int max, int median) {
		super();
		this.min = min;
		this.max = max;
		this.median = median;
	}

	public BloodPressureData() {

	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getMedian() {
		return median;
	}

	public void setMedian(int median) {
		this.median = median;
	}

	public void clear() {
		this.min = 0;
		this.max = 0;
		this.median = 0;
	}
}
