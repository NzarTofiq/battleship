package com.nzartofiq.battelship;

public class Board {
    public static final int MAX = 81;
    private int[] board = new int[MAX];

    //constructor
    public Board(){
        for(int i=0;i<MAX;i++){
            board[i]=0;
        }
    }

    public void updateBoard(int pos){
        board[pos]=1;
    }

    public boolean checkWin(){
        for(int i : board){
           if(i==0){return false;}
        }
        return true;
    }
}
