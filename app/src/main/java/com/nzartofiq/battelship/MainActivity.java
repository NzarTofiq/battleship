package com.nzartofiq.battelship;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //version 3
    private static int[] squares = {
            R.id.sq_1,R.id.sq_2,R.id.sq_3,R.id.sq_4,R.id.sq_5,
            R.id.sq_6,R.id.sq_7,R.id.sq_8,R.id.sq_9,R.id.sq_10,
            R.id.sq_11,R.id.sq_12,R.id.sq_13,R.id.sq_14,R.id.sq_15,
            R.id.sq_16,R.id.sq_17,R.id.sq_18,R.id.sq_19,R.id.sq_20,
            R.id.sq_21,R.id.sq_22,R.id.sq_23,R.id.sq_24,R.id.sq_25
    };

    private Board board;
    private String name;
    private static Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Intent intent = getIntent();
        name = intent.getStringExtra(StartActivity.NAME_EXTRA);
        board = new Board();
        setupBtnClicks();
    }

    private void setupBtnClicks(){
        for(int i=0;i< Board.MAX;i++){
            findViewById(squares[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton iBtn = (ImageButton) v;
                    iBtn.setImageResource(R.drawable.yellow);
                    for (int i = 0; i < Board.MAX; i++) {
                        if (squares[i] == iBtn.getId()) {
                            board.updateBoard(i);
                            iBtn.setOnClickListener(null);
                        }
                    }
                    if(board.checkWin()){
                        lastActivity();
                    }
                }
            });
        }
    }

    private void lastActivity(){
        Intent intent = new Intent(this,LastActivity.class);
        TextView textMsg  = (TextView)findViewById(R.id.goodByMsg);
        intent.putExtra(StartActivity.NAME_EXTRA, name);
        startActivity(intent);
    }
}
