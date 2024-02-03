package ru.dictation.services;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.dictation.domain.ExcelExportUtils;
import ru.dictation.entities.UserAnswer;
import ru.dictation.repositories.UserAnswerRepository;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UserAnswerService {

    private UserAnswerRepository userAnswerRepository;

    public List<UserAnswer> exportUserAnswerToExcel(HttpServletResponse response, @Nullable String gender, @Nullable String region,
                                                    @Nullable String city, @Nullable String identifier, @Nullable int firstAge, @Nullable int secondAge,
                                                    @Nullable String firstDate, @Nullable String secondDate) throws IOException, ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

        //List<UserAnswer> answers = userAnswerRepository.findAllByGenderAndRegionAndCityAndIdentifierAndAgeBetweenAndDateOfCreatedBetween(gender, region, city, identifier, firstAge, secondAge, sdf.parse(firstDate), sdf.parse(secondDate));

        List<UserAnswer> answers = userAnswerRepository.findAllByDateOfCreatedBetween(sdf.parse(firstDate), sdf.parse(secondDate));

        ExcelExportUtils exportUtils = new ExcelExportUtils(null, answers);
        exportUtils.exportDataToExcel(response);
        return answers;
    }
}
