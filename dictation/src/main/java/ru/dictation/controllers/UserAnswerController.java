package ru.dictation.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Transactional
public class UserAnswerController {

    private final UserAnswerRepository userAnswerRepository;

    private final MailSenderService mailSenderService;

    private final UserAnswerService userAnswerService;

    private final Logger logger = LogManager.getLogger(UserAnswerController.class);

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
            @RequestParam(value = "first_age", required = false) int firstAge,
            @RequestParam(value = "second_age", required = false) int secondAge,
            @RequestParam(value = "first_date", required = false) String firstDate,
            @RequestParam(value = "second_date", required = false) String secondDate,
            HttpServletResponse response) throws IOException, ParseException {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=User_answers.xlsx";

        response.setHeader(headerKey, headerValue);

        logger.info("Admin saves excel with users answers");

        userAnswerService.exportUserAnswerToExcel(response, gender, region, city, identifier, firstAge, secondAge, firstDate, secondDate);
    }
}
