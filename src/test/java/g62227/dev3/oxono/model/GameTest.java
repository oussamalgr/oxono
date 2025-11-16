package g62227.dev3.oxono.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private Game game;

    @BeforeEach
    void create() {
        game = new Game();
    }

    @Test
    void cant_move_totem_twice_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.moveTotem(TotemToMove, new Position(1, 0));
        assertNull(game.getCaseAt(new Position(1, 0)));
    }

    @Test
    void Start_Game_wrong_bot_level_test() {
        int boardSize = 6;
        // bot should be 0 or 1
        int botLevel = -1;
        assertThrows(IllegalArgumentException.class, () -> game.startGame(boardSize, botLevel));
    }

    @Test
    void Start_Game_wrong_board_size_test() {
        // boardsize should be between 2 and 9 included
        int boardSize = 10;
        int botLevel = 1;
        assertThrows(IllegalArgumentException.class, () -> game.startGame(boardSize, botLevel));
    }

    @Test
    void move_totem_if_state_insert_test() {
        // get the totem at 2,2 position to get its symbol
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        // move it first
        game.moveTotem(TotemToMove, new Position(2, 0));
        assertFalse(game.moveTotem(TotemToMove, new Position(2, 0)));
    }

    @Test
    void move_totem_in_wrong_place_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        // moving the totem in a wrong place
        assertFalse(game.moveTotem(TotemToMove, new Position(0, 0)));
    }

    @Test
    void state_should_be_at_move_totem_if_totem_moved_in_wrong_place_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(0, 0));
        assertTrue(game.isStateMove());
    }

    @Test
    void state_should_be_at_set_piece_if_totem_moved_in_good_place_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        assertTrue(game.isStateInsert());
    }

    @Test
    void state_should_be_at_set_piece_if_wrong_inserted_place_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.setPiece(new Position(5, 5), TotemToMove);
        assertTrue(game.isStateInsert());
    }

    @Test
    void state_should_be_at_move_totem_after_insert_a_piece_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.setPiece(new Position(2, 1), TotemToMove);
        assertTrue(game.isStateMove());
    }

    @Test
    void state_should_be_at_winner_after_check_win_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.setPiece(new Position(2, 1), TotemToMove);
        game.moveTotem(TotemToMove, new Position(3, 0));
        game.setPiece(new Position(3, 1), TotemToMove);
        game.moveTotem(TotemToMove, new Position(4, 0));
        game.setPiece(new Position(4, 1), TotemToMove);
        game.moveTotem(TotemToMove, new Position(5, 0));
        game.setPiece(new Position(5, 1), TotemToMove);
        game.checkWin(new Position(5, 1), TotemToMove);
        assertTrue(game.isStateWinner());
    }

    @Test
    void state_should_be_at_surrender_after_give_up_test() {
        game.surrender();
        assertTrue(game.isStateSurrender());
    }

    @Test
    void cant_move_totem_after_surrender_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.surrender();
        assertFalse(game.moveTotem(TotemToMove, new Position(2, 0)));
    }

    @Test
    void cant_insert_piece_after_surrender_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.surrender();
        assertFalse(game.setPiece(new Position(2, 1), TotemToMove));

    }

    @Test
    void cant_move_totem_after_win_test() {
        game.setStateAtWinner();
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        assertFalse(game.moveTotem(TotemToMove, new Position(2, 0)));
    }

    @Test
    void is_state_draw_when_board_full_trst() {
        game.startGame(2, 0);
        Totem totem = (Totem) game.getCaseAt(new Position(0, 0));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(0, 1));
        game.setPiece(new Position(0, 0), TotemToMove);
        totem = (Totem) game.getCaseAt(new Position(1, 1));
        TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(1, 0));
        game.setPiece(new Position(1, 1), TotemToMove);
        assertTrue(game.isStateDraw());
    }

    @Test
    void is_player_switched_after_insert_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        assertEquals(Color.PINK, game.getCurrentPlayerColor());
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.setPiece(new Position(2, 1), TotemToMove);
        // a check win is done before switching player
        game.checkWin(new Position(2, 1), TotemToMove);
        assertEquals(Color.BLACK, game.getCurrentPlayerColor());

    }

    @Test
    void is_move_options_list_unmodifiable_test() {
        List<Position> allPossibleMove = game.getTotemMoveOptions(Symbol.X);
        assertThrows(UnsupportedOperationException.class, () -> allPossibleMove.add(new Position(9, 9)));

    }

    @Test
    void is_insert_options_list_unmodifiable_test() {
        List<Position> allPossibleInsert = game.getInsertPieceOptions(Symbol.X);
        assertThrows(UnsupportedOperationException.class, () -> allPossibleInsert.add(new Position(9, 9)));

    }

    @Test
    void is_undo_working_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        assertNull(game.getCaseAt(new Position(2, 2)));
        game.undo();
        assertNotNull(game.getCaseAt(new Position(2, 2)));

    }

    @Test
    void is_redo_working_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.undo();
        assertNotNull(game.getCaseAt(new Position(2, 2)));
        game.redo();
        assertNull(game.getCaseAt(new Position(2, 2)));
    }

    @Test
    void is_triple_undo_when_can_undo_and_state_at_move_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.setPiece(new Position(2, 1), TotemToMove);
        game.checkWin(new Position(2, 1), TotemToMove);
        game.moveTotem(TotemToMove, new Position(1, 0));
        game.setPiece(new Position(1, 1), TotemToMove);
        game.checkWin(new Position(1, 1), TotemToMove);
        game.undo();
        assertEquals(GameState.SET_PIECE, game.getState());
    }

    @Test
    void is_triple_redo_when_triple_undo_done_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.setPiece(new Position(2, 1), TotemToMove);
        game.checkWin(new Position(2, 1), TotemToMove);
        game.moveTotem(TotemToMove, new Position(1, 0));
        game.setPiece(new Position(1, 1), TotemToMove);
        game.checkWin(new Position(1, 1), TotemToMove);
        game.undo();
        game.redo();
        // as a triple undo will pass the bot (pink) player and bring
        // the state to setPiece and Color will be Black
        assertEquals(GameState.MOVE_TOTEM, game.getState());
    }

    @Test
    void cant_undo_test() {
        assertFalse(game.canUndo());

    }

    @Test
    void can_undo_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        assertTrue(game.canUndo());

    }

    @Test
    void cant_redo_test() {
        assertFalse(game.canRedo());
    }

    @Test
    void can_redo_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.undo();
        assertTrue(game.canRedo());
    }

    @Test
    void get_size_of_board_test() {
        game.startGame(8, 1);
        assertEquals(8, game.getSizeOfBoard());
    }

    @Test
    void count_empty_slot_in_the_board_test() {
        game.startGame(3, 1);
        assertEquals(7, game.countFreeBoardSlots());
    }

    @Test
    void get_current_player_color_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();

        assertEquals(Color.PINK, game.getCurrentPlayerColor());

        game.moveTotem(TotemToMove, new Position(2, 0));
        game.setPiece(new Position(2, 1), TotemToMove);
        game.checkWin(new Position(2, 1), TotemToMove);

        assertEquals(Color.BLACK, game.getCurrentPlayerColor());

    }

    @Test
    void get_remaining_stack_pieces_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.setPiece(new Position(2, 1), TotemToMove);
        assertEquals(7, game.getRemainingStackPieces(Color.PINK, TotemToMove));
    }

    @Test
    void find_totem_position_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        assertEquals(new Position(2, 2), game.findTotemPosition(totem.getSymbol()));
    }

    @Test
    void find_last_totem_moved_symbol_test() {
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        assertEquals(TotemToMove, game.getLastTotemSymbol());
    }


    @Test
    void last_piece_inserted_by_pink_player_test(){
        Totem totem = (Totem) game.getCaseAt(new Position(2, 2));
        Symbol TotemToMove = totem.getSymbol();
        game.moveTotem(TotemToMove, new Position(2, 0));
        game.setPiece(new Position(2,1),TotemToMove);
        game.checkWin(new Position(2,1),TotemToMove);
        Totem totem1 = (Totem) game.getCaseAt(new Position(3, 3));
        Symbol TotemToMove1 = totem1.getSymbol();
        game.moveTotem(TotemToMove1, new Position(0, 0));
        game.setPiece(new Position(0,1),TotemToMove1);
        game.checkWin(new Position(0,1),TotemToMove1);
        assertEquals(TotemToMove,game.getLastUsedSymbolFromPink());
    }
}
