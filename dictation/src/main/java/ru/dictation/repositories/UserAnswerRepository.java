package ru.dictation.repositories;

import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dictation.entities.UserAnswer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    List<UserAnswer> findAllByGender(String gender);

    List<UserAnswer> findAllByRegion(String region);

    List<UserAnswer> findAllByCity(String city);

    List<UserAnswer> findAllByIdentifier(String identifier);

    List<UserAnswer> findAllByAgeBetween(int firstAge, int secondAge);

    List<UserAnswer> findAllByDateOfCreatedBetween(Date firstDate, Date secondDate);

    List<UserAnswer> findAllByGenderAndRegionAndCityAndIdentifierAndAgeBetweenAndDateOfCreatedBetween(String gender,
                                                                                                      String region,
                                                                                                      String city,
                                                                                                      String identifier,
                                                                                                      int firstAge, int secondAge,
                                                                                                      Date firstDate, Date secondDate);
}
