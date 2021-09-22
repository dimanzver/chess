package com.example.demo.models;

import com.example.demo.enums.GameResult;
import com.example.demo.enums.GameStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_time_start", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTimeStart;

    @Column(name = "date_time_end", columnDefinition = "TIMESTAMP")
    private LocalDateTime dateTimeEnd;

    private GameStatus status;

    private GameResult result;


    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }

    public int getId() {
        return id;
    }
}
