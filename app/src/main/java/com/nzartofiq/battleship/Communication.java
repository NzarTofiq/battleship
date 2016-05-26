package com.nzartofiq.battleship;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

/**
 * Created by nzartofiq on 18/05/16.
 */
public class Communication {

    private static Gson gson;

    /**
     *
     * @param myBoard
     */
    public static void opTurn(Board myBoard) {
        gson = new Gson();
        gson.toJson(myBoard);
    }
}
