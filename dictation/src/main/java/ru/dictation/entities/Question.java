package ru.dictation.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    Long id;

    @NonNull
    String text;

    @NonNull
    String type;

    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    List<QuestionAnswer> answerList = new ArrayList<>();

}
