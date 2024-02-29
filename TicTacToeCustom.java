import java.util.Scanner;

/**
 * This class is for the custom game
 *
 * @Author Nguyen Mai Lan Vo
 */
public class TicTacToeCustom {


    public char[][] board; // //declares a public two-dimensional array that can store characters (char) in Java.
    public boolean playerTurn; //// Boolean variable to track the current player's turn. 

    /**
     * Constructor for the TicTacToeCustom class
     */
    public TicTacToeCustom(int SIZE) {
        board = new char[SIZE][SIZE]; // Create the game board with the specified size
        playerTurn = true; // Set the initial player's turn to true
        initializeBoard(SIZE);// Initialize the game board with empty values
    }

    /**
     * Initialize the board
     */
    public void initializeBoard(int SIZE) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Player.EMPTY_SPACE; //// Set each cell on the board to empty space
            }
        }
    }

    /**
     * This method print the board
     */
    public void printBoard(int SIZE) {
        // Print column numbers on top
        System.out.print(" ");
        for (int i = 0; i < SIZE; i++) {
            System.out.print("   " + (i + 1));
        }
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            // Print row number on the side
            System.out.print((i + 1));

            for (int j = 0; j < SIZE; j++) {
                System.out.print(" | " + board[i][j]);
            }
            System.out.println(" |");

            // Print separating line
            if (i != SIZE - 1) {
                System.out.print("  ");
                for (int j = 0; j < SIZE; j++) {
                    System.out.print("----");
                }
                System.out.println();
            }
        }
    }



    /**
     * This method check if the board is full
     */
    public boolean isBoardFull(int SIZE) { // Check if the board is full
        for (int i = 1; i < SIZE; i++) { // Loop through each row and column
            for (int j = 1; j <= SIZE; j++) {
                if (board[i][j] == Player.EMPTY_SPACE) { // If there is an empty space, return false
                    return false;
                }
            }
        }
        return true; // If there are no empty spaces, return true
    }


    /**
     * This method check if the player has won
     */
    public boolean hasPlayerWon(int SIZE) {
        char mark; // Get the player's mark
        if (playerTurn) {
            mark = Player.HUMAN_PLAYER;
        } else {
            mark = Player.AI_PLAYER;
        }


        for (int i = 0; i < SIZE; i++) { // Loop through the board to check for wins in rows and columns
            boolean rowWin = true, colWin = true; // Initialize rowWin and colWin to true
            for (int j = 0; j < SIZE; j++) { // Loop through each row and column
                rowWin &= (board[i][j] == mark); // Check if the mark is in each cell of the row
                colWin &= (board[j][i] == mark); // Check if the mark is in each cell of the column
            }
            if (rowWin || colWin) return true; // If either rowWin or colWin is true, return true
        }


        boolean diagWin1 = true, diagWin2 = true; // Initialize win check for diagonals
        for (int i = 0; i < SIZE; i++) { // Loop through the board to check for wins in diagonals
            diagWin1 &= (board[i][i] == mark); // Check if the mark is in each cell of the diagonal
            diagWin2 &= (board[i][SIZE - 1 - i] == mark); // Check if the mark is in each cell of the other diagonal
        }
        return diagWin1 || diagWin2; // If either diagonal win is true, return true
    }


    /**
     * This method check if move is valid
     */
    public boolean isValidMove(int row, int col, int SIZE) {
        if (row < 1 || row >= SIZE || col < 1 || col >= SIZE) { // Check if the row and column are valid
            return false;
        }
        return board[row][col] == Player.EMPTY_SPACE;
    }

    /**
     * This method make a move
     */
    public void makeMove(int row, int col) { // Make a move
        char mark = playerTurn ? Player.HUMAN_PLAYER : Player.AI_PLAYER; // Get the player's mark
        board[row][col] = mark; // Set the mark on the board
        playerTurn = !playerTurn; // Switch the player's turn
    }

    /**
     * this method start the game
     */
    public void play(int SIZE) {
        Scanner scanner = new Scanner(System.in); // Create a scanner to read input

        while (!isBoardFull(SIZE)) { // Loop until the board is full
            // Print the board
            System.out.println("Current board:"); // Print the current board
            printBoard(SIZE); // Print the board

            if (playerTurn) {
                // Player's turn
                System.out.print("Your turn: "); // Prompt the user for input
                int row = scanner.nextInt(); // Get the row
                int col = scanner.nextInt(); // Get the column

                if (isValidMove(row , col , SIZE)) { // Check if the move is valid
                    makeMove(row - 1, col - 1); // Make the move
                    if (hasPlayerWon(SIZE)) { // Check if the player has won
                        System.out.println("Congratulations! You win!"); // Print the win message
                        printBoard(SIZE); // Print the board
                        break;
                    }
                } else {
                    System.out.println("Invalid move. Try again."); // Print an error message
                }
            } else {
                // AI's turn
                System.out.println("Computer plays:"); // Print the AI's move
                makeAIMove(SIZE); // Make the AI's move
                if (hasPlayerWon(SIZE)) { // Check if the AI has won
                    System.out.println("You win!"); // Print the win message
                    printBoard(SIZE); // Print the board
                    break;
                }
            }
        }

        if (isBoardFull(SIZE)) { // Check if the board is full
            System.out.println("It's a FULL!");
            printBoard(SIZE); // Print the board
        }

        System.out.println("Game over."); // Print the game over message
        scanner.close();
    }

    /**
     * This method make a move for AI
     */
    public void makeAIMove(int SIZE) {
        // This is where you would add your AI algorithm to determine the best move for the AI
        // For simplicity, we'll just make a random valid move for the AI in this example

        while (true) {
            int row = (int) (Math.random() * SIZE); // Generate a random row and column
            int col = (int) (Math.random() * SIZE);

            if (isValidMove(row, col, SIZE)) { // Check if the move is valid
                makeMove(row, col); // Make the move
                break;
            }
        }
    }

}