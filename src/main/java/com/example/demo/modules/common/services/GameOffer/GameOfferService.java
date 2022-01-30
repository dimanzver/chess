package com.example.demo.modules.common.services.GameOffer;

import com.example.demo.models.Game;
import com.example.demo.models.GameOffer;
import com.example.demo.models.User;

public interface GameOfferService {

    GameOffer create(GameOffer gameOffer);

    Game join(GameOffer gameOffer, User user);

}
