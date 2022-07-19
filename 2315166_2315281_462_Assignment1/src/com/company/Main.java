/**
 * Ufuk Bozkurt 2315166
 * Sabahattin Yigit Günaştı 2315281
 */
package com.company;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static java.lang.Math.abs;
import com.company.Cell;
import com.company.Destination;
import com.company.Edge;

public class Main {

    public static List<Cell> cell_List = new ArrayList<Cell>();
    public static List<Destination> destination_List = new ArrayList<Destination>();
    public static List<Edge> graph = new ArrayList<Edge>();
    public static int x = 0;
    public static int y = 0;

    public static void main(String[] args) throws IOException {
        //Reading input files
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("input.txt"), Charset.forName("UTF-8")));

        int c;
        while ((c = reader.read()) != -1) {

            char character = (char) c;
            //Finding destinations such as A to D
            if (character != ' ' && character != '*' && character != '\n' && character != 13) {

                Destination myDestination = new Destination(character, x, y);
                destination_List.add(myDestination);

            }
            //Sorting the destination list according to alphabetical order
            sort();

            //Check condition and adding cell in to the list
            if (character != '\n' && character != 13) {

                Cell myCell = new Cell(character, x, y);
                if (myCell.getValue()=='*')
                    myCell.setDontGo(true);
                cell_List.add(myCell);

            }

            //Finding every cell's coordinate
            if (character == '\n') {
                x++;
                y = 0;

            }

            if (character != 13 && character != '\n') {
                y++;
            }


        }

        //Doing A star search for every pair of destination such as A-B, A-C , A-D etc for finding the shortest path
        for (int i = 0; i < destination_List.size()-1; i++) {
            for (int j = i +1; j < destination_List.size(); j++) {
                AStarSearch(destination_List.get(i), destination_List.get(j));
            }
        }

        //Getting input from user and doing the process
        Scanner input = new Scanner(System.in);

        System.out.println("-------------------------");
        System.out.println(" Which task to perform: there are two possible values: 1. Construct a shortest path graph and 2. TSP Solution with BSF and UCS. 3. Exit");
        int option= input.nextInt();
        while (option!=3) {
            if (option == 1) {
                System.out.println("-------------------------");
                System.out.println("Each Cost Between Nodes");
                for (int i = 0; i < graph.size(); i++) {
                    System.out.println(graph.get(i).getSrc1().getDestinationName() + "," + graph.get(i).getSrc2().getDestinationName() + "," + graph.get(i).getWeight());
                }
            } else if (option == 2) {
                long startTime = System.nanoTime();
                System.out.println("Breath First Search");
                float costBFS, costUCS;
                double timeBFS, timeUCS;
                costBFS = getCost(BreathFirst());
                System.out.println("BFS Total Cost=" + costBFS);
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
                timeBFS = duration / 1000000.0;

                System.out.println("-------------------------");
                System.out.println("Uniform Cost Search");
                startTime = System.nanoTime();

                costUCS = getCost(UCS());
                System.out.println("UCS Total Cost=" + costUCS);
                endTime = System.nanoTime();
                duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
                timeUCS = duration / 1000000.0;
                System.out.println("-------------------------");
                System.out.println("Statistic");
                System.out.println("Nodes      Time      Cost");
                System.out.println("BFS      " + timeBFS + "ms" + "    " + costBFS);
                System.out.println("UCS      " + timeUCS + "ms" + "    " + costUCS);
            } else if (option == 3) {
                System.exit(0);
            } else {
                System.out.println("It is not an option");
            }
            System.out.println(" Which task to perform: there are two possible values: 1. Construct a shortest path graph and 2. TSP Solution with BSF and UCS. 3. Exit");
            option= input.nextInt();
        }

    }

    /**
     * This function getting the path as a string and produce the path cost
     * @param path is a string
     * @return the cost
     */
    public static float getCost(String path){
        float totalCost =0;
        for (int i = 0; i < path.length()-1; i++) {
            totalCost = totalCost + findOneEdge(path.charAt(i),path.charAt(i+1));
        }
        return totalCost;
    }

    /**
     * This function find the edge according to input chars for example c1='A' c2='B' and it find the edge that contain 'A' and 'B' into the graph
     * @param c1 is for edge's src1 destination attribute
     * @param c2 is for edge's src2 destination attribute
     * @return
     */
    public static float findOneEdge(char c1, char c2){
        for (int i = 0; i < graph.size(); i++) {
            if ((c1 == graph.get(i).getSrc1().getDestinationName() && c2 == graph.get(i).getSrc2().getDestinationName()) || (c2 == graph.get(i).getSrc1().getDestinationName() && c1 == graph.get(i).getSrc2().getDestinationName())){
                return graph.get(i).getWeight();
            }
        }
        return 0;
    }

    /**
     * Calculating heuristic using manhatten distance
     * @param cell1 starting cell
     * @param cell2 destination cell
     * @return heuristic value
     */
    public static float heuristic(Cell cell1, Cell cell2) {
        float heuristicValue = abs(cell1.getxIndex() - cell2.getxIndex()) + abs(cell1.getyIndex() - cell2.getyIndex());
        return heuristicValue;

    }

    /**
     * Finding path of breath first
     * @return path of breath first
     */
    public static String BreathFirst(){

        List<String> queue = new ArrayList<String>();
        Edge currentEdge= graph.get(0);
        String mypath="";
        queue.add("" +currentEdge.getSrc1().getDestinationName());
        // It control the path is okay
        while (checkCondition(mypath)){
            //we add the path in to queue with looking alphabetical order
            mypath = queue.get(0);
            queue.remove(0);
            List <Edge>  edges= findEdges(mypath);
            for (int j = 0; j < edges.size(); j++) {
                queue.add(mypath + edges.get(j).getSrc1().getDestinationName());
                queue.add(mypath + edges.get(j).getSrc2().getDestinationName());
            }

        }
        System.out.println(""+ mypath);
        return mypath;


    }

    /**
     * Uniform Cost Search
     * Using the graph get find shortest path between nodes and create the string
     *
     * @return the path as a string
     */
    public static String UCS(){

        float min=graph.get(0).getWeight();
        String path="";
        ArrayList<Character> oldnames= new ArrayList<Character>();
        char minName=graph.get(0).getSrc1().getDestinationName();

        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < graph.size()-1; i++) {
                //check the min path between the nodes
                if(minName == graph.get(i+1).getSrc1().getDestinationName() && !oldnames.contains(graph.get(i).getSrc2().getDestinationName())){

                    oldnames.add(minName);

                    if(min > graph.get(i+1).getWeight()){
                        min=graph.get(i+1).getWeight() ;
                        minName= graph.get(i+1).getSrc2().getDestinationName();
                    }
                    path=path+""+minName;
                }

                else if (minName == graph.get(i).getSrc2().getDestinationName() && !oldnames.contains(graph.get(i).getSrc1().getDestinationName())){

                    oldnames.add(minName);
                    min = graph.get(i).getWeight();
                    minName= graph.get(i).getSrc1().getDestinationName();

                    if(min > graph.get(i+1).getWeight()){
                        min=graph.get(i+1).getWeight() ;
                        minName= graph.get(i+1).getSrc1().getDestinationName();

                    }
                    path=path+""+minName;
                }
            }
        }

        if(path.length()==4){

            for (int i = 0; i < graph.size(); i++) {

                if(graph.get(i).getSrc1().getDestinationName()== graph.get(0).getSrc1().getDestinationName() && graph.get(i).getSrc2().getDestinationName() == path.charAt(3) ){

                    path=path+""+graph.get(i).getSrc1().getDestinationName();
                }
            }
        }

        System.out.println(path);

        return path;
    }

    /**
     * For checking condition it takes the path and it control that the path travel every node and it return back the initial node
     * @param s path
     * @return path is okay or not
     */
    public static boolean checkCondition(String s){
        for (int i = 0; i < destination_List.size(); i++) {
            if ( !(s.contains(destination_List.get(i).getDestinationName() +""))){
                return true;
            }
        }

        if (s.charAt(0) != s.charAt(s.length()-1) && s.charAt(0) == destination_List.get(0).getDestinationName() && destination_List.get(0).getDestinationName() != s.charAt(s.length()-1)){
            return true;
        }
        return false;
    }

    /**
     * It find the edges into graph according to path
     * @param s path
     * @return list of the edges given path
     */
    public static List<Edge> findEdges(String s){
        char c = s.charAt(s.length()-1);
        List<Edge> edges = new ArrayList<Edge>();
        for (int i = 0; i < graph.size() ; i++) {
            if (c == graph.get(i).getSrc1().getDestinationName() || c == graph.get(i).getSrc2().getDestinationName()){

                edges.add(graph.get(i));
            }
        }
        return edges;
    }

    /**
     * It takes the starting point and destination and using the A search for the find shortest path
     * @param des1 starting point
     * @param des2 ending point
     */
    public static void AStarSearch(Destination des1, Destination des2) {


        Cell currentCell = null;
        Cell destinationCell = null;

        // It finds the cell according to its destination name. It finds current( inital cell and destination cell)
        for (int i = 0; i < cell_List.size(); i++) {
            if (cell_List.get(i).getValue() == des1.getDestinationName()) {
                currentCell = cell_List.get(i);
                currentCell.setFlag(1);


            }
            if (cell_List.get(i).getValue() == des2.getDestinationName()) {
                destinationCell = cell_List.get(i);
            }
        }

        //We make the inital cell's dont go value true. As a result, The algorithm will not travel initial cell
        currentCell.setDontGo(true);
        currentCell.setH_distance(heuristic(currentCell,destinationCell));
        currentCell.calculateTotalCost();

        Cell myCell = null;
        //The algorithm search the destination cell, It travel cells using A star search algorithm. It look their heuristic and path cost. Its name is total cost
        while (currentCell!=destinationCell) {

            List<Cell> minCellList = new ArrayList<Cell>();
            //The algorithm will not look cells that contains '*' and cells that visited. And it visited all neighbours which suitible that condition
            //I the current cell index is x,y; the neighbours can be x+1,y ; x, y+1 ; x-1,y; x,y-1
            myCell = findCell(currentCell.getxIndex(), currentCell.getyIndex() + 1);
            if (myCell.getValue() !='*' && myCell.getFlag()==0) {
                cellProcess(myCell,currentCell,destinationCell);
                minCellList.add(myCell);
            }
            myCell = findCell(currentCell.getxIndex(), currentCell.getyIndex() - 1);
            if (myCell.getValue() !='*' && myCell.getFlag()==0) {
                cellProcess(myCell,currentCell,destinationCell);
                minCellList.add(myCell);
            }
            myCell = findCell(currentCell.getxIndex() + 1, currentCell.getyIndex());
            if (myCell.getValue() !='*' && myCell.getFlag()==0) {
                cellProcess(myCell,currentCell,destinationCell);
                minCellList.add(myCell);
            }
            myCell = findCell(currentCell.getxIndex() - 1, currentCell.getyIndex());
            if (myCell.getValue() !='*' && myCell.getFlag()==0) {
                cellProcess(myCell,currentCell,destinationCell);
                minCellList.add(myCell);
            }

            //Finding minimum total distance if the total distance is same the another cell's total distance, then it travel first one
            Cell minCell = null;
            if(minCellList.size()!=0){
                minCell = minCellList.get(0);
            }

            for (int j = 1; j < minCellList.size(); j++) {
                if (minCell.getTotalCost()> minCellList.get(j).getTotalCost()){
                    minCell = minCellList.get(j);
                }
            }

            if (minCell==null) {
                currentCell.setDontGo(true);
                for (int j = 0; j < cell_List.size(); j++) {
                    if (!cell_List.get(j).getDontGo() && cell_List.get(j).getFlag() == 1) {
                        minCell = cell_List.get(j);
                        break;
                    }
                }
            }

            for (int k = 0; k < cell_List.size(); k++) {

                if (cell_List.get(k).getTotalCost() < minCell.getTotalCost() && !cell_List.get(k).getDontGo() && cell_List.get(k).getFlag() == 1) {
                    minCell = cell_List.get(k);
                    currentCell.setDontGo(true);


                }
            }

            if (currentCell==minCell){
                currentCell.setDontGo(true);

            }
            else{
                currentCell = minCell;

            }
        }

        //When the A star search algorithm is finish, the cells will be reseted
        Edge newEdge = new Edge(des1,des2,currentCell.getPathCost());
        graph.add(newEdge);
        for (int i = 0; i < cell_List.size(); i++) {
            cell_List.get(i).setPathCost(0);
            cell_List.get(i).setH_distance(0);
            cell_List.get(i).setFlag(0);
            cell_List.get(i).setTotalCost(0);
            cell_List.get(i).setDontGo(false);
        }
    }

    /**
     * It takes previous cell's cost and it set it into current cell, incrementing path cost, setting heuristic of current cell and calculating total cost and
     * it marked as a visited. As a result, it makes the next cell to previous cell
     * @param myCell next cell
     * @param currentCell previous cell
     * @param destinationCell destination cell
     */
    public static void cellProcess(Cell myCell, Cell currentCell, Cell destinationCell){
        myCell.setPathCost(currentCell.getPathCost());
        myCell.incPathCost();
        myCell.setH_distance(heuristic(myCell,destinationCell));
        myCell.calculateTotalCost();
        myCell.setFlag(1);
    }

    /**
     * Finding cell according to x and y index
     * @param x index
     * @param y index
     * @return cell according to x,y coordinate
     */
    public static Cell findCell(int x, int y){
        for (int i = 0; i < cell_List.size(); i++) {
            if (cell_List.get(i).getyIndex()==y && cell_List.get(i).getxIndex()==x){
                return cell_List.get(i);
            }
        }
        return null;
    }

    /**
     * It sorting destination list according to order
     */
    public static void sort(){
        for (int i = 0; i < destination_List.size() - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < destination_List.size(); j++){
                if (destination_List.get(j).getDestinationName() <destination_List.get(index).getDestinationName()){
                    index = j;//searching for lowest index
                }
            }
           char smallerDestination = destination_List.get(index).getDestinationName();
            destination_List.get(index).setDestinationName(destination_List.get(i).getDestinationName());
            destination_List.get(i).setDestinationName(smallerDestination);
        }
    }
}

