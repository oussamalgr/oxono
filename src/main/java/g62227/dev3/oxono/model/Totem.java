package g62227.dev3.oxono.model;

/**
 * Represents a Totem in the OXONO game.
 * A Totem is a specialized token that has a position on the game board and a symbol
 * (X or O)
 */
public class Totem extends Token {

    private Position currentPosition;
    public static final String RESET = "\u001B[0m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    /**
     * Constructs a new Totem with the specified symbol and position.
     *
     * @param symbol   The symbol representing the Totem (either 'X' or 'O').
     * @param position The position of the Totem on the game board.
     */
    public Totem(Symbol symbol, Position position) {
        super(symbol);
        this.currentPosition = position;

    }

    /**
     * Gets the x-coordinate of the Totem's position.
     *
     * @return The x-coordinate of the Totem's position.
     */
    public int getX() {

        return currentPosition.getX();
    }

    /**
     * Gets the y-coordinate of the Totem's position.
     *
     * @return The y-coordinate of the Totem's position.
     */
    public int getY() {

        return currentPosition.getY();
    }

    /**
     * Sets the x-coordinate of the Totem's position.
     *
     * @param x The new x-coordinate of the Totem's position.
     */
     void setX(int x) {
        this.currentPosition.setX(x);
    }

    /**
     * Sets the y-coordinate of the Totem's position.
     *
     * @param y The new y-coordinate of the Totem's position.
     */
    void setY(int y) {
        this.currentPosition.setY(y);
    }

    @Override
    public String toString() {
        return ANSI_BLUE_BACKGROUND + super.toString() + RESET;
    }
}
