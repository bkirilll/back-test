package ru.dictation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dictation.entities.City;

public interface CityRepository extends JpaRepository<City, Long> {


}
