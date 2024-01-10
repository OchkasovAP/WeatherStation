package ru.ochkasovap.weatherStation.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.ochkasovap.weatherStation.models.Measurement;
import ru.ochkasovap.weatherStation.models.Sensor;
import ru.ochkasovap.weatherStation.repositories.MeasurementRepository;
@ExtendWith(MockitoExtension.class)
class MeasurementServiceTest {
	@InjectMocks
	private MeasurementService service;
	@Mock
	private MeasurementRepository repository;
	@Mock
	private SensorService sensorService;
	
	private List<Measurement> measurements;
	private Sensor sensor;
	@BeforeEach
	void setUp() {
		measurements = new ArrayList<>();
		sensor = Sensor.buildSensor().id(0).name("Sensor").build();
		measurements.addAll(List.of(
				Measurement.buildMeasurement()
				.id(0)
				.raining(true)
				.sensor(sensor)
				.temperature(10)
				.timeOfMeasurement(new GregorianCalendar(2023, 1, 1, 5, 12, 10).getTime()).build(),
				Measurement.buildMeasurement()
				.id(1)
				.raining(false)
				.sensor(sensor)
				.temperature(10)
				.timeOfMeasurement(new GregorianCalendar(2023, 1, 2, 4, 24, 42).getTime()).build(),
				Measurement.buildMeasurement()
				.id(2)
				.raining(true)
				.sensor(sensor)
				.temperature(10)
				.timeOfMeasurement(new GregorianCalendar(2023, 1, 1, 10, 20, 45).getTime()).build(),
				Measurement.buildMeasurement()
				.id(3)
				.raining(false)
				.sensor(sensor)
				.temperature(10)
				.timeOfMeasurement(new GregorianCalendar(2023, 1, 3, 12, 23, 59).getTime()).build(),
				Measurement.buildMeasurement()
				.id(4)
				.raining(true)
				.sensor(sensor)
				.temperature(10)
				.timeOfMeasurement(new GregorianCalendar(2023, 1, 4, 10, 23, 10).getTime()).build(),
				Measurement.buildMeasurement()
				.id(5)
				.raining(true)
				.sensor(sensor)
				.temperature(10)
				.timeOfMeasurement(new GregorianCalendar(2023, 1, 5, 1, 2, 3).getTime()).build()
				));
	}

	@Test
	void countRainyDays() {
		when(repository.findAll()).thenReturn(measurements);
		assertEquals(3, service.countRainyDays());
	}

}
