package ru.dictation.services;


import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dictation.domain.ExcelExportUtils;
import ru.dictation.entities.QuestionStat;
import ru.dictation.repositories.QuestionStatRepository;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class QuestionStatService {

    private final QuestionStatRepository questionStatRepository;

    public List<QuestionStat> exportQuestionsToExcel(HttpServletResponse response) throws IOException {

        List<QuestionStat> questionStats = questionStatRepository.findAll();
        ExcelExportUtils exportUtils = new ExcelExportUtils(questionStats, null);
        exportUtils.exportQuestionDataToExcel(response);
        return questionStats;
    }

}
