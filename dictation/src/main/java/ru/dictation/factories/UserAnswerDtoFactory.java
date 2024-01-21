package ru.dictation.factories;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.dictation.dto.UserAnswerDto;
import ru.dictation.entities.UserAnswer;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAnswerDtoFactory {

    public UserAnswerDto makeUserDto(UserAnswer userAnswer) {

        return UserAnswerDto.builder()
                .id(userAnswer.getId())
                .username(userAnswer.getUsername())
                .age(userAnswer.getAge())
                .gender(userAnswer.getGender())
                .region(userAnswer.getRegion())
                .city(userAnswer.getCity())
                .mail(userAnswer.getMail())
                .accuracy(userAnswer.getAccuracy())
                .identifier(userAnswer.getIdentifier())
                .build();
    }
}
