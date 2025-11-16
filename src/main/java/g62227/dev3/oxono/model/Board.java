package g62227.dev3.oxono.model;

import java.util.Random;

/**
 * Represent the board of the OXONO Game
 */
public class Board {
    private Token[][] board;
    private Totem totemX;
    private Totem totemO;
    private Totem lastTotemMoved;
    private int filledSlot;

    /**
     * Constructs a new game board of the specified size and initializes the positions of the totems.
     * The board must be at least 2x2 in size.
     * The positions of the totems (X and O) are randomly assigned to two predefined central slots,
     * depending on the size of the board.
     * @param size the size of the board (number of rows and columns). Must be >= 2.
     * @throws IllegalArgumentException if the size is less than 2.
     */
    Board(int size) {
        if (size < 2 || size > 9) {
            throw new IllegalArgumentException("Not a valid size");
        }
        board = new Token[size][size];
        filledSlot = 0;
        int boardCenter = size / 2;
        Position positionA = new Position(boardCenter, boardCenter);
        Position positionB = new Position(boardCenter - 1, boardCenter - 1);

        Random random = new Random();
        boolean randomSwitch = random.nextBoolean();
        Position totemXPosition = randomSwitch ? positionA : positionB;
        Position totemOPosition = randomSwitch ? positionB : positionA;

        totemX = new Totem(Symbol.X, totemXPosition);
        totemO = new Totem(Symbol.O, totemOPosition);

        board[totemO.getX()][totemO.getY()] = totemO;
        board[totemX.getX()][totemX.getY()] = totemX;
    }

    /**
     * Moves the totem on the board to the specified position.
     *
     * @param symbolTotem the symbol of the totem to be moved
     * @param position the position where the totem will be moved
     */
    void moveTotem(Symbol symbolTotem, Position position) {
        if (isPositionOutOfBound(position)) {
            throw new IllegalArgumentException("Not a valid position");
        }
        int startRow = (symbolTotem == Symbol.X) ? totemX.getX() : totemO.getX();
        int startCol = (symbolTotem == Symbol.X) ? totemX.getY() : totemO.getY();
        board[position.getX()][position.getY()] = board[startRow][startCol];
        board[startRow][startCol] = null;
        updateTotem(position.getX(), position.getY());
    }


    private void updateTotem(int newRowIndex, int newColIndex) {
        if (board[newRowIndex][newColIndex].getSymbol() == Symbol.X) {
            totemX.setX(newRowIndex);
            totemX.setY(newColIndex);
            lastTotemMoved = totemX;
        } else {
            totemO.setX(newRowIndex);
            totemO.setY(newColIndex);
            lastTotemMoved = totemO;
        }
    }

    /**
     * Inserts a piece on the board at the specified position and removes it from the player's stack.
     *
     * @param position the position where the piece will be inserted
     * @param currentPlayer the player who inserts the piece
     * @param symbolPiece the symbol of the inserted piece
     * @throws IllegalArgumentException if the position is out of bounds
     */
    void setPiece(Position position, Player currentPlayer, Symbol symbolPiece) {
        if (isPositionOutOfBound(position)) {
            throw new IllegalArgumentException("Not a valid position");
        }
        board[position.getX()][position.getY()] = currentPlayer.removePiece(symbolPiece);
        filledSlot++;

    }

    private boolean isPositionOutOfBound(Position position) {
        return position.getX() < 0 || position.getX() >= board.length || position.getY() < 0
                || position.getY() >= board.length;
    }

    /**
     * Gets the size of the board
     * @return the size of the board
     */
    int getSize() {
        return board.length;
    }

    /**
     * Gets the symbol of the last totem moved.
     * @return symbol of the last totem moved
     */
    Symbol getLastTotemSymbol () {
        if(lastTotemMoved == null){
            return null;
        }else{
            return lastTotemMoved.getSymbol();
        }
    }

    /**
     * Gets the cell of the board at the specified x and y position.
     *
     * @param x the row position on the board
     * @param y the column position on the board
     * @return the token at the specified position
     */
    Token getCellAt(int x, int y) {
        return board[x][y];
    }

    /**
     * Gets the position of the totem with the specified symbol.
     *
     * @param symbolTotem the symbol of the totem (could be X or O)
     * @return the position of the totem with the specified symbol
     */
    Position findTotemPosition(Symbol symbolTotem) {
        Position totem;
        if (symbolTotem == Symbol.O) {
            totem = new Position(totemO.getX(), totemO.getY());
        } else {
            totem = new Position(totemX.getX(), totemX.getY());
        }

        return totem;
    }

    /**
     * Removes a piece from the board at the specified position and returns it to the player's stack.
     *
     * @param position the position of the piece to be removed from the board
     * @param player the player who will receive the piece removed from the board
     */
    void removePiece(Position position, Player player) {
        if (board[position.getX()][position.getY()] instanceof Piece
                && board[position.getX()][position.getY()] != null) {
            Symbol lastPieceInserted = board[position.getX()][position.getY()].getSymbol();
            player.addPiece(lastPieceInserted);
            board[position.getX()][position.getY()] = null;
            filledSlot--;
        }
    }

    /**
     * Checks if the board is full.
     * A board is considered full when there are no free slots remaining.
     *
     * @return true if there are no free slots on the board, otherwise false
     */
    boolean isFull() {
        return countFreeSlots() == 0;
    }

    /**
     * Counts the number of free (empty) slots remaining on the board.
     * The board is assumed to have a total of (board.length * board.length) slots,
     * with 2 slots initially occupied by the totems.
     *
     * @return the number of free slots remaining on the board
     */
    int countFreeSlots() {
        return (board.length * board.length - 2) - filledSlot;
    }
}
