package com.company;

/***
 * It is the Destination cell. They can be A,B,C,D in our maze.
 */
public class Destination {
    private char destinationName;
    private int indexX;
    private int indexY;

    public Destination(char destinationName, int indexX, int indexY) {
        this.destinationName = destinationName;
        this.indexX = indexX;
        this.indexY = indexY;
    }

    public char getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(char destinationName) {
        this.destinationName = destinationName;
    }

    public int getIndexX() {
        return indexX;
    }

    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }
}
