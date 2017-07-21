/*
 * Pamaldeep Singh Dhillon
 * TetrisTimer.java
 * 
 * TCSS 305 - Autumn 2015
 * Assignment 6 - Tetris
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Block;
import model.Board;

/**
 * Creates an animation timer that updates the board every second.
 * 
 * @author Pamaldeep Dhillon
 * @version 11 December 2015
 */
public final class TetrisTimer {
    /** Initial delay of 1 second for when the GUI is started. */
    private static final int INITIAL_DELAY = 1000;
    /** 1 second delay for animation. */
    private static final int MOVE_DELAY = 1000;
    /** Score for clearing one row. */
    private static final int ONE_SCORE = 40;
    /** Score for clearing two rows. */
    private static final int TWO_SCORE = 100;
    /** Score for clearing three rows. */
    private static final int THREE_SCORE = 300;
    /** Score for clearing four rows. */
    private static final int FOUR_SCORE = 1200;
    /** Three rows cleared. */
    private static final int THREE_ROWS = 3;
    /** Four rows cleared. */
    private static final int FOUR_ROWS = 4;
    /** Fifty rows cleared. */
    private static final int FIFTY_ROWS = 50;
    /** Number of blocks in a row (10). */
    private static final int BLOCK_COUNT = 10;
    /** Max level for the game. */
    private static final int LVL_FIFTEEN = 15;
    /** Speed change for each level. */
    private static final int SPEED_CHANGE = 50;
    /** The animation timer. */
    private final Timer myTimer;
    /** The timer for the score/level/rows counter. */
    private final Timer myScoreTimer;
    /** True if timer is started, false otherwise. */
    private boolean myTimerStarted;
    /** Score for the game. */
    private int myScore;
    /** Current level in the game. */
    private int myLevel;
    /** Current number of frozen blocks. */
    private int myNewCount;
    /** Previous number of frozen blocks. */
    private int myOldCount;
    /** Number of rows cleared. */
    private int myRowsCleared;
    /** Number of rows needed to be cleared to reach the next level. */
    private int myRowsUntilLvl;
    /**
     * Constructs the timer.
     * 
     * @param theBoard Board on which game is played.
     */
    public TetrisTimer(final Board theBoard) {
        myTimer = new Timer(MOVE_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                theBoard.step();
            }
        });
        myScoreTimer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myNewCount = 0;
                for (int i = theBoard.getFrozenBlocks().size() - 1; i >= 0; i--) {
                    for (final Block b : theBoard.getFrozenBlocks().get(i)) {
                        if (b != Block.EMPTY) {
                            myNewCount++;
                        }
                    }
                }
                if (myNewCount < myOldCount) {
                    calculateScore();
                }
                myOldCount = myNewCount;
                myTimer.setDelay(levelSpeed());
                myRowsUntilLvl = (FIFTY_ROWS + (myLevel - 1) * BLOCK_COUNT) - myRowsCleared;
            }
        });
        myTimer.setInitialDelay(INITIAL_DELAY);
    }
    /**
     * Calculates the score.
     */
    private void calculateScore() {
        final int rowsCleared = ((myOldCount + FOUR_ROWS) - myNewCount) / BLOCK_COUNT;
        if (rowsCleared == 1) {
            myScore += ONE_SCORE * myLevel;
        } else if (rowsCleared == 2) {
            myScore += TWO_SCORE * myLevel;
        } else if (rowsCleared == THREE_ROWS) {
            myScore +=  THREE_SCORE * myLevel;
        } else if (rowsCleared == FOUR_ROWS) {
            myScore += FOUR_SCORE * myLevel;
        }
        myRowsCleared += rowsCleared;
        calculateLevel();
    }
    /**
     * Calculates the level.
     */
    private void calculateLevel() {
        if (myRowsCleared >= ONE_SCORE && myLevel < LVL_FIFTEEN) {
            myLevel = (myRowsCleared - (ONE_SCORE - BLOCK_COUNT)) / BLOCK_COUNT;
        } else if (myLevel == LVL_FIFTEEN) {
            myLevel = LVL_FIFTEEN;
        }
    }
    /**
     * @return Current speed for animation.
     */
    private int levelSpeed() {
        int result = MOVE_DELAY;
        if (myLevel < LVL_FIFTEEN) {
            result = MOVE_DELAY - (myLevel * SPEED_CHANGE);
        } else {
            result = MOVE_DELAY - (LVL_FIFTEEN * SPEED_CHANGE);
        }
        return result;
    }
    /**
     * @return The current level for the game.
     */
    public int getLevel() {
        return myLevel;
    }
    /**
     * @return The current score for the game.
     */
    public int getScore() {
        return myScore;
    }
    /**
     * @return The current number of rows cleared.
     */
    public int getRowsCleared() {
        return myRowsCleared;
    }
    /**
     * @return The remaining rows needed to be cleared until the next level.
     */
    public int getRowsUntilNextLevel() {
        int result = myRowsUntilLvl;
        if (myLevel < LVL_FIFTEEN) {
            result = myRowsUntilLvl;
        } else {
            result = 0;
        }
        return result;
    }
    /**
     * Starts the timer.
     */
    public void start() {
        myTimerStarted = true;
        myTimer.start();
        myScoreTimer.start();
    }
    /**
     * Stops the timer.
     */
    public void stop() {
        myTimerStarted = false;
        myTimer.stop();
        myScoreTimer.stop();
    }
    /**
     * Restarts the timer.
     */
    public void restart() {
        myTimerStarted = true;
        myTimer.restart();
        myScoreTimer.restart();
        myScore = 0;
        myLevel = 1;
        myRowsCleared = 0;
        myRowsUntilLvl = FIFTY_ROWS;
        myOldCount = 0;
    }
    /**
     * @return Whether or not timer is started.
     */
    public boolean isTimerStarted() {
        return myTimerStarted;
    }
}
