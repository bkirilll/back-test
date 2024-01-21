package ru.dictation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dictation.entities.UserAnswer;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
}
