package g62227.dev3.oxono.model;

import java.util.List;
import java.util.Random;

public class EasyBotStrategy implements Strategy {


    private Random random = new Random();
    private Symbol currentSymbol;


    @Override
    public Position executeMoveTotem(Oxono oxono) {
        this.currentSymbol = new Random().nextBoolean() ? Symbol.X : Symbol.O;
        List<Position> allMovePossible = oxono.getTotemMoveOptions(this.currentSymbol);
        return allMovePossible.get(random.nextInt(allMovePossible.size()));
    }

    @Override
    public Symbol getCurrentSymbol() {
        return currentSymbol;
    }


    @Override
    public Position executeInsertPiece(Oxono oxono, Symbol symbolPiece) {
        List<Position> allInsertPossible = oxono.getInsertPieceOptions(symbolPiece);
        return allInsertPossible.get(random.nextInt(allInsertPossible.size()));
    }
}
