package ru.ochkasovap.weatherStation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.ochkasovap.weatherStation.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer>{

}
