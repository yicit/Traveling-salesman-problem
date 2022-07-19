package com.company;

/**
 * Cell reperent each square in the maze and it hold its information
 */
public class Cell {
    private float h_distance;
    private char value;
    private float pathCost;
    private int xIndex;
    private int yIndex;
    private int flag;
    private boolean dontGo;
    private float totalCost;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Cell(char value, int xIndex, int yIndex) {
        this.value = value;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.h_distance=0;
        this.pathCost=0;
        this.flag=0;
        this.totalCost=0;
        this.dontGo=false;
    }

    public int getxIndex() {
        return xIndex;
    }

    public void setxIndex(int xIndex) {
        this.xIndex = xIndex;
    }

    public boolean getDontGo() {
        return dontGo;
    }

    public void setDontGo(boolean dontGo) {
        this.dontGo = dontGo;
    }

    public int getyIndex() {
        return yIndex;
    }

    public void setyIndex(int yIndex) {
        this.yIndex = yIndex;
    }

    public float getH_distance() {
        return h_distance;
    }

    public void setH_distance(float h_distance) {
        this.h_distance = h_distance;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public float getPathCost() {
        return pathCost;
    }

    public void setPathCost(float pathCost) {
        this.pathCost = pathCost;
    }

    public void incPathCost(){
        this.pathCost=this.pathCost+1;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }
    public void calculateTotalCost(){
        this.totalCost= this.pathCost+this.h_distance;
    }

}
