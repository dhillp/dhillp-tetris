/*
 * Pamaldeep Singh Dhillon
 * TetrisPanel.java
 * 
 * TCSS 305 - Autumn 2015
 * Assignment 6 - Tetris
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.AbstractPiece;
import model.Block;
import model.Board;

/**
 * Panel that draws the Tetris board on to the GUI.
 * 
 * @author Pamaldeep Dhillon
 * @version 11 December 2015
 */
public final class TetrisPanel extends JPanel implements Observer {
    /** A generated serialization ID. */
    private static final long serialVersionUID = -7778642252688582633L;
    /** Width of panel when small size is selected. */
    private static final int SMALL_WIDTH = 200;
    /** Height of panel when small size is selected. */
    private static final int SMALL_HEIGHT = 380;
    /** Block size when small size is selected. */
    private static final int SMALL_BLOCK_SIZE = 20;
    /** Offset for game over string when small size is selected. */
    private static final int SMALL_OFFSET = 6;
    /** Font size for game over string when small size is selected. */
    private static final int SMALL_FONT = 30;
    /** Width of panel when medium size is selected. */
    private static final int MEDIUM_WIDTH = 300;
    /** Height of panel when medium size is selected. */
    private static final int MEDIUM_HEIGHT = 570;
    /** Block size when medium size is selected. */
    private static final int MEDIUM_BLOCK_SIZE = 30;
    /** Offset for game over string when medium size is selected. */
    private static final int MEDIUM_OFFSET = 7;
    /** Font size for game over string when medium size is selected. */
    private static final int MEDIUM_FONT = 45;
    /** Width of panel when large size is selected. */
    private static final int LARGE_WIDTH = 400;
    /** Height of panel when large size is selected. */
    private static final int LARGE_HEIGHT = 760;
    /** Block size when small large is selected. */
    private static final int LARGE_BLOCK_SIZE = 40;
    /** Offset for game over string when large size is selected. */
    private static final int LARGE_OFFSET = 10;
    /** Font size for game over string when large size is selected. */
    private static final int LARGE_FONT = 60;
    /** Number of rows on the Tetris board. */
    private static final int ROW_SIZE = 10;
    /** The board on which the game is being played. */
    private final Board myBoard;
    /** Color for the Tetris piece. */
    private Color myColor;
    /** Width of the panel. */
    private int myWidth;
    /** Height of the panel. */
    private int myHeight;
    /** Size of the blocks. */
    private int myBlockSize;
    /** Offset for the Game Over string. */
    private int myOffset;
    /** Font size for the Game Over string. */
    private int myFontSize;
    /** Boolean that is true when user ends the game, false otherwise.*/
    private boolean myGameEnded;
    /**
     * Constructs the TetrisPanel and initializes fields.
     * 
     * @param theBoard Board on which Tetris game is played.
     */
    public TetrisPanel(final Board theBoard) {
        super();
        myBoard = theBoard;
        myWidth = SMALL_WIDTH;
        myHeight = SMALL_HEIGHT;
        myBlockSize = SMALL_BLOCK_SIZE;
        myOffset = SMALL_OFFSET;
        myFontSize = SMALL_FONT;
        myGameEnded = false;
        setPreferredSize(new Dimension(myWidth, myHeight + myBlockSize));
        setBackground(Color.BLACK);
    }
    /**
     * Changes the size of the panel and blocks based on user input in the menu of the GUI.
     * 0 means small size, 1 means medium and 2 means large.
     * 
     * @param theSize Integer(0,1,2) that correlates to a panel size.
     */
    public void setNewSize(final int theSize) {
        if (theSize == 0) {
            myWidth = SMALL_WIDTH;
            myHeight = SMALL_HEIGHT;
            myBlockSize = SMALL_BLOCK_SIZE;
            myOffset = SMALL_OFFSET;
            myFontSize = SMALL_FONT;
        } else if (theSize == 1) {
            myWidth = MEDIUM_WIDTH;
            myHeight = MEDIUM_HEIGHT;
            myBlockSize = MEDIUM_BLOCK_SIZE;
            myOffset = MEDIUM_OFFSET;
            myFontSize = MEDIUM_FONT;
        } else if (theSize == 2) {
            myWidth = LARGE_WIDTH;
            myHeight = LARGE_HEIGHT;
            myBlockSize = LARGE_BLOCK_SIZE;
            myOffset = LARGE_OFFSET;
            myFontSize = LARGE_FONT;
        }
        setPreferredSize(new Dimension(myWidth, myHeight + myBlockSize));
    }
    
    /**
     * 
     * @param theGameEnded True when game is ended by user, false otherwise.
     */
    public void endGame(final boolean theGameEnded) {
        myGameEnded = theGameEnded;
        repaint();
    }
    
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Draw dashed grid behind blocks.
        g2d.setPaint(Color.WHITE);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                                      new float[]{2}, 0));
        for (int i = 0; i < getWidth(); i += myBlockSize) {
            g2d.drawLine(i, 0, i, getHeight());
        }
        for (int j = 0; j < getHeight(); j += myBlockSize) {
            g2d.drawLine(0, j, getWidth(), j);
        }
        
        //Draw the current piece
        final AbstractPiece currentPiece = (AbstractPiece) myBoard.getCurrentPiece();
        setColor(currentPiece.getBlock());
        g2d.setColor(myColor);
        final int[][] pCoords = currentPiece.getBoardCoordinates();
        for (int i = 0; i < pCoords.length; i++) {
            g2d.fillRect(pCoords[i][0] * myBlockSize, myHeight - pCoords[i][1] * myBlockSize,
                         myBlockSize, myBlockSize);
        }
        
        //Draw the frozen blocks
        final List<Block[]> frozen = myBoard.getFrozenBlocks();
        for (int j = 0; j < frozen.size(); j++) {
            for (int k = 0; k < ROW_SIZE; k++) {
                if (frozen.get(j)[k] != Block.EMPTY) {
                    setColor(frozen.get(j)[k]);
                    g2d.setColor(myColor);
                    g2d.fillRect(k * myBlockSize, myHeight - j * myBlockSize, myBlockSize,
                                 myBlockSize);
                }
            }
        }
        
        //Draw GAME OVER string if game is over
        if (myBoard.isGameOver() || myGameEnded) {
            g2d.setColor(Color.GRAY);
            g2d.setFont(new Font("TimesRoman", Font.BOLD, myFontSize));
            g2d.drawString("GAME OVER", myOffset, myWidth);
        }
    }
    
    /**
     * Sets the color for the graphics based on the block. I blocks are cyan, J blocks are
     * blue, L blocks are orange, O blocks are yellow, S blocks are green, T blocks are
     * magenta and Z blocks are red.
     * 
     * @param theBlock Block that will decide the color to be used by paintComponent.
     */
    private void setColor(final Block theBlock) {
        if (theBlock == Block.I) {
            myColor = Color.CYAN;
        } else if (theBlock == Block.J) {
            myColor = Color.BLUE;
        } else if (theBlock == Block.L) {
            myColor = Color.ORANGE;
        } else if (theBlock == Block.O) {
            myColor = Color.YELLOW;
        } else if (theBlock == Block.S) {
            myColor = Color.GREEN;
        } else if (theBlock == Block.T) {
            myColor = Color.MAGENTA;
        } else if (theBlock == Block.Z) {
            myColor = Color.RED;
        }
    }

    @Override
    public void update(final Observable theObservable, final Object theArg) {
        repaint();
    }
}
