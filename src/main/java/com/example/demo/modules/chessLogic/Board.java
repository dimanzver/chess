package com.example.demo.modules.chessLogic;

import com.example.demo.dto.Cell;
import com.example.demo.enums.Side;
import com.example.demo.modules.chessLogic.figures.Figure;
import com.example.demo.modules.chessLogic.figures.Pawn;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;

public class Board implements Cloneable {
    private Figure[][] figures = new Figure[8][8];
    private Side nextMoveSide = Side.WHITE;
    private boolean whiteShortCastling = true;
    private boolean whiteLongCastling = true;
    private boolean blackShortCastling = true;
    private boolean blackLongCastling = true;
    private Pawn lastPawnTwoMoved;
    private int returnableMovesCount = 0;
    private int moveNumber = 1;

    private Board(){}

    public Figure getFigureOnCell(Cell cell) {
        if(!cell.isOnBoard()) return null;
        return figures[cell.getX()][cell.getY()];
    }

    public void moveFigureToCell(Figure figure, Cell cellTo) {
        this.removeFigureOnCell(figure.getCell());
        figure.setCell(cellTo);
        figures[cellTo.getX()][cellTo.getY()] = figure;
    }

    public void removeFigureOnCell(Cell cell) {
        if(cell.isOnBoard())
            this.figures[cell.getX()][cell.getY()] = null;
    }

    /**
     * Generate fen from board position
     *
     * @return String
     */

    public String toFen() {
        StringBuilder fen = new StringBuilder();

        for(int y = 7; y >= 0; y--) {
            StringBuilder rowFen = new StringBuilder();
            int freeCount = 0;

            for(int x = 0; x <= 7; x++) {
                Figure figure = this.getFigureOnCell(new Cell(x, y));
                if(figure == null) {
                    freeCount++;
                    if(x == 7) rowFen.append(freeCount);
                    continue;
                }

                if(freeCount > 0) {
                    rowFen.append(freeCount);
                    freeCount = 0;
                }
                rowFen.append(FenChar.getForFigure(figure));
            }
            fen.append(y == 0 ? rowFen : rowFen + "/");
        }
        fen.append(this.getNextMoveSide() == Side.WHITE ? " w " : " b ");
        if(this.isWhiteShortCastling())
            fen.append('K');
        if(this.isWhiteLongCastling())
            fen.append('Q');
        if(this.isBlackShortCastling())
            fen.append('k');
        if(this.isBlackLongCastling())
            fen.append('q');
        fen.append(' ');

        Figure pawnTwoMoved = this.getLastPawnTwoMoved();
        if(pawnTwoMoved == null) {
            fen.append('-');
        }else {
            int directionToSkipped = pawnTwoMoved.getSide() == Side.WHITE ? -1 : 1;
            fen.append(this.getLastPawnTwoMoved().getCell().addY(directionToSkipped));
        }
        fen.append(" ").append(getReturnableMovesCount()).append(" ").append(getMoveNumber());

        return fen.toString();
    }

    public static Board fromFen(String fen) throws ParseException {
        Board board = new Board();
        String[] fenParts = fen.split(" ");
        String[] figuresRows = fenParts[0].split("/");

        // fill figures
        Figure[][] figures = new Figure[8][8];
        int y = 7;
        for (String figuresRow : figuresRows) {
            int x = 0;
            for(int i = 0; i < figuresRow.length(); i++) {
                char c = figuresRow.charAt(i);
                if(Character.isDigit(c)) {
                    x += Character.getNumericValue(c);
                    continue;
                }

                Figure figure = FenChar.charToFigure(c);
                figure.setCell(new Cell(x, y));
                figures[x][y] = figure;
                x++;
            }

            y--;
        }
        board.setFigures(figures);

        board.setNextMoveSide(Objects.equals(fenParts[1], "w") ? Side.WHITE : Side.BLACK);
        board.setWhiteShortCastling(fenParts[2].contains("K"));
        board.setWhiteLongCastling(fenParts[2].contains("Q"));
        board.setBlackShortCastling(fenParts[2].contains("k"));
        board.setBlackLongCastling(fenParts[2].contains("q"));
        board.setReturnableMovesCount(NumberFormat.getInstance().parse(fenParts[4]).intValue());
        board.setMoveNumber(NumberFormat.getIntegerInstance().parse(fenParts[5]).intValue());

        Cell lastTwoPawnMoveCell = Cell.fromString(fenParts[3]);
        if(lastTwoPawnMoveCell != null) {
            lastTwoPawnMoveCell.setY(lastTwoPawnMoveCell.getY() == 6 ? 5 : 4);
            board.setLastPawnTwoMoved((Pawn) board.getFigureOnCell(lastTwoPawnMoveCell));
        }

        return board;
    }


    public Figure[][] getFigures() {
        return figures;
    }

    public void setFigures(Figure[][] figures) {
        this.figures = figures;
    }

    public Side getNextMoveSide() {
        return nextMoveSide;
    }

    public void setNextMoveSide(Side nextMoveSide) {
        this.nextMoveSide = nextMoveSide;
    }

    public boolean isWhiteShortCastling() {
        return whiteShortCastling;
    }

    public void setWhiteShortCastling(boolean whiteShortCastling) {
        this.whiteShortCastling = whiteShortCastling;
    }

    public boolean isWhiteLongCastling() {
        return whiteLongCastling;
    }

    public void setWhiteLongCastling(boolean whiteLongCastling) {
        this.whiteLongCastling = whiteLongCastling;
    }

    public boolean isBlackShortCastling() {
        return blackShortCastling;
    }

    public void setBlackShortCastling(boolean blackShortCastling) {
        this.blackShortCastling = blackShortCastling;
    }

    public boolean isBlackLongCastling() {
        return blackLongCastling;
    }

    public void setBlackLongCastling(boolean blackLongCastling) {
        this.blackLongCastling = blackLongCastling;
    }

    public int getReturnableMovesCount() {
        return returnableMovesCount;
    }

    public void setReturnableMovesCount(int returnableMovesCount) {
        this.returnableMovesCount = returnableMovesCount;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    public Pawn getLastPawnTwoMoved() {
        return lastPawnTwoMoved;
    }

    public void setLastPawnTwoMoved(Pawn lastPawnTwoMoved) {
        this.lastPawnTwoMoved = lastPawnTwoMoved;
    }

    @Override
    public Board clone() {
        try {
            return (Board) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
