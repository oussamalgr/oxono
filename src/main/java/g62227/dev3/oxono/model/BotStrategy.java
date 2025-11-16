package g62227.dev3.oxono.model;

import java.util.List;
import java.util.Random;

class BotStrategy implements Strategy {
    private Random random = new Random();
    private Symbol currentSymbol;
    private int level = -1;


    @Override
    public Position executeMoveTotem(Oxono oxono) {
        if (this.level == 1) {
            Position winningPosMove = testWinningMoveForBothSymbols(oxono);
            if (winningPosMove != null) {
                return winningPosMove;
            }
        }

        this.currentSymbol = new Random().nextBoolean() ? Symbol.X : Symbol.O;
        List<Position> allMovePossible = oxono.getTotemMoveOptions(this.currentSymbol);
        return allMovePossible.get(random.nextInt(allMovePossible.size()));

    }

    @Override
    public Position executeInsertPiece(Oxono oxono, Symbol symbol) {
        Player botPlayer = oxono.getCurrentPlayer();
        if (this.level == 1) {
            for (Position pos : oxono.getInsertPieceOptions(symbol)) {
                oxono.setPiece(pos, symbol, botPlayer);
                boolean isWinningMove = oxono.checkWin(pos, symbol);
                oxono.removePieceAtPosition(pos, botPlayer);
                if (isWinningMove) {
                    return pos;
                }
            }
        }
        List<Position> allInsertPossible = oxono.getInsertPieceOptions(symbol);
        return allInsertPossible.get(random.nextInt(allInsertPossible.size()));
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
    public Symbol getCurrentSymbol() {
        return currentSymbol;
    }

    @Override
    public void setLevel(int lvl) {
        if (lvl < 0 || lvl > 1) {
            throw new IllegalArgumentException("Error : current level is  : " + lvl
                    + " but should be 0 or 1");
        }
        this.level = lvl;
    }

}
