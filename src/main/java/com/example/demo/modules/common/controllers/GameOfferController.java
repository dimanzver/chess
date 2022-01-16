package com.example.demo.modules.common.controllers;

import com.example.demo.models.GameOffer;
import com.example.demo.modules.common.services.GameOffer.GameOfferService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class GameOfferController {

    private final GameOfferService gameOfferService;

    public GameOfferController(GameOfferService gameOfferService) {
        this.gameOfferService = gameOfferService;
    }

    @PostMapping("/offer")
    public GameOffer create(){
        GameOffer gameOffer = new GameOffer();
        gameOffer.setDateTime(LocalDateTime.now());
        return gameOfferService.create(gameOffer);
    }

}
