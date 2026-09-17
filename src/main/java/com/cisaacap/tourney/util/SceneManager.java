package main.java.com.cisaacap.tourney.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.cisaacap.tourney.controller.auth.LoginController;
import main.java.com.cisaacap.tourney.controller.auth.RegisterController;
import main.java.com.cisaacap.tourney.repository.auth.UsuarioRepository;
import main.java.com.cisaacap.tourney.service.auth.UsuarioService;

public class SceneManager {

    //atributos
    private Stage primaryStage;
    private final String FXML_PATH = "/main/resources/view/";

    //constructor
    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    //metodo
    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "auth/login-view.fxml"));
        loader.setControllerFactory(
                clazz -> {
                    if (clazz == LoginController.class) {
                        UsuarioRepository authRepository = new UsuarioRepository();
                        UsuarioService authService = new UsuarioService(authRepository);
                        return new LoginController(this, authService);
                    }
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error al crear el constructor" + e.getMessage());
                    }
                });
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**/
    public void showRegisterView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "auth/register-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == RegisterController.class) {
                UsuarioRepository authRepository = new UsuarioRepository();
                UsuarioService authService = new UsuarioService(authRepository);
                return new RegisterController(this, authService);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("error al crear el constructor" + e.getMessage());
            }
        }
        );
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
