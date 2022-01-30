package com.example.demo.models;

import com.example.demo.enums.Side;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "game_players")
public class GamePlayer {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "game_id")
    int gameId;

    @Column(name = "user_id")
    int userId;

    Side side;

    @Column(name = "time_remains")
    Integer timeRemains;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="game_id", nullable = false, insertable = false, updatable = false)
    private Game game;
}
