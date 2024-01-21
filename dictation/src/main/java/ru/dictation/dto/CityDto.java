package ru.dictation.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CityDto {

    @NonNull
    Long id;

    @NonNull
    String city;

    @NonNull
    Long regionId;
}
