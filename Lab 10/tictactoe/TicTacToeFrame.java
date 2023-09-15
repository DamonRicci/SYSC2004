import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


/**
 * A class modelling a tic-tac-toe (noughts and crosses, Xs and Os) game in a very
 * simple GUI window.
 * 
 * @author Lynn Marshall and Damon Ricci 101229913
 * @version November 8, 2012
 */

public class TicTacToeFrame extends TicTacToe implements ActionListener {
    private JFrame frame;
    private JButton[][] buttons;
    private JLabel statusLabel;
    private ImageIcon xIcon;
    private ImageIcon oIcon;
    private GameMode gameMode;
    private Random random = new Random();
    private int lastMoveRow;
    private int lastMoveCol;
    private int xWins = 0;
    private int oWins = 0;
    private int ties = 0;
    private JMenuItem scoreMenuItem;
    private boolean gameEnded;
    private String startingPlayer = PLAYER_X;
    private JMenuItem xFirstItem;
    private JMenuItem oFirstItem;

    /**
     * Constructs a new TicTacToeFrame object, initializing the GUI components and setting up the
     * game board. The constructor creates a JFrame with a BorderLayout, sets up the game board
     * panel, creates and adds menu items for different game modes and actions, and initializes
     * the game board state by calling the clearBoard() method.
     */
    public TicTacToeFrame() {
        gameMode = GameMode.PLAYER_VS_PLAYER;
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        buttons = new JButton[3][3];
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setBackground(Color.BLUE);
        
        boardPanel.setPreferredSize(new Dimension(600, 600));
        
        xIcon = new ImageIcon("x_image.png");
        oIcon = new ImageIcon("o_image.png");
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].addActionListener(this);
                buttons[i][j].setContentAreaFilled(false);
                buttons[i][j].setBackground(Color.BLUE);
                buttons[i][j].putClientProperty("row", i);
                buttons[i][j].putClientProperty("col", j);
                boardPanel.add(buttons[i][j]);
            }
        }
        
        frame.add(boardPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Game in progress - X's turn");
        frame.add(statusLabel, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newItem = new JMenuItem("New");
        newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        newItem.addActionListener(e -> newGame());
        gameMenu.add(newItem);

        JMenu modeMenu = new JMenu("Mode");
        
        // Adds the new menu for choosing the starting player
        JMenu firstPlayerMenu = new JMenu("First Player");
        xFirstItem = new JMenuItem("X");
        oFirstItem = new JMenuItem("O");
    
        xFirstItem.addActionListener(e -> {
            startingPlayer = PLAYER_X;
            if (gameMode == GameMode.PLAYER_VS_PLAYER) {
                newGame();
            }
        });
    
        oFirstItem.addActionListener(e -> {
            startingPlayer = PLAYER_O;
            if (gameMode == GameMode.PLAYER_VS_PLAYER) {
                newGame();
            }
        });
    
        firstPlayerMenu.add(xFirstItem);
        firstPlayerMenu.add(oFirstItem);
    
        menuBar.add(gameMenu);
        menuBar.add(modeMenu);
        frame.setJMenuBar(menuBar);
        
        xFirstItem.setEnabled(true);
        oFirstItem.setEnabled(true);

        
        //ADD THE DIFFERENT MODES
        JMenuItem pvpItem = new JMenuItem("1v1");
        pvpItem.addActionListener(e -> {
            gameMode = GameMode.PLAYER_VS_PLAYER;
            xFirstItem.setEnabled(true);
            oFirstItem.setEnabled(true);
            newGame();
        });
        modeMenu.add(pvpItem);
        
        JMenuItem easyBotItem = new JMenuItem("Easy Bot");
        easyBotItem.addActionListener(e -> {
            gameMode = GameMode.EASY_BOT;
            xFirstItem.setEnabled(false);
            oFirstItem.setEnabled(false);
            startingPlayer = PLAYER_X;
            newGame();
        });
        modeMenu.add(easyBotItem);
        
        JMenuItem hardBotItem = new JMenuItem("Hard Bot");
        hardBotItem.addActionListener(e -> {
            gameMode = GameMode.HARD_BOT;
            xFirstItem.setEnabled(false);
            oFirstItem.setEnabled(false);
            startingPlayer = PLAYER_X;
            newGame();
        });
        modeMenu.add(hardBotItem);
        
        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        quitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(quitItem);
        
        scoreMenuItem = new JMenuItem("X Wins: " + xWins + " | O Wins: " + oWins + " | Ties: " + ties);
        scoreMenuItem.setEnabled(false); // Disable the item so users cannot click on it.
        gameMenu.add(scoreMenuItem);

        menuBar.add(gameMenu);
        menuBar.add(modeMenu);
        menuBar.add(firstPlayerMenu); // Adds the new menu to the menu bar
        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        clearBoard();
    }
    
    /**
     * Handles button clicks on the game board. This method is called when a button on the game
     * board is clicked. It processes the player's move, checks for a winner or a tie, updates
     * the board state and UI, and, if applicable, triggers the bot's move based on the current
     * game mode.
     *
     * @param e An ActionEvent object containing information about the button click event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (winner != EMPTY) {
            return;
        }
    
        JButton source = (JButton) e.getSource();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (source == buttons[i][j] && source.getText().equals(EMPTY)) {
                    playSound("moveSound.wav");
                    board[i][j] = player;
                    if (player == PLAYER_X) {
                        source.setIcon(xIcon);
                    } else {
                        source.setIcon(oIcon);
                    }
                    source.setEnabled(false);
                    numFreeSquares--;
    
                    if (haveWinner(i, j)) {
                        winner = player;
                    } else if (numFreeSquares == 0) {
                        winner = TIE;
                    }
    
                    player = (player == PLAYER_X) ? PLAYER_O : PLAYER_X;
    
                    if (winner == EMPTY && gameMode != GameMode.PLAYER_VS_PLAYER) {
                        if (player == PLAYER_O) {
                            botMove();
                            player = PLAYER_X;
                        }
                    }
                    updateStatusLabel();
                }
            }
        }
    }
    
    /**
     * Sets the game mode to the specified mode, enables or disables the 'X' and 'O' first move options based on the game mode,
     * and starts a new game.
     *
     * @param gameMode The game mode to set (e.g., GameMode.PLAYER_VS_PLAYER, GameMode.PLAYER_VS_COMPUTER)
     */
    private void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        xFirstItem.setEnabled(gameMode == GameMode.PLAYER_VS_PLAYER);
        oFirstItem.setEnabled(gameMode == GameMode.PLAYER_VS_PLAYER);
        newGame();
    }

    /**
     * Resets the game state and initializes a new game. This method clears the board,
     * enables all the buttons, and updates the status label.
     */
    private void newGame() {
        clearBoard();
        player = startingPlayer;
        gameEnded = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText(EMPTY);
                buttons[i][j].setEnabled(true);
            }
        }
        updateStatusLabel();
    }
    
    /**
     * Updates the status label with the current game state. The label displays the current
     * game mode, the status of the game (in progress, tie, or a player has won), and the
     * current player's turn.
     */
    private void updateStatusLabel() {
        if (gameEnded) {
            return;
        }
        
        String gameModeText = "Mode: " + gameMode.getDisplayName() + " - ";
        
        if (winner == EMPTY) {
            statusLabel.setText(gameModeText + "Game in progress - " + player + "'s turn");
        } else if (winner == TIE) {
            statusLabel.setText(gameModeText + "It's a tie");
            ties++;
            gameEnded = true;
        } else {
            statusLabel.setText(gameModeText + winner + " wins");
            if (winner == PLAYER_X) {
                xWins++;
            } else if (winner == PLAYER_O) {
                oWins++;
            }
            playSound("winner.wav");
            gameEnded = true;
        }
        
        // Update the score menu item with the latest counts for X wins, O wins, and ties
        scoreMenuItem.setText("X Wins: " + xWins + " | O Wins: " + oWins + " | Ties: " + ties);
    }

    /**
     * Clears the game board, resets the icons and buttons, and updates the status label.
     * This method extends the superclass's clearBoard method and additionally updates
     * the buttons and status label.
     */
    @Override
    protected void clearBoard() {
        super.clearBoard();
        if (buttons != null) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setText(EMPTY);
                    buttons[i][j].setIcon(null);
                    buttons[i][j].setEnabled(true);
                }
            }
        }
        updateStatusLabel();
    }
    
    /**
     * Plays a sound file given its file name. The method loads the audio clip from the
     * resource folder and plays it once.
     *
     * @param soundFileName The name of the sound file to play.
     */
    private void playSound(String soundFileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource(soundFileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Enum representing the different game modes available in Tic Tac Toe: player versus
     * player, player versus easy bot, and player versus hard bot.
     */
    public enum GameMode {
        PLAYER_VS_PLAYER("1v1"),
        EASY_BOT("Easy Bot"),
        HARD_BOT("Hard Bot");
    
        private final String displayName;
    
        GameMode(String displayName) {
            this.displayName = displayName;
        }
    
        public String getDisplayName() {
            return displayName;
        }
    }
    
    /**
     * Makes a move for the bot based on the current game mode. The bot will either
     * make an easy or hard move depending on the selected game mode.
     */
    private void botMove() {
        switch (gameMode) {
            case EASY_BOT:
                easyBotMove();
                break;
            case HARD_BOT:
                hardBotMove();
                break;
            default:
                // Do nothing for PLAYER_VS_PLAYER mode
                break;
        }
    }
    
    /**
     * Makes an easy move for the bot by randomly selecting an empty square.
     */
    private void easyBotMove() {
        // Make a random move
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!board[row][col].equals(EMPTY));
    
        board[row][col] = PLAYER_O;
        buttons[row][col].setIcon(oIcon);
        buttons[row][col].setEnabled(false);
        numFreeSquares--;
    
        if (haveWinner(row, col)) {
            winner = PLAYER_O;
        } else if (numFreeSquares == 0) {
            winner = TIE;
        }
    
        updateStatusLabel();
    }
    
    /**
     * Makes a hard move for the bot by attempting to win, block the opponent,
     * take the center square, or default to an easy move.
     */
    private void hardBotMove() {
        // Check if the bot can win with its next move.
        if (tryToWin()) {
            if (haveWinner(lastMoveRow, lastMoveCol)) {
                winner = PLAYER_O;
            }
            updateStatusLabel();
            return;
        }
    
        // Check if the bot can block the player's winning move.
        if (tryToBlock()) {
            if (haveWinner(lastMoveRow, lastMoveCol)) {
                winner = PLAYER_O;
            }
            updateStatusLabel();
            return;
        }
    
        // If the center square is open, take it.
        if (board[1][1] == EMPTY) {
            board[1][1] = PLAYER_O;
            updateSquare(1, 1);
            if (haveWinner(1, 1)) {
                winner = PLAYER_O;
            }
            updateStatusLabel();
            return;
        }
    
        // Otherwise, play an easy move.
        easyBotMove();
    }
    
    //HARD BOT LOGIC
    /**
     * Tries to block the opponent's winning move by checking if the player
     * can win on the next move and placing the bot's symbol in that square.
     *
     * @return true if a blocking move was made, false otherwise.
     */
    private boolean tryToBlock() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER_X;
                    if (haveWinner(i, j)) {
                        board[i][j] = PLAYER_O;
                        updateSquare(i, j);
                        return true;
                    }
                    board[i][j] = EMPTY;
                }
            }
        }
        return false;
    }
    
    /**
     * Tries to make a winning move for the bot by checking if there is a square
     * that would result in a win when the bot's symbol is placed there.
     *
     * @return true if a winning move was made, false otherwise.
     */
    private boolean tryToWin() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    board[i][j] = PLAYER_O;
                    if (haveWinner(i, j)) {
                        updateSquare(i, j);
                        return true;
                    }
                    board[i][j] = EMPTY;
                }
            }
        }
        return false;
    }
    
    /**
     * Updates the square at the given row and column with the bot's symbol
     * and disables the button associated with the square.
     *
     * @param i The row index of the square to update.
     * @param j The column index of the square to update.
     */
    private void updateSquare(int i, int j) {
        buttons[i][j].setIcon(oIcon);
        buttons[i][j].setEnabled(false);
        numFreeSquares--;
    
        lastMoveRow = i;
        lastMoveCol = j;
    }

    public static void main(String[] args) {
        new TicTacToeFrame();
    }
}