package com.example.demo.modules.chessLogic.figures;

import com.example.demo.dto.Cell;
import com.example.demo.dto.MoveDto;
import com.example.demo.enums.Side;
import com.example.demo.modules.chessLogic.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class King extends Figure{
    public King(Side side) {
        super(side);
    }

    @Override
    public List<MoveDto> getAvailableMovesOnBoard(Board board) {
        List<MoveDto> nearMoves = this.getNearMoves(board);
        List<MoveDto> castleMoves = this.getCastleMoves(board);
        return Stream.concat(nearMoves.stream(), castleMoves.stream()).collect(Collectors.toList());
    }

    protected List<MoveDto> getNearMoves(Board board) {
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
            if(!currentCell.isOnBoard()) continue;
            Figure figureOnCell = board.getFigureOnCell(currentCell);
            if(figureOnCell == null || this.mayEat(figureOnCell))
                moves.add(new MoveDto(this, currentCell));
        }

        return moves;
    }

    protected List<MoveDto> getCastleMoves(Board board) {
        List<MoveDto> moves = new ArrayList<>();

        if (this.getSide() == Side.WHITE) {
            if(
                board.isWhiteLongCastling() &&
                board.getFigureOnCell(new Cell(1, 0)) == null &&
                board.getFigureOnCell(new Cell(2, 0)) == null &&
                board.getFigureOnCell(new Cell(3, 0)) == null
            ) {
                moves.add(new MoveDto(this, new Cell(2, 0)));
            }

            if(
                board.isWhiteShortCastling() &&
                board.getFigureOnCell(new Cell(5, 0)) == null &&
                board.getFigureOnCell(new Cell(6, 0)) == null
            ) {
                moves.add(new MoveDto(this, new Cell(6, 0)));
            }
        }else{
            if(
                board.isBlackLongCastling() &&
                board.getFigureOnCell(new Cell(1, 7)) == null &&
                board.getFigureOnCell(new Cell(2, 7)) == null &&
                board.getFigureOnCell(new Cell(3, 7)) == null
            ) {
                moves.add(new MoveDto(this, new Cell(2, 7)));
            }

            if(
                board.isBlackShortCastling() &&
                board.getFigureOnCell(new Cell(5, 7)) == null &&
                board.getFigureOnCell(new Cell(6, 7)) == null
            ) {
                moves.add(new MoveDto(this, new Cell(6, 7)));
            }
        }

        return moves;
    }

}
