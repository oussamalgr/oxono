package g62227.dev3.oxono.model;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represents the rules of the OXONO game
 */
public class Oxono {
    private Board board;
    private Player playerBlack;
    private Player playerPink;
    private Player currentPlayer;
    private GameState state;
    private static final int INITIAL_BOARD_SIZE = 6;
    private static final BotLevel INITIAL_BOT_LEVEL = BotLevel.EASY;

    /**
     * Constructor of the Oxono class, which initializes the game with two players,
     * sets the initial game state,
     * and starts the game by instantiating the board.
     * The constructor sets up a black player and a pink player, assigns a bot
     * strategy to the black player,
     * and sets the black player as the starting player. The initial game state is
     * set to MOVE_TOTEM.
     *
     * @see Player
     * @see Strategy
     * @see GameState
     */
    Oxono() {
        this.playerBlack = new Player(Color.BLACK);
        this.playerPink = new Player(Color.PINK);
        this.currentPlayer = playerPink;
        state = GameState.MOVE_TOTEM;
        startGame(INITIAL_BOARD_SIZE, INITIAL_BOT_LEVEL);
    }

    /**
     * Initializes the game by creating a new game board and setting the bot's
     * difficulty level.
     * This method creates a new `Board` object with the specified size and sets up
     * the bot's behavior
     * based on the provided difficulty level.
     * if this method isn't called then default values are assigned to the bot and
     * the size of the board
     *
     * @param size     the size of the game board (number of rows and columns)
     * @param levelBot the difficulty level for the bot (should be 0 or 1)
     */
    void startGame(int size, BotLevel botLevel) {
        this.board = new Board(size);
        if(botLevel == BotLevel.EASY){
            this.playerBlack.setBotBehavior(new EasyBotStrategy());
        }else{
            this.playerBlack.setBotBehavior(new MediumBotStrategy());
        }

    }

    /**
     * Checks if a piece can be placed at the specified position on the board
     * depending on
     * the rules of oxono game
     *
     * @param symbolPiece the symbol of the piece to place
     * @param position    the position (row/column) on the board
     * @return true if the position is valid for placing the piece, false otherwise
     */
    boolean isValidPiecePosition(Symbol symbolPiece, Position position) {
        if (board.getCellAt(position.getX(), position.getY()) != null) {
            return false;
        }
        if (isTotemBlocked(symbolPiece)) {
            return true;
        }
        return isPieceAdjacentToTotem(symbolPiece, position.getX(), position.getY());
    }

    private boolean isPieceAdjacentToTotem(Symbol symbol, int row, int col) {
        Position totemPos = board.findTotemPosition(symbol);
        return (totemPos.getX() + 1 == row && totemPos.getY() == col) ||
                (totemPos.getX() - 1 == row && totemPos.getY() == col) ||
                (totemPos.getY() - 1 == col && totemPos.getX() == row) ||
                (totemPos.getY() + 1 == col && totemPos.getX() == row);
    }

    private boolean isTotemBlocked(Symbol symbol) {
        Position totem = board.findTotemPosition(symbol);
        boolean isBlocked = true;
        int BOARD_SIZE = board.getSize();
        if (totem.getX() > 0 && board.getCellAt(totem.getX() - 1, totem.getY()) == null) {
            isBlocked = false;
        }
        if (totem.getX() < BOARD_SIZE - 1 && board.getCellAt(totem.getX() + 1, totem.getY()) == null) {
            isBlocked = false;
        }
        if (totem.getY() > 0 && board.getCellAt(totem.getX(), totem.getY() - 1) == null) {
            isBlocked = false;
        }
        if (totem.getY() < BOARD_SIZE - 1 && board.getCellAt(totem.getX(), totem.getY() + 1) == null) {
            isBlocked = false;
        }
        return isBlocked;
    }

    /**
     * Checks if the provided position is a valid move for the totem based on OXONO
     * rules.
     *
     * @param symbolTotem    the symbol of the totem to be moved
     * @param targetPosition the target position to check
     * @return true if the move to the specified position is allowed, false
     *         otherwise
     */
    boolean allowedTotemPosition(Symbol symbolTotem, Position targetPosition) {
        int currentRow = board.findTotemPosition(symbolTotem).getX();
        int currentCol = board.findTotemPosition(symbolTotem).getY();
        if (board.getCellAt(targetPosition.getX(), targetPosition.getY()) != null) {
            return false;
        } else if (isTotemRowAndColumnFull(currentRow, currentCol)) {
            return true;
        } else if (currentRow != targetPosition.getX() && currentCol != targetPosition.getY()) {
            return false;
        } else if (isTotemBlocked(symbolTotem)) {
            return moveTotemBlocked(currentRow, currentCol, targetPosition.getX(), targetPosition.getY());
        } else {
            return isPathClear(currentRow, currentCol, targetPosition);
        }
    }

    private boolean isTotemRowAndColumnFull(int currentTotemRow, int currentTotemCol) {
        int occupiedCells = 0;
        int BOARD_SIZE = board.getSize();
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board.getCellAt(currentTotemRow, i) != null && board.getCellAt(i, currentTotemCol) != null) {
                occupiedCells++;
            }
        }
        return occupiedCells == BOARD_SIZE;
    }

    // checks a valid totem move when it's blocked
    private boolean moveTotemBlocked(int currentRow, int currentCol, int targetRow, int targetCol) {
        boolean isValidPositon = true;
        if (currentRow == targetRow) {
            isValidPositon = VerifyTotemBlockedCol(currentRow, targetCol, currentCol);

        } else if (currentCol == targetCol) {
            isValidPositon = VerifyTotemBlockedRow(currentCol, targetRow, currentRow);
        }
        return isValidPositon;
    }

    private boolean VerifyTotemBlockedCol(int currentRow, int targetCol, int currentCol) {
        if (targetCol < currentCol) {
            for (int i = currentCol - 2; i >= targetCol; i--) {
                if (board.getCellAt(currentRow, i) == null && i != targetCol) {
                    return false;
                }
            }
        } else {
            for (int i = currentCol + 2; i <= targetCol; i++) {
                if (board.getCellAt(currentRow, i) == null && i != targetCol) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean VerifyTotemBlockedRow(int currentCol, int targetRow, int currentRow) {
        if (targetRow < currentRow) {
            for (int i = currentRow - 2; i >= targetRow; i--) {
                if (board.getCellAt(i, currentCol) == null && i != targetRow) {
                    return false;
                }
            }
        } else {
            for (int i = currentRow + 2; i <= targetRow; i++) {
                if (board.getCellAt(i, currentCol) == null && i != targetRow) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPathClear(int currentRow, int currentCol, Position targetPosition) {
        int rowStep = Integer.compare(targetPosition.getX(), currentRow);
        int colStep = Integer.compare(targetPosition.getY(), currentCol);
        currentRow += rowStep;
        currentCol += colStep;
        while (currentRow != targetPosition.getX() || currentCol != targetPosition.getY()) {
            if (board.getCellAt(currentRow, currentCol) != null) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }
        return true;
    }

    /**
     * provides a list of valid positions where the totem can be moved
     *
     * @param symbol symbol of the totem
     * @return positions of the valid move.
     */
    List<Position> getTotemMoveOptions(Symbol symbol) {
        List<Position> allMovePossible = new ArrayList<>();
        int BOARD_SIZE = board.getSize();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Position position = new Position(i, j);
                if (allowedTotemPosition(symbol, position)) {
                    allMovePossible.add(position);
                }
            }
        }
        return allMovePossible;
    }

    /**
     * Returns a list of valid positions where the totem can be moved based on the
     * game rules.
     * This method iterates over the entire game board and checks each position to
     * determine
     * if it is a valid move for the provided totem symbol. A position is considered
     * valid
     * if it meets the game's movement constraints.
     *
     * @param symbol the symbol of the totem whose move options are being evaluated
     * @return a list of {@link Position} objects representing the valid move
     *         options for the given totem
     */
    List<Position> getInsertPieceOptions(Symbol symbol) {
        List<Position> allInsertPossible = new ArrayList<>();
        int BOARD_SIZE = board.getSize();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Position position = new Position(i, j);
                if (isValidPiecePosition(symbol, position)) {
                    allInsertPossible.add(position);
                }
            }
        }
        return allInsertPossible;

    }

    /**
     * Checks if the current position (position of an inserted piece in the board)
     * results in a win by aligning 4 pieces in a row or column.
     *
     * @param insertedPiecePos the position of the current piece
     * @param symbolPiece      the symbol of the current piece
     * @return true if 4 pieces are aligned in a row or column, false otherwise
     */
    boolean checkWin(Position insertedPiecePos, Symbol symbolPiece) {
        return checkRow(insertedPiecePos.getX(), insertedPiecePos.getY(), symbolPiece, currentPlayer.getColor()) == 4
                || checkCol(insertedPiecePos.getX(), insertedPiecePos.getY(), symbolPiece,
                        currentPlayer.getColor()) == 4;
    }

    private int checkRow(int row, int col, Symbol symbolPiece, Color color) {
        int countBySymbol = 1, countByColor = 1;
        int leftCol = col - 1;
        int count = 0;
        while (count < 3 && leftCol >= 0 && board.getCellAt(row, leftCol) instanceof Piece piece) {
            if ((piece.getSymbol() != symbolPiece && piece.getColor() != color) || (countBySymbol == 4)
                    || (countByColor == 4)) {
                break;
            }
            if (piece.getSymbol() == symbolPiece) {
                countBySymbol++;
            }
            if (piece.getColor() == color) {
                countByColor++;
            }
            leftCol--;
            count++;
        }
        count = 0;
        int rightCol = col + 1;
        while (count < 3 && rightCol < board.getSize() && board.getCellAt(row, rightCol) instanceof Piece piece) {
            if ((piece.getSymbol() != symbolPiece && piece.getColor() != color) || (countBySymbol == 4)
                    || (countByColor == 4)) {
                break;
            }
            if (piece.getSymbol() == symbolPiece) {
                countBySymbol++;
            } else {
                countBySymbol = 1;
            }
            if (piece.getColor() == color) {
                countByColor++;
            } else {
                countByColor = 1;
            }

            rightCol++;
            count++;
        }

        return Math.max(countBySymbol, countByColor);
    }

    private int checkCol(int row, int col, Symbol symbolPiece, Color color) {
        int countBySymbol = 1, countByColor = 1;
        int lowerRow = row - 1;
        int count = 0;
        while (count < 3 && lowerRow >= 0 && board.getCellAt(lowerRow, col) instanceof Piece piece) {
            if ((piece.getSymbol() != symbolPiece && piece.getColor() != color) || (countBySymbol == 4)
                    || (countByColor == 4)) {
                break;
            }
            if (piece.getSymbol() == symbolPiece) {
                countBySymbol++;
            }
            if (piece.getColor() == color) {
                countByColor++;
            }

            lowerRow--;
            count++;
        }
        count = 0;
        int upperRow = row + 1;
        while (count < 3 && upperRow < board.getSize() && board.getCellAt(upperRow, col) instanceof Piece piece) {
            if ((piece.getSymbol() != symbolPiece && piece.getColor() != color) || (countBySymbol == 4)
                    || (countByColor == 4)) {
                break;
            }
            if (piece.getSymbol() == symbolPiece) {
                countBySymbol++;
            } else {
                countBySymbol = 1;
            }
            if (piece.getColor() == color) {
                countByColor++;
            } else {
                countByColor = 1;
            }
            upperRow++;
            count++;
        }
        return Math.max(countBySymbol, countByColor);
    }

    /**
     * Moves the totem to the specified position on the board.
     * For the actual movement logic, see {@link Board#moveTotem(Symbol, Position)}.
     *
     * @param symbolTotem    the symbol of the totem to move
     * @param targetPosition the target position where the totem should be moved
     */
    void moveTotem(Symbol symbolTotem, Position targetPosition) {
        board.moveTotem(symbolTotem, targetPosition);
    }

    /**
     * Inserts a piece on the board at the specified position and removes it from
     * the player's stack.
     *
     * @param posToInsert the position where the piece will be inserted
     * @param symbolPiece the symbol of the inserted piece
     * @param player      the currentPlayer
     */
    void setPiece(Position posToInsert, Symbol symbolPiece, Player player) {
        board.setPiece(posToInsert, player, symbolPiece);
    }

    /**
     * Gets the position of the totem with the specified symbol.
     *
     * @param symbolTotem the symbol of the totem (e.g., X or O)
     * @return the position of the totem with the specified symbol
     */
    Position findTotemPosition(Symbol symbolTotem) {
        return board.findTotemPosition(symbolTotem);
    }

    /**
     * Removes a piece from the board at the specified position.
     * For the actual removal logic, see
     * {@link Board#removePiece(Position, Player)}.
     *
     * @param position the position of the piece to be removed
     * @param player   the player who owns the piece being removed
     */
    void removePieceAtPosition(Position position, Player player) {
        board.removePiece(position, player);
    }

    /**
     * Checks if the board is full.
     * For the actual check, refer to the {@link Board#isFull()} method.
     *
     * @return true if the board is full, false otherwise
     */
    boolean isBoardFull() {
        return board.isFull();
    }

    /**
     * Sets the game state to the specified new state.
     *
     * @param state the new game state
     */
    void setGameState(GameState state) {
        this.state = state;
    }

    /**
     * Retrieves the current game state.
     *
     * @return the current game state
     */
    GameState getState() {
        return state;
    }

    /**
     * get the size of the board
     *
     * @return the size of the board
     */
    int getBoardSize() {
        return board.getSize();
    }

    /**
     * Counts the number of available (empty) slots on the board.
     *
     * @return the number of free slots remaining on the board
     */
    int countFreeBoardSlots() {
        return board.countFreeSlots();
    }

    /**
     * Retrieves the current player.
     *
     * @return the current player
     */
    Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Retrieves the symbol of the last moved totem.
     *
     * @return the symbol of the last moved totem
     */
    Symbol getLastTotemSymbol() {
        return board.getLastTotemSymbol();
    }

    /**
     * Retrieves the remaining stack of pieces for the specified player based on
     * their color and symbol.
     *
     * @param color  the color of the player
     * @param symbol the symbol of the pieces in the stack
     * @return the number of remaining pieces for the specified player and symbol
     */
    int getTheStackOfThePlayer(Color color, Symbol symbol) {
        return color == Color.BLACK ? playerBlack.getRemainingPieces(symbol) : playerPink.getRemainingPieces(symbol);
    }

    /**
     * switch the current player
     */
    void switchPlayer() {
        currentPlayer = currentPlayer == playerBlack ? playerPink : playerBlack;
    }

    /**
     * Retrieves the token at the specified position on the board.
     *
     * @param position position to get the case
     * @return the token present at the specified position, or null if no token is
     *         present
     */
    Token getCaseAt(Position position) {
        return board.getCellAt(position.getX(), position.getY());

    }

    /**
     * Checks if both players have no remaining pieces in their stacks.
     *
     * @return true if both players' stacks (for both symbols) are empty, false
     *         otherwise
     */
    boolean areAllStacksEmpty() {
        return playerBlack.getRemainingPieces(Symbol.X) == 0 && playerBlack.getRemainingPieces(Symbol.O) == 0 &&
                playerPink.getRemainingPieces(Symbol.X) == 0 && playerPink.getRemainingPieces(Symbol.O) == 0;
    }

    /**
     * Checks if the current player's stack of the provided symbol is empty.
     *
     * @param symbol the symbol (X or O) whose stack to check
     * @return true if the current player's stack for the given symbol is empty,
     *         false otherwise
     */
    boolean isStackEmpty(Symbol symbol) {
        return currentPlayer.isStackEmpty(symbol);
    }

    /**
     * this method will return the last symbol used from the player pink (not a bot)
     * @return the last symbol that was placed on the board.
     */
    Symbol getLastUsedPinkSymbol(){
        return  playerPink.getLastUsedSymbol();
    }
}
