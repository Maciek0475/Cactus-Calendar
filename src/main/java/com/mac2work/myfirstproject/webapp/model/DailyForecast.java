package com.mac2work.myfirstproject.webapp.model;

import java.time.LocalDate;

public class DailyForecast {

	private double temp;

	private double humidity;

	private LocalDate date;

	private double success;

	public DailyForecast(double temp, double humidity, LocalDate date) {
		this.temp = temp;
		this.humidity = humidity;
		this.date = date;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getSuccess() {
		return success;
	}

	public void setSuccess(double success) {
		this.success = success;
	}

}
