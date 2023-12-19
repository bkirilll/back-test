package ru.dictation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dictation.entities.QuestionAnswer;

import java.util.stream.Stream;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {

    Stream<QuestionAnswer> findQuestionAnswersByQuestionId(Long id);
}
