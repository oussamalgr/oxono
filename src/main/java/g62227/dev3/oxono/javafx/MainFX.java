package g62227.dev3.oxono.javafx;

import g62227.dev3.oxono.javafx.controller.ControllerFx;
import g62227.dev3.oxono.javafx.viewFX.MainView;
import g62227.dev3.oxono.model.Game;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainFX extends Application {


    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        MainView mainView = new MainView(game,primaryStage);
        ControllerFx controllerFx = new ControllerFx(game,mainView);
        mainView.setControllerFx(controllerFx);
        controllerFx.init();
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch();
    }


}
