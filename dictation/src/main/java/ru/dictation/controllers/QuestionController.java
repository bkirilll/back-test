package ru.dictation.controllers;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dictation.dto.AskDto;
import ru.dictation.dto.QuestionAnswerDto;
import ru.dictation.dto.QuestionDto;
import ru.dictation.entities.Question;
import ru.dictation.entities.QuestionAnswer;
import ru.dictation.exceptions.NotFoundException;
import ru.dictation.factories.QuestionAnswerDtoFactory;
import ru.dictation.factories.QuestionDtoFactory;
import ru.dictation.repositories.QuestionAnswerRepository;
import ru.dictation.repositories.QuestionRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
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


    @GetMapping("/tests/")
    public List<QuestionDto> getQuestionsByType(@RequestParam(value = "type") Optional<String> optionalType) {


        optionalType = optionalType.filter(type -> !type.trim().isEmpty());

        Stream<Question> questionStream = optionalType
                .map(questionRepository::findAllByType)
                .orElseGet(questionRepository::streamAllBy);

        return questionStream
                .map(questionDtoFactory::makeQuestionDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/secure/add")
    @ResponseBody
    public AskDto addQuestion(@RequestBody Question question) {
        Question saveQuestion = new Question();
        saveQuestion.setText(question.getText());
        saveQuestion.setType(question.getType());
        questionRepository.saveAndFlush(saveQuestion);

        return AskDto.makeDefault(true);
    }

    @PutMapping("/secure/edit/{question_id}")
    @ResponseBody
    public void editQuestion(@RequestBody Question q,
                             @PathVariable(value = "question_id") Long id) {

        Optional<Question> question = questionRepository.findById(id);

    }

    @PostMapping("/secure/add/{question_id}")
    @ResponseBody
    public AskDto addQuestionAnswers(@RequestBody QuestionAnswer q,
                                              @PathVariable(value = "question_id") Long id) {
        QuestionAnswer answer = new QuestionAnswer();
        answer.setText(q.getText());
        answer.setValidaty(q.isValidaty());
        answer.setQuestion(questionRepository.findById(id).get());
        questionAnswerRepository.saveAndFlush(answer);

        return AskDto.makeDefault(true);
    }

    @DeleteMapping("/secure/delete/{question_id}")
    public AskDto deleteQuestion(@PathVariable(value = "question_id") Long id) {

        controllerHelper.getQuestionOrThrowException(id);

        questionRepository.deleteById(id);

        return AskDto.makeDefault(true);
    }

}
