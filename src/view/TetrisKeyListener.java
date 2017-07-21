/*
 * Pamaldeep Singh Dhillon
 * TetrisKeyListener.java
 * 
 * TCSS 305 - Autumn 2015
 * Assignment 6 - Tetris
 */
package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.Board;

/**
 * Handler for Key Events.
 * 
 * @author Pamaldeep Dhillon
 * @version 11 December 2015
 */
public class TetrisKeyListener extends KeyAdapter {
    /** Board on which the game is played. */
    private final Board myBoard;
    /** The timer for the game. */
    private final TetrisTimer myTimer;
    
    /**
     * Constructor that initializes the fields.
     * 
     * @param theBoard Board on which the game is played.
     * @param theTimer The timer for the game.
     */
    public TetrisKeyListener(final Board theBoard, final TetrisTimer theTimer) {
        super();
        myBoard = theBoard;
        myTimer = theTimer;
    }
    
    @Override
    public void keyPressed(final KeyEvent theEvent) {
        if (myTimer.isTimerStarted()) {
            if (theEvent.getKeyCode() == KeyEvent.VK_A) {
                myBoard.moveLeft();
            } else if (theEvent.getKeyCode() == KeyEvent.VK_D) {
                myBoard.moveRight();
            } else if (theEvent.getKeyCode() == KeyEvent.VK_S) {
                myBoard.moveDown();
            } else if (theEvent.getKeyCode() == KeyEvent.VK_W) {
                myBoard.rotate();
            } else if (theEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                myBoard.hardDrop();
            } else if (theEvent.getKeyCode() == KeyEvent.VK_P) {
                myTimer.stop();
            }
        } else {
            if (theEvent.getKeyCode() == KeyEvent.VK_P) {
                myTimer.start();
            }
        }
    }
}
