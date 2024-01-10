package ru.ochkasovap.weatherStation.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.ochkasovap.weatherStation.models.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer>{
	Optional<Sensor> findByName(String name);
}
