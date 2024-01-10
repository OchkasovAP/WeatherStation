package ru.ochkasovap.weatherStation.controllers;


import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import ru.ochkasovap.weatherStation.models.Measurement;
import ru.ochkasovap.weatherStation.services.MeasurementService;
import ru.ochkasovap.weatherStation.util.ErrorResponse;
import ru.ochkasovap.weatherStation.util.DTO.MeasurementDTO;
import ru.ochkasovap.weatherStation.util.exceptions.MeasurementNotValidException;
import ru.ochkasovap.weatherStation.util.validators.MeasurementValidator;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
	@Autowired
	private MeasurementService measurementService;
	@Autowired
	private MeasurementValidator validator;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping()
	public List<MeasurementDTO> getAllMeasurements() {
		return measurementService.findAll().stream().map(this::convertMeasurement).toList();
	}
	@GetMapping("/rainyDaysCount")
	public ResponseEntity<?> getRainyDaysCount() {
		return new ResponseEntity<>(Map.of("rainyDaysCount", measurementService.countRainyDays()), HttpStatus.OK);
	}
	@PostMapping("/add")
	public ResponseEntity<MeasurementDTO> addNewMeasurement(@RequestBody @Valid MeasurementDTO dto, BindingResult bindingResult) {
		Measurement measurement = convertDTO(dto);
		validator.validate(measurement, bindingResult);
		if(bindingResult.hasErrors()) {
			throw new MeasurementNotValidException(handleValidatorException(bindingResult));
		}
		measurementService.create(measurement);
		return new ResponseEntity<MeasurementDTO>(dto, HttpStatus.OK);
	}
	
	@ExceptionHandler
	private ResponseEntity<ErrorResponse> handleException(MeasurementNotValidException ex) {
		ErrorResponse response = new ErrorResponse(ex.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<ErrorResponse> (response, HttpStatus.BAD_REQUEST);
	}
	
	private MeasurementDTO convertMeasurement(Measurement measurement) {
		return modelMapper.map(measurement, MeasurementDTO.class);
	}
	private Measurement convertDTO(MeasurementDTO dto) {
		return modelMapper.map(dto, Measurement.class);
	}
	private String handleValidatorException(BindingResult bindingResult) {
		StringBuilder builder = new StringBuilder();
		for(FieldError error:bindingResult.getFieldErrors()) {
			builder.append("Field - ").append(error.getField())
			.append(", error - ").append(error.getDefaultMessage()).append(";");
		}
		return builder.toString();
	}
}
