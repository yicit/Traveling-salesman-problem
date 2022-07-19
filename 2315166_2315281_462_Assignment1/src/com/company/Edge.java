package com.company;

/**
 * Edge class is edge of the graph. It is undirected graph's edge. IT hold the destinations as a src1 and src2 and its weigth
 */
public class Edge {
    private Destination src1;
    private Destination src2;
    private float weight;

    public Edge(Destination src1, Destination src2, float weight) {
        this.src1 = src1;
        this.src2 = src2;
        this.weight = weight;
    }

    public Destination getSrc1() {
        return src1;
    }

    public void setSrc1(Destination src1) {
        this.src1 = src1;
    }

    public Destination getSrc2() {
        return src2;
    }

    public void setSrc2(Destination src2) {
        this.src2 = src2;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
}
