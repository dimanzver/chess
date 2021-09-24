package com.example.demo.modules.chessLogic;

import com.example.demo.dto.Cell;
import com.example.demo.dto.MoveDto;
import com.example.demo.enums.Side;
import com.example.demo.modules.chessLogic.figures.Figure;
import com.example.demo.modules.chessLogic.figures.King;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class Checkmate {
    public static boolean check(Board board, Side side) {
        King king = findKing(board, side);
        String kingStringCell = king.getCell().toString();
        for(int x = 0; x <= 7; x++) {
            for(int y = 0; y <= 7; y++) {
                Figure figure = board.getFigureOnCell(new Cell(x, y));
                if(figure == null || figure.getSide() == side) continue;
                for(MoveDto move : figure.getAvailableMovesOnBoard(board)) {
                    if (Objects.equals(move.getCellTo().toString(), kingStringCell))
                        return true;
                }
            }
        }
        return false;
    }

    protected static King findKing(Board board, Side side) {
        for(int x = 0; x <= 7; x++) {
            for(int y = 0; y <= 7; y++) {
                Figure figure = board.getFigureOnCell(new Cell(x, y));
                if(figure instanceof King && figure.getSide() == side)
                    return (King) figure;
            }
        }
        throw new IllegalArgumentException("King not found");
    }
}
