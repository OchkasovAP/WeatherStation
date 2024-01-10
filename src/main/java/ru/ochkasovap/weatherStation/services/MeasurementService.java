package ru.ochkasovap.weatherStation.services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.ochkasovap.weatherStation.models.Measurement;
import ru.ochkasovap.weatherStation.models.Sensor;
import ru.ochkasovap.weatherStation.repositories.MeasurementRepository;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
	@Autowired
	private MeasurementRepository repository;
	@Autowired
	private SensorService sensorService;

	public List<Measurement> findAll() {
		return repository.findAll();
	}

	public Integer countRainyDays() {
		int count = 0;
		Set<Calendar> rainyDays = new HashSet<>();
		for (Measurement measurement : findAll()) {
			Calendar time = new Calendar.Builder().setInstant(measurement.getTimeOfMeasurement()).build();
			Calendar day = new GregorianCalendar(time.get(Calendar.YEAR), time.get(Calendar.MONTH),
					time.get(Calendar.DAY_OF_MONTH));
			if (measurement.isRaining() && !rainyDays.contains(day)) {
				count++;
				rainyDays.add(day);
			}
		}
		return count;
	}
	@Transactional
	public void create(Measurement measurement) {
		measurement.setTimeOfMeasurement(new Date());
		Sensor sensor = sensorService.findByName(measurement.getSensor().getName()).get();
		sensor.getMeasurements().add(measurement);
		measurement.setSensor(sensor);
	}
}

