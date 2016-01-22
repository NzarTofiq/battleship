package com.nzartofiq.battelship;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.TextView;

public class MainActivity extends Activity {
    //version 3
    private static int[] squares = {
            R.id.sq_1,R.id.sq_2,R.id.sq_3,R.id.sq_4,R.id.sq_5,R.id.sq_6,R.id.sq_7,R.id.sq_8,R.id.sq_9,
            R.id.sq_10,R.id.sq_11,R.id.sq_12,R.id.sq_13,R.id.sq_14,R.id.sq_15, R.id.sq_16,R.id.sq_17,R.id.sq_18,
            R.id.sq_19,R.id.sq_20,R.id.sq_21,R.id.sq_22,R.id.sq_23,R.id.sq_24,R.id.sq_25,R.id.sq_26,R.id.sq_27,
            R.id.sq_28,R.id.sq_29,R.id.sq_30,R.id.sq_31,R.id.sq_32,R.id.sq_33,R.id.sq_34,R.id.sq_35,R.id.sq_36,
            R.id.sq_37,R.id.sq_38,R.id.sq_39, R.id.sq_40,R.id.sq_41,R.id.sq_42,R.id.sq_43,R.id.sq_44,R.id.sq_45,
            R.id.sq_46,R.id.sq_47,R.id.sq_48,R.id.sq_49,R.id.sq_50,R.id.sq_51,R.id.sq_52,R.id.sq_53,R.id.sq_54,
            R.id.sq_55, R.id.sq_56,R.id.sq_57,R.id.sq_58,R.id.sq_59,R.id.sq_60,R.id.sq_61,R.id.sq_62,R.id.sq_63,
            R.id.sq_64,R.id.sq_65, R.id.sq_66,R.id.sq_67,R.id.sq_68,R.id.sq_69,R.id.sq_70,R.id.sq_71,R.id.sq_72,
            R.id.sq_73,R.id.sq_74, R.id.sq_75,R.id.sq_76,R.id.sq_77,R.id.sq_78,R.id.sq_79,R.id.sq_80,R.id.sq_81
    };

    private Board board;
    private String name;
    private ImageSwitcher imageSwitcher;

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
