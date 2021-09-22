package com.example.demo.modules.chessLogic.figures;

import com.example.demo.dto.Cell;
import com.example.demo.dto.MoveDto;
import com.example.demo.modules.chessLogic.Board;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Figure{
    @Override
    public List<MoveDto> getAvailableMovesOnBoard(Board board) {
        List<MoveDto> moves = new ArrayList<>();
        Cell[] cells = new Cell[]{
            new Cell(this.getCell().getX() + 1, getCell().getY() + 2),
            new Cell(this.getCell().getX() - 1, getCell().getY() + 2),
            new Cell(this.getCell().getX() + 1, getCell().getY() - 2),
            new Cell(this.getCell().getX() - 1, getCell().getY() - 2),
            new Cell(this.getCell().getX() + 2, getCell().getY() + 1),
            new Cell(this.getCell().getX() - 2, getCell().getY() + 1),
            new Cell(this.getCell().getX() + 2, getCell().getY() - 1),
            new Cell(this.getCell().getX() - 2, getCell().getY() - 1),
        };

        for(Cell cell : cells) {
            if(!cell.isOnBoard()) continue;
            Figure figureOnCell = board.getFigureOnCell(cell);
            if(figureOnCell == null || this.mayEat(figureOnCell))
                moves.add(new MoveDto(this, cell));
        }

        return moves;
    }
}
