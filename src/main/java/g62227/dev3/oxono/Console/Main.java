package g62227.dev3.oxono.Console;

import g62227.dev3.oxono.Console.controller.ConsoleController;
import g62227.dev3.oxono.model.Game;
import g62227.dev3.oxono.Console.view.GameView;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        GameView gameView = new GameView(game);
        ConsoleController controller = new ConsoleController(game, gameView);
        controller.startGame();

    }
}
