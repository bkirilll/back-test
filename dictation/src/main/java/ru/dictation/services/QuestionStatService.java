package ru.dictation.services;


import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dictation.entities.ExcelExportUtils;
import ru.dictation.entities.QuestionStat;
import ru.dictation.repositories.QuestionStatRepository;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionStatService {

    private final QuestionStatRepository questionStatRepository;

    public List<QuestionStat> exportQuestionsToExcel(HttpServletResponse response) {

        List<QuestionStat> questionStats = questionStatRepository.findAll();
        ExcelExportUtils exportUtils = new ExcelExportUtils(questionStats, null);

        try {
            exportUtils.exportQuestionDataToExcel(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return questionStats;
    }

}
