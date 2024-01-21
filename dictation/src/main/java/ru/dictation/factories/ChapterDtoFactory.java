package ru.dictation.factories;

import org.springframework.stereotype.Component;
import ru.dictation.dto.ChapterDto;
import ru.dictation.dto.QuestionAnswerDto;
import ru.dictation.entities.Chapter;
import ru.dictation.entities.QuestionAnswer;

@Component
public class ChapterDtoFactory {

    public ChapterDto makeChapterDto(Chapter chapter) {

        return ChapterDto.builder()
                .id(chapter.getId())
                .description(chapter.getDescription())
                .build();
    }
}
