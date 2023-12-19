package ru.dictation.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users_answer")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    Long id;

    @Column(name = "username")
    String username;

    @Column(name = "age")
    int age;

    @Column(name = "gender")
    String gender;

    @Column(name = "region")
    String region;

    @Column(name = "city")
    String city;

    @Column(name = "email")
    String mail;

    @Column(name = "accuracy")
    int accuracy;
}
