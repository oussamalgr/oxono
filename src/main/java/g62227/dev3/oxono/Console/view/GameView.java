package g62227.dev3.oxono.Console.view;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import g62227.dev3.oxono.model.*;

/**
 * The GameView class is responsible for managing the console-based
 * user interface of the OXONO game. It provides methods for displaying
 * the game state, prompting user input, and handling messages related to the
 * game flow.
 */
public class GameView {
    private Scanner scanner;
    private Game game;

    /**
     * represents the constructor of the GameView
     * instantiate game and scanner
     *
     * @param game the facade providing access to game logic and data.
     */
    public GameView(Game game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    /**
     * displays the board of the oxono game
     */
    public void display() {
        int BOARD_SIZE = game.getSizeOfBoard();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Token token = game.getCaseAt(new Position(row,col));
                System.out.print(token == null ? " . " :" "+token+" ");
            }
            System.out.println();
        }

    }


    /**
     *  message if the Board is full
     */
    public void fullBoardView(){
        System.out.println("No more free slot :");
        System.out.println("That's a draw");
        System.out.println("===================");
        display();
        System.out.println("===================");


    }

    /**
     * Prompts the user to select the difficulty level for the bot and the size of the
     * board.
     */
    public void promptStartGame() {
        System.out.println("Please, select the difficulty of bot: 0 or 1 and the size of board");
        String inputBotLevel = scanner.nextLine();
        while (!inputBotLevel.matches("^[0-1]$")) {
            System.out.println("There are only 2 levels, Please write 0 or 1");
            inputBotLevel = scanner.nextLine();

        }

        System.out.println("Please enter the size of the board :");
        String inputsizeBoard = scanner.nextLine();
        while ((!inputsizeBoard.matches("^[2-9]$|^10"))) {
            System.out.println("Invalid input. Please enter a valid size :");
            inputsizeBoard = scanner.nextLine();
        }
        game.startGame(Integer.parseInt(inputsizeBoard), Integer.parseInt(inputBotLevel));
    }

    /**
     * number of free slots on the board
     */
    public void CaseInBoard() {
        System.out.println("Case in board : " + game.countFreeBoardSlots());
    }

    /**
     * message if the bot has successfully moved the totem and inserted a piece
     */
    public void displayBotMoveMessage() {
        System.out.println("The bot has successfully moved the totem and inserted a piece on the board.");
        display();
        CaseInBoard();
    }

    /**
     * displays the number of pieces the player has in  both stacks
     */
    public void disPlaySetPlayer() {
        System.out.println("Pieces O left in your bag: " + game.getRemainingStackPieces(game.getCurrentPlayerColor(),Symbol.O));
        System.out.println("Pieces X left in your bag: " + game.getRemainingStackPieces(game.getCurrentPlayerColor(),Symbol.X));

    }

    /**
     * displays the current totem position
     */
    public void displayTotemPos() {
        Position positionTotemX = game.findTotemPosition(Symbol.X);
        Position positionTotemO = game.findTotemPosition(Symbol.O);
        System.out.println("Current coordinates of Totem X: x = " + positionTotemX.getX() + ", y = "
                + positionTotemX.getY());
        System.out.println("Current coordinates of Totem O: x = " + positionTotemO.getX() + ", y = "
                + positionTotemO.getY());

    }

    /**
     * displays the rules of the game.
     */
    public void rulesGame() {
        System.out.println("=".repeat(30));
        System.out.println("Welcome in the game : OXONO ");
        System.out.println("=".repeat(30));
        System.out.println("Rules : ");
        System.out.println("This game is played by maximum 2 players");
        System.out.println("the aim of the game is to line up 4 pieces of the same color or symbol");
        System.out.println("each turn you must move a Totem and place a piece");
        System.out.println("the totem can only be moved vertically and horizontally");
        System.out.println(
                "each time you move a totem you have to put a piece next to the totem vertically or horizontally");
        System.out.println("You can't pass over a piece or Totem unless the totem is blocked");
        System.out.println("If the Totem moved is blocked, you can place a piece everywhere in a  null case");
        System.out.println("if the row and col of the totem are full of piece you can move it everywhere");
        System.out.println("Have a good Game");
        System.out.println("the player " + game.getCurrentPlayerColor() + " start");
        System.out.println("==================================================");

    }

    /**
     * displays the prompt if no more pawn in both sides
     */
    public void promptNoMorePawn(){
        System.out.println("No more pawns in both sides :  ");
        System.out.println("it's a draw");
        System.out.println("===================");
        display();
        System.out.println("===================");
    }

    /**
     * display the current winner if the user give up
     */
    public void displayGiveUp() {
        System.out.println("The player : " + game.getCurrentPlayerColor() + " gave up !");
        Color color = switchPlayer();
        System.out.println("Consequently : the player with the color : "
                + color + " won the game !");

    }

    /**
     * displays the winner of the game
     */
    public void displayWinner() {
        System.out.println("===================");
        display();
        System.out.println("The player with color " +
                game.getCurrentPlayerColor()
                + " won the game! Congratulations!");
    }

    /**
     * switch the Color player in case of surrender
     * @return - color of the player
     */
    private Color switchPlayer() {
        if (game.getCurrentPlayerColor() == Color.BLACK) {
            return Color.PINK;
        } else {
            return Color.BLACK;
        }
    }

    /**
     * Displays the options for inserting a piece.
     * This method prompts the user with the allowed positions for the specified
     * symbol and instructions for entering their move.
     *
     * @param symbol the symbol ("X" or "O") representing the piece to be placed
     */
    public void displayBeginPiece(String symbol) {
        Symbol symbolPiece = symbol.equalsIgnoreCase("X") ? Symbol.X : Symbol.O;
        System.out.println("Allowed position  : " + game.getInsertPieceOptions(symbolPiece));
        System.out.println("Enter the position (row and col) to place a piece or q or undo or redo");

    }

    /**
     * Displays the game state after a totem move,
     * including the totems' positions, the game board,
     * and the current player's status.
     */
    public void displayAfterMove() {
        displayTotemPos();
        display();
        disPlaySetPlayer();
    }

    /**
     * displays the board before the move.
     */
    public void displayBeforeMove() {
        System.out.println("Current player: " + game.getCurrentPlayerColor());
    }



    /**
     * displays an error message if unknown command
     */
    public void ERROR_COMMAND() {
        System.out.println("Unknown command, please try again.");

    }

    /**
     * prompt for the move Totem Game State
     *
     * @return the input of the player.
     */
    public String promptSymbolTotem() {
        displayBeforeMove();
        System.out.println("Enter the Symbol of the totem (X or O) you want to move or undo  or  redo  or q:  ");
        String input = scanner.nextLine();
        while (!input.matches("[oOxX]|undo|redo|[qQ]|restart")) {
            display();
            System.out.println("Wrong command : please enter the symbol of the totem (X or O) or undo or redo or q: ");
            input = scanner.nextLine();

        }
        return input;
    }

    /**
     * prompt if a wrong position has been written
     * @return position of the input
     */
    public Position prompt_Move_And_Insert() {
        String input = scanner.nextLine();
        String regex = "(\\d+)\\s+(\\d+)";
        while (!input.matches(regex)) {
            display();
            System.out.println("please enter valid position : ");
            input = scanner.nextLine();
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        return new Position(x, y);

    }

    /**
     * prompt for not valid position written
     */
    public void promptMoveTotem() {
        System.out.println("Please enter a valid position for the move totem ");
    }


    /**
     * prompt for the player to enter a position
     */
    public void promptStartMoveTotem() {
        System.out.println("Enter the position of the move x y :");
    }

    /**
     * prompt for the user to write a valid position for the insert piece
     */
    public void prompt_insert_Piece() {
        display();
        System.out.println("Please Insert a token in a valid position : ");
    }

    /**
     * prompt the player in the SET Piece state
     * the player can choose between restart undo redo a position or surrender
     * @param symbol - symbol of the current piece
     * @return input of the player
     */
    public String promptInsertPiece(Symbol symbol) {
        displayBeginPiece(symbol.toString());
        String input = scanner.nextLine();
        while (!input.matches("|undo|redo|restart|[qQ]|(\\d+)\\s+(\\d+)\\s*")) {
            prompt_insert_Piece();
            input = scanner.nextLine();

        }
        return input.trim();
    }


    public String promptWhenBotPlaying() {
        System.out.println("Type 'undo' or 'redo' to perform an action, or press Enter to continue.");
        String input = scanner.nextLine().trim();

        while (!input.isEmpty() && !input.matches("^(undo|redo)$")) {
            System.out.println("Invalid command! Type 'undo' or 'redo' to perform an action, or press Enter to continue.");
            input = scanner.nextLine().trim();
        }

        return input;
    }



}
