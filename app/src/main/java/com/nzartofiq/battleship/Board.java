package com.nzartofiq.battleship;

import android.util.Log;

import java.util.ArrayList;

public class Board {
    public String TAG = "Board class";
    public static final int MAX = 36;
    private static final int NUMBER_OF_SHIPS = 3;
    private static int CIRCLE_RADIUS = 3;

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

    public ArrayList getCircle() {
        int j = 22;
        ArrayList<Integer> circle = new ArrayList<>();
        int boardWidth = (int) Math.sqrt(MAX);
        int boardHeight = (int) Math.sqrt(MAX);
        int xj = j % boardWidth;
        int yj = j / boardHeight + 1;
        for (int i = 0; i < 360; i++) {
            Double xRad;
            Double yRad;
            for (int k = 1; k < MAX + 1; k++) {
                int xk = k % boardWidth;
                int yk = k / boardHeight;
                if (i <= 90) {
                    xRad = xj + (Math.cos(i) * CIRCLE_RADIUS);
                    yRad = yj + (Math.sin(i) * CIRCLE_RADIUS);
                    if (xk <= xRad && xk >= xj && yk <= yRad && yk >= yj && !(circle.contains(k))) {
                        circle.add(k);
                    }
                } else if (i <= 180) {
                    xRad = xj - (Math.cos(i) * CIRCLE_RADIUS);
                    yRad = yj + (Math.sin(i) * CIRCLE_RADIUS);
                    if (xk >= xRad && xk <= xj && yk <= yRad && yk >= yj && !(circle.contains(k))) {
                        circle.add(k);
                    }
                } else if (i <= 270) {
                    xRad = xj - (Math.cos(i) * CIRCLE_RADIUS);
                    yRad = yj - (Math.sin(i) * CIRCLE_RADIUS);
                    if (xk >= xRad && xk <= xj && yk >= yRad && yk <= yj && !(circle.contains(k))) {
                        circle.add(k);
                    }
                } else {
                    xRad = xj + (Math.cos(i) * CIRCLE_RADIUS);
                    yRad = yj - (Math.sin(i) * CIRCLE_RADIUS);
                    if (xk <= xRad && xk >= xj && yk >= yRad && yk <= yj && !(circle.contains(k))) {
                        circle.add(k);
                    }
                }
            }
        }
        Log.d(TAG, circle.toString());
        return circle;
    }
}
