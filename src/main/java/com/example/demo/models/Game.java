package com.example.demo.models;

import com.example.demo.enums.GameResult;
import com.example.demo.enums.GameStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "games")
public class Game {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "date_time_start", columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'+03:00'")
    LocalDateTime dateTimeStart;

    @Column(name = "date_time_end", columnDefinition = "TIMESTAMP")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'+03:00'")
    LocalDateTime dateTimeEnd;

    GameStatus status;
    GameResult result;

    @OneToMany(mappedBy = "game")
    List<GamePlayer> players;
}
