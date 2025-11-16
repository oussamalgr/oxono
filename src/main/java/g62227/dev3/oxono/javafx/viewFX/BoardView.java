package g62227.dev3.oxono.javafx.viewFX;

import java.util.List;

import g62227.dev3.oxono.javafx.controller.ControllerFx;
import g62227.dev3.oxono.model.Color;
import g62227.dev3.oxono.model.Game;
import g62227.dev3.oxono.model.Position;
import g62227.dev3.oxono.model.Symbol;
import g62227.dev3.oxono.model.Token;
import g62227.dev3.oxono.model.Totem;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Represents the board of the game Oxono
 */
public class BoardView extends GridPane {
    private Cell cell;
    private ControllerFx controllerFx;
    private Symbol symbolSelected = null;
    private Symbol lastPinkTotemSymbol = null;


    /**
     * Initializes the BoardView with the given game and controller.
     * Configures the gridPane layout for the game board.
     *
     * @param controllerFx the controller for handling user interactions
     */
    BoardView(ControllerFx controllerFx) {
        this.controllerFx = controllerFx;
        this.setAlignment(Pos.CENTER);
        this.setMaxSize(600, 700);
        this.setStyle("-fx-background-color: purple;");

    }

    /**
     * will update the board and displays it
     */
    void updateBoard(Game game) {

        this.getChildren().clear();
        int BOARD_SIZE = game.getSizeOfBoard();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Token token = game.getCaseAt(new Position(row, col));
                this.cell = new Cell(token);
                this.add(cell, col, row);
                executeCellAction(token, row, col, game);
            }
        }

    }

    private void executeCellAction(Token token, int row, int col, Game game) {
        if (game.getCurrentPlayerColor() == Color.PINK) {
            playerPinkAction(token, row, col, game);
        } else if (game.getCurrentPlayerColor() == Color.BLACK) {
            playerBlackAction(game);

        }

    }

    private void playerBlackAction(Game game) {
        symbolSelected = game.getLastUsedSymbolFromPink();
        if (game.isStateMove()) {
            controllerFx.handleBotMove();
        } else if (game.isStateInsert()) {
            controllerFx.handleBotInsert();
        }

    }

    private void playerPinkAction(Token token, int row, int col, Game game) {
        if (game.isStateMove()) {
            cell.setOnMouseClicked(e -> {
                if (token instanceof Totem totem) {
                    highlightPossibleMove(totem, game);
                    setSymbolSelected(totem.getSymbol());

                } else if (symbolSelected != null) {
                    controllerFx.handleMoveTotem(symbolSelected, row, col);

                }
            });
        } else if (game.isStateInsert()) {
            highlightPossibleInsert(game);
            cell.setOnMouseClicked(e -> {
                controllerFx.handleSetPiece(symbolSelected, row, col);
            });
        }
    }

    private void highlightPossibleMove(Totem totem, Game game) {
        if (game.isStackEmpty(totem.getSymbol())) {
            for (Node node : this.getChildren()) {
                if (GridPane.getRowIndex(node) == totem.getX() && GridPane.getColumnIndex(node) == totem.getY()) {
                    StackPane cell = (StackPane) node;
                    cell.setStyle("-fx-border-color: white; -fx-border-width: 2; -fx-background-color: red;");
                    PauseTransition pause = new PauseTransition(Duration.seconds(0.2));
                    pause.setOnFinished(e -> cell.setStyle("-fx-border-color: white;"));
                    pause.play();
                }
            }

        } else {
            resetBoardStyle();
            List<Position> positionsToHighlight = game.getTotemMoveOptions(totem.getSymbol());
            for (Position pos : positionsToHighlight) {
                int row = pos.getX();
                int col = pos.getY();
                for (Node node : this.getChildren()) {
                    if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                        StackPane cell = (StackPane) node;
                        cell.setStyle("-fx-border-color: white; -fx-border-width: 2; -fx-background-color: #5C4584;");
                    }
                }
            }
        }
    }

    private void highlightPossibleInsert(Game game) {
        resetBoardStyle();
        List<Position> possibleInsertToHighlight = game.getInsertPieceOptions(symbolSelected);
        for (Position pos : possibleInsertToHighlight) {
            int row = pos.getX();
            int col = pos.getY();
            for (Node node : this.getChildren()) {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                    StackPane cell = (StackPane) node;
                    cell.setStyle("-fx-border-color: white; -fx-border-width: 2; -fx-background-color: #5C4584;");
                }
            }
        }
    }

    private void resetBoardStyle() {
        for (Node node : this.getChildren()) {
            StackPane cell = (StackPane) node;
            cell.setStyle("-fx-border-color: white; -fx-border-width: 1;");
        }
    }

    void setSymbolSelected(Symbol symbol) {
        this.symbolSelected = symbol;
    }

}
