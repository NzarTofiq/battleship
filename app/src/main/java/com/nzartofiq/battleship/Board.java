package com.nzartofiq.battleship;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    public static final int MAX = 36;
    private static final int NUMBER_OF_SHIPS = 3;
    private static int CIRCLE_RADIUS = 3;
    public String TAG = "Board class";
    private SquareType[] squareTypes = new SquareType[MAX];

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

    public SquareType getSquareTypes(int i) {
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
            if (i == SquareType.FREE || i == SquareType.SHIP) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<Integer> getRandomNumbers() {
        ArrayList<Integer> randoms = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < NUMBER_OF_SHIPS; i++) {
            Integer next = r.nextInt(MAX) + 1;
            Log.d(TAG, String.valueOf(next));
            randoms.add(next);
        }
        return randoms;
    }

    public void setSquareType(int i, SquareType squareType) {
        squareTypes[i] = squareType;
    }

    public ArrayList<Integer> getCircle(int centerPos) {
        ArrayList<Integer> circle = new ArrayList<>();
        int side = (int) Math.floor(Math.sqrt(MAX));

        //assuming the array of squares is are laid in a grid
        //assuming the the board is starts from (1,1) and ends at (sqrt(MAX), sqrt(MAX)) like (6, 6)
        //to rectify the nth element issue with arrays
        centerPos = centerPos + 1;

        //the remainder of MAX/the number of the square in the array will give us its x position in the grid
        int centerX = MAX % centerPos;

        //assuming the grid is always a square, then max-y is the square root of MAX
        //which will be the row number, but to make it an int it has to start from one, hence ceiling

        int centerY = (int) Math.ceil(centerPos / side) < side ? (int) Math.ceil(centerPos / side) : side;

        if (centerX > 0 && centerX < side && centerY > 0 && centerY < side) {
            for (int i = 0; i < 360; i++) {
                //starting from the center point extend the radius until it and get the squares it covers
                for (int j = 0; j <= CIRCLE_RADIUS; j++) {
                    //find the length of x-radius at each degree in a circle round the center, this is to define the edges Xs
                    double rX = Math.cos((double) i) * j;
                    //find the length of y-radius at each degree in a circle round the center, this is to define the edges Ys
                    double rY = Math.sin((double) i) * j;

                    //now we have a circle somewhere in the grid
                    //to put the circle around the center is to get the end of radius square's X and Y
                    // by adding or taking away the length and position of the radius line from center point
                    int sqX = (int) Math.abs(centerX + rX);
                    int sqY = (int) Math.abs(centerY + rY);
                    if (sqX > 0 && sqX <= side && sqY > 0 && sqY <= side) {
                        int inside = sqX + (sqY * side);
                        if (!circle.contains(inside)) {
                            circle.add(inside);
                        }
                    }
                }
            }
        }
        Log.d("circle: ", String.valueOf(circle));
        return circle;
    }
}
