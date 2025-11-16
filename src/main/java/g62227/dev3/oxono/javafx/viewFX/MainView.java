package g62227.dev3.oxono.javafx.viewFX;


import g62227.dev3.oxono.javafx.controller.ControllerFx;
import g62227.dev3.oxono.model.*;
import g62227.dev3.oxono.util.Observer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * This class represents the main view of the JavaFX application.
 * It manages the integration of different views, including the game board and the game over screen,....
 */
public class MainView implements Observer {
    private BorderPane root = new BorderPane();
    private Game game;
    private BoardView boardView;
    private PlayerInfoView players = new PlayerInfoView();
    private SettingView settingView;
    private InfoGameView infoGame = new InfoGameView();
    private GameOverView gameOverView = new GameOverView();

    /**
     * Initializes the main view of the JavaFX application, setting up the game and other views.
     * Configures the stage and root layout for the game interface.
     *
     * @param game  the current game instance
     * @param stage the JavaFX stage where the scene is displayed
     */
    public MainView(Game game, Stage stage) {
        this.game = game;
        this.settingView = new SettingView(game);
        initializeViewConnections();
        styleRoot();
        Scene scene = new Scene(root, 1300, 750);
        stage.setMinWidth(1300);
        stage.setMinHeight(800);
        stage.setTitle("OXONO");
        stage.getIcons().add(new Image("icon.png"));
        stage.setScene(scene);

    }

    private void initializeViewConnections() {
        settingView.setMainView(this);
        gameOverView.setMainView(this);
    }


    /**
     * Sets the controller for handling user interactions and initializes the board view.
     *
     * @param controller the controller to be set for handling game interactions
     */
    public void setControllerFx(ControllerFx controller) {
        this.boardView = new BoardView(controller);
    }


    /**
     * Configures the root layout by clearing existing children, setting a background color,
     * and placing the setting view at the center.
     */
    void styleRoot() {
        root.getChildren().clear();
        root.setStyle("-fx-background-color: purple;");
        root.setCenter(settingView);
    }

    /**
     * Configures the game view layout by clearing existing children,
     * setting the board view in the center, top view at the top, and players at the bottom.
     */
    void setupGameLayout() {
        root.getChildren().clear();
        root.setCenter(boardView);
        root.setTop(infoGame);
        root.setBottom(players);
        updateGameStates();
        BorderPane.setMargin(boardView, new Insets(20, 0, 20, 0));
    }


    /**
     * this method  is called when the current state is winner,surrender or draw.
     * If the game is over, clears the current view and displays the game over screen.
     */
    private void MatchOver() {
        root.getChildren().clear();
        gameOverView.displayWinner(root, boardView, game);
    }


    /**
     * Performs the redo action by reapplying the last undone game move.
     */
    public void handlesRedo(EventHandler<ActionEvent> handler) {
        players.actionOnRedo(handler);
    }

    /**
     * Performs the undo action by reverting the last game move.
     */
    public void handlesUndo(EventHandler<ActionEvent> handler) {
        players.actionOnUndo(handler);
    }

    public void handlesSurrender(EventHandler<ActionEvent> handler) {
        players.actionOnSurrender(handler);
    }


    private void updateGameStates() {
        infoGame.updateEmptyCellCount(game.countFreeBoardSlots());
        infoGame.updateCurrentPlayer(game.getCurrentPlayerColor());
        players.updateBlackXPiecesCount(game.getRemainingStackPieces(Color.BLACK, Symbol.X));
        players.updateBlackOPiecesCount(game.getRemainingStackPieces(Color.BLACK, Symbol.O));
        players.updatePinkOPiecesCount(game.getRemainingStackPieces(Color.PINK, Symbol.O));
        players.updatePinkXPiecesCount(game.getRemainingStackPieces(Color.PINK, Symbol.X));
        players.updateStateOfGame(game.getState(), game.getCurrentPlayerColor());
        boardView.updateBoard(game);

    }

    @Override
    public void update() {
        updateGameStates();
        if (game.isStateWinner() || game.isStateDraw() || game.isStateSurrender()) {
            MatchOver();
        }
    }
}
