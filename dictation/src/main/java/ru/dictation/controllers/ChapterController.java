package ru.dictation.controllers;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dictation.dto.ChapterDto;
import ru.dictation.entities.Chapter;
import ru.dictation.factories.ChapterDtoFactory;
import ru.dictation.repositories.ChapterRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Transactional
public class ChapterController {

    private final ChapterDtoFactory chapterDtoFactory;

    private final ChapterRepository chapterRepository;

    private final Logger logger = LogManager.getLogger(ChapterController.class);

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secure/chapters/{chapter_id}")
    public ChapterDto getChapterById(@PathVariable(value = "chapter_id") Long id) {

        Optional<Chapter> chapter = chapterRepository.findById(id);

        return chapterDtoFactory.makeChapterDto(chapter.get());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/secure/chapters/")
    public List<ChapterDto> getAllChapters() {

        List<Chapter> chapters = chapterRepository.findAll();

        return chapters.stream()
                .map(chapterDtoFactory::makeChapterDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/training/")
    public List<ChapterDto> getAllChapter() {

        List<Chapter> chapters = chapterRepository.findAll();

        return chapters.stream()
                .map(chapterDtoFactory::makeChapterDto)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/secure/chapters/add")
    @ResponseBody
    public void addChapter(@RequestBody Chapter chapter) {
        Chapter saveChapter = new Chapter();
        saveChapter.setDescription(chapter.getDescription());

        logger.info("Admin add chapter: " + chapter.getDescription());

        chapterRepository.saveAndFlush(saveChapter);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/secure/chapters/edit/{chapter_id}")
    @ResponseBody
    public void editChapter(@RequestBody Chapter chapter,
                            @PathVariable(value = "chapter_id") Long id) {

        Optional<Chapter> saveChapter = chapterRepository.findById(id);

        saveChapter.get().setDescription(chapter.getDescription());

        logger.info("Admin edit chapter: " + chapter.getDescription());

        chapterRepository.saveAndFlush(saveChapter.get());
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/secure/chapters/delete/{chapter_id}")
//    public void deleteChapter(@PathVariable(value = "chapter_id") Long id) {
//
//
//    }
}
