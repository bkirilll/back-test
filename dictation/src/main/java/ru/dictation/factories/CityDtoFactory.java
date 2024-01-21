package ru.dictation.factories;

import org.springframework.stereotype.Component;
import ru.dictation.dto.ChapterDto;
import ru.dictation.dto.CityDto;
import ru.dictation.entities.Chapter;
import ru.dictation.entities.City;

@Component
public class CityDtoFactory {

    public CityDto makeCityDto(City city) {

        return CityDto.builder()
                .id(city.getId())
                .city(city.getCity())
                .regionId(city.getRegion().getId())
                .build();
    }
}
