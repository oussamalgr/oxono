package g62227.dev3.oxono.model;


/**
 * this class represents a piece.
 * a piece has 2 symbol : X or O and 2 colors :  black or pink
 */
public class Piece extends Token {
    public static final String BLACK_TEXT = "\u001B[30m";
    public static final String PINK_TEXT = "\u001B[95m";
    public static final String RESET = "\u001B[0m";
    private Color color;

    /**
     * constructor of the Piece class
     * will be instantiated with a color and a symbol
     * @param color
     * @param symbol
     */
    public Piece(Color color, Symbol symbol) {
        super(symbol);
        this.color = color;
    }

    /**
     * Retrieves the color of the piece.
     * This method returns the color of the piece, which can either be black or pink.
     *
     * @return the color of the piece (either black or pink)
     */
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        if (color == Color.BLACK) {
            return BLACK_TEXT + super.toString() + RESET;
        } else {
            return PINK_TEXT + super.toString() + RESET;
        }

    }
}
