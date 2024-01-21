package ru.dictation.services;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dictation.domain.ExcelExportUtils;
import ru.dictation.entities.UserAnswer;
import ru.dictation.repositories.UserAnswerRepository;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserAnswerService {

    private UserAnswerRepository userAnswerRepository;

    public List<UserAnswer> exportUserAnswerToExcel(HttpServletResponse response) throws IOException {

        List<UserAnswer> answers = userAnswerRepository.findAll();
        System.out.println(answers);
        ExcelExportUtils exportUtils = new ExcelExportUtils(answers);
        exportUtils.exportDataToExcel(response);
        return answers;
    }
}
