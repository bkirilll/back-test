package ru.dictation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class RegionDto {

    @NonNull
    Long id;

    @NonNull
    String region;

    @NonNull
    List<CityDto> cities;

}
