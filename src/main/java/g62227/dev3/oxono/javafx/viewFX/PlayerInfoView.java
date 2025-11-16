package g62227.dev3.oxono.javafx.viewFX;

import g62227.dev3.oxono.model.Color;
import g62227.dev3.oxono.model.GameState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;


/**
 * represents the bottom part of the screen with the ‘undo redo surrender’ button and the number of pieces remaining in the players' stack.
 */
public class PlayerInfoView extends VBox {
    private static final String STYLE_TEXT = "-fx-text-fill: white; -fx-font-size: 23px; -fx-font-family: 'Cooper Black';";
    private Label pinkOPiecesCount = new Label();
    private Label pinkXPiecesCount = new Label();
    private Label blackOPiecesCount = new Label();
    private Label blackXPiecesCount = new Label();
    private Label state_of_Game = new Label();
    private HBox hboxButton = new HBox();

    /**
     * Initializes the player information view by configuring the layout and applying styles.
     * Sets up the VBox and HBox for player info and buttons.
     */
    PlayerInfoView() {
        this.setAlignment(Pos.CENTER);
        styleState();
        configureHBoxPlayerInfo();
        configureHBoxButton();
    }

    private void styleState() {
        state_of_Game.setStyle("-fx-font-size: 18px; -fx-font-family: 'Cooper Black'; -fx-background-color: black; -fx-background-radius: 5; -fx-text-fill:white;");
        state_of_Game.setPadding(new Insets(5));
    }

    private void configureHBoxButton() {
        hboxButton.setPadding(new Insets(10, 0, 10, 0));
        hboxButton.setPrefWidth(200);
        hboxButton.setPrefHeight(40);
        hboxButton.setAlignment(Pos.CENTER);
        this.getChildren().add(hboxButton);
    }


    private void configureHBoxPlayerInfo() {
        HBox playersInfo = new HBox();
        playersInfo.setAlignment(Pos.CENTER);
        playersInfo.setPrefWidth(958);
        playersInfo.setPrefHeight(40);
        configurePinkHBox(playersInfo);
        playersInfo.getChildren().add(state_of_Game);
        configureBlackHBox(playersInfo);
        this.getChildren().add(playersInfo);
    }

    private void configurePinkHBox(HBox root) {
        HBox pink = new HBox();
        pink.setAlignment(Pos.CENTER);
        pink.setPrefHeight(100);
        pink.setPrefWidth(200);
        createContentPink(pink);
        HBox.setHgrow(pink, Priority.ALWAYS);
        root.getChildren().add(pink);

    }

    private void createContentPink(HBox contentPink) {
        Label playerPink = new Label("Player Pink - ");
        HBox.setMargin(playerPink, new Insets(0, 0, 0, 10));
        playerPink.setStyle(STYLE_TEXT);
        ImageView pieceO = createPinkPieceOPicture();
        pinkOPiecesCount.setStyle(STYLE_TEXT);
        HBox.setMargin(pinkOPiecesCount, new Insets(0, 0, 0, 10));
        ImageView pieceX = createPinkPieceXPicture();
        pinkXPiecesCount.setStyle(STYLE_TEXT);
        contentPink.getChildren().addAll(playerPink, pieceO, pinkOPiecesCount
                , pieceX, pinkXPiecesCount);
    }

    private ImageView createPinkPieceOPicture() {
        ImageView pieceO = new ImageView(new Image("vraipionOrose.png"));
        pieceO.setFitWidth(50);
        pieceO.setFitHeight(36);
        HBox.setMargin(pieceO, new Insets(0, 0, 0, 5));
        return pieceO;
    }

    private ImageView createPinkPieceXPicture() {
        ImageView pieceX = new ImageView(new Image("vraipionxrose.png"));
        pieceX.setFitWidth(50);
        pieceX.setFitHeight(36);
        HBox.setMargin(pieceX, new Insets(0, 5, 0, 5));
        return pieceX;
    }

    private void configureBlackHBox(HBox root) {
        HBox black = new HBox();
        black.setAlignment(Pos.CENTER);
        black.setPrefHeight(80);
        black.setPrefWidth(200);
        createContentBlack(black);
        HBox.setHgrow(black, Priority.ALWAYS);
        root.getChildren().add(black);
    }

    private void createContentBlack(HBox contentBlack) {
        Label playerBlack = new Label("Player Black - ");
        HBox.setMargin(playerBlack, new Insets(0, 5, 0, 0));
        playerBlack.setStyle(STYLE_TEXT);
        ImageView pieceO = createBlackPieceOPicture();
        blackOPiecesCount.setStyle(STYLE_TEXT);
        HBox.setMargin(blackOPiecesCount, new Insets(0, 0, 0, 10));
        ImageView pieceX = createBlackPieceXPicture();
        blackXPiecesCount.setStyle(STYLE_TEXT);
        HBox.setMargin(pieceX, new Insets(0, 5, 0, 5));
        contentBlack.getChildren().addAll(playerBlack, pieceO, blackOPiecesCount
                , pieceX, blackXPiecesCount);
    }

    private ImageView createBlackPieceOPicture() {
        ImageView pieceO = new ImageView(new Image("vraipionOnoir.png"));
        pieceO.setFitWidth(50);
        pieceO.setFitHeight(36);
        HBox.setMargin(pieceO, new Insets(0, 0, 0, 5));
        return pieceO;
    }

    private ImageView createBlackPieceXPicture() {
        ImageView pieceX = new ImageView(new Image("vraipionxnoir.png"));
        pieceX.setFitWidth(50);
        pieceX.setFitHeight(36);
        return pieceX;
    }

    /**
     * Creates and styles an "undo" button, which triggers the undo action when clicked.
     * The button is added to the HBox layout for display.
     */
    void actionOnUndo(EventHandler<ActionEvent> handler) {
        Button undoButton = new Button("<");
        String style = "-fx-border-radius: 15px; -fx-background-radius: 15px; -fx-background-color: orange; -fx-font-weight:bolder; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'system';";
        undoButton.setStyle(style);
        undoButton.setPadding(new Insets(5, 20, 5, 20));
        HBox.setMargin(undoButton, new Insets(0, 50, 0, 0));
        undoButton.setOnAction(handler);
        hboxButton.getChildren().addFirst(undoButton);
    }


    /**
     * Creates and styles a "redo" button, which triggers the redo action when clicked.
     * The button is added to the HBox layout for display.
     */
    void actionOnRedo(EventHandler<ActionEvent> handler) {
        Button redoButton = new Button(">");
        String style = "-fx-border-radius: 15px; -fx-background-radius: 15px; -fx-background-color: orange; -fx-font-weight:bolder; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'system';";
        redoButton.setStyle(style);
        redoButton.setPadding(new Insets(5, 20, 5, 20));
        redoButton.setOnAction(handler);
        hboxButton.getChildren().add(1, redoButton);
    }
    /**
     * Creates and styles a "Surrender" button, which triggers the surrender action when clicked.
     * The button is added to the HBox layout for display.
     *
     * @param handler The event handler that defines the action to be performed when the "Surrender" button is clicked.
     */
    void actionOnSurrender(EventHandler<ActionEvent> handler) {
        String style = "-fx-border-radius: 15px; -fx-background-radius: 15px; -fx-background-color: orange; -fx-font-weight:bold; -fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'system';";
        Button surrender = new Button("Surrender");
        surrender.setStyle(style);
        surrender.setPadding(new Insets(5, 20, 5, 20));
        HBox.setMargin(surrender, new Insets(0, 0, 0, 155));
        surrender.setOnAction(handler);
        hboxButton.getChildren().add(2, surrender);
    }

    /**
     * Updates the amount of pink 'O' pieces displayed.
     *
     * @param amount the number of pink 'O' pieces
     */
    void updatePinkOPiecesCount(int amount) {
        pinkOPiecesCount.setText(": " + amount + " ");
    }

    /**
     * Updates the amount of pink 'X' pieces displayed.
     *
     * @param amount the number of pink 'X' pieces
     */
    void updatePinkXPiecesCount(int amount) {
        pinkXPiecesCount.setText(": " + amount + " ");
    }

    /**
     * Updates the amount of black 'O' pieces displayed.
     *
     * @param amount the number of black 'O' pieces
     */
    void updateBlackOPiecesCount(int amount) {
        blackOPiecesCount.setText(": " + amount + " ");
    }

    /**
     * Updates the amount of black 'X' pieces displayed.
     *
     * @param amount the number of black 'X' pieces
     */
    void updateBlackXPiecesCount(int amount) {
        blackXPiecesCount.setText(": " + amount + " ");
    }


    /**
     * Updates the messages based on the current game state and the player's color.
     *
     * @param currentState the current state of the game
     * @param playerColor the color of the current player
     */
    void updateStateOfGame(GameState currentState, Color playerColor) {
        if (playerColor == Color.PINK) {
            if (currentState == GameState.MOVE_TOTEM) {
                state_of_Game.setText("Please move a totem");
            } else if (currentState == GameState.SET_PIECE) {
                state_of_Game.setText("Please insert a piece");
            }
        } else {
            state_of_Game.setText("Bot is thinking...");
        }
    }




}
