package ru.dictation.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dictation.dto.ChapterDto;
import ru.dictation.entities.Chapter;
import ru.dictation.exceptions.BadRequestException;
import ru.dictation.factories.ChapterDtoFactory;
import ru.dictation.repositories.ChapterRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ChapterController {

    private final ChapterDtoFactory chapterDtoFactory;

    private final ChapterRepository chapterRepository;

    private final ControllerHelper controllerHelper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secure/chapters/{chapter_id}")
    public ChapterDto getChapterById(@PathVariable(value = "chapter_id") Optional<Long> optionalId) {

        Chapter chapter = optionalId
                .map(controllerHelper::getChapterOrThrowException)
                .orElseGet(() -> Chapter.builder().build());

        return chapterDtoFactory.makeChapterDto(chapter);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secure/chapters/")
    public List<ChapterDto> getAllChaptersForAdmin() {

        return getAllChapters();
    }

    @GetMapping("/training/")
    public List<ChapterDto> getAllChapterForUsers() {

        return getAllChapters();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/secure/chapters/add")
    @ResponseBody
    public void createChapter(@RequestBody Chapter chapter) {

        if (chapter.getDescription().isBlank()) {
            throw new BadRequestException("Chapter description can't be empty. ");
        }

        Chapter saveChapter = new Chapter();
        saveChapter.setDescription(chapter.getDescription());

        log.info("Admin add chapter. Description: " + saveChapter.getDescription());

        chapterRepository.saveAndFlush(saveChapter);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/secure/chapters/{chapter_id}/edit")
    @ResponseBody
    public void editChapter(@RequestBody Chapter chapter,
                            @PathVariable(value = "chapter_id") Long id) {

        if (chapter.getDescription().isBlank()) {
            throw new BadRequestException("Chapter description can't be empty. ");
        }

        Chapter chapterFromDb = controllerHelper.getChapterOrThrowException(id);

        chapterFromDb.setDescription(chapter.getDescription());

        log.info("Admin edit chapter: " + chapter.getDescription());

        chapterRepository.saveAndFlush(chapterFromDb);
    }

    private List<ChapterDto> getAllChapters() {
        List<Chapter> chapters = chapterRepository.findAll();

        return chapters.stream()
                .map(chapterDtoFactory::makeChapterDto)
                .collect(Collectors.toList());
    }
}
