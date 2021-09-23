package com.example.demo.modules.chessLogic.figures;

import com.example.demo.dto.Cell;
import com.example.demo.dto.MoveDto;
import com.example.demo.enums.Side;
import com.example.demo.modules.chessLogic.Board;

import java.util.ArrayList;
import java.util.List;

public class King extends Figure{
    public King(Side side) {
        super(side);
    }

    @Override
    public List<MoveDto> getAvailableMovesOnBoard(Board board) {
        List<MoveDto> moves = new ArrayList<>();

        Integer[][] directions = new Integer[][]{
                new Integer[]{1, 1},
                new Integer[]{-1, 1},
                new Integer[]{1, -1},
                new Integer[]{-1, -1},
                new Integer[]{1, 0},
                new Integer[]{-1, 0},
                new Integer[]{0, -1},
                new Integer[]{0, 1},
        };

        // Walk on each direction
        for(Integer[] direction : directions) {
            Cell currentCell = new Cell(this.getCell().getX() + direction[0], this.getCell().getY() + direction[1]);
            Figure figureOnCell = board.getFigureOnCell(currentCell);
            if(figureOnCell == null || this.mayEat(figureOnCell))
                moves.add(new MoveDto(this, currentCell));
        }

        return moves;
    }

}
