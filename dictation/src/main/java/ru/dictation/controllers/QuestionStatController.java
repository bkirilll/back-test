package ru.dictation.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dictation.entities.Question;
import ru.dictation.entities.QuestionStat;
import ru.dictation.repositories.QuestionRepository;
import ru.dictation.repositories.QuestionStatRepository;
import ru.dictation.services.QuestionStatService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Transactional
public class QuestionStatController {

    private final QuestionStatRepository questionStatRepository;

    private final QuestionRepository questionRepository;

    private final QuestionStatService questionStatService;

    private final Logger logger = LogManager.getLogger(QuestionStatController.class);


    @PostMapping("/test/{question_id}")
    public void saveQuestionStat(@PathVariable(value = "question_id") Long questionId,
                                 @RequestParam(value = "validaty") boolean validaty) {

        Question question = questionRepository.findById(questionId).get();

        QuestionStat questionStatFromDb = questionStatRepository.findByQuestion(question.getText());

        if (questionStatFromDb != null) {
            questionStatFromDb.setAllQuantity(questionStatFromDb.getAllQuantity() + 1);
            if (validaty && questionStatFromDb.getRightQuantity() != null) {
                questionStatFromDb.setRightQuantity(questionStatFromDb.getRightQuantity() + 1);
            }
            if (validaty && questionStatFromDb.getRightQuantity() == null) {
                questionStatFromDb.setRightQuantity(1L);
            }
            questionStatRepository.saveAndFlush(questionStatFromDb);
        } else {
            QuestionStat questionStat = new QuestionStat();
            questionStat.setQuestion(question.getText());
            questionStat.setAllQuantity(1L);
            if (validaty) {
                questionStat.setRightQuantity(1L);
            }
            questionStatRepository.saveAndFlush(questionStat);
        }
    }

    @GetMapping("/export-to-excel/questions")
    public void exportToExcelAnswers(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Questions_stats.xlsx";

        response.setHeader(headerKey, headerValue);

        logger.info("Admin saves questions stats");

        questionStatService.exportQuestionsToExcel(response);
    }
}
