package com.example.demo.modules.chessLogic;

import com.example.demo.enums.Side;
import com.example.demo.modules.chessLogic.figures.*;

public class FenChar {

    public static char getForFigure(Figure figure) {
        char result;
        if(figure instanceof Bishop)
            result = 'b';
        else if(figure instanceof King)
            result = 'k';
        else if(figure instanceof Knight)
            result = 'n';
        else if(figure instanceof Pawn)
            result = 'p';
        else if(figure instanceof Queen)
            result = 'q';
        else if(figure instanceof Rook)
            result = 'r';
        else
            throw new IllegalArgumentException("Unknown figure type");
        return figure.getSide() == Side.BLACK ? result : Character.toUpperCase(result);
    }

    public static Figure charToFigure(char fenChar) {
        char fenCharNormal = Character.toLowerCase(fenChar);
        switch (fenCharNormal) {
            case 'b':
                return new Bishop(fenChar == 'b' ? Side.BLACK : Side.WHITE);
            case 'k':
                return new King(fenChar == 'k' ? Side.BLACK : Side.WHITE);
            case 'n':
                return new Knight(fenChar == 'n' ? Side.BLACK : Side.WHITE);
            case 'p':
                return new Pawn(fenChar == 'p' ? Side.BLACK : Side.WHITE);
            case 'q':
                return new Queen(fenChar == 'q' ? Side.BLACK : Side.WHITE);
            case 'r':
                return new Rook(fenChar == 'r' ? Side.BLACK : Side.WHITE);
        }
        throw new IllegalArgumentException("Unknown figure " + fenChar);
    }
}
