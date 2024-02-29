import java.util.Scanner;
/**
 * this is the main file to play the game
 * this file will call the functions from other files
 * 
 * @author Nguyen Mai Lan Vo
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Tic Tac Toe!");  // Print a welcome message to the console.
        System.out.println("You will be playing against the computer\n");  // Print a message indicating the player will be playing against the computer.

        System.out.println("Choose difficulty level:");  // Prompt the user to choose a difficulty level.
        System.out.println("1. Easy (6x6 board)");  // Display the option for easy difficulty.
        System.out.println("2. Medium (5x5 board)");  // Display the option for medium difficulty.
        System.out.println("3. Hard (3x3 board)");  // Display the option for hard difficulty.
        System.out.print("Enter your choice: ");  // Prompt the user to enter their choice.
        Scanner scanner = new Scanner(System.in);  // Create a new Scanner object to read user input.
        int choice = scanner.nextInt();  // Read the user's choice from the console and store it as an integer.
        int size = 0;  // Initialize a variable to store the size of the game board.

        switch (choice) {  // Start a switch statement based on the user's choice.
            case 1:  // If the user chose option 1 (easy difficulty).
                size = 6;  // Set the size of the game board to 6x6.
                TicTacToeCustom gameeasy = new TicTacToeCustom(size);  // Create a new instance of the TicTacToeCustom class with the specified size.
                gameeasy.play(size);  // Start the game with the specified size.
                break;  // Exit the switch statement.
            case 2:  // If the user chose option 2 (medium difficulty).
                size = 5;  // Set the size of the game board to 5x5.
                TicTacToeCustom gamemedium = new TicTacToeCustom(size);  // Create a new instance of the TicTacToeCustom class with the specified size.
                gamemedium.play(size);  // Start the game with the specified size.
                break;  // Exit the switch statement.
            default:  // If the user entered an invalid choice or any other value.
                TicTacToe3X3 difficulty = new TicTacToe3X3();  // Create a new instance of the TicTacToe3X3 class for the default (hard) difficulty.
                difficulty.start();  // Start the game with the default (3x3) board size.
                break;  // Exit the switch statement.
        }

        scanner.close();  // Close the scanner to release system resources.
    }
}