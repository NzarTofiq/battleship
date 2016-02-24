package com.nzartofiq.battleship;

/**
 * Created by nzartofiq on 24/02/16.
 */
public enum SquareType {
    FREE(0), USED(1), SHIP(2), WRECK(3);

    private final int ID;

    SquareType(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
