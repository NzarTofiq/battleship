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
    public int hit;

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
        hit = 0;
    }

    public SquareType getSquareType(int i) {
        return squareTypes[i];
    }
    public void setSquareType(int i, SquareType squareType) {
        squareTypes[i] = squareType;
    }

    public void updateBoard(int pos) {
        switch (squareTypes[pos]) {
            case  FREE:
                squareTypes[pos] = SquareType.USED;
                break;
            case AVAILABLE:
                squareTypes[pos] = SquareType.USED;
                break;
            case SHIP:
                squareTypes[pos] = SquareType.WRECK;
                hit++;
                break;
            case USED:
                squareTypes[pos] = SquareType.USED;
                break;
            case WRECK:
                squareTypes[pos] = SquareType.WRECK;
                break;
            default:
                squareTypes[pos] = SquareType.FREE;
        }
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

    public ArrayList<Integer> getCircle(int centerPos) {
        if(centerPos >= MAX){
            centerPos = MAX - 1;
        }
        ArrayList circle = new ArrayList();
        double side = Math.sqrt(MAX);

        double centerX = centerPos % side;
        double centerY = centerPos / side;
        for (int i = 0; i< MAX; i++){
            double pX = Math.abs(i % side + 1) - centerX;
            double pY = Math.abs(i / side + 1) - centerY;
            for (double j = 0; j < 360; j+=10){
                double distanceX = Math.cos(Math.toRadians(j) * CIRCLE_RADIUS) + pX;
                double distanceY = Math.sin(Math.toRadians(j) * CIRCLE_RADIUS) + pY;
                double distance = Math.abs(distanceX) > Math.abs(distanceY) ? distanceX : distanceY;
                if (Math.abs(distance) < CIRCLE_RADIUS && !circle.contains(i)){
                    circle.add(i);
                }
            }
        }
        Log.d(TAG, String.valueOf(circle));
        return circle;
    }

    public boolean checkWin() {
        if(hit < NUMBER_OF_SHIPS){
            return false;
        }
        return true;
    }

    public void normalize() {
        for(int i= 0; i< MAX; i++){
            if (squareTypes[i] == SquareType.AVAILABLE)
                squareTypes[i] = SquareType.FREE;
        }
    }
    public void highLight(int i){
        if(squareTypes[i] == SquareType.FREE){
            squareTypes[i] = SquareType.AVAILABLE;
        }
    }
}
