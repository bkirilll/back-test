package ru.dictation.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dictation.dto.QuestionDto;
import ru.dictation.entities.Chapter;
import ru.dictation.entities.Question;
import ru.dictation.entities.QuestionAnswer;
import ru.dictation.exceptions.BadRequestException;
import ru.dictation.factories.QuestionDtoFactory;
import ru.dictation.repositories.ChapterRepository;
import ru.dictation.repositories.QuestionAnswerRepository;
import ru.dictation.repositories.QuestionRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@Transactional
@Log4j2
public class QuestionController {

    private final QuestionDtoFactory questionDtoFactory;

    private final QuestionRepository questionRepository;

    private final QuestionAnswerRepository questionAnswerRepository;

    private final ControllerHelper controllerHelper;

    private final ChapterRepository chapterRepository;

    @GetMapping("/training/{type}")
    public List<QuestionDto> getAllQuestionsByTypeForUsers(@PathVariable(value = "type") Optional<Long> chapter_id) {

        Stream<Question> questionStream = chapter_id
                .map(questionRepository::streamAllByChapterId)
                .orElseGet(questionRepository::streamAllBy);

        return questionStream
                .map(questionDtoFactory::makeQuestionDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/test/")
    public List<QuestionDto> getTestQuestionsForUsers() {

        List<Chapter> chapters = chapterRepository.findAll();

        List<Question> questions = new ArrayList<>();


        for (Chapter chapter : chapters) {

            List<Question> list = questionRepository.findAllByChapterId(chapter.getId());

            Collections.shuffle(list);

            questions.addAll(list.stream().limit(10).toList());
        }

        return questions.stream()
                .map(questionDtoFactory::makeQuestionDto)
                .collect(Collectors.toList());

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secure/questions/{question_id}")
    public QuestionDto getQuestionById(@PathVariable(value = "question_id") Optional<Long> optionalId) {

        Question question = optionalId
                .map(controllerHelper::getQuestionOrThrowException)
                .orElseGet(() -> Question.builder().build());

        return questionDtoFactory.makeQuestionDto(question);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secure/questions/")
    public List<QuestionDto> getAllQuestions() {

        List<Question> questions = questionRepository.findAll();

        return questions.stream()
                .map(questionDtoFactory::makeQuestionDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/secure/questions/add")
    @ResponseBody
    public void createQuestion(@RequestBody Question question) {

        Chapter chapter = controllerHelper.getChapterOrThrowException(question.getChapter().getId());

        List<QuestionAnswer> answers = question.getAnswers();

        if (question.getText().isBlank()) {
            throw new BadRequestException("Question can't be empty");
        } else if (question.getAnswers().isEmpty()) {
            throw new BadRequestException("Answers can't be empty");
        }

        Question saveQuestion = new Question();
        saveQuestion.setText(question.getText());
        saveQuestion.setChapter(chapter);
        saveQuestion.setAnswers(answers);

        for (QuestionAnswer answer : answers) {
            questionAnswerRepository.saveAndFlush(answer);
        }

        log.info("Admin add question: " + question.getText() + " and answers:" + question.getAnswers());

        questionRepository.saveAndFlush(saveQuestion);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/secure/questions/edit/{question_id}")
    @ResponseBody
    public void editQuestion(@RequestBody Question question,
                             @PathVariable(value = "question_id") Long id) {

        Question questionFromDb = controllerHelper.getQuestionOrThrowException(id);

        List<QuestionAnswer> answersFromDb = questionFromDb.getAnswers();

        List<QuestionAnswer> answers = question.getAnswers();

        questionFromDb.setText(question.getText());
        questionFromDb.setChapter(controllerHelper.getChapterOrThrowException(question.getChapter().getId()));

        for (int i = 0; i < answersFromDb.size(); i++) {
            answersFromDb.get(i).setText(answers.get(i).getText());
            answersFromDb.get(i).setValidaty(answers.get(i).isValidaty());
        }

        for (QuestionAnswer answer : answersFromDb) {
            questionAnswerRepository.saveAndFlush(answer);
        }

        log.info("Admin edit question: " + question.getText() + " and answers:" + question.getAnswers());

        questionRepository.saveAndFlush(questionFromDb);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/secure/questions/delete/{question_id}")
    public void deleteQuestion(@PathVariable(value = "question_id") Long id) {

        Question question = controllerHelper.getQuestionOrThrowException(id);

        List<QuestionAnswer> answers = question.getAnswers();

        questionAnswerRepository.deleteAll(answers);

        log.info("Admin delete question: " + question.getText());

        questionRepository.delete(question);
    }


}
