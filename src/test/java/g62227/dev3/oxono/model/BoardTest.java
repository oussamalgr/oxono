package g62227.dev3.oxono.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board board;
    @BeforeEach
    void Instantiate_Board() {
        board = new Board(6);

    }

    @Test
    void SizeBoard_test() {
        assertThrows(IllegalArgumentException.class, () -> new Board(1));

    }

    @Test
    void Totem_no_more_in_the_last_position_test(){
        Token token = board.getCellAt(2,2);
        board.moveTotem(token.getSymbol(),new Position(2,3));
        assertNull(board.getCellAt(2,2));

    }
    @Test
    void totem_move_test(){
        Token token = board.getCellAt(2,2);

        board.moveTotem(token.getSymbol(),new Position(2,3));
        assertNotNull(board.getCellAt(2,3));
    }
    @Test
    void MoveTotem_out_of_bound_test(){
        assertThrows(IllegalArgumentException.class, () -> board.moveTotem(Symbol.X,new Position(10,10)));
    }

    @Test
    void setPiece_out_of_bound_test(){
        assertThrows(IllegalArgumentException.class, () -> board.setPiece(new Position(10,10),new Player(Color.BLACK),Symbol.X));
    }
    @Test
    void setPiece_well_placer_test(){
        board.setPiece(new Position(2,3),new Player(Color.BLACK),Symbol.X);
        assertNotNull(board.getCellAt(2,3));
    }

    @Test
    void get_last_moved_test(){
        board.moveTotem(Symbol.X,new Position(2,3));
        assertEquals(Symbol.X,board.getLastTotemSymbol());
    }

    @Test
    void Board_full(){
        int BOARD_SIZE = board.getSize();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if((i == 2 && j == 2) || (i == 3 && j == 3)){
                    continue;
                }
                board.setPiece(new Position(i,j),new Player(Color.BLACK),Symbol.X);
            }
        }
        assertTrue(board.isFull());
    }

    @Test
    void less_free_slot_when_set_piece(){
        board.setPiece(new Position(0,0),new Player(Color.PINK),Symbol.X);
        assertEquals(33,board.countFreeSlots());
    }

    @Test
    void more_fre_slot_when_piece_removed_test(){
        board.setPiece(new Position(2,1),new Player(Color.PINK),Symbol.X);
        board.removePiece(new Position(2,1),new Player(Color.PINK));
        assertEquals(34,board.countFreeSlots());
    }

    @Test
    void totem_already_placed_in_the_start_of_the_game(){
        assertNotNull(board.getCellAt(2,2));
    }

    @Test
    void get_pos_of_totem_test(){
        Totem totem = (Totem) board.getCellAt(2,2);
        assertEquals(totem.getX(),board.findTotemPosition(totem.getSymbol()).getX());
        assertEquals(totem.getY(),board.findTotemPosition(totem.getSymbol()).getY());
    }

    @Test
    void remove_a_piece_from_board(){
        Player player = new Player(Color.PINK);
        board.setPiece(new Position(0,0),player,Symbol.X);
        assertNotNull(board.getCellAt(0,0));
        board.removePiece(new Position(0,0),player);
        assertNull(board.getCellAt(0,0));
    }


}
