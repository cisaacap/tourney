package main.java.com.cisaacap.tourney.controller.dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import main.java.com.cisaacap.tourney.repository.dashboard.equipos.EquipoRepository;

public class CrearEquipoController implements Initializable {

    @FXML
    private TextField nombreEquipoField;
    @FXML
    private TextField ciudadField;
    @FXML
    private Label statusLabel;
    @FXML
    private Button closeButton;

    private final EquipoRepository equipoRepository;

    public CrearEquipoController() {
        this.equipoRepository = new EquipoRepository();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void handleGuardarEquipo() {
        String nombre = nombreEquipoField.getText() != null ? nombreEquipoField.getText().trim() : "";
        String ciudad = ciudadField.getText() != null ? ciudadField.getText().trim() : "";

        if (nombre.isEmpty() || ciudad.isEmpty()) {
            statusLabel.setStyle("-fx-text-fill: #e63946;");
            statusLabel.setText("Todos los campos son obligatorios.");
            return;
        }

        boolean guardado = equipoRepository.save(nombre, ciudad);

        if (guardado) {
            cerrarVentana();
        } else {
            statusLabel.setStyle("-fx-text-fill: #e63946;");
            statusLabel.setText("No se pudo guardar el equipo en la base de datos.");
        }
    }

    @FXML
    private void handleCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
