package com.example.demo.modules.chessLogic.figures;

import com.example.demo.dto.Cell;
import com.example.demo.dto.MoveDto;
import com.example.demo.enums.Side;
import com.example.demo.modules.chessLogic.Board;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure{
    public Pawn(Side side) {
        super(side);
    }

    @Override
    public List<MoveDto> getAvailableMovesOnBoard(Board board) {
        List<MoveDto> moves = new ArrayList<>();
        int direction = this.getSide() == Side.WHITE ? 1 : -1;
        int twoStepLine = this.getSide() == Side.WHITE ? 1 : 6;

        Cell nextCell = this.getCell().addY(direction);
        boolean nextCellAvailable = board.getFigureOnCell(nextCell) == null && nextCell.isOnBoard();
        if(nextCellAvailable)
            moves.add(new MoveDto(this, nextCell));

        // check two-step move
        Cell twoStepCell = this.getCell().addY(direction * 2);
        if(nextCellAvailable && board.getFigureOnCell(twoStepCell) == null && twoStepCell.isOnBoard())
            moves.add(new MoveDto(this, twoStepCell));

        // check available eat
        Cell leftEatCell = this.getCell().addX(-1).addY(direction);
        Figure leftEatFigure = board.getFigureOnCell(leftEatCell);
        if(leftEatFigure != null && leftEatFigure.getSide() != this.getSide())
            moves.add(new MoveDto(this, leftEatCell));

        Cell rightEatCell = this.getCell().addX(1).addY(direction);
        Figure rightEatFigure = board.getFigureOnCell(rightEatCell);
        if(rightEatFigure != null && rightEatFigure.getSide() != this.getSide())
            moves.add(new MoveDto(this, rightEatCell));

        // check taking on pass
        Figure lastPawnTwoMoved = board.getLastPawnTwoMoved();
        if(
            lastPawnTwoMoved != null &&
            lastPawnTwoMoved.getCell().getY() == this.getCell().getY() &&
            Math.abs(lastPawnTwoMoved.getCell().getX() - this.getCell().getX()) == 1
        ) {
            moves.add(new MoveDto(this, lastPawnTwoMoved.getCell().addY(direction)));
        }

        return moves;
    }

}
