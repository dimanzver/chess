package com.example.demo.modules.common.controllers;

import com.example.demo.models.Game;
import com.example.demo.models.GameOffer;
import com.example.demo.models.User;
import com.example.demo.modules.common.requests.GameOfferRequest;
import com.example.demo.modules.common.services.Auth.WebAuth;
import com.example.demo.modules.common.services.GameOffer.GameOfferService;
import com.example.demo.repositories.GameOfferRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class GameOfferController {

    private final GameOfferService gameOfferService;
    private final GameOfferRepository gameOfferRepository;
    private final WebAuth webAuth;

    public GameOfferController(GameOfferService gameOfferService, GameOfferRepository gameOfferRepository, WebAuth webAuth) {
        this.gameOfferService = gameOfferService;
        this.gameOfferRepository = gameOfferRepository;
        this.webAuth = webAuth;
    }

    @PostMapping("/offer")
    public GameOffer create(@RequestBody GameOfferRequest request){
        GameOffer gameOffer = new GameOffer();
        gameOffer.setDateTime(LocalDateTime.now());
        gameOffer.setUserId(this.webAuth.authenticate().getId());
        gameOffer.setJoinedColor(request.getJoinedColor());
        gameOffer.setGameTime(request.getGameTime());
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

    @PostMapping("/offer/join/{id}")
    public ResponseEntity<Game> join(@PathVariable int id) {
        Optional<GameOffer> gameOffer = gameOfferRepository.findById(id);
        if(!gameOffer.isPresent())
            return ResponseEntity.notFound().build();

        User user = webAuth.authenticate();
        Game game = gameOfferService.join(gameOffer.get(), user);
        return ResponseEntity.ok().body(game);
    }
}
