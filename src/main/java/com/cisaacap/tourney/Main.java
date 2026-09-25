package main.java.com.cisaacap.tourney;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.com.cisaacap.tourney.util.SceneManager;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        SceneManager sceneManager = new SceneManager(primaryStage);
        try {
            sceneManager.showLoginView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
