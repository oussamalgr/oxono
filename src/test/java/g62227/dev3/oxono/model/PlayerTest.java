package g62227.dev3.oxono.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {



    private Player blackPlayer;
    private Player pinkPlayer;

    @BeforeEach
    void setUp() {
        blackPlayer = new Player(Color.BLACK);
        pinkPlayer = new Player(Color.PINK);
    }

    @Test
    void testInitialPieces() {
        assertEquals(8, blackPlayer.getRemainingPieces(Symbol.O));
        assertEquals(8, blackPlayer.getRemainingPieces(Symbol.X));
        assertEquals(8, pinkPlayer.getRemainingPieces(Symbol.O));
        assertEquals(8, pinkPlayer.getRemainingPieces(Symbol.X));
    }

    @Test
    void testRemovePiece() {
        blackPlayer.removePiece(Symbol.X);
        assertEquals(7, blackPlayer.getRemainingPieces(Symbol.X));

        pinkPlayer.removePiece(Symbol.O);
        assertEquals(7, pinkPlayer.getRemainingPieces(Symbol.O));
    }


    @Test
    void testAddPiece() {
        blackPlayer.addPiece(Symbol.X);
        assertEquals(9, blackPlayer.getRemainingPieces(Symbol.X));

        pinkPlayer.addPiece(Symbol.O);
        assertEquals(9, pinkPlayer.getRemainingPieces(Symbol.O));
    }

    @Test
    void testIsEmpty() {
        for (int i = 0; i < 8; i++) {
            blackPlayer.removePiece(Symbol.X);
        }
        assertTrue(blackPlayer.isStackEmpty(Symbol.X));
        assertFalse(pinkPlayer.isStackEmpty(Symbol.O));
    }

    @Test
    void testGetColor() {
        assertEquals(Color.BLACK, blackPlayer.getColor());
        assertEquals(Color.PINK, pinkPlayer.getColor());
    }



    @Test
    void testIsBotControlled() {
        blackPlayer.setBotBehavior(new BotStrategy());
        assertTrue(blackPlayer.isBotControlled());
        pinkPlayer.setBotBehavior(null);
        assertFalse(pinkPlayer.isBotControlled() );
    }

    @Test
    void last_piece_added_symbol_should_be_X_test(){
        blackPlayer.removePiece(Symbol.X);
        assertEquals(Symbol.X,blackPlayer.getLastUsedSymbol());
    }
    @Test
    void last_piece_added_symbol_should_be_O_test(){
        blackPlayer.removePiece(Symbol.O);
        assertEquals(Symbol.O,blackPlayer.getLastUsedSymbol());
    }

}