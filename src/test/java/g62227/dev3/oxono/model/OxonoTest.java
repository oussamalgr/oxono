package g62227.dev3.oxono.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class OxonoTest {


    private Oxono oxono;
    private Player playerblack;
    private Player playerPink;

    @BeforeEach
    void instantiate_game() {
        oxono = new Oxono();
        playerblack = new Player(Color.BLACK);
        playerPink = new Player(Color.PINK);
    }


    @Test
    void totemCannotMoveDiagonally_test() {
        Totem token = (Totem) oxono.getCaseAt(new Position(2, 2));
        assertFalse(oxono.allowedTotemPosition(token.getSymbol(), new Position(3, 3)));

    }


    @Test
    void moveTotem_in_right_case_test() {
        Totem token = (Totem) oxono.getCaseAt(new Position(2, 2));
        assertTrue(oxono.allowedTotemPosition(token.getSymbol(), new Position(2, 0)));
    }

    @Test
    void Totem_must_not_pass_piece_test() {
        Totem token = (Totem) oxono.getCaseAt(new Position(2, 2));
        oxono.setPiece(new Position(2, 1), token.getSymbol(), playerblack);
        assertFalse(oxono.allowedTotemPosition(token.getSymbol(), new Position(2, 0)));

    }

    @Test
    void totem_must_pass_pawn_if_blocked_test() {
        Totem token = (Totem) oxono.getCaseAt(new Position(2, 2));
        oxono.moveTotem(token.getSymbol(), new Position(0, 0));
        oxono.setPiece(new Position(0, 1), token.getSymbol(), playerblack);
        oxono.setPiece(new Position(1, 0), token.getSymbol(), playerblack);
        assertTrue(oxono.allowedTotemPosition(token.getSymbol(), new Position(0, 2)));
    }

    @Test
    void totem_must_only_pass_pawn_in_the_first_free_slot_if_blocked() {
        Totem token = (Totem) oxono.getCaseAt(new Position(2, 2));
        oxono.moveTotem(token.getSymbol(), new Position(0, 0));
        oxono.setPiece(new Position(0, 1), token.getSymbol(), playerblack);
        oxono.setPiece(new Position(1, 0), token.getSymbol(), playerblack);
        assertFalse(oxono.allowedTotemPosition(token.getSymbol(), new Position(0, 3)));
    }

    @Test
    void totem_can_be_placed_everywhere_if_row_and_col_full() {
        Totem token = (Totem) oxono.getCaseAt(new Position(2, 2));
        oxono.moveTotem(token.getSymbol(), new Position(0, 0));
        oxono.setPiece(new Position(0, 1), token.getSymbol(), playerblack);
        oxono.setPiece(new Position(0, 2), token.getSymbol(), playerblack);
        oxono.setPiece(new Position(0, 3), token.getSymbol(), playerblack);
        oxono.setPiece(new Position(0, 4), token.getSymbol(), playerblack);
        oxono.setPiece(new Position(0, 5), token.getSymbol(), playerblack);

        oxono.setPiece(new Position(1, 0), token.getSymbol(), playerblack);
        oxono.setPiece(new Position(2, 0), token.getSymbol(), playerblack);
        oxono.setPiece(new Position(3, 0), token.getSymbol(), playerblack);
        oxono.setPiece(new Position(4, 0), token.getSymbol(), playerblack);
        oxono.setPiece(new Position(5, 0), token.getSymbol(), playerblack);

        assertTrue(oxono.allowedTotemPosition(token.getSymbol(), new Position(5, 5)));

    }


    @Test
    void setPiece_not_next_a_totem() {
        Totem token = (Totem) oxono.getCaseAt(new Position(2, 2));
        assertFalse(oxono.isValidPiecePosition(token.getSymbol(), new Position(2, 4)));
    }

    @Test
    void setPiece_when_totem_blocked() {
        Totem token = (Totem) oxono.getCaseAt(new Position(2, 2));
        oxono.moveTotem(token.getSymbol(), new Position(0, 0));
        oxono.setPiece(new Position(0, 1), token.getSymbol(), playerblack);
        oxono.setPiece(new Position(1, 0), token.getSymbol(), playerblack);
        assertTrue(oxono.isValidPiecePosition(token.getSymbol(), new Position(5, 5)));
    }

    @Test
    void setPiece_in_not_null_slot() {
        Totem token = (Totem) oxono.getCaseAt(new Position(2, 2));
        oxono.moveTotem(token.getSymbol(), new Position(2, 0));
        assertFalse(oxono.isValidPiecePosition(token.getSymbol(), new Position(2, 0)));
    }


    @Test
    void pieceCannotBePlacedOnOccupiedPosition() {
        Totem token = (Totem) oxono.getCaseAt(new Position(2, 2));
        oxono.setPiece(new Position(2, 3), token.getSymbol(), playerblack);
        assertFalse(oxono.isValidPiecePosition(token.getSymbol(), new Position(2, 3)));
    }


    @Test
    void get_current_state() {
        assertEquals(GameState.MOVE_TOTEM, oxono.getState());
    }

    @Test
    void get_size_of_board_test() {
        assertEquals(6, oxono.getBoardSize());
    }

    @Test
    void count_empty_slot_in_the_board_test() {
        assertEquals(34, oxono.countFreeBoardSlots());
    }

    @Test
    void get_current_player_test() {
        assertEquals(Color.PINK, oxono.getCurrentPlayer().getColor());
    }


    @Test
    void detects_Win_By_Color_Alignment_test() {
        oxono.setPiece(new Position(0, 0), Symbol.O, playerblack);
        oxono.setPiece(new Position(1, 0), Symbol.O, playerblack);
        oxono.setPiece(new Position(2, 0), Symbol.O, playerblack);
        oxono.setPiece(new Position(3, 0), Symbol.O, playerblack);
        assertTrue(oxono.checkWin(new Position(3, 0), Symbol.O));
    }

    @Test
    void detect_win_by_last_symbol_and_different_Color_test() {
        oxono.setPiece(new Position(0, 0), Symbol.O, playerblack);
        oxono.setPiece(new Position(1, 0), Symbol.O, playerPink);
        oxono.setPiece(new Position(2, 0), Symbol.O, playerblack);
        assertTrue(oxono.checkWin(new Position(3, 0), Symbol.O));
    }

    @Test
    void not_a_winning_case_if_last_piece_not_same_Color_test() {
        oxono.setPiece(new Position(0, 0), Symbol.O, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 0), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(2, 0), Symbol.O, oxono.getCurrentPlayer());
        oxono.switchPlayer();
        assertFalse(oxono.checkWin(new Position(3, 0), Symbol.X));
    }

    @Test
    void winner_case_if_five_piece_align_test() {
        oxono.setPiece(new Position(0, 0), Symbol.O, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 0), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(2, 0), Symbol.O, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(4, 0), Symbol.O, oxono.getCurrentPlayer());
        assertTrue(oxono.checkWin(new Position(3, 0), Symbol.X));
    }


    @Test
    void check_Win_Horizontal_Same_Symbol_same_color_test() {
        oxono.switchPlayer();
        oxono.setPiece(new Position(1, 0), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 1), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 2), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 3), Symbol.X, oxono.getCurrentPlayer());
        assertTrue(oxono.checkWin(new Position(1, 2), Symbol.X));
    }

    @Test
    void check_Win_Horizontal_Same_Symbol_Different_Color_test() {
        oxono.switchPlayer();
        oxono.setPiece(new Position(1, 0), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 1), Symbol.X, oxono.getCurrentPlayer());
        oxono.switchPlayer();
        oxono.setPiece(new Position(1, 2), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 3), Symbol.X, oxono.getCurrentPlayer());
        assertTrue(oxono.checkWin(new Position(1, 2), Symbol.X));
    }

    @Test
    void check_Win_Horizontal_Same_Color_Different_Symbol() {
        oxono.setPiece(new Position(1, 0), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 1), Symbol.O, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 2), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 3), Symbol.O, oxono.getCurrentPlayer());
        assertTrue(oxono.checkWin(new Position(1, 2), Symbol.X));
    }

    @Test
    void check_Win_Horizontal_Different_Symbol_And_Color_test() {
        oxono.switchPlayer();
        oxono.setPiece(new Position(1, 0), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 1), Symbol.O, oxono.getCurrentPlayer());
        oxono.switchPlayer();
        oxono.setPiece(new Position(1, 2), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 3), Symbol.O, oxono.getCurrentPlayer());
        assertFalse(oxono.checkWin(new Position(1, 2), Symbol.X));
    }

    @Test
    void check_Win_Vertical_Same_Symbol_Different_Color_test() {
        oxono.switchPlayer();
        oxono.setPiece(new Position(0, 0), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 0), Symbol.X, oxono.getCurrentPlayer());
        oxono.switchPlayer();
        oxono.setPiece(new Position(2, 0), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(3, 0), Symbol.X, oxono.getCurrentPlayer());
        assertTrue(oxono.checkWin(new Position(2, 0), Symbol.X));
    }


    @Test
    void insert_options_possibility_count_size_test() {
        oxono.moveTotem(Symbol.X, new Position(0, 0));
        List<Position> allPossibleInsert = oxono.getInsertPieceOptions(Symbol.X);
        assertEquals(2, allPossibleInsert.size());
    }

    @Test
    void insert_options_possibility_compare_pos_test() {
        oxono.moveTotem(Symbol.X, new Position(0, 0));
        List<Position> allPossibleInsert = oxono.getInsertPieceOptions(Symbol.X);
        assertTrue(allPossibleInsert.contains(new Position(0, 1)));
        assertTrue(allPossibleInsert.contains(new Position(1, 0)));

    }


    @Test
    void move_options_for_totem_test() {
        oxono.moveTotem(Symbol.X, new Position(0, 0));
        List<Position> possibleMove = oxono.getTotemMoveOptions(Symbol.X);
        assertEquals(10, possibleMove.size());
    }

    @Test
    void move_options_contains_the_right_position_test() {
        oxono.moveTotem(Symbol.X, new Position(0, 0));
        List<Position> possibleMove = oxono.getTotemMoveOptions(Symbol.X);
        assertTrue(possibleMove.contains(new Position(0, 5)));
        assertTrue(possibleMove.contains(new Position(5, 0)));

    }

    @Test
    void move_options_when_totem_blocked_test() {
        oxono.moveTotem(Symbol.O, new Position(0, 0));
        oxono.setPiece(new Position(0, 1), Symbol.O, playerblack);
        oxono.setPiece(new Position(1, 0), Symbol.O, playerblack);
        List<Position> possibleMove = oxono.getTotemMoveOptions(Symbol.O);
        assertEquals(2, possibleMove.size());
        assertTrue(possibleMove.contains(new Position(0, 2)));
        assertTrue(possibleMove.contains(new Position(2, 0)));

    }


    @Test
    void piece_inserted_for_real() {
        oxono.setPiece(new Position(0, 1), Symbol.O, playerblack);
        assertNotNull(oxono.getCaseAt(new Position(0, 1)));
    }

    @Test
    void remove_piece_at_position_test() {
        oxono.setPiece(new Position(0, 1), Symbol.O, playerblack);
        oxono.removePieceAtPosition(new Position(0, 1), playerblack);
        assertNull(oxono.getCaseAt(new Position(0, 1)));
    }

    @Test
    void totem_is_moved_in_the_position_test() {
        oxono.moveTotem(Symbol.X, new Position(0, 0));
        assertNotNull(oxono.getCaseAt(new Position(0, 0)));
    }

    @Test
    void cant_remove_a_totem_from_board() {
        oxono.moveTotem(Symbol.X, new Position(0, 0));
        oxono.removePieceAtPosition(new Position(0, 0), playerblack);
        assertNotNull(oxono.getCaseAt(new Position(0, 0)));
    }

    @Test
    void Board_full_test() {
        int BOARD_SIZE = oxono.getBoardSize();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if ((i == 2 && j == 2) || (i == 3 && j == 3)) {
                    continue;
                }
                oxono.setPiece(new Position(i, j), Symbol.X, playerblack);
            }
        }
        assertTrue(oxono.isBoardFull());
    }


    @Test
    void set_Game_State_test() {
        oxono.setGameState(GameState.WINNER);
        assertEquals(GameState.WINNER, oxono.getState());
    }

    @Test
    void get_game_state_test() {
        // the gameState is initialized to Move Totem in start of the game
        assertEquals(GameState.MOVE_TOTEM, oxono.getState());
    }

    @Test
    void get_the_current_player_test() {
        //PINK player always start so should be black
        assertEquals(Color.PINK, oxono.getCurrentPlayer().getColor());
        oxono.switchPlayer();
        //should be BLACK player after a switch
        assertEquals(Color.BLACK, oxono.getCurrentPlayer().getColor());
    }

    @Test
    void get_last_totem_moved_symbol_if_not_moved_test() {
        //no move has been done
        assertNull( oxono.getLastTotemSymbol());
    }

    @Test
    void get_last_totem_moved_symbol() {
        // move the totem
        oxono.moveTotem(Symbol.X, new Position(2, 0));
        //checks if return the right symbol
        assertEquals(Symbol.X, oxono.getLastTotemSymbol());
    }

    @Test
    void get_the_Stack_of_the_player() {
        //switch player because the currentplayer is PINK
        oxono.switchPlayer();
        oxono.setPiece(new Position(0, 0), Symbol.X, oxono.getCurrentPlayer());
        assertEquals(7, oxono.getTheStackOfThePlayer(Color.BLACK, Symbol.X));
    }

    @Test
    void are_all_stack_empty_test() {
        // Placing in the free slots pieces to test the method
        for (int j = 0; j < 6; j++) {
            oxono.setPiece(new Position(0, j), Symbol.X, oxono.getCurrentPlayer());
            oxono.setPiece(new Position(1, j), Symbol.O, oxono.getCurrentPlayer());

        }
        for (int i = 0; i < 2; i++) {
            oxono.setPiece(new Position(2, i), Symbol.X, oxono.getCurrentPlayer());
            oxono.setPiece(new Position(3, i), Symbol.O, oxono.getCurrentPlayer());
        }
        oxono.switchPlayer();
        for (int i = 0; i < 6; i++) {
            oxono.setPiece(new Position(4, i), Symbol.X, oxono.getCurrentPlayer());
            oxono.setPiece(new Position(5, i), Symbol.O, oxono.getCurrentPlayer());
        }
        for (int i = 0; i < 2; i++) {
            oxono.setPiece(new Position(i, 2), Symbol.X, oxono.getCurrentPlayer());
            oxono.setPiece(new Position(i, 3), Symbol.O, oxono.getCurrentPlayer());
        }
        assertTrue(oxono.areAllStacksEmpty());
    }

    @Test
    void is_stack_of_current_player_empty(){
        for (int j = 0; j < 6; j++) {
            oxono.setPiece(new Position(0, j), Symbol.X, oxono.getCurrentPlayer());
        }
        for (int i = 0; i < 2; i++) {
            oxono.setPiece(new Position(2, i), Symbol.X, oxono.getCurrentPlayer());
        }
        assertTrue(oxono.isStackEmpty(Symbol.X));

    }

    @Test
    void check_win_test(){
        oxono.switchPlayer();
        oxono.setPiece(new Position(1, 0), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 1), Symbol.X, oxono.getCurrentPlayer());
        oxono.switchPlayer();
        oxono.setPiece(new Position(1, 3), Symbol.O, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 4), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 2), Symbol.X, oxono.getCurrentPlayer());
        assertFalse( oxono.checkWin(new Position(1,2),Symbol.X));
    }
    @Test
    void last_piece_placed_by_pink_player_test(){
        oxono.setPiece(new Position(1, 0), Symbol.X, oxono.getCurrentPlayer());
        oxono.setPiece(new Position(1, 1), Symbol.X, oxono.getCurrentPlayer());
        oxono.switchPlayer();
        oxono.setPiece(new Position(2, 0), Symbol.O, oxono.getCurrentPlayer());
        assertEquals(Symbol.X,oxono.getLastUsedPinkSymbol());
    }

}