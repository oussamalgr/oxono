package g62227.dev3.oxono.javafx.viewFX;

import g62227.dev3.oxono.model.Color;
import g62227.dev3.oxono.model.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * Represents the final display when a game ends, showing the result and winner
 * (or draw).
 */
public class GameOverView extends VBox {
    private GridPane finalBoardView;
    private MainView mainView;

    /**
     * Displays the winner (or draw) and final game details in the provided root and
     * board view.
     *
     * @param root      the main UI container to display the winner
     * @param boardView the board view to integrate with the game over display
     */
    void displayWinner(BorderPane root, BoardView boardView, Game game) {
        this.getChildren().clear();
        this.setAlignment(Pos.CENTER);
        this.finalBoardView = boardView;
        reSizeBoardView(boardView);
        resetBoardStyle(boardView);
        this.getChildren().add(boardView);
        configureEndGameVbox(game);
        createOxonoPicture(root);
        root.setCenter(this);

    }

    /**
     * Resets the style of the board to its original style, removing any custom
     * styling applied during gameplay.
     * This method applies a default border style to all cells on the board.
     *
     * @param boardView The BoardView object that represents the visual display of
     *                  the game board.
     */
    private void resetBoardStyle(BoardView boardView) {
        for (Node node : boardView.getChildren()) {
            StackPane cell = (StackPane) node;
            cell.setStyle("-fx-border-color: white; -fx-border-width: 1;");
        }
    }

    /**
     * this method creates an imageView and put it the top of the root
     *
     * @param root the current borderPane.
     */
    private void createOxonoPicture(BorderPane root) {
        HBox Header = new HBox();
        Header.setPrefHeight(100);
        Header.setAlignment(Pos.CENTER);
        ImageView oxo = new ImageView(new Image("oxo.png"));
        VBox.setMargin(oxo, new Insets(5, 0, 0, 0));
        oxo.setFitWidth(200);
        oxo.setFitHeight(30);
        Header.getChildren().add(oxo);
        root.setTop(Header);
    }

    /**
     * Resizes and disables the board view.
     *
     * @param boardView The board view to resize and disable.
     */
    private void reSizeBoardView(BoardView boardView) {
        boardView.setMouseTransparent(true);
        boardView.setPrefSize(600, 700);
        VBox.setMargin(boardView, new Insets(10, 0, 10, 0));

    }

    /**
     * Configures the actual vbox of the class
     * this vbox includes button restart and a label depending on the state of the
     * game
     *
     * @param game facade of the game including the game logic
     */
    private void configureEndGameVbox(Game game) {

        Label endMessage = new Label();

        if (game.isStateWinner()) {
            endMessage.setText("Congratulation the winner is : " + game.getCurrentPlayerColor());

        } else if (game.isStateDraw()) {
            endMessage.setText("No winner  :  it's a draw !");
        } else if (game.isStateSurrender()) {
            Color WinnerPlayer = game.getCurrentPlayerColor() == Color.BLACK ? Color.PINK : Color.BLACK;
            endMessage.setText(game.getCurrentPlayerColor() + " gave up the game : the winner is  : " + WinnerPlayer);

        }

        VBox.setMargin(endMessage, new Insets(0, 0, 50, 0));
        Button restart = new Button("Restart");
        restart.setOnAction(e -> {
            this.getChildren().clear();
            finalBoardView.setMouseTransparent(false);
            mainView.styleRoot();
            game.restart();
        });

        endMessage.setStyle(styleEndMessage());
        VBox.setMargin(restart, new Insets(0, 0, 15, 0));
        restart.setPadding(new Insets(5, 15, 5, 15));
        restart.setStyle(styleRestartButton());
        this.getChildren().addAll(endMessage, restart);

    }

    /**
     * Sets the main view for the application.
     * This method assigns the provided MainView object to the mainView field,
     * allowing access to the main UI elements of the JavaFX application.
     *
     * @param mainView The MainView object representing the main user interface of
     *                 the application.
     */
    void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    private String styleRestartButton() {
        return "-fx-background-color: orange; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bolder; " +
                "-fx-font-family: 'System'; " +
                "-fx-font-size: 20px; " +
                "-fx-background-radius: 15px; " +
                "-fx-border-radius: 15px; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(gaussian, black, 5, 0, 0, 2); " +
                "-fx-transition: background-color 0.3s ease; ";
    }

    private String styleEndMessage() {
        return "-fx-text-fill: white;" +
                "-fx-font-size: 18px;" +
                "-fx-font-family: 'Arial';" +
                "-fx-font-weight: bold;" +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 4, 0.5, 0, 2);" +
                "-fx-background-color: linear-gradient(to bottom, #8e44ad, #9b59b6);" +
                "-fx-padding: 12px;" +
                "-fx-border-color: #ffffff;" +
                "-fx-border-radius: 10px;" +
                "-fx-background-radius: 10px;";
    }

}
