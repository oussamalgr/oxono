package g62227.dev3.oxono.javafx.viewFX;

import g62227.dev3.oxono.model.*;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


/**
 * Represents the treatments and visual representation for a cell in the game board.
 */
public class Cell extends StackPane {
    private Rectangle totemX;
    private Rectangle totemO;


    /**
     * represents a cell to be placed in the boardView
     * @param token - token provided that will be treated
     */
    Cell(Token token) {
        totemX = new Rectangle(45, 45);
        totemX.setStyle("-fx-arc-width:20; -fx-arc-height:20; -fx-stroke: dodgerblue; -fx-fill: dodgerblue;");
        totemO = new Rectangle(45, 45);
        totemO.setStyle("-fx-arc-width:20; -fx-arc-height:20; -fx-stroke: dodgerblue; -fx-fill: dodgerblue;");
        this.setStyle("-fx-border-color: white;");
        this.setPrefSize(150, 200);
        if (token != null) {
            insertShapes(token);

        } else {
            this.getChildren().clear();
        }
    }


    /**
     * inserts a Shape in the BoardView depending on the token provided
     *
     * @param token - the token who will be represented
     */
    private void insertShapes(Token token) {
        if (token instanceof Totem totem) {
            createTotem(totem.getSymbol());
        } else if (token instanceof Piece piece) {
            createPiece(piece);
        }

    }

    /**
     * create the totem depends on the symbol provided
     * totem is  represented by a rectangle
     * @param symbolTotem symbol of the totem x or o
     */
    private void createTotem(Symbol symbolTotem) {
        Label label = new Label(symbolTotem.toString());
        label.setStyle(" -fx-text-fill:white; -fx-font-weight:bolder; -fx-font-family: system; -fx-font-size: 35px;");
        if (symbolTotem == Symbol.X) {
            this.getChildren().addAll(totemX, label);
        } else {
            this.getChildren().addAll(totemO, label);

        }

    }

    /**
     * create the piece depends on the symbol provided
     * a piece is represented by a circle
     * @param piece - can be black or pink and have x and o as symbol
     */
    private void createPiece(Piece piece) {
        Label label = new Label();
        label.setText(piece.getSymbol().toString());
        label.setStyle("-fx-font-weight: bold; -fx-text-fill:yellowgreen; -fx-font-family: system; -fx-font-size: 35px;");
        Circle circle = new Circle(24);
        if (piece.getColor() == Color.PINK) {
            circle.setStyle("-fx-fill:#9b4d96;");
        } else {
            circle.setStyle("-fx-fill:black;");
        }
        this.getChildren().addAll(circle, label);


    }


}
