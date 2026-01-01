package g62227.dev3.oxono.javafx.viewFX;

import g62227.dev3.oxono.model.BotLevel;
import g62227.dev3.oxono.model.Game;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/**
 * This class represents the view for game settings, allowing the user to configure game options.
 * It includes options to select difficulty level, board size, and start the game.
 */
public class SettingView extends VBox {
    private ChoiceBox<String> levelBot;
    private Button confirmButton;
    private Spinner<Integer> sizeBoardChoice;
    private MainView mainView;


    /**
     * Initializes the settings view for configuring game options such as bot level, board size,
     * and confirming the settings to start the game.
     *
     * @param game the current game instance
     */
    SettingView(Game game){
        levelBot = new ChoiceBox<>();
        sizeBoardChoice =new Spinner<>(2, 9, 2);
        confirmButton = new Button("Confirm");
        this.setAlignment(Pos.CENTER);
        settingTitle();
        displayGameIntro();
        displayBotLevelOptions();
        set_Board_Size();
        buttonConfirmSetting(game);
        instructionsToRead();
    }

    private void settingTitle(){
        Label OXONO = new Label("OXONO Settings");
        OXONO.setStyle("-fx-font-family:'Cooper black'; -fx-text-fill:white; -fx-font-size:21px;");
        this.getChildren().add(OXONO);

    }
    private void displayGameIntro(){
        String style = "-fx-font-family:carlito; -fx-text-fill:white; -fx-font-size:15px;";
        Label firstLine = new Label("Welcome to the OXONO game");
        firstLine.setStyle(style);
        Label secondLine = new Label("Please before starting choose the level of the bot and the size* of the board");
        secondLine.setStyle(style);
        this.getChildren().addAll(firstLine,secondLine);
    }

    private void displayBotLevelOptions(){
        HBox levelArea = new HBox();
        levelArea.setAlignment(Pos.CENTER);
        Label botLevelPrompt = new Label("Bot level");
        botLevelPrompt.setStyle("-fx-text-fill:white; -fx-font-size:16px;  -fx-font-family :'DejaVu Sans'");
        HBox.setMargin(levelBot,new Insets(20,0,20,10));
        levelBot.getItems().addAll("Easy","Normal");
        levelBot.setStyle("-fx-background-color : white;");
        levelBot.setPrefWidth(100);
        levelBot.setValue("Easy");
        levelArea.getChildren().addAll(botLevelPrompt,levelBot);
        this.getChildren().add(levelArea);
    }
    private void set_Board_Size(){
        HBox sizeBoardBox = new HBox();
        sizeBoardBox.setAlignment(Pos.TOP_CENTER);
        Label sizeBoardLabel = new Label("Board Size ");
        sizeBoardLabel.setStyle("-fx-text-fill:white; -fx-font-size:16px;  -fx-font-family :'DejaVu Sans'");
        HBox.setMargin(sizeBoardChoice,new Insets(0,0,10,10));
        sizeBoardBox.getChildren().addAll(sizeBoardLabel,sizeBoardChoice);
        this.getChildren().add(sizeBoardBox);
    }

    private void buttonConfirmSetting(Game game){
        confirmButton.setStyle("-fx-background-color:orange; -fx-text-fill:white; -fx-padding : 5 10 5 10; -fx-font-weight: bolder; -fx-font-family:'System'; -fx-font-size :12px; ");
        confirmButton.setOnAction(e -> {
            BotLevel levelOfBot;
            String ProvidedLevelBot = levelBot.getValue();
            if(ProvidedLevelBot.equalsIgnoreCase("Easy")){
                 levelOfBot = BotLevel.EASY;
            }else{
                levelOfBot = BotLevel.MEDIUM;
            }
            game.startGame(sizeBoardChoice.getValue(),levelOfBot);
            if (mainView != null) {
                mainView.setupGameLayout();
            }
        });
        this.getChildren().add(confirmButton);
    }
    private void instructionsToRead(){
        Label etoile = new Label("*Please check the rules in the official OXONO website");
        etoile.setStyle("-fx-font-size:10px; -fx-text-fill:white;");
        VBox.setMargin(etoile,new Insets(30,0,0,0));
        this.getChildren().add(etoile);
    }


    /**
     * Sets the main view for the application.
     * This method assigns the provided MainView object to the mainView field,
     * allowing access to the main UI elements of the JavaFX application.
     *
     * @param mainView The MainView object representing the main user interface of the application.
     */
    void setMainView(MainView mainView){
        this.mainView = mainView;
    }



}
