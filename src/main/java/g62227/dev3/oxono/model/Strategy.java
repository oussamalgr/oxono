package g62227.dev3.oxono.model;


/**
 * Strategy interface from design pattern strategy used for giving a strategy to implement an intelligent player
 */
public interface Strategy {


    /**
     * Chooses a totem to move and performs the move based on the bot's strategy.
     * This method uses the bot's behavior strategy to select the totem and decide
     * its next position according to the game rules.
     *
     * @param oxono the game rules used to determine valid moves
     * @return the position where the selected totem will be moved
     */
    Position executeMoveTotem(Oxono oxono);


    /**
     * Retrieves the current symbol of the totem that was moved.
     * This method returns the symbol (X or O) of the totem after it has been moved.
     * @return the symbol of the totem
     */
    Symbol getCurrentSymbol();



    /**
     * Inserts a piece into the Oxono game board.
     *
     * @param oxono The Oxono game instance.
     * @param symbolPiece The symbol of the piece to insert.
     * @return The position where the piece is inserted.
     */
    Position executeInsertPiece(Oxono oxono, Symbol symbolPiece);
}