package com.example.demo.dto;

public class Cell {
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public boolean isOnBoard() {
        return this.x >= 0 && this.y >= 0 && this.x <= 7 && this.y <= 7;
    }

    public Cell addX(int x) {
        return new Cell(this.getX() + x, this.getY());
    }

    public Cell addY(int y) {
        return new Cell(this.getX(), this.getY() + y);
    }

    @Override
    public String toString() {
        String columns = "abcdefgh";
        return "" + columns.charAt(this.getX()) + (this.getY() + 1);
    }

    public static Cell fromString(String cellString) {
        if(cellString.length() != 2) return null;
        String columns = "abcdefgh";
        int x = columns.indexOf(cellString.charAt(0));
        return new Cell(x, Character.getNumericValue(cellString.charAt(1)));
    }
}
