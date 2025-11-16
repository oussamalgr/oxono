package g62227.dev3.oxono.model;

/**
 * Represents a player in the OXONO game. Each player has two stacks: one
 * for 'O' pieces and one for 'X' pieces,
 * with a maximum of 8 pieces of each symbol (16 pieces in total).
 */
public class Player {

    private int piecesO;
    private int piecesX;
    private Color playerColor;
    private Strategy botBehavior;
    private final static int DEFAULT_STACK_SIZE = 8;
    private Symbol lastUsedSymbol  = null;

    /**
     * Constructor that initializes the player with a specific color and 8 pieces
     * for each symbol ('O' and 'X').
     *
     * @param playerColor The color of the player (either BLACK or PINK).
     */
     Player(Color playerColor) {
        this.piecesO = DEFAULT_STACK_SIZE;
        this.piecesX = DEFAULT_STACK_SIZE;
        this.playerColor = playerColor;
    }


    /**
     * Removes a piece from the player's stack and returns it.
     *
     * @param symbolPiece the symbol of the piece to remove (e.g., X or O)
     * @return a new Piece to place on the board, with the player's color and the given symbol
     */
     Piece removePiece(Symbol symbolPiece) {
        if (symbolPiece == Symbol.X) {
            this.piecesX = piecesX - 1;
            lastUsedSymbol = Symbol.X;
        } else if(symbolPiece == Symbol.O){
            this.piecesO = piecesO - 1;
            lastUsedSymbol = Symbol.O;
        }
        return new Piece(playerColor, symbolPiece);

    }

    /**
     * Checks if the stack of the specified symbol is empty.
     *
     * @param symbolStack the symbol of the stack to check (X or O)
     * @return true if the stack is empty (count is 0),  false otherwise
     */
    boolean isStackEmpty(Symbol symbolStack) {
        return switch (symbolStack) {
            case X -> piecesX == 0;
            case O -> piecesO == 0;
        };
    }

    /**
     * Adds a piece of the specified symbol to the player's stack.
     *
     * @param symbolPiece the symbol of the piece to add (e.g., X or O)
     */
     void addPiece(Symbol symbolPiece) {
        if (symbolPiece == Symbol.X) {
            this.piecesX = piecesX + 1;
        } else if(symbolPiece ==Symbol.O){
            this.piecesO = piecesO + 1;
        }

     }

    /**
     * Retrieves the number of remaining pieces in the player's stack for the specified symbol.
     *
     * @param symbolStack the symbol of the stack to check (e.g., X or O)
     * @return True if the stack is empty (count is 0), False otherwise
     */
    int getRemainingPieces(Symbol symbolStack){
         return symbolStack == Symbol.X ? piecesX : piecesO;
    }


    /**
     * Retrieves the color associated with the player.
     *
     * @return the player's color
     */
     Color getColor() {
        return playerColor;
    }

    /**
     * Sets the behavior strategy for the bot player.
     * This method utilizes the Strategy design pattern to dynamically assign
     * a behavior implementation to the bot.
     *
     * @param botBehavior the strategy to assign to the bot
     */
     void setBotBehavior(Strategy botBehavior) {
        this.botBehavior = botBehavior;
    }


    /**
     * Retrieves the current behavior strategy assigned to the bot.
     * This method is part of the implementation of the Strategy design pattern,
     * allowing dynamic retrieval of the bot's behavior.
     *
     * @return the current strategy of the bot
     */
     Strategy getBotBehavior() {
        return botBehavior;
    }


    /**
     * Checks whether the player is controlled by a bot.
     * This method determines if a bot behavior strategy has been assigned to the player
     * by verifying whether the  botBehavior is non-null.
     *
     * @return true if the player is controlled by a bot, False otherwise
     */
     boolean isBotControlled() {
        return botBehavior != null;
    }


    /**
     * Keeps track of the last symbol placed by the player.
     *
     * @return the last symbol that was placed on the board.
     */
     Symbol getLastUsedSymbol() {
        return lastUsedSymbol;
    }
}
