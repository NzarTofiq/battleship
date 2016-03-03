package com.nzartofiq.battleship;

import android.util.Log;

import java.util.ArrayList;

public class Board {
    public String TAG = "Board class";
    public static final int MAX = 36;
    private static final int NUMBER_OF_SHIPS = 3;

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
            case WRECK:
                squareTypes[pos] = SquareType.WRECK;
                break;
            case USED:
                squareTypes[pos] = SquareType.USED;
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

    public ArrayList getCircle(int centrePos) {
        ArrayList<Integer> circle = new ArrayList<>();
        Double gridWidth = Math.sqrt(MAX);
        Double centreX = centrePos % gridWidth;
        Double centreY = Math.ceil(centrePos / gridWidth);
        Double radius = 2.0;
        for (int i = 1; i < gridWidth + 1; i++) {
            for (int j = 1; j < gridWidth + 1; j++) {
                Double xDist = Math.abs(i - centreX);
                Double yDist = Math.abs(j - centreY);
                int square = (int) ((j * gridWidth) + i);
                if (xDist <= radius && yDist <= radius && !(circle.contains(square)) && square <= MAX) {
                    circle.add(square);
                }
            }
        }
        Log.d(TAG, String.valueOf(circle));
        return circle;
    }
}
