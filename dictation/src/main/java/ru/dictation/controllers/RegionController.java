package ru.dictation.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dictation.dto.RegionDto;
import ru.dictation.entities.Region;
import ru.dictation.factories.RegionDtoFactory;
import ru.dictation.repositories.CityRepository;
import ru.dictation.repositories.RegionRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Transactional
public class RegionController {

    private final RegionRepository regionRepository;


    private final RegionDtoFactory regionDtoFactory;

    @GetMapping("/test/registration")
    public List<RegionDto> getAllRegion() {

        List<Region> regions = regionRepository.findAll();

        return regions.stream()
                .map(regionDtoFactory::makeRegionDto)
                .collect(Collectors.toList());
    }
}
