package com.example.demo.models;

import com.example.demo.enums.JoinedColor;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "game_offers")
public class GameOffer {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "date_time", columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'+03:00'")
    LocalDateTime dateTime;

    @Column(name = "user_id")
    @JsonIgnore
    int userId;

    @Column(name = "joined_color")
    JoinedColor joinedColor;

    @Column(name = "game_time")
    Integer gameTime;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    User user;
}
