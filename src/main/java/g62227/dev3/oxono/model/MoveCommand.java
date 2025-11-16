package g62227.dev3.oxono.model;

import g62227.dev3.oxono.util.Command;

/**
 * Command to move a totem on the game board.
 * Handles both bot-controlled and manual moves for a player.
 */
public class MoveCommand implements Command {
    private Symbol totemSymbol;
    private Position oldPosition;
    private Position position;
    private Oxono oxono;

    /**
     * Constructs a MoveCommand to move a totem on the board.
     * This constructor initializes the command with the necessary game data to move
     * a totem from its current position to a new specified position.
     * @param oxono       the game instance providing access to the game rules and state
     * @param totemSymbol the symbol of the totem to be moved (e.g., X or O)
     * @param position    the new position where the totem will be moved
     */
    MoveCommand(Oxono oxono, Symbol totemSymbol, Position position) {
        this.oxono = oxono;
        this.totemSymbol = totemSymbol;
        this.position = position;
        this.oldPosition = oxono.findTotemPosition(totemSymbol);
    }




    @Override
    public void execute() {
        if (oxono.isStackEmpty(totemSymbol)) {
            oxono.setGameState(GameState.MOVE_TOTEM);
        } else {
            oxono.moveTotem(totemSymbol, position);
            oxono.setGameState(GameState.SET_PIECE);

        }



    }

    @Override
    public void unexecute() {
        oxono.moveTotem(totemSymbol, oldPosition);
        oxono.setGameState(GameState.MOVE_TOTEM);



    }
}
