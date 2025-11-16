package g62227.dev3.oxono.javafx.controller;

import g62227.dev3.oxono.javafx.viewFX.MainView;
import g62227.dev3.oxono.model.Color;
import g62227.dev3.oxono.model.Game;
import g62227.dev3.oxono.model.Position;
import g62227.dev3.oxono.model.Symbol;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

/**
 * The ControllerFx class manages the interaction between the game model and the JavaFX view.
 * It handles user actions such as undo, redo, surrender, moving a totem, and inserting a piece..
 */
public class ControllerFx {
    private Game game;
    private MainView mainView;

    /**
     * Creates a new instance of ControllerFx.
     * @param game     the game model to be controlled
     * @param mainView the JavaFX main view to be updated with game changes
     */
    public ControllerFx(Game game, MainView mainView) {
        this.game = game;
        this.mainView = mainView;
        this.game.addObserver(mainView);

    }

    /**
     * Initializes the controller by linking user actions in the view to corresponding handlers.
     */
    public void init() {
        mainView.handlesUndo(e -> handleUndo());
        mainView.handlesRedo(e -> handleRedo());
        mainView.handlesSurrender(e -> handleSurrender());
    }



    private void handleUndo() {
        game.undo();
    }

    private void handleRedo() {
        game.redo();
    }

    private void handleSurrender() {
        if (game.getCurrentPlayerColor() == Color.PINK) {
            game.surrender();

        }
    }

    /**
     * Handles the movement of a totem to a new position.
     * @param totemToMove the symbol of the totem to move
     * @param row         the target row where the totem will be moved
     * @param col         the target column where the totem will be moved
     */
    public void handleMoveTotem(Symbol totemToMove, int row, int col) {
        Position targetPosition = new Position(row, col);
        game.moveTotem(totemToMove, targetPosition);
    }

    /**
     * Handles the insertion of a piece into the board.
     *
     * @param symbolPieceToInsert the symbol of the piece to be inserted
     * @param row   the row where the piece will be inserted
     * @param col   the column where the piece will be inserted
     */
    public void handleSetPiece(Symbol symbolPieceToInsert, int row, int col) {
        Position targetPosition = new Position(row, col);
        if (game.setPiece(targetPosition, symbolPieceToInsert)) {
            game.checkWin(targetPosition, symbolPieceToInsert);

        }

    }


    /**
     * Handles the bot's move action with a delay for better visual feedback.
     */
    public void handleBotMove() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(e -> game.moveTotemBot());
        pause.play();

    }


    /**
     * Handles the bot's piece insertion action with a delay for better visual feedback.
     */
    public void handleBotInsert() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(e -> {
            Position posToInsert = game.insertPieceBot();
            if (posToInsert != null) {
                game.checkWin(posToInsert, game.getLastTotemSymbol());
            }
        });
        pause.play();
    }
}
