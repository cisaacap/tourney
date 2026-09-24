package main.java.com.cisaacap.tourney.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.java.com.cisaacap.tourney.controller.auth.LoginController;
import main.java.com.cisaacap.tourney.controller.auth.RegisterController;
import main.java.com.cisaacap.tourney.controller.dashboard.DashboardController;
import main.java.com.cisaacap.tourney.repository.auth.UsuarioRepository;
import main.java.com.cisaacap.tourney.repository.dashboard.partidos.PartidoRepository;
import main.java.com.cisaacap.tourney.repository.jugadores.JugadorRepository;
import main.java.com.cisaacap.tourney.service.auth.UsuarioService;
import main.java.com.cisaacap.tourney.service.dashboard.partidos.PartidoService;
import main.java.com.cisaacap.tourney.service.jugadores.JugadorService;

public class SceneManager {

    private Stage primaryStage;
    private final String FXML_PATH = "/main/resources/view/";

    // Atributo temporal para mantener la sesión del registro entre vistas
    private Long tempUserId;

    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Long getTempUserId() {
        return tempUserId;
    }

    public void setTempUserId(Long tempUserId) {
        this.tempUserId = tempUserId;
    }

    
    public void showLoginView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "auth/login-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == LoginController.class) {
                UsuarioRepository authRepository = new UsuarioRepository();
                UsuarioService authService = new UsuarioService(authRepository);
                return new LoginController(this, authService);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el constructor: " + e.getMessage());
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    
    // Paso 1: Registro básico (Email, contraseña, nickname)
    public void showRegisterView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "auth/register-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == RegisterController.class) {
                UsuarioRepository authRepository = new UsuarioRepository();
                JugadorRepository playerRepository = new JugadorRepository();
                UsuarioService authService = new UsuarioService(authRepository);
                JugadorService playerService = new JugadorService(playerRepository);
                return new RegisterController(this, authService, playerService);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el constructor: " + e.getMessage());
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void showRegisterPlayerView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "jugadores/register-jugador-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == RegisterController.class) {
                UsuarioRepository authRepository = new UsuarioRepository();
                JugadorRepository playerRepository = new JugadorRepository();
                UsuarioService authService = new UsuarioService(authRepository);
                JugadorService playerService = new JugadorService(playerRepository);

                RegisterController controller = new RegisterController(this, authService, playerService);
                // Le inyectamos el ID guardado en la sesión
                controller.setUserId(this.tempUserId);
                return controller;
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el constructor: " + e.getMessage());
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    
    public void showDashboardView() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "dashboard/dashboard-view.fxml"));
        loader.setControllerFactory(clazz -> {
            if (clazz == DashboardController.class) {
                PartidoRepository gameRepo = new PartidoRepository();
                JugadorRepository playerRepository = new JugadorRepository();
                PartidoService gameService = new PartidoService(gameRepo);
                JugadorService playerService = new JugadorService(playerRepository);
                return new DashboardController(this, gameService, playerService);
            }
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el constructor: " + e.getMessage());
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
    
}
