package ru.dictation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AskDto {

    Boolean answer;

    public static AskDto makeDefault(Boolean answer) {
        return builder()
                .answer(answer)
                .build();
    }

}
