package com.example.demo.modules.chessLogic;

import com.example.demo.dto.Cell;
import com.example.demo.modules.chessLogic.figures.Figure;

public class Board {
    private Figure[][] figures = new Figure[8][8];

    public Figure getFigureOnCell(Cell cell) {
        if(!cell.isOnBoard()) return null;
        return figures[cell.getX()][cell.getY()];
    }

    public Figure[][] getFigures() {
        return figures;
    }

    public void setFigures(Figure[][] figures) {
        this.figures = figures;
    }
}
