package com.example.demo.modules.chessLogic.figures;

import com.example.demo.dto.Cell;
import com.example.demo.dto.MoveDto;
import com.example.demo.enums.Side;
import com.example.demo.modules.chessLogic.Board;

import java.util.List;

public abstract class Figure {

    private Side side;
    private Cell cell;


    public Figure(){}
    public Figure(Side side) {
        this.side = side;
    }

    public abstract List<MoveDto> getAvailableMovesOnBoard(Board board);

    public boolean mayEat(Figure figure) {
        return figure.getSide() != this.getSide();
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
