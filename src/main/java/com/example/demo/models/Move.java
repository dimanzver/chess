package com.example.demo.models;

import javax.persistence.*;

@Entity
@Table(name = "moves")
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "game_player_id")
    private int gamePlayerId;

    private int number;

    @Column(name = "fen_start")
    private String fenStart;

    private String move;



    public int getId() {
        return id;
    }

    public int getGamePlayerId() {
        return gamePlayerId;
    }

    public void setGamePlayerId(int gamePlayerId) {
        this.gamePlayerId = gamePlayerId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFenStart() {
        return fenStart;
    }

    public void setFenStart(String fenStart) {
        this.fenStart = fenStart;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
