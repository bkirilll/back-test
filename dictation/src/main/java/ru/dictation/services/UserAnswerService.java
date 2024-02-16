package ru.dictation.services;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dictation.entities.ExcelExportUtils;
import ru.dictation.entities.UserAnswer;
import ru.dictation.repositories.UserAnswerRepository;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserAnswerService {

    private UserAnswerRepository userAnswerRepository;

    public List<UserAnswer> exportUserAnswerToExcel(HttpServletResponse response, String gender, String region,
                                                    String city, String identifier, String firstAge, String secondAge,
                                                    Date firstDate, Date secondDate) {

        int fAge = 0;

        int sAge = 100;

        if (!firstAge.isEmpty()) {
            fAge = Integer.parseInt(firstAge);
        }
        if (!secondAge.isEmpty()) {
            sAge = Integer.parseInt(secondAge);
        }

        if (region.isEmpty()) {
            region = null;
        }
        if (gender.isEmpty()) {
            gender = null;
        }
        if (city.isEmpty()) {
            city = null;
        }
        if (identifier.isEmpty()) {
            identifier = null;
        }

        List<UserAnswer> answers = userAnswerRepository.findAllByGenderAndRegionAndCityAndIdentifierAnAndAgeBetween(gender, region, city, identifier, fAge, sAge, firstDate, secondDate);

        ExcelExportUtils exportUtils = new ExcelExportUtils(null, answers);

        try {
            exportUtils.exportDataToExcel(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return answers;
    }
}
