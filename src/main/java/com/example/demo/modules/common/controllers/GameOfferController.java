package com.example.demo.modules.common.controllers;

import com.example.demo.models.GameOffer;
import com.example.demo.modules.common.services.GameOffer.GameOfferService;
import com.example.demo.repositories.GameOfferRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class GameOfferController {

    private final GameOfferService gameOfferService;
    private final GameOfferRepository gameOfferRepository;

    public GameOfferController(GameOfferService gameOfferService, GameOfferRepository gameOfferRepository) {
        this.gameOfferService = gameOfferService;
        this.gameOfferRepository = gameOfferRepository;
    }

    @PostMapping("/offer")
    public GameOffer create(){
        GameOffer gameOffer = new GameOffer();
        gameOffer.setDateTime(LocalDateTime.now());
        return gameOfferService.create(gameOffer);
    }

    @GetMapping("/offers")
    public List<GameOffer> getAll() {
        return (List<GameOffer>) gameOfferRepository.findAll();
    }

    @PostMapping("/offer/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable int id) {
        Optional<GameOffer> gameOffer = gameOfferRepository.findById(id);
        if(!gameOffer.isPresent())
            return ResponseEntity.notFound().build();
        gameOfferRepository.delete(gameOffer.get());
        return ResponseEntity.ok().build();
    }
}
