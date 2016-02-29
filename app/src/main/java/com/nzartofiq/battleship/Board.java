package com.nzartofiq.battleship;

import java.util.ArrayList;

public class Board {
    public static final int MAX = 36;
    private SquareType[] squareTypes = new SquareType[MAX];
    private static final int NUMBER_OF_SHIPS = 3;

    //constructor
    public Board(){
        ArrayList r = getRandomNumbers();
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
            case FREE: squareTypes[pos] = SquareType.USED;
                break;
            case SHIP: squareTypes[pos] = SquareType.WRECK;
                break;
            default: squareTypes[pos]=SquareType.FREE;
        }
    }

    public boolean checkWin(){
        for(SquareType i : squareTypes){
           if(i == SquareType.FREE){
               return false;
           }
        }
        return true;
    }

    private ArrayList getRandomNumbers() {
        ArrayList randoms = new ArrayList();
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
}
