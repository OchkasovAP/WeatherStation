package ru.ochkasovap.weatherStation.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "measurements")
public class Measurement {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "temperature")
	@Min(value = -50, message = "The temperature must be in the range [-50;50]")
	@Max(value = 50, message = "The temperature must be in the range [-50;50]")
	private double temperature;
	
	@Column(name = "raining")
	private boolean raining;
	
	@Column(name = "time_of_measurement")
	@Temporal(TemporalType.TIMESTAMP)
	private Date timeOfMeasurement;
	
	@ManyToOne()
	@JoinColumn(name = "sensor_id", referencedColumnName = "id")
	private Sensor sensor;

	public static class Builder {

		private Measurement measurement;
		
		private Builder() {
			measurement = new Measurement();
		}
		public Measurement build() {
			return measurement;
		}

		public Builder id(int id) {
			measurement.setId(id);
			return this;
		}

		public Builder temperature(double temperature) {
			measurement.setTemperature(temperature);
			return this;
		}

		public Builder raining(boolean raining) {
			measurement.setRaining(raining);
			return this;
		}

		public Builder timeOfMeasurement(Date timeOfMeasurement) {
			measurement.setTimeOfMeasurement(timeOfMeasurement);
			return this;
		}

		public Builder sensor(Sensor sensor) {
			measurement.setSensor(sensor);
			return this;
		}
		
	}
	
	public static Builder buildMeasurement() {
		return new Builder();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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


	public Date getTimeOfMeasurement() {
		return timeOfMeasurement;
	}

	public void setTimeOfMeasurement(Date timeOfMeasurement) {
		this.timeOfMeasurement = timeOfMeasurement;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
	
	
}
