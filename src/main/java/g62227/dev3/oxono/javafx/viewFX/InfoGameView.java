package g62227.dev3.oxono.javafx.viewFX;

import g62227.dev3.oxono.model.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * This class represents the top view of the game interface, displaying the current player and
 * the empty cells left in the board.
 */
public class InfoGameView extends VBox {
    private Label emptyCells = new Label();
    private Label currentPlayer = new Label();

    /**
     * Initializes the InfoGameView by configuring the layout, header, label styling,
     * and current player information displays.
     */
     InfoGameView(){
        configureVbox();
        configureHeader();
        stylingLabel();
        configureGameInfoView();
    }

    private void configureVbox(){
        this.setPrefHeight(100);
        this.setAlignment(Pos.CENTER);
    }

    private void stylingLabel(){
        String style = "-fx-text-fill: pink; -fx-font-size: 25px; -fx-font-family: 'Cooper Black';";
        emptyCells.setStyle(style);
        currentPlayer.setStyle(style);
        HBox.setMargin(emptyCells, new Insets(0, 50, 0, 0));
        HBox.setHgrow(emptyCells, Priority.ALWAYS);
        HBox.setHgrow(currentPlayer, Priority.ALWAYS);

    }

    private void configureHeader(){
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setMaxHeight(100);
        header.setPrefHeight(100);
        ImageView image = new ImageView(new Image("oxo.png"));
        image.setFitWidth(200);
        image.setFitHeight(30);
        header.getChildren().add(image);
        this.getChildren().add(header);
    }

    private void configureGameInfoView(){
        HBox info = new HBox();
        info.setAlignment(Pos.CENTER);
        info.getChildren().addAll(emptyCells,currentPlayer);
        this.getChildren().add(info);
    }


    /**
     * Updates the label displaying the number of empty cells in the game.
     *
     * @param countCell The number of empty cells to be displayed.
     */
    void updateEmptyCellCount(int countCell){
        emptyCells.setText("Empty cells : "+countCell);
    }


    /**
     * Updates the label displaying the current player.
     *
     * @param color The color representing the current player (either BLACK or PINK).
     */
    void updateCurrentPlayer(Color color){
        currentPlayer.setText("Current player : "+color);
    }
}
