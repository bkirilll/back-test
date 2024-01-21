package ru.dictation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dictation.entities.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
}
