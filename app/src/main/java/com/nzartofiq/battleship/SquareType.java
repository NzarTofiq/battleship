package com.nzartofiq.battleship;

/**
 * Created by nzartofiq on 24/02/16.
 * Defines the state of a Square
 */
public enum SquareType {
    /**
     * a square on the grid contains only one of these
     * FREE: the square does not contain anything
     * USED: square was fired at, when it ws empty/FREE
     * SHIP: square contains one of my Ships
     * WRECK: square was fired at when it contained one of my ships
     * AVAILABLE: square is available to hit with a torpedo OR
     * AVAILABLE: square is checked with radas and it does not contain anything
     * OP_WRECK: square contains an opponents ship-wreck
     * OP_SHIP: square contains an opponents ship but is not displayed
     * OP_SHIP_DISP: square contains an opponents ship but can be displayed with my radar
     */
    FREE, USED, SHIP, WRECK, AVAILABLE, OP_WRECK, OP_SHIP, OP_SHIP_DISP
}
