package main.java.com.cisaacap.tourney.controller.auth;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import main.java.com.cisaacap.tourney.dto.request.auth.LoginRequest;
import main.java.com.cisaacap.tourney.dto.response.auth.UsuarioResponse;
import main.java.com.cisaacap.tourney.service.auth.UsuarioService;
import main.java.com.cisaacap.tourney.util.SceneManager;

public class LoginController implements Initializable {

    @FXML
    private HBox windowHeader;

    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerHyperlink;

    private double xOffset = 0;
    private double yOffset = 0;

    private final SceneManager stage;
    private final UsuarioService usuarioService;

    public LoginController(SceneManager stage, UsuarioService usuarioService) {
        this.stage = stage;
        this.usuarioService = usuarioService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupWindowControls();

        if (loginButton != null) {
            loginButton.setOnAction(event -> handleLogin());
        }

        if (registerHyperlink != null) {
            registerHyperlink.setOnAction(event -> {
                try {
                    handleGoToRegister();
                } catch (Exception ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    @FXML
    private void handleLogin() {
        removerEstilosError();

        boolean camposValidos = true;

        if (esVacio(emailField.getText())) {
            marcarError(emailField);
            camposValidos = false;
        }

        if (esVacio(passwordField.getText())) {
            marcarError(passwordField);
            camposValidos = false;
        }

        if (!camposValidos) {
            return;
        }

        try {
            LoginRequest request = new LoginRequest();
            request.setEmail(emailField.getText().trim());
            request.setPsswrd(passwordField.getText());

            UsuarioResponse response = usuarioService.autenticarUsuario(request);

            if (response != null && response.isExito()) {
                // === AQUÍ SE GUARDA LA SESIÓN EN EL SCENEMANAGER ===
                // Asumiendo que response.getIdUsuario() retorna Long o Integer
                stage.setTempUserId((long) response.getIdUsuario());

                // Navegar al Dashboard
                stage.showDashboardView();
            } else {
                marcarError(emailField);
                marcarError(passwordField);
                System.out.println(response != null ? response.getMensaje() : "Error de credenciales.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToRegister() throws Exception {
        try {
            stage.showRegisterView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupWindowControls() {
        if (windowHeader != null) {
            windowHeader.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            windowHeader.setOnMouseDragged(event -> {
                Stage windowStage = (Stage) windowHeader.getScene().getWindow();
                windowStage.setX(event.getScreenX() - xOffset);
                windowStage.setY(event.getScreenY() - yOffset);
            });
        }

        if (closeButton != null) {
            closeButton.setOnAction(event -> {
                Stage windowStage = (Stage) closeButton.getScene().getWindow();
                windowStage.close();
            });
        }

        if (minimizeButton != null) {
            minimizeButton.setOnAction(event -> {
                Stage windowStage = (Stage) minimizeButton.getScene().getWindow();
                windowStage.setIconified(true);
            });
        }
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private void marcarError(javafx.scene.control.Control campo) {
        if (campo != null) {
            campo.setStyle("-fx-border-color: #d90429; -fx-border-width: 2px; -fx-border-radius: 30px; -fx-background-radius: 30px;");
        }
    }

    private void removerEstilosError() {
        String estiloNormal = "";
        if (emailField != null) {
            emailField.setStyle(estiloNormal);
        }
        if (passwordField != null) {
            passwordField.setStyle(estiloNormal);
        }
    }
}
