package ru.dictation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dictation.entities.QuestionStat;

import java.util.Optional;

public interface QuestionStatRepository extends JpaRepository<QuestionStat, Long> {

    QuestionStat findByQuestion(String question);

}
