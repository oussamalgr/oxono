package g62227.dev3.oxono.model;

import g62227.dev3.oxono.util.Command;

/**
 * Command to insert a piece on the game board.
 * Handles the insertion of pieces and the updating of the game state.
 */
public class InsertCommand implements Command {

    private Position position;
    private Symbol symbol;
    private Oxono oxono;
    private Player player;

    // todo : current player

    /**
     * Constructs an InsertCommand4 to place a piece on the board.
     * This constructor initializes the command with the necessary game data to
     * insert a piece at the specified position with the given symbol.
     *
     * @param oxono    the game instance providing access to the game rules and
     *                 state
     * @param symbol   the symbol of the piece to be inserted (X or O)
     * @param position the position where the piece will be inserted on the board
     */
    InsertCommand(Oxono oxono, Symbol symbol, Position position) {
        this.oxono = oxono;
        this.symbol = symbol;
        this.position = position;
        this.player = oxono.getCurrentPlayer();

    }

    @Override
    public void execute() {
        if (oxono.getState() == GameState.SET_PIECE) {
            oxono.setPiece(position, symbol, player);
            oxono.setGameState(GameState.MOVE_TOTEM);
        }
        if (oxono.isBoardFull() || oxono.areAllStacksEmpty()) {
            oxono.setGameState(GameState.DRAW);
        }

    }

    @Override
    public void unexecute() {
        oxono.removePieceAtPosition(position, player);
        oxono.setGameState(GameState.SET_PIECE);

    }

}
