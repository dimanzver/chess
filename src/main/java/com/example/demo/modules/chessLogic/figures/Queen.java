package com.example.demo.modules.chessLogic.figures;

import com.example.demo.dto.Cell;
import com.example.demo.dto.MoveDto;
import com.example.demo.enums.Side;
import com.example.demo.modules.chessLogic.Board;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Figure {
    public Queen(Side side) {
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
            Cell currentCell = this.getCell();
            while (true) {
                currentCell = new Cell(currentCell.getX() + direction[0], currentCell.getY() + direction[1]);
                if(!currentCell.isOnBoard())
                    break;
                Figure figureOnCell = board.getFigureOnCell(currentCell);
                if(figureOnCell != null) {
                    if(this.mayEat(figureOnCell)) {
                        moves.add(new MoveDto(this, currentCell));
                    }
                    break;
                }

                moves.add(new MoveDto(this, currentCell));
            }
        }

        return moves;
    }

}
