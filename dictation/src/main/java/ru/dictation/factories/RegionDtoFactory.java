package ru.dictation.factories;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.dictation.dto.RegionDto;
import ru.dictation.entities.Region;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegionDtoFactory {

    CityDtoFactory cityDtoFactory;

    public RegionDto makeRegionDto(Region region) {

        return RegionDto.builder()
                .id(region.getId())
                .region(region.getRegion())
                .cities(
                        region
                                .getCities()
                                .stream()
                                .map(cityDtoFactory::makeCityDto)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
