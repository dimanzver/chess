package com.example.demo.modules.common.services.GameOffer;

import com.example.demo.models.GameOffer;
import com.example.demo.repositories.GameOfferRepository;
import org.springframework.stereotype.Service;

@Service
public class GameOfferServiceImpl implements GameOfferService {

    private final GameOfferRepository gameOfferRepository;

    public GameOfferServiceImpl(GameOfferRepository gameOfferRepository) {
        this.gameOfferRepository = gameOfferRepository;
    }

    @Override
    public GameOffer create(GameOffer gameOffer) {
        return this.gameOfferRepository.save(gameOffer);
    }
}
