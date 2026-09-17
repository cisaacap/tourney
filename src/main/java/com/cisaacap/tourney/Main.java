package main.java.com.cisaacap.tourney;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.com.cisaacap.tourney.util.SceneManager;

public class Main extends Application {
   
    private Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.showLoginView();
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }  
}