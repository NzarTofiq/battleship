package com.nzartofiq.battleship;

import java.util.ArrayList;

public class Board {
    public String TAG = "Board class";
    public static final int MAX = 36;
    private static final int NUMBER_OF_SHIPS = 3;
    /*private static final int CIRCLE_RADIUS = 3;*/

    private SquareType[] squareTypes = new SquareType[MAX];

    //constructor
    public Board(){
        ArrayList<Integer> r = getRandomNumbers();
        for(int i=0;i<MAX;i++){
            if (r.contains(i)){
                squareTypes[i] = SquareType.SHIP;
            } else {
                squareTypes[i] = SquareType.FREE;
            }
        }
    }

    public SquareType getSquareTypes(int i) {
        return squareTypes[i];
    }

    public void updateBoard(int pos){
        switch (squareTypes[pos]){
            case FREE:
                squareTypes[pos] = SquareType.USED;
                break;
            case SHIP:
                squareTypes[pos] = SquareType.WRECK;
                break;
            default:
                squareTypes[pos] = SquareType.FREE;
        }
    }

    public boolean checkWin(){
        for(SquareType i : squareTypes){
            if (i == SquareType.FREE || i == SquareType.SHIP) {
               return false;
           }
        }
        return true;
    }

    private ArrayList<Integer> getRandomNumbers() {
        ArrayList<Integer> randoms = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_SHIPS; i++){
            int r = (int) Math.floor(Math.random() * squareTypes.length);
            if (randoms.contains(r)){
                getRandomNumbers();
            } else {
                randoms.add(r);
            }
        }
        return randoms;
    }

    public void setSquareType(int i, SquareType squareType) {
        squareTypes[i] = squareType;
    }

    /*public ArrayList getCircle(int j) {
        ArrayList circle = new ArrayList();
        int boardWidth = (int) Math.sqrt(MAX);
        int boardHeight = (int) Math.sqrt(MAX);
        int xj = j % boardWidth;
        int yj = j % boardHeight;
        for(int i = 0; i < MAX; i++){
            int xi = i % boardWidth;
            int yi = i % boardHeight;
            for(int k = 0; k < CIRCLE_RADIUS; k++){
                if (((xi - xj == k && !(xi - xj < 0)) && (yi - yj == k && !(yi - yj < 0))) || ((xj - xi == k && !(xj - xi < 0)) && (yi - yj == k && !(yi - yj < 0))) || ((xi - xj == k && !(xi - xj < 0)) && (yj - yi == k && !(yj - yi < 0))) || ((xj - xi == k && !(xj - xi < 0)) && (yj - yi == k && !(yj - yi < 0)))) {
                    squareTypes[i] = SquareType.AVAILABLE;
                    circle.add(i);
                }
            }
        }
        return circle;
    }*/
}
