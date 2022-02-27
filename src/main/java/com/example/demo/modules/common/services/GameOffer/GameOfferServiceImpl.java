package com.example.demo.modules.common.services.GameOffer;

import com.example.demo.enums.GameStatus;
import com.example.demo.enums.Side;
import com.example.demo.models.Game;
import com.example.demo.models.GameOffer;
import com.example.demo.models.GamePlayer;
import com.example.demo.models.User;
import com.example.demo.repositories.GameOfferRepository;
import com.example.demo.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class GameOfferServiceImpl implements GameOfferService {

    private final GameOfferRepository gameOfferRepository;
    private final GameRepository gameRepository;

    public GameOfferServiceImpl(GameOfferRepository gameOfferRepository, GameRepository gameRepository) {
        this.gameOfferRepository = gameOfferRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public GameOffer create(GameOffer gameOffer) {
        return this.gameOfferRepository.save(gameOffer);
    }

    @Override
    public Game join(GameOffer gameOffer, User user) {
        Game game = new Game();
        game.setStatus(GameStatus.PLAYING);
        game.setDateTimeStart(LocalDateTime.now());

        GamePlayer firstPlayer = new GamePlayer();
        firstPlayer.setUserId(gameOffer.getUserId());
        firstPlayer.setSide(Side.WHITE); //TODO: random/from game offer
        firstPlayer.setTimeRemains(gameOffer.getGameTime());

        GamePlayer secondPlayer = new GamePlayer();
        secondPlayer.setUserId(user.getId());
        secondPlayer.setSide(Side.BLACK);
        secondPlayer.setTimeRemains(gameOffer.getGameTime());

        game.setPlayers(Arrays.asList(firstPlayer, secondPlayer));
        gameOfferRepository.delete(gameOffer);

        return gameRepository.save(game);
    }
}
