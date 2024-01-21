package ru.dictation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserAnswerDto {

    @NonNull
    Long id;

    @NonNull
    String username;

    @NonNull
    int age;

    @NonNull
    String gender;

    @NonNull
    String region;

    @NonNull
    String city;

    @NonNull
    String mail;

    @NonNull
    int accuracy;

    String identifier;
}
