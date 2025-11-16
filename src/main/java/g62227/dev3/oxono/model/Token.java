package g62227.dev3.oxono.model;


/**
 * Represents a token in the game, which can either be a piece or a totem.
 */
public class Token {

    private Symbol symbol;

    /**
     * constructor of the class token
     * This constructor initializes the token with the given symbol (X or O)
     * @param symbol X or O
     */
    public Token(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the symbol of the token.
     *
     * @return the symbol of the token (either X or O)
     */
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol.SymbolName;
    }
}
