package ru.ochkasovap.weatherStation.util;

public class ErrorResponse {
	private String message;
	private long timeOfError;
	public ErrorResponse(String message, long timeOfError) {
		super();
		this.message = message;
		this.timeOfError = timeOfError;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getTimeOfError() {
		return timeOfError;
	}
	public void setTimeOfError(long timeOfError) {
		this.timeOfError = timeOfError;
	}
	
}
