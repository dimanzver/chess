package com.example.demo.modules.common.requests;

import com.example.demo.enums.JoinedColor;
import lombok.Data;

@Data
public class GameOfferRequest {
    JoinedColor joinedColor = JoinedColor.RANDOM;
    Integer gameTime;
}
