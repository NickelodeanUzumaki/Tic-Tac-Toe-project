import java.util.Scanner;

/**
 * This file is for the hard mode
 *
 * @author Nguyen Mai Lan Vo
 */
public class TicTacToe3X3 {
    static final int SIZE = 3; //initialise the size for the hard method to 3x3

    /**
     * Main method that initializes the game and runs the game loop.
     */
    public void start() {
        char[][] board = new char[SIZE][SIZE];  // Create a 2D array to represent the game board.
        Scanner scanner = new Scanner(System.in);  // Create a new Scanner object to read user input.
        initializeBoard(board);  // Initialize the game board with empty spaces.

        printBoard(board);  // Print the initial state of the game board.

        while (true) {
            // Human player's turn
            int row, col;
            do {
                System.out.print("Your turn: ");  // Prompt the user to enter their move.
                row = scanner.nextInt() - 1;  // Read the row number (adjusted to match array index).
                col = scanner.nextInt() - 1;  // Read the column number (adjusted to match array index).

                // Check if the selected position is already occupied
                if (board[row][col] != Player.EMPTY_SPACE) {
                    System.out.println("Invalid move. Try again.");  // Print an error message for an invalid move.
                    row = scanner.nextInt() - 1;  // Read the row number again.
                    col = scanner.nextInt() - 1;  // Read the column number again.
                }
            } while (board[row][col] != Player.EMPTY_SPACE);  // Repeat until a valid move is entered.

            board[row][col] = Player.HUMAN_PLAYER;  // Update the game board with the human player's move.
            printBoard(board);  // Print the updated game board.

            if (checkWin(board, Player.HUMAN_PLAYER)) { //Check if the player win
                System.out.println("Congratulations! You win!");  // Print a message indicating that the human player has won.
                break;  // Exit the game loop.
            } else if (isBoardFull(board)) { //Check if the board is full and there is no move available
                System.out.println("It's FULL.");  // Print a message indicating that the game board is full.
                break;  // Exit the game loop.
            }

            // AI's turn
            int[] move = getBestMove(board);  // Get the best move for the AI player.
            board[move[0]][move[1]] = Player.AI_PLAYER;  // Update the game board with the AI player's move.
            System.out.println("Computer plays: " + (move[0] + 1) + " " + (move[1] + 1));  // Print the AI player's move.
            printBoard(board);  // Print the updated game board.

            if (checkWin(board, Player.AI_PLAYER)) {
                System.out.println("You lose!");  // Print a message indicating that the human player has lost.
                break;  // Exit the game loop.
            } else if (isBoardFull(board)) {
                System.out.println("It's FULL.");  // Print a message indicating that the game board is full.
                break;  // Exit the game loop.
            }
        }
        scanner.close();  // Close the scanner to release system resources.
    }


    /**
     * Finds the best move for the AI player using the minimax algorithm
     */
    public static int[] getBestMove(char[][] board) {
        int[] bestMove = new int[2];  // Initialize an array to store the best move.
        int bestScore = Integer.MIN_VALUE;  // Initialize the best score as the lowest possible value.

        // Iterate over each position on the board
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == Player.EMPTY_SPACE) {  // Check if the position is empty
                    board[i][j] = Player.AI_PLAYER;  // Assume AI player makes a move in the
                    int score = minimax(board, 0, false); /// Initialize the variable score by calling the minimax function
                    board[i][j] = Player.EMPTY_SPACE;
                    if (score > bestScore) {  // Check if the current move has a higher score.
                        bestScore = score;  // Update the best score.
                        bestMove[0] = i;  // Update the best move's row index.
                        bestMove[1] = j;  // Update the best move's column index.
                    }
                }
            }
        }
        return bestMove;  // Return the best move found
    }

    /**
     * Minimax algorithm for determining the best move for the AI.
     */
    public static int minimax(char[][] board, int depth, boolean isMaximizingPlayer) {
        if (checkWin(board, Player.AI_PLAYER)) { //checks if the AI player has won the game
            return 10 - depth; // AI wins, return score, the depth is subtracted to favor shorter winning paths and penalize longer paths
        } else if (checkWin(board, Player.HUMAN_PLAYER)) { //check if the player has won the game
            return depth - 10; // Human wins, return score
        } else if (isBoardFull(board)) { //check if the board is full and no move available
            return 0; // Draw, return score
        }

        if (isMaximizingPlayer) {
            int bestScore = Integer.MIN_VALUE;  // Initialize the best score to the lowest possible value.
            for (int i = 0; i < SIZE; i++) {  // Iterate over the rows of the game board.
                for (int j = 0; j < SIZE; j++) {  // Iterate over the columns of the game board.
                    if (board[i][j] == Player.EMPTY_SPACE) {  // If the current position on the board is empty.
                        board[i][j] = Player.AI_PLAYER;  // Set the current position as AI's move.
                        int score = minimax(board, depth + 1, false);  // Recursively call the minimax function with the updated board and depth, but with the next player as the minimizing player.
                        board[i][j] = Player.EMPTY_SPACE;  // Undo the AI's move by resetting the current position to an empty space.
                        bestScore = Math.max(bestScore, score);  // Update the best score by taking the maximum of the current best score and the score obtained from the recursive call.
                    }
                }
            }
            return bestScore;  // Return the best score found for the maximizing player.
        } else {
            int bestScore = Integer.MAX_VALUE;  // Initialize the best score to the highest possible value.
            for (int i = 0; i < SIZE; i++) {  // Iterate over the rows of the game board.
                for (int j = 0; j < SIZE; j++) {  // Iterate over the columns of the game board.
                    if (board[i][j] == Player.EMPTY_SPACE) {  // If the current position on the board is empty.
                        board[i][j] = Player.HUMAN_PLAYER;  // Set the current position as the human player's move.
                        int score = minimax(board, depth + 1, true);  // Recursively call the minimax function with the updated board and depth, but with the next player as the maximizing player.
                        board[i][j] = Player.EMPTY_SPACE;  // Undo the human player's move by resetting the current position to an empty space.
                        bestScore = Math.min(bestScore, score);  // Update the best score by taking the minimum of the current best score and the score obtained from the recursive call.
                    }
                }
            }
            return bestScore;  // Return the best score found for the minimizing player.
        }
    }

    /**
     * Checks if the game board is full, i.e., all positions are filled
     */
    public static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < SIZE; i++) {  // Iterate over the rows of the game board.
            for (int j = 0; j < SIZE; j++) {  // Iterate over the columns of the game board.
                if (board[i][j] == Player.EMPTY_SPACE) {  // If the current position on the board is empty.
                    return false;  // Return false as the board is not full.
                }
            }
        }
        return true;  // Return true as all positions on the board are filled.
    }

    /**
     * check who is the winner of the game
     */
    public static boolean checkWin(char[][] board, char player) {
        // Check rows
        for (int i = 0; i < SIZE; i++) {  // Iterate over the rows of the game board.
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {  // If the current row has three consecutive positions occupied by the given player.
                return true;  // Return true to indicate a win.
            }
        }

        // Check columns
        for (int i = 0; i < SIZE; i++) {  // Iterate over the columns of the game board.
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {  // If the current column has three consecutive positions occupied by the given player.
                return true;  // Return true to indicate a win.
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {  // If the main diagonal has three consecutive positions occupied by the given player.
            return true;  // Return true to indicate a win.
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {  // If the anti-diagonal has three consecutive positions occupied by the given player.
            return true;  // Return true to indicate a win.
        }
        return false;  // Return false if no win condition is found.
    }

    /*  Initialize the game board with empty spaces */
    public static void initializeBoard(char[][] board) {
        for (int i = 0; i < SIZE; i++) {  // Iterate over the rows of the game board.
            for (int j = 0; j < SIZE; j++) {  // Iterate over the columns of the game board.
                board[i][j] = Player.EMPTY_SPACE;  // Set the current position on the board as an empty space.
            }
        }
    }

    /*  Print the game board after each play */
    public static void printBoard(char[][] board) {
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
            if (i != board.length - 1) {
                System.out.print("  ");
                for (int j = 0; j < SIZE; j++) {
                    System.out.print("----");
                }
                System.out.println();
            }
        }
    }
}
