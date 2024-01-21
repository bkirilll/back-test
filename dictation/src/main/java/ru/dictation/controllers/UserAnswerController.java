package ru.dictation.controllers;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.dictation.entities.UserAnswer;
import ru.dictation.repositories.UserAnswerRepository;
import ru.dictation.services.MailSenderService;
import ru.dictation.services.UserAnswerService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Transactional
public class UserAnswerController {

    private final UserAnswerRepository userAnswerRepository;

    private final MailSenderService mailSenderService;

    private final UserAnswerService userAnswerService;

    @GetMapping("/test/result")
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


    @GetMapping("/export-to-excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=User_answers.xlsx";

        response.setHeader(headerKey, headerValue);

        userAnswerService.exportUserAnswerToExcel(response);
    }
}
