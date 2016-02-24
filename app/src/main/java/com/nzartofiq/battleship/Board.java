package com.nzartofiq.battleship;

public class Board {
    public static final int MAX = 36;
    private SquareType[] squares = new SquareType[MAX];

    //constructor
    public Board(){
        for(int i=0;i<MAX;i++){
            squares[i]= SquareType.FREE;
        }
        squares[0] = SquareType.SHIP;
    }

    public void updateBoard(int pos){
        squares[pos]=SquareType.USED;
    }

    public boolean checkWin(){
        for(SquareType i : squares){
           if(i==SquareType.FREE){return false;}
        }
        return true;
    }
}
