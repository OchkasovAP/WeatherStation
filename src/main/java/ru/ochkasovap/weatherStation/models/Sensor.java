package ru.ochkasovap.weatherStation.models;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "sensor")
public class Sensor {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	@NotEmpty(message = "Field 'name' must not be empty")
	private String name;
	
	@OneToMany(mappedBy = "sensor", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Measurement> measurements;
	
	public static class Builder {
		private Sensor sensor;
		private Builder() {
			sensor = new Sensor();
		}

		public Sensor build() {
			return sensor;
		}

		public Builder id(int id) {
			sensor.setId(id);
			return this;
		}

		public Builder name(String name) {
			sensor.setName(name);
			return this;
		}
		
	}
	public static Builder buildSensor() {
		return new Builder();
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Measurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<Measurement> measurements) {
		this.measurements = measurements;
	}

	
	
}
