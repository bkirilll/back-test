package ru.dictation.dto;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.dictation.entities.Chapter;
import ru.dictation.entities.Question;
import ru.dictation.entities.QuestionAnswer;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class QuestionDto {

    @NonNull
    Long id;

    @NonNull
    String text;

    @NonNull
    ChapterDto chapter;

    @NonNull
    List<QuestionAnswerDto> answers;
}
