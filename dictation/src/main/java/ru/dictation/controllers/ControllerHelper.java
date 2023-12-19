package ru.dictation.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.dictation.entities.Question;
import ru.dictation.exceptions.NotFoundException;
import ru.dictation.repositories.QuestionRepository;

@RequiredArgsConstructor
@Component
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ControllerHelper {

    QuestionRepository questionRepository;

    public Question getQuestionOrThrowException(Long id) {
        return questionRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Question with \"%s\" doesn't exist", id)
                ));
    }
}
