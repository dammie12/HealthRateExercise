package org.dami.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InitialData {

	LocalDateTime date = LocalDateTime.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

	private String start_time;
	private String end_time;
	private int bpm;

	public LocalDateTime getStart_time() {
		return LocalDateTime.parse(start_time, formatter);
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public int getBpm() {
		return bpm;
	}

	public void setBpm(int bpm) {
		this.bpm = bpm;
	}

	public LocalDate getStart_date() {
		return getStart_time().toLocalDate();
	}

}
