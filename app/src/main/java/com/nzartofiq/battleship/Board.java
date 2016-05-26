package com.nzartofiq.battleship;

import java.util.ArrayList;
import java.util.Random;

import static com.nzartofiq.battleship.SquareType.AVAILABLE;
import static com.nzartofiq.battleship.SquareType.FREE;
import static com.nzartofiq.battleship.SquareType.OP_SHIP;
import static com.nzartofiq.battleship.SquareType.OP_SHIP_DISP;
import static com.nzartofiq.battleship.SquareType.OP_WRECK;
import static com.nzartofiq.battleship.SquareType.SHIP;
import static com.nzartofiq.battleship.SquareType.USED;
import static com.nzartofiq.battleship.SquareType.WRECK;

public class Board {
    private static final int MAX = 36;
    private final int NUMBER_OF_SHIPS = 3;
    private SquareType[] squareTypes = new SquareType[MAX];
    private ArrayList circle;
    public boolean invisible = false;

    //constructor
    public Board() {
        ArrayList<Integer> r = getRandomNumbers();
        for (int i = 0; i < MAX; i++) {
            if (r.contains(i)) {
                squareTypes[i] = SHIP;
            } else {
                squareTypes[i] = FREE;
            }
        }
    }

    public SquareType getSquareType(int i) {
        return squareTypes[i];
    }

    public void setSquareType(int i, SquareType squareType) {
        squareTypes[i] = squareType;
    }

    public void fireAt(int pos) {
        switch (squareTypes[pos]) {
            case FREE:
            case AVAILABLE:
            case USED:
                squareTypes[pos] = USED;
                break;
            case SHIP:
            case WRECK:
                squareTypes[pos] = WRECK;
                break;
            case OP_SHIP_DISP:
            case OP_WRECK:
            case OP_SHIP:
                squareTypes[pos] = OP_WRECK;
                break;
            default:
                squareTypes[pos] = squareTypes[pos];
        }
    }

    private ArrayList getRandomNumbers() {
        ArrayList randoms = new ArrayList();
        Random r = new Random();
        while (randoms.size() < NUMBER_OF_SHIPS) {
            Integer next = r.nextInt(MAX);
            if (!randoms.contains(next)) {
                randoms.add(next);
            }
        }
        return randoms;
    }

    public ArrayList getCircle(int centerPos) {
        if(centerPos >= MAX){
            centerPos = MAX - 1;
        }
        circle = null;
        circle = new ArrayList();
        int side = (int) Math.abs(Math.floor(Math.sqrt(MAX)));

        push(centerPos - side - side);
        push(centerPos - side - 1);
        push(centerPos - side);
        push(centerPos - side + 1);
        push(centerPos - 2);
        push(centerPos - 1);
        push(centerPos);
        push(centerPos + 1);
        push(centerPos + 2);
        push(centerPos + side - 1);
        push(centerPos + side);
        push(centerPos + side + 1);
        push(centerPos + side + side);
        return circle;
    }

    private void push(int i) {
        if (i >= 0 && i < MAX && !circle.contains(i)){
            circle.add(i);
        }
    }

    public boolean playing() {
        for (SquareType squareType : squareTypes) {
            if ((squareType == SHIP)) {
                return true;
            }
        }
        return false;
    }

    public void normalize() {
        for(int i= 0; i< MAX; i++){
            if (squareTypes[i] == AVAILABLE) {
                setSquareType(i, FREE);
            }
        }
    }
    public void highLight(int i, boolean disp){
        if(disp){
            if(squareTypes[i] == FREE) {
                setSquareType(i, AVAILABLE);
            } else if (squareTypes[i] == OP_SHIP && disp){
                setSquareType(i, OP_SHIP_DISP);
            }
        } else {
            if(squareTypes[i] == FREE || squareTypes[i] == OP_SHIP) {
                setSquareType(i, AVAILABLE);
            }
        }
    }
}
