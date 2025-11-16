package g62227.dev3.oxono.model;


/**
 * represents a symbol X or O
 */
public enum Symbol {
    X("X"), O("O");


    final String SymbolName;

    Symbol(String SymbolName) {
        this.SymbolName = SymbolName;
    }

    @Override
    public String toString() {
        return SymbolName;
    }

}
