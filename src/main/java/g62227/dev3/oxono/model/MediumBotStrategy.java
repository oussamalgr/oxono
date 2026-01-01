package g62227.dev3.oxono.model;

import java.util.List;
import java.util.Random;

public class MediumBotStrategy implements Strategy {


    private Symbol currentSymbol;
    private Random random = new Random();

    @Override
    public Position executeMoveTotem(Oxono oxono) {
        Position winningPosMove = testWinningMoveForBothSymbols(oxono);
        if (winningPosMove != null) {
            return winningPosMove;
        }

        this.currentSymbol = new Random().nextBoolean() ? Symbol.X : Symbol.O;
        List<Position> allMovePossible = oxono.getTotemMoveOptions(this.currentSymbol);
        return allMovePossible.get(random.nextInt(allMovePossible.size()));
    }

    @Override
    public Symbol getCurrentSymbol() {
        return currentSymbol;
    }

    private Position checkWinningPlacement(Oxono oxono, Symbol symbol) {
        Player botPlayer = oxono.getCurrentPlayer();
        List<Position> canInsertList = oxono.getInsertPieceOptions(symbol);
        for (Position pos : canInsertList) {
            oxono.setPiece(pos, symbol, botPlayer);
            boolean isWinningMove = oxono.checkWin(pos, symbol);
            oxono.removePieceAtPosition(pos, botPlayer);
            if (isWinningMove) {
                return pos;
            }
        }
        return null;
    }


    private Position testWinningMoveForBothSymbols(Oxono oxono) {
        Position oldPositionX = oxono.findTotemPosition(Symbol.X);
        Position oldPositionO = oxono.findTotemPosition(Symbol.O);

        for (Position pos : oxono.getTotemMoveOptions(Symbol.X)) {
            oxono.moveTotem(Symbol.X, pos);
            Position insertPosX = checkWinningPlacement(oxono, Symbol.X);
            oxono.moveTotem(Symbol.X, oldPositionX);
            this.currentSymbol = Symbol.X;
            if (insertPosX != null) {
                return pos;
            }
        }
        for (Position pos : oxono.getTotemMoveOptions(Symbol.O)) {
            oxono.moveTotem(Symbol.O, pos);
            Position insertPosO = checkWinningPlacement(oxono, Symbol.O);
            oxono.moveTotem(Symbol.O, oldPositionO);
            this.currentSymbol = Symbol.O;
            if (insertPosO != null) {
                return pos;
            }
        }
        return null;
    }

    @Override
    public Position executeInsertPiece(Oxono oxono, Symbol symbolPiece) {
        Player botPlayer = oxono.getCurrentPlayer();
        for (Position pos : oxono.getInsertPieceOptions(symbolPiece)) {
            oxono.setPiece(pos, symbolPiece, botPlayer);
            boolean isWinningMove = oxono.checkWin(pos, symbolPiece);
            oxono.removePieceAtPosition(pos, botPlayer);
            if (isWinningMove) {
                return pos;
            }
        }
        List<Position> allInsertPossible = oxono.getInsertPieceOptions(symbolPiece);
        return allInsertPossible.get(random.nextInt(allInsertPossible.size()));
    }
}
