package com.nzartofiq.battleship;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import static com.nzartofiq.battleship.R.string.torpedo_desc;

//import com.google.gson.Gson;

//import java.util.ArrayList;

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
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
//    private Gson boardObject;
//    private ArrayList circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Intent intent = getIntent();
        name = intent.getStringExtra(StartActivity.NAME_EXTRA);
        setUpActionClicks();
        updateViewAll();
        setupBtnClicks();
//        boardObject = new Gson();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        testCircle();
    }

    public void updateViewAll() {
        for (int i = 0; i < squares.length; i++) {
            syncBoard(i);
            ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            switch (myBoard.getSquareType(i)) {
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
    }
    private void testCircle() {
        ArrayList circle = myBoard.getCircle(14);
        for (int i = 0; i<circle.size(); i++){
            myBoard.updateBoard((Integer) circle.get(i));
            updateViewAll();
        }
    }
    private void syncBoard(int i) {
        if (opBoard.getSquareType(i) != SquareType.SHIP && myBoard.getSquareType(i) != opBoard.getSquareType(i)) {
            myBoard.setSquareType(i, opBoard.getSquareType(i));
        }
    }

    private void setupBtnClicks() {
        for (final int square : squares) {
            findViewById(square).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton iBtn = (ImageButton) v;
                    for (int i = 0; i < squares.length; i++) {
                        if (square == iBtn.getId()) {
                            myBoard.updateBoard(i);
                            updateViewAll();
                            iBtn.setOnClickListener(null);
                        }
                    }
                    if (myBoard.checkWin()) {
                        lastActivity();
                    }
                }
            });
        }
    }

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
                            setupBtnClicks();
                    }
                }
            });
        }
    }

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
                desc.setText(String.format(getString(torpedo_desc), Board.CIRCLE_RADIUS));
                break;
            case "missile":
                img.setImageResource(R.drawable.missile75_75);
                header.setText(R.string.missile);
                desc.setText(R.string.missile_desc);
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

    private void move() {
        setInfo("move");
        updateViewAll();
        removeOnClickListeners();
        for (int i = 0; i < squares.length; i++) {
            final ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            final int oldPos = i;
            if (myBoard.getSquareType(i) == SquareType.SHIP) {
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageButton shipBtn = (ImageButton) view;
                        moveNextClick(shipBtn);
                        myBoard.setSquareType(oldPos, SquareType.FREE);
                        updateViewAll();
                    }
                });
            }
        }
    }

    private void moveNextClick(View v) {
        for (int i = 0; i < squares.length; i++) {
            ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            removeOnClickListeners();
            final int newPos = i;
            if (myBoard.getSquareType(i) == SquareType.FREE) {
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myBoard.setSquareType(newPos, SquareType.SHIP);
                        updateViewAll();
                        view.setOnClickListener(null);
                        removeOnClickListeners();

                    }
                });
            }
        }
    }

    private void torpedo() {
        setInfo("torpedo");
        removeOnClickListeners();
        for (int i = 0; i < squares.length; i++) {
            final ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            if (myBoard.getSquareType(i) == SquareType.SHIP) {
                final int finalI = i;
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        torpedoNextClick(iBtn, finalI);
                    }
                });
            }
        }
    }

    private void torpedoNextClick(ImageButton shipBtn, int pos) {
        ArrayList circle = myBoard.getCircle(pos);
        updateViewAll();
        for (int i = 0; i < circle.size(); i++) {
            ImageButton iBtn = (ImageButton) findViewById(squares[i]);
            if (shipBtn != iBtn) {
                Log.d("available btns: ", String.valueOf(i));
                final int hitPos = i;
                myBoard.setSquareType(i, SquareType.USED);
                updateViewAll();
                iBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myBoard.updateBoard(hitPos);
                        updateViewAll();
                        removeOnClickListeners();
                    }
                });
            }
        }
    }

    private void missile() {
        setInfo("missile");
        setupBtnClicks();
    }

    private void radar() {
        setInfo("radar");
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
                            if(opBoard.getSquareType(j) == SquareType.SHIP && !opBoard.invisible){
                                myBoard.setSquareType(j, SquareType.SHIP);
                            }
                        }
                        updateViewAll();
                        removeOnClickListeners();
                    }
                });
            }
        }
    }

    private void invisible() {
        setInfo("invisible");
        myBoard.invisible = true;
    }

    private void bombard() {
        setInfo("bombard");
        removeOnClickListeners();
        for (final int square : squares) {
            ImageButton iBtn = (ImageButton) findViewById(square);
            iBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList circle = myBoard.getCircle(square);
                    for (int j = 0; j > circle.size(); j++){
                        myBoard.updateBoard(j);
                    }
                }
            });
            updateViewAll();
        }
    }

    private void removeOnClickListeners() {
        for (int square : squares) {
            ImageButton iBtn = (ImageButton) findViewById(square);
            iBtn.setOnClickListener(null);
        }
    }

    private void lastActivity() {
        Intent intent = new Intent(this, LastActivity.class);
        TextView textMsg = (TextView) findViewById(R.id.goodByMsg);
        intent.putExtra(StartActivity.NAME_EXTRA, name);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.nzartofiq.battleship/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.nzartofiq.battleship/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
