package ru.ochkasovap.weatherStation.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.ochkasovap.weatherStation.models.Sensor;
import ru.ochkasovap.weatherStation.repositories.SensorRepository;

@Service
@Transactional(readOnly = true)
public class SensorService {
	@Autowired
	private SensorRepository sensorRepository;
	
	public List<Sensor> findAll() {
		return sensorRepository.findAll();
	}
	
	public Optional<Sensor> findByName(String name) {
		return sensorRepository.findByName(name);
	}
	
	@Transactional
	public Sensor create(Sensor sensor) {
		return sensorRepository.save(sensor);
	}
	@Transactional
	public void remove(int sensorId) {
		sensorRepository.deleteById(sensorId);
	}
}
