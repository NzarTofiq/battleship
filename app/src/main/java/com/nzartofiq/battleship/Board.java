package com.nzartofiq.battleship;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.StringTokenizer;

public class Board {
    public static final int MAX = 36;
    private static final int NUMBER_OF_SHIPS = 3;
    public static final int CIRCLE_RADIUS = 1;
    public String TAG = "Board class";
    private static SquareType[] squareTypes = new SquareType[MAX];
    public boolean invisible = false;

    //constructor
    public Board() {
        ArrayList<Integer> r = getRandomNumbers();
        for (int i = 0; i < MAX; i++) {
            if (r.contains(i)) {
                squareTypes[i] = SquareType.SHIP;
            } else {
                squareTypes[i] = SquareType.FREE;
            }
        }
    }

    public SquareType getSquareType(int i) {
        return squareTypes[i];
    }

    public void updateBoard(int pos) {
        switch (squareTypes[pos]) {
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

    public boolean checkWin() {
        for (SquareType i : squareTypes) {
            if (i == SquareType.SHIP) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<Integer> getRandomNumbers() {
        ArrayList<Integer> randoms = new ArrayList<>();
        Random r = new Random();
        while (randoms.size() < NUMBER_OF_SHIPS) {
            Integer next = r.nextInt(MAX);
            if (!randoms.contains(next)) {
                randoms.add(next);
            }
        }
        return randoms;
    }

    public void setSquareType(int i, SquareType squareType) {
        squareTypes[i] = squareType;
    }

    public ArrayList<Integer> getCircle(int centerPos) {
        if(centerPos >= MAX){
            centerPos = MAX - 1;
        }
        ArrayList circle = new ArrayList();
        int side = (int) Math.floor(Math.sqrt(MAX));
        int centerX = centerPos % side;
        int centerY = centerPos / side;
        Log.d("centerY: ", String.valueOf(centerY));

        Log.d("center square", String.valueOf(Math.round((centerY * side) + (centerX))));

        for (int angle= 0; angle < 2 * Math.PI; angle+=1) {

            //starting from the center point extend the radius until radius value and get the squares it covers
            for (int j = 0; j <= CIRCLE_RADIUS; j++) {
                //find the length of x-radius at each degree in a circle round the center, this is to define the edges Xs
                double rX = Math.floor(Math.cos(Math.toDegrees(angle)) * j);
                Log.d("i ", String.valueOf(angle));
                Log.d("rx i", String.valueOf(rX));

                //find the length of y-radius at each degree in a circle round the center, this is to define the edges Ys
                double rY = -1 * Math.floor(Math.sin(Math.toDegrees(angle)) * j);

                //now we have a circle somewhere in the grid
                //to put the circle around the center is to get the end of radius square's X and Y
                // by adding or taking away the length and position of the radius line from center point
                double sqX = (centerX) + rX;
                double sqY = (centerY) + rY;

                Log.d("sqX", String.valueOf(sqX));

                int square = 0;
                if (!circle.contains(square) && square >= 0) {
                    circle.add(square);
                }
            }
        }
        Log.d(TAG, String.valueOf(circle));
        return circle;
    }
}
