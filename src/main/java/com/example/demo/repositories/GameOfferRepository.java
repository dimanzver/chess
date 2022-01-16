package com.example.demo.repositories;

import com.example.demo.models.GameOffer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("gameOfferRepository")
public interface GameOfferRepository extends CrudRepository<GameOffer, Integer> {
}
