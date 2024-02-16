package ru.dictation.repositories;

import org.eclipse.angus.mail.util.QEncoderStream;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dictation.entities.Chapter;
import ru.dictation.entities.Question;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Stream<Question> streamAllBy();

    Stream<Question> streamAllByChapterId(Long chapterId);

    List<Question> findAllByChapterId(Long chapterId);

}
