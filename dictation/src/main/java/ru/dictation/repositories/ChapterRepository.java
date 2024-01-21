package ru.dictation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dictation.entities.Chapter;

import java.util.stream.Stream;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {


}
