package com.example.demo.dto;

import com.example.demo.modules.chessLogic.FenChar;
import com.example.demo.modules.chessLogic.figures.Figure;

public class MoveDto {
    private Figure figure;
    private Cell cellTo;

    public MoveDto(Figure figure, Cell cellTo) {
        this.figure = figure;
        this.cellTo = cellTo;
    }

    @Override
    public String toString() {
        return FenChar.getForFigure(this.getFigure()) + this.getCellTo().toString();
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Cell getCellTo() {
        return cellTo;
    }

    public void setCellTo(Cell cellTo) {
        this.cellTo = cellTo;
    }
}
