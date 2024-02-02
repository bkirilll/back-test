package ru.dictation.factories;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.dictation.dto.QuestionAnswerDto;
import ru.dictation.dto.QuestionDto;
import ru.dictation.entities.Chapter;
import ru.dictation.entities.Question;
import ru.dictation.entities.QuestionAnswer;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class QuestionDtoFactory {

    QuestionAnswerDtoFactory questionAnswerDtoFactory;

    ChapterDtoFactory chapterDtoFactory;

    public QuestionDto makeQuestionDto(Question question) {

        return QuestionDto.builder()
                .id(question.getId())
                .text(question.getText())
                .answers(
                        question
                                .getAnswers()
                                .stream()
                                .map(questionAnswerDtoFactory::makeQuestionAnswerDto)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
