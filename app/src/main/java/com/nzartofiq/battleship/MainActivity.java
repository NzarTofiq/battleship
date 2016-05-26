package com.nzartofiq.battleship;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.nzartofiq.battleship.R.string.torpedo_desc;


public class MainActivity extends AppCompatActivity {
    //    public static final String BOARD_EXTRA = "com.nzartofiq.MainActivity.BOARD_EXTRA";
    //    private static final String TAG = "Main activity ";
    private static int[] actionBtns = {R.id.move, R.id.missile, R.id.torpedo, R.id.invisible, R.id.radar, R.id.bombard};

    //version 3
    private static int[] squares = {
            R.id.sq_1, R.id.sq_2, R.id.sq_3, R.id.sq_4, R.id.sq_5, R.id.sq_6, R.id.sq_7, R.id.sq_8, R.id.sq_9,
            R.id.sq_10, R.id.sq_11, R.id.sq_12, R.id.sq_13, R.id.sq_14, R.id.sq_15, R.id.sq_16, R.id.sq_17, R.id.sq_18,
            R.id.sq_19, R.id.sq_20, R.id.sq_21, R.id.sq_22, R.id.sq_23, R.id.sq_24, R.id.sq_25, R.id.sq_26, R.id.sq_27,
            R.id.sq_28, R.id.sq_29, R.id.sq_30, R.id.sq_31, R.id.sq_32, R.id.sq_33, R.id.sq_34, R.id.sq_35, R.id.sq_36
    };

    private Board myBoard = new Board();
    private Board opBoard = new Board();
    private String name;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Intent intent = getIntent();
        name = intent.getStringExtra(StartActivity.NAME_EXTRA);
        setUpActionClicks();
        updateViewAll();
    }

    /**
     * updates the board view
     */
    public void updateViewAll() {
        for (int i = 0; i < squares.length; i++) {
            updateView(i);
        }
    }

    /**
     *
      * @param pos
     */
    public void updateView(int pos) {
        syncBoard();
        ImageButton iBtn = (ImageButton) findViewById(squares[pos]);
        switch (myBoard.getSquareType(pos)) {
            case SHIP:
                iBtn.setImageResource(R.drawable.ship);
                break;
            case OP_SHIP:
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
            case OP_SHIP_DISP:
                iBtn.setImageResource(R.drawable.op_ship);
            case OP_WRECK:
                iBtn.setImageResource(R.drawable.op_wreck);
                break;
            default:
                iBtn.setImageResource(R.drawable.green);
        }
    }

    /**
     * synchronises my board to opponent's board
     * so the view shows only what is required
     */
    private void syncBoard() {
        for (int i =0; i< squares.length;i++){
            if(opBoard.getSquareType(i) == SquareType.SHIP){
                myBoard.setSquareType(i, SquareType.OP_SHIP);
            } else if(opBoard.getSquareType(i) == SquareType.WRECK){
                myBoard.setSquareType(i, SquareType.OP_WRECK);
            }
            if (!myBoard.playing()) {
                lastActivity();
            }
        }
    }

    /**
     * sets up what happens when an action button is clicked or tapped on
     */
    public void setUpActionClicks() {
        for (int actionBtn : actionBtns) {
            findViewById(actionBtn).setOnClickListener(new View.OnClickListener() {
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
                        case R.id.bombard:
                            bombard();
                            break;
                        case R.id.invisible:
                            invisible();
                            break;
                        case R.id.radar:
                            radar();
                            break;
                        default:
                            missile();
                    }
                }
            });
        }
    }

    /**
     * sets up what to show to help the player make a decision
     * on where to click next
     * also shows information about the action selected
     * @param type
     */
    private void setInfo(String type) {
        ImageView img = (ImageView) findViewById(R.id.action_image);
        TextView header = (TextView) findViewById(R.id.action_header);
        TextView desc = (TextView) findViewById(R.id.action_description);
        switch (type){
            case "move":
                img.setImageResource(R.drawable.move);
                header.setText(R.string.move_header);
                desc.setText(R.string.move_desc);
                break;
            case "torpedo":
                img.setImageResource(R.drawable.torpedo75_75);
                header.setText(R.string.torpedo_header);
                desc.setText(String.format(getString(torpedo_desc), "2"));
                break;
            case "missile":
                img.setImageResource(R.drawable.missile75_75);
                header.setText(R.string.missile);
                desc.setText(R.string.missile_desc);
                break;
            case "bombard":
                img.setImageResource(R.drawable.bombard75_75);
                header.setText(R.string.bombard);
                desc.setText(R.string.bombard_desc);
                break;
            case "invisible":
                img.setImageResource(R.drawable.invisible75_75);
                header.setText(R.string.invisible);
                desc.setText(R.string.invisible_desc);
                break;
            case "radar":
                img.setImageResource(R.drawable.radar75_75);
                header.setText(R.string.radar);
                desc.setText(R.string.radar_desc);
                break;
            default:
                img.setImageResource(R.drawable.red);
                header.setText(R.string.hit_header);
                desc.setText(R.string.hit_desc);
        }
    }

    /**
     * moves a ship from one square to another
     */
    private void move() {
        setInfo("move");
        updateViewAll();
        removeOnClickListeners();
        for (int i = 0; i < squares.length; i++) {
            ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            final int oldPos = i;
            if (myBoard.getSquareType(i) == SquareType.SHIP) {
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myBoard.setSquareType(oldPos, SquareType.FREE);
                        updateViewAll();
                        for (int i = 0; i < squares.length; i++) {
                            ImageButton nextBtn = (ImageButton) findViewById(squares[i]);
                            final int newPos = i;
                            if (myBoard.getSquareType(newPos) == SquareType.FREE) {
                                nextBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        myBoard.setSquareType(newPos, SquareType.SHIP);
                                        updateView(newPos);
                                        removeOnClickListeners();
                                        Communication.opTurn(myBoard);
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * fires a torpedo from a selected ship to a selected square
     * relative to the ship
     */
    private void torpedo() {
        setInfo("torpedo");
        removeOnClickListeners();
        for (int i = 0; i < squares.length; i++) {
            if (myBoard.getSquareType(i) == SquareType.SHIP) {
                ImageButton iBtn = (ImageButton) findViewById(squares[i]);
                final int finalI = i;
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ArrayList circle = myBoard.getCircle(finalI);
                        for (int j = 0; j < circle.size(); j++) {
                            myBoard.highLight((int) circle.get(j), false);
                            ImageButton highLighted = (ImageButton) findViewById(squares[(int) circle.get(j)]);
                            updateView((int) circle.get(j));
                            final int finalJ = j;
                            removeOnClickListeners();
                            highLighted.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    myBoard.fireAt((int) circle.get(finalJ));
                                    myBoard.normalize();
                                    updateView((int) circle.get(finalJ));
                                    removeOnClickListeners();
                                    Communication.opTurn(myBoard);
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    /**
     * fires at any square on the board
     */
    private void missile() {
        setInfo("missile");
        for (int i = 0; i < squares.length; i++) {
            ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            final int finalI = i;
            iBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myBoard.fireAt(finalI);
                    myBoard.normalize();
        updateViewAll();
        removeOnClickListeners();
        Communication.opTurn(myBoard);;
                }
            });
        }
    }

    /**
     * fires at an area
     * when a square is clicked the square itself and 2 suare radus is burnt out
     * can be fired at anywhere on the grid
     * can only be used once
     */
    private void bombard() {
        setInfo("bombard");
        removeOnClickListeners();
        for (int i= 0; i< squares.length; i++) {
            ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            final int finalI = i;
            iBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList circle = myBoard.getCircle(finalI);
                    for (int j = 0; j < circle.size(); j++){
                        myBoard.fireAt((int) circle.get(j));
                        findViewById(R.id.bombard).setEnabled(false);
                    }
                    myBoard.normalize();
                    updateViewAll();
                    removeOnClickListeners();
                    Communication.opTurn(myBoard);
                }
            });
        }
    }

    /**
     * highlights a radius area around one of my ships
     * if there are any enemy ships, their state is changed to opponent ship displayed
     */
    private void radar() {
        setInfo("radar");
        syncBoard();
        removeOnClickListeners();
        for (int i = 0; i < squares.length; i++) {
            final ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            if (myBoard.getSquareType(i) == SquareType.SHIP) {
                final int radarPos = i;
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList circle = myBoard.getCircle(radarPos);
                        for (int j = 0; j < circle.size(); j++){
                            int square = (int) circle.get(j);
                            myBoard.highLight(square, true);
                            updateView(square);
                            removeOnClickListeners();
                        }
                    }
                });
            }
        }
    }

    /**
     * makes my ships invisible to enemy radar
     */
    private void invisible() {
        setInfo("invisible");
        myBoard.invisible = true;
        myBoard.normalize();
        updateViewAll();
        removeOnClickListeners();
        Communication.opTurn(myBoard);;
    }

    /**
     * removes onClick event listeners on all the squares of the grid
     */
    private void removeOnClickListeners() {
        for (int square : squares) {
            ImageButton iBtn = (ImageButton) findViewById(square);
            iBtn.setOnClickListener(null);
        }
    }

    /**
     * loads last activity
     */
    private void lastActivity() {
        Intent intent = new Intent(this, LastActivity.class);
        TextView textMsg = (TextView) findViewById(R.id.goodByMsg);
        intent.putExtra(StartActivity.NAME_EXTRA, name);
        startActivity(intent);
    }
}
