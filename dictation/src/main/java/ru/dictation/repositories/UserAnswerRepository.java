package ru.dictation.repositories;

import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dictation.entities.UserAnswer;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    List<UserAnswer> findAllByAgeBetween(int firstAge, int secondAge);
    List<UserAnswer> findAllByGenderOrRegionOrCityOrIdentifierOrAgeBetween(String gender, String region, String city, String identifier, int firstAge, int secondAge);

}
