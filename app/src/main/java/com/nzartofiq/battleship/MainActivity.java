package com.nzartofiq.battleship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String BOARD_EXTRA = "com.nzartofiq.MainActivity.BOARD_EXTRA";
    private static final String TAG = "Main activity ";
    private static int[] actionBtns = {R.id.move, R.id.missile, R.id.torpedo, R.id.invisible, R.id.radar, R.id.bombard};

    //version 3
    private static int[] squares = {
            R.id.sq_1, R.id.sq_2, R.id.sq_3, R.id.sq_4, R.id.sq_5, R.id.sq_6, R.id.sq_7, R.id.sq_8, R.id.sq_9,
            R.id.sq_10, R.id.sq_11, R.id.sq_12, R.id.sq_13, R.id.sq_14, R.id.sq_15, R.id.sq_16, R.id.sq_17, R.id.sq_18,
            R.id.sq_19, R.id.sq_20, R.id.sq_21, R.id.sq_22, R.id.sq_23, R.id.sq_24, R.id.sq_25, R.id.sq_26, R.id.sq_27,
            R.id.sq_28, R.id.sq_29, R.id.sq_30, R.id.sq_31, R.id.sq_32, R.id.sq_33, R.id.sq_34, R.id.sq_35, R.id.sq_36
    };

    private static Board board;
    private ArrayList<Integer> circle = new ArrayList<>();
    private String name;
    private Gson boardObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Intent intent = getIntent();
        name = intent.getStringExtra(StartActivity.NAME_EXTRA);
        board = new Board();
        setUpActionClicks();
        setUpBoard(findViewById(R.id.board_grid));
        setupBtnClicks();
    }

    private void setUpBoard(View v) {
        for (int i = 0; i < Board.MAX; i++) {
            updateSquareView(v, i);
        }
    }

    public void updateSquareView(View v, int i) {
        ImageButton iBtn = (ImageButton) v.findViewById(squares[i]);
        switch (board.getSquareTypes(i)) {
            case SHIP:
                iBtn.setImageResource(R.drawable.ship);
                break;
            case FREE:
                iBtn.setImageResource(R.drawable.green);
                break;
            case WRECK:
                iBtn.setImageResource(R.drawable.ship_wrecked);
                break;
            case USED:
                iBtn.setImageResource(R.drawable.red);
                break;
            case AVAILABLE:
                iBtn.setImageResource(R.drawable.yellow);
                break;
            default:
                iBtn.setImageResource(R.drawable.blue);
        }
    }

    public void updateAllSquareViews() {
        for (int i = 0; i < Board.MAX; i++) {
            View square = findViewById(squares[i]);
            updateSquareView(square, i);
        }
    }

    private void setupBtnClicks() {
        for (int i = 0; i < Board.MAX; i++) {
            findViewById(squares[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton iBtn = (ImageButton) v;
                    for (int i = 0; i < Board.MAX; i++) {
                        if (squares[i] == iBtn.getId()) {
                            board.updateBoard(i);
                            updateSquareView(v, i);
                            iBtn.setOnClickListener(null);
                        }
                    }
                    if (board.checkWin()) {
                        lastActivity();
                    }
                }
            });
        }
    }

    public void setUpActionClicks() {
        for (int i = 0; i < actionBtns.length; i++) {
            findViewById(actionBtns[i]).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button aBtn = (Button) view;
                    switch (aBtn.getId()) {
                        case R.id.move:
                            move();
                            break;
                        case R.id.torpedo:
                            torpedo();
                            break;
                        case R.id.missile:
                            missile();
                            break;
                        case R.id.invisible:
                            invisible();
                            break;
                        case R.id.radar:
                            radar();
                            break;
                        case R.id.bombard:
                            bombard();
                            break;
                        default:
                            missile();
                    }
                }
            });
        }
    }

    private void bombard() {
        removeOnClickListeners();
        for (int i = 0; i < Board.MAX; i++) {
            final ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            board.updateBoard(i);
            updateSquareView(iBtn, i);
        }
    }

    private void radar() {
        removeOnClickListeners();
        for (int i = 0; i < Board.MAX; i++) {
            final ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            final int radarPos = i;
            iBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateSquareView(view, radarPos);
                    removeOnClickListeners();
                }
            });
        }
    }

    private void invisible() {
        removeOnClickListeners();
        for (int i = 0; i < Board.MAX; i++) {
            final ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            if (board.getSquareTypes(i) == SquareType.SHIP) {
                iBtn.setImageResource(R.drawable.green);
            }
        }
    }

    private void missile() {
        removeOnClickListeners();
        for (int i = 0; i < Board.MAX; i++) {
            final ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            if (board.getSquareTypes(i) == SquareType.SHIP) {
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageButton shipBtn = (ImageButton) view;
                        missileNextClick(shipBtn);
                    }
                });
            }
        }
    }

    private void missileNextClick(ImageButton shipBtn) {
        shipBtn.setOnClickListener(null);
        for (int i = 0; i < Board.MAX; i++) {
            final int hitPos = i;
            ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            if (shipBtn != iBtn) {
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        board.updateBoard(hitPos);
                        updateSquareView(view, hitPos);
                        removeOnClickListeners();
                    }
                });
            }
        }
    }

    private void torpedo() {
        removeOnClickListeners();
        for (int i = 0; i < Board.MAX; i++) {
            final ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            if (board.getSquareTypes(i) == SquareType.SHIP) {
                final int finalI = i;
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageButton shipBtn = (ImageButton) view;
                        torpedoNextClick(shipBtn, finalI);
                    }
                });
            }
        }
    }

    private void torpedoNextClick(ImageButton shipBtn, int pos) {
        shipBtn.setOnClickListener(null);
        circle = board.getCircle(pos);
        for (int i = 0; i < Board.MAX; i++) {
            final int hitPos = i;
            board.setSquareType(i, SquareType.AVAILABLE);
            updateAllSquareViews();
            ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            if (shipBtn != iBtn && circle.contains(i)) {
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        board.updateBoard(hitPos);
                        updateSquareView(view, hitPos);
                        removeOnClickListeners();
                    }
                });
            }
        }
    }

    private void move() {
        removeOnClickListeners();
        for (int i = 0; i < Board.MAX; i++) {
            final ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            final int oldPos = i;
            if (board.getSquareTypes(i) == SquareType.SHIP) {
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageButton shipBtn = (ImageButton) view;
                        moveNextClick(shipBtn);
                        board.setSquareType(oldPos, SquareType.FREE);
                        updateSquareView(view, oldPos);
                    }
                });
            }
        }
    }

    private void moveNextClick(View v) {
        for (int i = 0; i < Board.MAX; i++) {
            ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            iBtn.setOnClickListener(null);
            final int newPos = i;
            if (board.getSquareTypes(i) == SquareType.FREE) {
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        board.setSquareType(newPos, SquareType.SHIP);
                        updateSquareView(view, newPos);
                        view.setOnClickListener(null);
                        removeOnClickListeners();
                    }
                });
            }
        }
    }

    private void removeOnClickListeners() {
        for (int i = 0; i < Board.MAX; i++) {
            ImageButton imageButton = (ImageButton) findViewById(squares[i]);
            imageButton.setOnClickListener(null);
        }
    }

    private void lastActivity() {
        Intent intent = new Intent(this, LastActivity.class);
        TextView textMsg = (TextView) findViewById(R.id.goodByMsg);
        intent.putExtra(StartActivity.NAME_EXTRA, name);
        startActivity(intent);
    }
}
