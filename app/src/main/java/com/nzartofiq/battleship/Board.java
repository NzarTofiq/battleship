package com.nzartofiq.battleship;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    public static final int MAX = 36;
    private static final int NUMBER_OF_SHIPS = 3;
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

    public ArrayList<Integer> getCircle(int centrePos) {
        ArrayList<Integer> circle = new ArrayList<>();
        centrePos = 1;
        Double CIRCLE_ERROR = 0.45;

        int width = (int) Math.sqrt(MAX);
        int centerX = centrePos % width + 1;
        int centerY = (int) Math.ceil(centrePos + 1 / width);
        for (int pX = 1; pX <= width; pX++) {
            for (int pY = 1; pY <= width; pY++) {
                int r = 2;
                int dX = Math.abs(pX - centerX) >= Math.abs(centerX - pX) ? Math.abs(pX - centerX) : Math.abs(centerX - pX);
                int dY = Math.abs(pY - centerY) >= Math.abs(centerY - pY) ? Math.abs(pY - centerY) : Math.abs(centerY - pY);
                int sqNum = (int) Math.floor((pY * width) + (width - pX));
                Log.d("px", String.valueOf(pX));
                Log.d("py", String.valueOf(pY));
                if (dX == 0 && dY <= r) {
                    /*Log.d("dx", String.valueOf(dX));
                    Log.d("dy", String.valueOf(dY));
                    Log.d("r", String.valueOf(r));*/
                    setSquareType(sqNum, SquareType.AVAILABLE);
                } else if (dX <= r && dY == 0) {
                    setSquareType(sqNum, SquareType.AVAILABLE);
                } else {
                    for (int angle = 1; angle < 90; angle++) {
                        r = (int) ((Math.cos(angle) + CIRCLE_ERROR) * r);
                        if (dX <= r && dY <= r) {
                            setSquareType(sqNum, SquareType.AVAILABLE);
                        }
                    }
                }
            }
        }
        return circle;
    }
}
