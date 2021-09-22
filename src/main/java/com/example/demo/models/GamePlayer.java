package com.example.demo.models;

import com.example.demo.enums.Side;

import javax.persistence.*;

@Entity
@Table(name = "game_players")
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "game_id")
    private int gameId;

    @Column(name = "user_id")
    private int userId;

    private Side side;

    @Column(name = "time_remains")
    private int timeRemains;




    public int getId() {
        return id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public int getTimeRemains() {
        return timeRemains;
    }

    public void setTimeRemains(int timeRemains) {
        this.timeRemains = timeRemains;
    }
}
