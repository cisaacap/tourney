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
import main.java.com.cisaacap.tourney.dto.request.auth.RegisterRequest;
import main.java.com.cisaacap.tourney.dto.request.jugadores.JugadorRequest;
import main.java.com.cisaacap.tourney.dto.response.auth.RegisterResponse;
import main.java.com.cisaacap.tourney.dto.response.jugadores.JugadorResponse;
import main.java.com.cisaacap.tourney.service.auth.UsuarioService;
import main.java.com.cisaacap.tourney.service.jugadores.JugadorService;
import main.java.com.cisaacap.tourney.util.SceneManager;

public class RegisterController implements Initializable {

    @FXML
    private HBox windowHeader;

    @FXML
    private Button closeButton;

    @FXML
    private Button minimizeButton;

    // Campos Paso 1
    @FXML
    private TextField nicknameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    // Campos Paso 2
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidoField;
    @FXML
    private TextField edadField;

    @FXML
    private Button registerButton;
    @FXML
    private Hyperlink loginHyperlink;

    private double xOffset = 0;
    private double yOffset = 0;

    private Long userId;

    private final SceneManager stage;
    private final UsuarioService usuarioService;
    private final JugadorService jugadorService;

    public RegisterController(SceneManager stage, UsuarioService usuarioService, JugadorService jugadorService) {
        this.stage = stage;
        this.usuarioService = usuarioService;
        this.jugadorService = jugadorService;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupWindowControls();

        if (registerButton != null) {
            registerButton.setOnAction(event -> {
                // Si el campo nombreField NO es nulo, estamos en el Paso 2 (Jugador)
                if (nombreField != null) {
                    handleShowLoginView();
                } else {
                    // Si nombreField es nulo, estamos en el Paso 1 (Usuario)
                    handleShowRegisterView();
                }
            });
        }

        if (loginHyperlink != null) {
            loginHyperlink.setOnAction(event -> {
                try {
                    handleGoToLogin();
                } catch (Exception ex) {
                    Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }

    // Proceso del Paso 2: Registro del jugador
    @FXML
    private void handleShowLoginView() {
        removerEstilosError();

        boolean camposValidos = true;

        if (esVacio(nombreField.getText())) {
            marcarError(nombreField);
            camposValidos = false;
        }

        if (esVacio(apellidoField.getText())) {
            marcarError(apellidoField);
            camposValidos = false;
        }

        if (esVacio(edadField.getText())) {
            marcarError(edadField);
            camposValidos = false;
        }

        if (!camposValidos) {
            return;
        }

        try {
            JugadorRequest req = new JugadorRequest();
            req.setNombre(nombreField.getText().trim());
            req.setApellido(apellidoField.getText().trim());

            // Validar conversión numérica de la edad
            try {
                req.setEdad(Integer.parseInt(edadField.getText().trim()));
            } catch (NumberFormatException e) {
                marcarError(edadField);
                return;
            }

            // Obtener el ID del usuario guardado
            Long idTemp = stage.getTempUserId() != null ? stage.getTempUserId() : this.userId;
            if (idTemp != null) {
                req.setIdUsuario(idTemp.intValue());
            }

            JugadorResponse response = jugadorService.registrarJugador(req);

            if (response != null && response.isExito()) {
                stage.setTempUserId(null); // Limpiar ID temporal
                stage.showLoginView();     // Redirigir al Login
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Proceso del Paso 1: Registro del usuario
    @FXML
    private void handleShowRegisterView() {
        removerEstilosError();

        boolean camposValidos = true;

        if (esVacio(nicknameField.getText())) {
            marcarError(nicknameField);
            camposValidos = false;
        }

        if (esVacio(emailField.getText())) {
            marcarError(emailField);
            camposValidos = false;
        }

        if (esVacio(passwordField.getText())) {
            marcarError(passwordField);
            camposValidos = false;
        }

        if (esVacio(confirmPasswordField.getText())) {
            marcarError(confirmPasswordField);
            camposValidos = false;
        }

        if (!camposValidos) {
            return;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            marcarError(passwordField);
            marcarError(confirmPasswordField);
            return;
        }

        try {
            RegisterRequest request = new RegisterRequest();
            request.setNickname(nicknameField.getText().trim());
            request.setEmail(emailField.getText().trim());
            request.setPsswrd(passwordField.getText());

            RegisterResponse response = usuarioService.registrarUsuario(request);

            if (response != null && response.isExito()) {
                if (response.getIdUsuario() != 0) {
                    stage.setTempUserId((long) response.getIdUsuario());
                }

                stage.showRegisterPlayerView();
            } else {
                System.out.println(response != null ? response.getMensaje() : "Error desconocido en el registro.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGoToLogin() throws Exception {
        try {
            stage.showLoginView();
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
        if (nicknameField != null) {
            nicknameField.setStyle(estiloNormal);
        }
        if (emailField != null) {
            emailField.setStyle(estiloNormal);
        }
        if (passwordField != null) {
            passwordField.setStyle(estiloNormal);
        }
        if (confirmPasswordField != null) {
            confirmPasswordField.setStyle(estiloNormal);
        }
        if (nombreField != null) {
            nombreField.setStyle(estiloNormal);
        }
        if (apellidoField != null) {
            apellidoField.setStyle(estiloNormal);
        }
        if (edadField != null) {
            edadField.setStyle(estiloNormal);
        }
    }
}
