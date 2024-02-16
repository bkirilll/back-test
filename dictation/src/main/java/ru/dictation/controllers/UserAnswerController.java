package ru.dictation.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dictation.entities.UserAnswer;
import ru.dictation.repositories.UserAnswerRepository;
import ru.dictation.services.MailSenderService;
import ru.dictation.services.UserAnswerService;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserAnswerController {

    private final UserAnswerRepository userAnswerRepository;

    private final MailSenderService mailSenderService;

    private final UserAnswerService userAnswerService;

    @PostMapping("/test/result")
    public void saveUserAnswer(@RequestBody UserAnswer userAnswer) throws MessagingException {

        UserAnswer answer = new UserAnswer();

        answer.setUsername(userAnswer.getUsername());
        answer.setAge(userAnswer.getAge());
        answer.setGender(userAnswer.getGender());
        answer.setRegion(userAnswer.getRegion());
        answer.setCity(userAnswer.getCity());
        answer.setMail(userAnswer.getMail());
        answer.setAccuracy(userAnswer.getAccuracy());
        answer.setIdentifier(userAnswer.getIdentifier());

        if (userAnswer.getAccuracy() > 90) {
            mailSenderService.sendMessage(userAnswer.getMail(), "Congratulation!!!", "/home/kirill/java-projects/works-projects/dictation/dictation/src/main/resources/static/silver.jpg");
        }

        userAnswerRepository.saveAndFlush(answer);
    }


    @GetMapping("/export-to-excel/answers")
    public void exportToExcelAnswers(
            @RequestParam(value = "gender", required = false) String gender,
            @RequestParam(value = "region",required = false) String region,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "identifier", required = false) String identifier,
            @RequestParam(value = "first_age", required = false) String firstAge,
            @RequestParam(value = "second_age", required = false) String secondAge,
            @RequestParam(value = "first_date", required = false) Date firstDate,
            @RequestParam(value = "second_date", required = false) Date secondDate,
            HttpServletResponse response) {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=User_answers.xlsx";

        response.setHeader(headerKey, headerValue);

        log.info("Admin saves excel with users answers");

        userAnswerService.exportUserAnswerToExcel(response, gender, region, city, identifier, firstAge, secondAge, firstDate, secondDate);
    }
}
