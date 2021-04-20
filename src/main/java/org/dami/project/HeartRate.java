package org.dami.project;

import java.time.LocalDate;

public class HeartRate {

	private LocalDate date;
	private BloodPressureData bpm;

	public HeartRate(LocalDate date, BloodPressureData bpm) {
		super();
		this.date = date;
		this.bpm = bpm;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BloodPressureData getBpm() {
		return bpm;
	}

	public void setBpm(BloodPressureData bpm) {
		this.bpm = bpm;
	}

	public HeartRate() {
		// TODO Auto-generated constructor stub
	}

	public void clear() {
		this.date = null;
		this.bpm.clear();
	}
}
