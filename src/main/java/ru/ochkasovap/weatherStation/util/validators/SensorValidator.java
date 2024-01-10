package ru.ochkasovap.weatherStation.util.validators;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.ochkasovap.weatherStation.models.Sensor;
import ru.ochkasovap.weatherStation.services.SensorService;
import ru.ochkasovap.weatherStation.util.exceptions.SensorAlreadyExistException;
@Component
public class SensorValidator implements Validator{
	@Autowired
	private SensorService sensorService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Sensor.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Sensor sensor = (Sensor) target;
		Optional<Sensor> optionalSensor = sensorService.findByName(sensor.getName());
		if(optionalSensor.isPresent()) {
			throw new SensorAlreadyExistException();
		}
		
	}
	
}
