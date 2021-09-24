package com.example.demo.modules.chessLogic;

import com.example.demo.dto.Cell;
import com.example.demo.dto.MoveDto;
import com.example.demo.enums.Side;
import com.example.demo.modules.chessLogic.figures.Figure;
import com.example.demo.modules.chessLogic.figures.King;
import com.example.demo.modules.chessLogic.figures.Pawn;
import com.example.demo.modules.chessLogic.figures.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Moving {
    public static List<MoveDto> getMoves(Board board, Figure figure) {
        if(figure.getSide() != board.getNextMoveSide())
            return new ArrayList<>();

        List<MoveDto> moves = figure.getAvailableMovesOnBoard(board);
        return moves.stream().filter(move -> {
            Board boardAfterMove = doMoveHard(board, move);
            return !Checkmate.check(boardAfterMove, move.getFigure().getSide());
        }).collect(Collectors.toList());
    }

    public static boolean checkAvailable(Board board, MoveDto move) {
        String moveString = move.toString();
        for (MoveDto availableMove : getMoves(board, move.getFigure())) {
            if(Objects.equals(availableMove.toString(), moveString))
                return true;
        }
        return false;
    }

    public static Board doMove(Board boardFrom, MoveDto move) {
        if(!checkAvailable(boardFrom, move))
            return boardFrom;
        return doMoveHard(boardFrom, move);
    }

    protected static Board doMoveHard(Board boardFrom, MoveDto move) {
        Board board = boardFrom.clone();
        board.setNextMoveSide(move.getFigure().getSide() == Side.WHITE ? Side.BLACK : Side.WHITE);
        Pawn lastPawnTwoMoved = board.getLastPawnTwoMoved();
        Figure figureOnCell = board.getFigureOnCell(move.getCellTo());

        // Fill returnable moves count and all moves count
        if(figureOnCell != null || move.getFigure() instanceof Pawn) {
            board.setReturnableMovesCount(0);
        }else if(move.getFigure().getSide() == Side.BLACK){
            board.setReturnableMovesCount(board.getReturnableMovesCount() + 1);
        }
        if(move.getFigure().getSide() == Side.BLACK)
            board.setMoveNumber(board.getMoveNumber() + 1);

        // fill last pawn two moved
        if(move.getFigure() instanceof Pawn &&
                Math.abs(move.getFigure().getCell().getY() - move.getCellTo().getY()) == 2
        ) {
            board.setLastPawnTwoMoved((Pawn) move.getFigure());
        }else{
            board.setLastPawnTwoMoved(null);
        }

        // check is castle
        if (move.getFigure() instanceof King &&
                move.getFigure().getCell().getX() == 4 &&
                (move.getCellTo().getX() == 6 || move.getCellTo().getX() == 2)
        )
            return doCastle(board, move);

        // check taking on pass
        if(lastPawnTwoMoved != null &&
                lastPawnTwoMoved.getSide() != move.getFigure().getSide() &&
                move.getFigure() instanceof Pawn) {
            Cell passedCell = new Cell(lastPawnTwoMoved.getCell().getX(), lastPawnTwoMoved.getSide() == Side.WHITE ? 2 : 5);
            if(Objects.equals(passedCell.toString(), move.getCellTo().toString())) {
                board.removeFigureOnCell(lastPawnTwoMoved.getCell());
                board.moveFigureToCell(move.getFigure(), move.getCellTo());
                return board;
            }
        }

        board.moveFigureToCell(move.getFigure(), move.getCellTo());
        return board;
    }

    protected static Board doCastle(Board board, MoveDto move) {
        board.moveFigureToCell(move.getFigure(), move.getCellTo());
        int rookXChange = move.getCellTo().getX() == 6 ? -2 : 3;
        Rook rook = (Rook) board.getFigureOnCell(new Cell(rookXChange > 0 ? 0 : 7, move.getCellTo().getY()));
        board.moveFigureToCell(rook, rook.getCell().addX(rookXChange));

        if (rook.getSide() == Side.WHITE) {
            board.setWhiteLongCastling(false);
            board.setWhiteShortCastling(false);
        } else {
            board.setBlackLongCastling(false);
            board.setBlackShortCastling(false);
        }

        return board;
    }

}
