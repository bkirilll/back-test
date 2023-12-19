package ru.dictation.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dictation.entities.Question;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class QuestionAnswerDto {

    @NonNull
    Long id;


    @NonNull
    String text;

    @NonNull
    boolean validaty;

    @NonNull
    Long questionId;
}
