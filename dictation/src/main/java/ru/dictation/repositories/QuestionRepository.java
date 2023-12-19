package ru.dictation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dictation.entities.Question;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Stream<Question> findAllByType(String type);

    Stream<Question> streamAllBy();
}
