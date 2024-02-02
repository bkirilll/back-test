package ru.dictation.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "question_stats")
public class QuestionStat {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    String question;

    Long rightQuantity;

    Long allQuantity;

}
