package ru.dictation.repositories;

import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.dictation.entities.UserAnswer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    @Query("SELECT u from UserAnswer u where (:gender is null or u.gender = :gender) and (:region is null or u.region = :region) and (:city is null or u.city = :city)" +
            " and (:identifier is null or u.identifier = :identifier) and (u.age between :firstAge and :secondAge) and (u.dateOfCreated between :firstDate and :secondDate)")
    List<UserAnswer> findAllByGenderAndRegionAndCityAndIdentifierAnAndAgeBetween(String gender,
                                                                                 String region,
                                                                                 String city,
                                                                                 String identifier,
                                                                                 int firstAge, int secondAge,
                                                                                 java.sql.Date firstDate, Date secondDate);

}
