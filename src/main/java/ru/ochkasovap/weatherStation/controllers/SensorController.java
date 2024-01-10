package ru.ochkasovap.weatherStation.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import ru.ochkasovap.weatherStation.models.Sensor;
import ru.ochkasovap.weatherStation.services.SensorService;
import ru.ochkasovap.weatherStation.util.ErrorResponse;
import ru.ochkasovap.weatherStation.util.DTO.SensorDTO;
import ru.ochkasovap.weatherStation.util.exceptions.SensorAlreadyExistException;
import ru.ochkasovap.weatherStation.util.exceptions.SensorNotValidException;
import ru.ochkasovap.weatherStation.util.validators.SensorValidator;

@RestController
@RequestMapping("/sensors")
public class SensorController {
	@Autowired
	private SensorService sensorService;
	@Autowired
	private SensorValidator validator;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/registration")
	public ResponseEntity<SensorDTO> registerSensor(@RequestBody @Valid SensorDTO sensorDTO,
			BindingResult bindingResult) {
		Sensor sensor = convertDtoToSensor(sensorDTO);
		validator.validate(sensor, bindingResult);
		if (bindingResult.hasErrors()) {
			handleValidationError(bindingResult);
		}
		
		sensorService.create(sensor);
		return new ResponseEntity<SensorDTO>(sensorDTO, HttpStatus.CREATED);
	}
	@GetMapping("")
	public List<SensorDTO> showAllSensors() {
		List<Sensor> sensors = sensorService.findAll();
		return sensors.stream().map(this::convertSensorToDTO).toList();
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> removeSensor(@PathVariable("id") int id) {
		sensorService.remove(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleException(SensorAlreadyExistException ex) {
		ErrorResponse response = new ErrorResponse("This sensor already exist", System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleException(SensorNotValidException ex) {
		ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.BAD_REQUEST);
	}

	private Sensor convertDtoToSensor(SensorDTO sensorDTO) {
		return modelMapper.map(sensorDTO, Sensor.class);
	}

	private SensorDTO convertSensorToDTO(Sensor sensor) {
		return modelMapper.map(sensor, SensorDTO.class);
	}

	private void handleValidationError(BindingResult bindingResult) {
		StringBuilder builder = new StringBuilder();
		for (FieldError error : bindingResult.getFieldErrors()) {
			builder.append("field - ").append(error.getField()).append(", error - ").append(error.getDefaultMessage())
					.append(";");
		}
		throw new SensorNotValidException(builder.toString());
	}
}
