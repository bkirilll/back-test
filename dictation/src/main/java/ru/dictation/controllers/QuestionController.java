package ru.dictation.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dictation.dto.AskDto;
import ru.dictation.dto.QuestionDto;
import ru.dictation.entities.Chapter;
import ru.dictation.entities.Question;
import ru.dictation.entities.QuestionAnswer;
import ru.dictation.exceptions.BadRequestException;
import ru.dictation.factories.QuestionDtoFactory;
import ru.dictation.repositories.ChapterRepository;
import ru.dictation.repositories.QuestionAnswerRepository;
import ru.dictation.repositories.QuestionRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@Transactional
public class QuestionController {

    private final QuestionDtoFactory questionDtoFactory;

    private final QuestionRepository questionRepository;

    private final QuestionAnswerRepository questionAnswerRepository;

    private final ControllerHelper controllerHelper;

    private final ChapterRepository chapterRepository;


    @GetMapping("/training/{type}")
    public List<QuestionDto> getAllQuestionsByType(@PathVariable(value = "type") Optional<Long> chapter_id) {


        Stream<Question> questionStream = chapter_id
                .map(questionRepository::findAllByChapterId)
                .orElseGet(questionRepository::streamAllBy);

        return questionStream
                .map(questionDtoFactory::makeQuestionDto)
                .collect(Collectors.toList());
    }

    //toDo
    @GetMapping("/test/")
    public List<QuestionDto> getQuestions() {

        List<Chapter> chapters = chapterRepository.findAll();

        for (int i = 0; i < chapters.size(); i++) {
            Stream<Question> questions = questionRepository.findAllByChapterId(chapters.get(i).getId());


            return questions.map(questionDtoFactory::makeQuestionDto)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secure/questions/{question_id}")
    public QuestionDto getQuestionById(@PathVariable(value = "question_id") Long id) {

        Optional<Question> question = questionRepository.findById(id);

        return questionDtoFactory.makeQuestionDto(question.get());
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
    public void addQuestion(@RequestBody Question question) {

        Optional<Chapter> chapter = chapterRepository.findById(question.getChapter().getId());

        List<QuestionAnswer> answers = question.getAnswers();

        Question saveQuestion = new Question();
        saveQuestion.setText(question.getText());
        saveQuestion.setChapter(chapter.get());
        saveQuestion.setAnswers(answers);

        for (QuestionAnswer answer : answers) {
            questionAnswerRepository.saveAndFlush(answer);
        }

        questionRepository.saveAndFlush(saveQuestion);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/secure/questions/edit/{question_id}")
    @ResponseBody
    public void editQuestion(@RequestBody Question question,
                             @PathVariable(value = "question_id") Long id) {

        Optional<Question> saveQuestion = questionRepository.findById(id);

        List<QuestionAnswer> saveAnswers = controllerHelper.getQuestionOrThrowException(id).getAnswers();

        List<QuestionAnswer> answers = question.getAnswers();

        saveQuestion.get().setText(question.getText());
        saveQuestion.get().setChapter(chapterRepository.findById(question.getChapter().getId()).get());

        for (int i = 0; i < saveAnswers.size(); i++) {
            saveAnswers.get(i).setText(answers.get(i).getText());
            saveAnswers.get(i).setValidaty(answers.get(i).isValidaty());
        }

        for (QuestionAnswer answer : saveAnswers) {
            questionAnswerRepository.saveAndFlush(answer);
        }

        questionRepository.saveAndFlush(saveQuestion.get());

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/secure/questions/delete/{question_id}")
    public void deleteQuestion(@PathVariable(value = "question_id") Long id) {

        Question question = controllerHelper.getQuestionOrThrowException(id);

        List<QuestionAnswer> answers = controllerHelper.getQuestionOrThrowException(id).getAnswers();

        System.out.println(Arrays.stream(answers.toArray()).toArray());

        questionAnswerRepository.deleteAll(answers);

        questionRepository.delete(question);
    }


}
