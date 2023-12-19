package ru.dictation.factories;

import org.springframework.stereotype.Component;
import ru.dictation.dto.QuestionAnswerDto;
import ru.dictation.entities.QuestionAnswer;

@Component
public class QuestionAnswerDtoFactory {

    public QuestionAnswerDto makeQuestionAnswerDto(QuestionAnswer questionAnswer) {

        return QuestionAnswerDto.builder()
                .id(questionAnswer.getId())
                .text(questionAnswer.getText())
                .validaty(questionAnswer.isValidaty())
                .questionId(questionAnswer.getQuestion().getId())
                .build();
    }
}
