package ru.ochkasovap.weatherStation.util.DTO;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;


public class MeasurementDTO {
	
	@Min(value = -50, message = "The temperature must be in the range [-50;50]")
	@Max(value = 50, message = "The temperature must be in the range [-50;50]")
	private double temperature;
	
	private boolean raining;
	
	private SensorDTO sensor;
	

	public MeasurementDTO() {
		super();
	}

	public MeasurementDTO(
			@Min(value = -50, message = "The temperature must be in the range [-50;50]") @Max(value = 50, message = "The temperature must be in the range [-50;50]") double temperature,
			boolean raining, SensorDTO sensor) {
		super();
		this.temperature = temperature;
		this.raining = raining;
		this.sensor = sensor;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public boolean isRaining() {
		return raining;
	}

	public void setRaining(boolean raining) {
		this.raining = raining;
	}

	public SensorDTO getSensor() {
		return sensor;
	}

	public void setSensor(SensorDTO sensor) {
		this.sensor = sensor;
	}
	
	
}
