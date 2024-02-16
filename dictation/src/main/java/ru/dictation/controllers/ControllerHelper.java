package ru.dictation.controllers;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.dictation.entities.Chapter;
import ru.dictation.entities.Question;
import ru.dictation.exceptions.NotFoundException;
import ru.dictation.repositories.ChapterRepository;
import ru.dictation.repositories.QuestionRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ControllerHelper {

    QuestionRepository questionRepository;

    ChapterRepository chapterRepository;

    public Question getQuestionOrThrowException(Long id) {
        return questionRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Question with \"%s\" doesn't exist", id)
                ));
    }

    public Chapter getChapterOrThrowException(Long id) {
        return chapterRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Chapter with \"%s\" doesn't exist", id)
                ));
    }
}
