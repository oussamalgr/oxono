package g62227.dev3.oxono.model;

import java.util.Collections;
import java.util.List;

import g62227.dev3.oxono.util.Command;
import g62227.dev3.oxono.util.Observable;
import g62227.dev3.oxono.util.Observer;


/**
 * This class represents the façade of the OXONO game, managing the interactions with oxono ManageObservable and CommandManager.
 * It coordinates the game's logic by interacting with other components such as  oxono, commandManager and manageObservable.
 * <p>
 * The Game class handles player turns, state transitions, and provides an interface for updating and querying the game state.
 */
public class Game implements Observable {
    private Oxono oxono;
    private ManageObservable manageObservable = new ManageObservable();
    private CommandManager cm;


    /**
     * Constructs a new instance of the Game class.
     * Initializes the Oxono game logic, the CommandManager for handling game commands,
     * and the ManageObservable to manage game state changes and observers.
     */
    public Game() {
        this.cm = new CommandManager();
        oxono = new Oxono();
    }


    /**
     * Initializes and starts a new game with the specified board size and bot difficulty level.
     *
     * @param size     the size of the board
     * @param levelBot the difficulty level of the bot (0 or 1)
     */
    public void startGame(int size, BotLevel botLevel) {
        oxono.startGame(size, botLevel);
    }

    /**
     * Initializes or restarts the game by resetting the game components.
     * This method creates a new instance of the Oxono game and a new CommandManager.
     */
    public void restart() {
        oxono = new Oxono();
        this.cm = new CommandManager();
    }

    /**
     * Moves the totem to the specified position on the board if the move is allowed.
     * Executes the move command and notifies observers of the change.
     *
     * @param totemSymbol    the symbol of the totem to move (e.g., X or O)
     * @param targetPosition the target position to move the totem to
     * @return true if the move is successful, false otherwise
     */
    public boolean moveTotem(Symbol totemSymbol, Position targetPosition) {
        if (!isStateMove()) {
            return false;
        }
        if (oxono.allowedTotemPosition(totemSymbol, targetPosition)) {
            Command command = new MoveCommand(oxono, totemSymbol, targetPosition);
            cm.executeCommand(command);
            notifyObservers();
            return true;
        }
        return false;
    }


    public boolean setPiece(Position posToInsert, Symbol symbolPiece) {
        if (!isStateInsert()) {
            return false;
        }
        if (oxono.isValidPiecePosition(symbolPiece, posToInsert)) {
            Command command = new InsertCommand(oxono, symbolPiece, posToInsert);
            cm.executeCommand(command);
            notifyObservers();
            return true;
        }
        return false;

    }


    /**
     * Checks if both players have no remaining pieces in their stacks.
     *
     * @return true if both players' stacks (for both symbols) are empty, false otherwise
     */
    public boolean areAllStacksEmpty() {
        return oxono.areAllStacksEmpty();
    }


    /**
     * Allows the bot to move the totem during its turn.
     * The bot will only move if it is the bot's turn and the game is in a valid move state.
     *
     * @return true if the totem was successfully moved, false otherwise
     */
    public boolean moveTotemBot() {
        if (!isStateMove()) {
            return false;
        }
        Player player = oxono.getCurrentPlayer();
        if (player.isBotControlled() && !canRedo()) {
            Position targetPosition = player.getBotBehavior().executeMoveTotem(oxono);
            Symbol symbolTotem = player.getBotBehavior().getCurrentSymbol();
            Command command = new MoveCommand(oxono, symbolTotem, targetPosition);
            cm.executeCommand(command);
            notifyObservers();
            return true;
        }
        return false;
    }


    /**
     * Allows the bot to insert a token at a valid position during its turn.
     * The bot will only insert a token if it is the bot's turn and the game is in a valid insert state.
     *
     * @return the position where the token was inserted, or null if no token was inserted
     */
    public Position insertPieceBot() {
        if (!isStateInsert()) {
            return null;
        }
        Player botPlayer = oxono.getCurrentPlayer();
        if (botPlayer.isBotControlled() && !canRedo()) {
            Symbol symbolPiece = oxono.getLastTotemSymbol();
            Position insertPos = botPlayer.getBotBehavior().executeInsertPiece(oxono, symbolPiece);
            Command command = new InsertCommand(oxono, symbolPiece, insertPos);
            cm.executeCommand(command);
            notifyObservers();
            return insertPos;
        }

        return null;

    }

    /**
     * Sets the game state to the specified new state.
     */
    void setStateAtWinner() {
        oxono.setGameState(GameState.WINNER);

    }

    /**
     * Allows the current player to surrender, ending the game and setting the state to SURRENDER.
     * This method updates the game state and notifies observers of the change.
     */
    public void surrender() {
        oxono.setGameState(GameState.SURRENDER);
        notifyObservers();

    }

    /**
     * Checks if the current game state is SET_PIECE.
     *
     * @return true if the game state is SET_PIECE, false otherwise.
     */
    public boolean isStateInsert() {
        return oxono.getState() == GameState.SET_PIECE;
    }


    /**
     * Checks if the current game state is MOVE_TOTEM.
     *
     * @return true if the game state is MOVE_TOTEM, false otherwise.
     */
    public boolean isStateMove() {
        return getState() == GameState.MOVE_TOTEM;
    }

    /**
     * Checks if the current game state is a surrender state.
     *
     * @return true if the game state is SURRENDER, false otherwise
     */
    public boolean isStateSurrender() {
        return getState() == GameState.SURRENDER;
    }


    /**
     * Checks if the current game state is a draw.
     *
     * @return true if the game state is DRAW, false otherwise
     */
    public boolean isStateDraw() {
        return getState() == GameState.DRAW;
    }


    /**
     * Checks if the current game state is a winner state.
     *
     * @return true if the game state is WINNER, false otherwise
     */
    public boolean isStateWinner() {
        return oxono.getState() == GameState.WINNER;
    }


    /**
     * Checks if the  currentplayer's stack of the specified symbol is empty.
     *
     * @param symbol - symbol of the piece
     * @return true if deck is empty, false else
     */
    public boolean isStackEmpty(Symbol symbol) {
        return oxono.isStackEmpty(symbol);
    }

    /**
     * Checks if the current position results in a win for the given symbol.
     * If a win is detected, the game state is set to WINNER. Otherwise, the turn is switched to the next player.
     *
     * @param position    the position of the most recently placed piece
     * @param symbolPiece the symbol of the piece (X or O) for the current move
     * @return true if the move results in a win, false otherwise
     */
    public boolean checkWin(Position position, Symbol symbolPiece) {
        if (oxono.checkWin(position, symbolPiece)) {
            setStateAtWinner();
            notifyObservers();
            return true;
        } else {
            oxono.switchPlayer();
            notifyObservers();
            return false;
        }

    }


    /**
     * Retrieves the token at the specified position on the board.
     *
     * @param pos the position of the slot on the board
     * @return the token at the specified position, or null if the slot is empty
     */
    public Token getCaseAt(Position pos) {
        return oxono.getCaseAt(pos);
    }


    /**
     * Retrieves the current game state.
     *
     * @return the current game state
     */
    public GameState getState() {
        return oxono.getState();
    }


    /**
     * Retrieves a list of positions where a piece can be inserted for the given symbol.
     *
     * @param symbolPiece the symbol of the piece ('X' or 'O') for which insertion positions are being queried
     * @return an unmodifiable list of positions where the piece can be inserted
     */
    public List<Position> getInsertPieceOptions(Symbol symbolPiece) {
        List<Position> availableInsertsPositions = oxono.getInsertPieceOptions(symbolPiece);
        return Collections.unmodifiableList(availableInsertsPositions);
    }

    /**
     * Retrieves a list of positions where the totem can be moved for the given symbol.
     *
     * @param symbolTotem the symbol of the totem to move ('X' or 'O')
     * @return an unmodifiable list of available positions for the totem
     */
    public List<Position> getTotemMoveOptions(Symbol symbolTotem) {
        List<Position> movablePositions = oxono.getTotemMoveOptions(symbolTotem);
        return Collections.unmodifiableList(movablePositions);
    }

    /**
     * Undoes the last action using the CommandManager's undo method.
     */
    public void undo() {
        if (canUndo()) {
            cm.undo();
            notifyObservers();

        }
    }


    /**
     * Redoes the last undone action using the CommandManager's redo method.
     */
    public void redo() {
        if (canRedo()) {
            cm.redo();
            notifyObservers();
        }
    }


    /**
     * Checks if the user can redo the last undone action.
     *
     * @return true if the user can redo, false otherwise
     */
    boolean canRedo() {
        return cm.canRedo();
    }

    /**
     * Checks if the user can undo the last action.
     *
     * @return true if the user can undo, false otherwise
     */
    public boolean canUndo() {
        return cm.canUndo();
    }

    /**
     * Returns the size of the game board.
     *
     * @return The size of the board.
     */
    public int getSizeOfBoard() {
        return oxono.getBoardSize();
    }


    /**
     * Counts the number of free slots on the board.
     *
     * @return the number of free slots remaining on the board
     * @throws IllegalStateException if oxono has not been initialized
     */
    public int countFreeBoardSlots() {
        if (oxono == null) {
            throw new IllegalStateException("oxono has not been initialized yet.");
        }

        return oxono.countFreeBoardSlots();
    }

    /**
     * Returns the current player.
     *
     * @return The player whose turn it is.
     */
    public Color getCurrentPlayerColor() {
        return oxono.getCurrentPlayer().getColor();
    }

    /**
     * Gets the number of remaining pieces of the specified symbol in the current player's stack.
     *
     * @param color  The color of the player.
     * @param symbol The symbol of the pieces (e.g., X or O).
     * @return The number of remaining pieces in the player's stack.
     */
    public int getRemainingStackPieces(Color color, Symbol symbol) {
        return oxono.getTheStackOfThePlayer(color, symbol);
    }


    /**
     * Returns the position of the totem associated with the given symbol.
     *
     * @param symbolTotem The symbol of the totem ('X' or 'O').
     * @return The position of the totem corresponding to the given symbol.
     */
    public Position findTotemPosition(Symbol symbolTotem) {
        return oxono.findTotemPosition(symbolTotem);
    }

    /**
     * Returns the symbol of the last moved totem.
     *
     * @return The symbol of the totem that was most recently moved.
     */
    public Symbol getLastTotemSymbol() {

        return oxono.getLastTotemSymbol();
    }


    @Override
    public void addObserver(Observer o) {
        manageObservable.addObserver(o);
    }

    @Override
    public void removeObserver(Observer o) {
        manageObservable.removeObserver(o);
    }


    /**
     * Notifies all registered observers about a change.
     * This will trigger the update method of each observer.
     */
    private void notifyObservers() {
        manageObservable.notifyObservers();
    }

    /**
     * this method will return the last symbol used from the player pink (not a bot)
     * will be useful for the undo/redo
     * @return
     */
    public Symbol getLastUsedSymbolFromPink(){
       return oxono.getLastUsedPinkSymbol();
    }

}
