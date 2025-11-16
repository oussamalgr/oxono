package g62227.dev3.oxono.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BotStrategyTest {

    private  Player playerPink;
    @BeforeEach
    void initialize(){
        playerPink = new Player(Color.PINK);
        playerPink.setBotBehavior(new BotStrategy());
        playerPink.getBotBehavior().setLevel(0);
    }

    @Test
    void is_position_to_move_not_null_test() {
        playerPink.getBotBehavior().executeMoveTotem(new Oxono());
        assertNotNull(playerPink.getBotBehavior().getCurrentSymbol());
    }

    @Test
    void is_position_to_insert_by_bot_not_null_test() {
        Oxono oxono = new Oxono();
        playerPink.getBotBehavior().executeMoveTotem(oxono);
        Symbol lastSymbol = playerPink.getBotBehavior().getCurrentSymbol();
        assertNotNull(playerPink.getBotBehavior().executeInsertPiece(oxono,lastSymbol));

    }



}