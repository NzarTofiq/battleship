package com.nzartofiq.battleship;

/**
 * Created by nzartofiq on 20/05/16.
 */
public class Link {
    private static Link ourInstance = new Link();

    public static Link getInstance() {
        return ourInstance;
    }

    private Link() {
    }
}
