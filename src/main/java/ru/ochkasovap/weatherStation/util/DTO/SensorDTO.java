package ru.ochkasovap.weatherStation.util.DTO;

import jakarta.validation.constraints.NotEmpty;

public class SensorDTO {
	@NotEmpty(message = "Field 'name' must not be empty")
	private String name;
	private int id;
	
	public SensorDTO() {
		super();
	}

	public SensorDTO(@NotEmpty(message = "Field 'name' must not be empty") String name) {
		super();
		this.name = name;
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
	
	
}
