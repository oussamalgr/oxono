package g62227.dev3.oxono.Console.controller;

import g62227.dev3.oxono.Console.view.GameView;
import g62227.dev3.oxono.model.*;

public class ConsoleController {

    private Game game;
    private GameView gameView;
    private boolean isGameActive = true;

    public ConsoleController(Game game, GameView view) {
        this.game = game;
        this.gameView = view;
    }


    /**
     * Starts the game, initializes the view, and runs the game loop.
     * It handles user input for settings, player moves, and responds to game state changes.
     * The loop continues until a game-over condition is met.
     */
    public void startGame() {
        gameView.rulesGame();
        gameView.promptStartGame();
        gameView.display();
        while (isGameActive) {
            if (game.getCurrentPlayerColor() == Color.PINK) {
                if (game.isStateMove()) {
                    handleMoveTotem();
                } else if (game.isStateInsert() && game.getCurrentPlayerColor() == Color.PINK) {
                    handleSetPiece();
                } else if (game.isStateDraw()) {
                    gameView.fullBoardView();
                    isGameActive = false;
                } else if (game.areAllStacksEmpty()) {
                    gameView.promptNoMorePawn();
                } else {
                    gameView.ERROR_COMMAND();
                }
            } else {
                handleBotMove();
            }

        }
    }

    private void handleMoveTotem() {
        String in = gameView.promptSymbolTotem();
        if (in.equalsIgnoreCase("X") || in.equalsIgnoreCase("O")) {
            Symbol symbol = in.equalsIgnoreCase("X") ? Symbol.X : Symbol.O;
            MoveTotemPattern(symbol);
        } else {
            handleOtherCommand(in);
        }

    }

    private void MoveTotemPattern(Symbol in) {
        gameView.promptStartMoveTotem();
        Position pos = gameView.prompt_Move_And_Insert();
        //todo si x et y plus petit que 0
        while ((pos.getY() >= game.getSizeOfBoard() ||
                pos.getX() >= game.getSizeOfBoard()
                || pos.getX() < 0 || pos.getY() < 0)
                || !game.moveTotem(in, pos)) {
            gameView.display();
            gameView.promptMoveTotem();
            pos = gameView.prompt_Move_And_Insert();
        }
        gameView.displayAfterMove();

    }

    private void handleSetPiece() {
        String in = gameView.promptInsertPiece(game.getLastTotemSymbol());
        String[] part = in.split("\\s+");
        if (part.length == 2) {
            int x = Integer.parseInt(part[0]);
            int y = Integer.parseInt(part[1]);
            Position pos = new Position(x, y);
            handleInsertPiece(pos);
        } else {
            handleOtherCommand(in);
        }

    }

    private void handleInsertPiece(Position pos) {
        while ((pos.getY() >= game.getSizeOfBoard() || pos.getX() >= game.getSizeOfBoard())
                || !game.setPiece(pos, game.getLastTotemSymbol())) {
            gameView.prompt_insert_Piece();
            pos = gameView.prompt_Move_And_Insert();
        }
        gameView.display();
        gameView.CaseInBoard();
        if (game.checkWin(pos, game.getLastTotemSymbol())) {
            isGameActive = false;
            gameView.displayWinner();
        }

    }

    private void handleOtherCommand(String in) {
        if (in.isBlank()) {
            gameView.display();
            return;
        }
        if (in.equalsIgnoreCase("undo")) {
            game.undo();
            gameView.display();
        } else if (in.equalsIgnoreCase("redo")) {
            game.redo();
            gameView.display();
        } else if (in.equalsIgnoreCase("q")) {
            isGameActive = false;
            gameView.displayGiveUp();
        } else if (in.equalsIgnoreCase("restart")) {
            game.restart();
            startGame();
        } else {
            gameView.ERROR_COMMAND();

        }
    }


    private void handleBotMove() {
        if (game.moveTotemBot()) {
            Position pos = game.insertPieceBot();
            if (pos != null && game.checkWin(pos, game.getLastTotemSymbol())) {
                isGameActive = false;
                gameView.displayWinner();
            } else {
                gameView.displayBotMoveMessage();
            }
        }
        String in = gameView.promptWhenBotPlaying();
        handleOtherCommand(in);

    }
}
