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
@Table(name = "question_answer")
public class QuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    Long id;


    @NonNull
    String text;

    @NonNull
    boolean validaty;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    Question question;


}
