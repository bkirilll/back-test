package ru.dictation.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users_answer")
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Column(name = "accuracy(%)")
    int accuracy;

    @Column(name = "identifier")
    String identifier;

    Date dateOfCreated;

    @PrePersist
    void init() {
        Calendar cal = Calendar.getInstance();
        dateOfCreated = new java.sql.Date(cal.getTimeInMillis());
    }
}
