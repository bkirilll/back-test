package ru.dictation.services;

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

    public List<UserAnswer> exportUserAnswerToExcel(HttpServletResponse response, String gender, String region, String city, String identifier, int firstAge, int secondAge) throws IOException {

        List<UserAnswer> answers = userAnswerRepository.findAllByGenderOrRegionOrCityOrIdentifierOrAgeBetween(gender, region, city, identifier, firstAge, secondAge);
        ExcelExportUtils exportUtils = new ExcelExportUtils(null, answers);
        exportUtils.exportDataToExcel(response);
        return answers;
    }
}
