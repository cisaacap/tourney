package main.java.com.cisaacap.tourney.controller.dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import main.java.com.cisaacap.tourney.dto.request.dashboard.torneos.TorneoRequest;
import main.java.com.cisaacap.tourney.dto.response.dashboard.torneos.TorneoResponse;
import main.java.com.cisaacap.tourney.model.deporte.Deportes; // Importa tu modelo Deportes
import main.java.com.cisaacap.tourney.repository.dashboard.deporte.DeporteRepository;
import main.java.com.cisaacap.tourney.repository.dashboard.torneos.TorneoRepository;
import main.java.com.cisaacap.tourney.service.dashboard.torneos.TorneoService;

public class CrearTorneoController implements Initializable {

    @FXML private TextField nombreTorneoField;
    @FXML private ComboBox<Deportes> deporteComboBox; // Cambiado de TextField a ComboBox de tipo Deportes
    @FXML private DatePicker fechaInicioPicker;
    @FXML private DatePicker fechaFinPicker;
    @FXML private Label statusLabel;
    @FXML private Button closeButton;

    private final TorneoService torneoService;

    public CrearTorneoController() {
        this.torneoService = new TorneoService(new TorneoRepository(), new DeporteRepository());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDeportes();
    }

    private void cargarDeportes() {
        try {
            // Obtenemos la lista observable desde el servicio y la cargamos en el ComboBox
            deporteComboBox.setItems(torneoService.obtenerDeportes());
            

            deporteComboBox.setConverter(new javafx.util.StringConverter<Deportes>() {
                @Override
                public String toString(Deportes deporte) {
                    return deporte == null ? "" : deporte.getNombreDeporte();
                }

                @Override
                public Deportes fromString(String string) {
                    return null;
                }
            });
        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: #e63946;");
            statusLabel.setText("Error al cargar los deportes.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGuardarTorneo() {
        try {
            // Validar que se haya seleccionado un deporte en el ComboBox
            Deportes deporteSeleccionado = deporteComboBox.getValue();
            if (deporteSeleccionado == null) {
                statusLabel.setStyle("-fx-text-fill: #e63946;");
                statusLabel.setText("Debe seleccionar un deporte.");
                return;
            }

            TorneoRequest request = new TorneoRequest();
            request.setNombreTorneo(nombreTorneoField.getText() != null ? nombreTorneoField.getText().trim() : "");
            
            // Asignamos el ID obtenido del objeto Deporte seleccionado 
            // (Asegúrate de que el método para obtener el ID en tu modelo sea getId() o similar)
            request.setIdDeporte(deporteSeleccionado.getIdDeporte()); 
            
            request.setFechaInicio(fechaInicioPicker.getValue());
            request.setFechaFin(fechaFinPicker.getValue());

            TorneoResponse response = torneoService.registrarTorneo(request);

            if (response.isExito()) {
                cerrarVentana();
            } else {
                statusLabel.setStyle("-fx-text-fill: #e63946;");
                statusLabel.setText(response.getMensaje());
            }

        } catch (Exception e) {
            statusLabel.setStyle("-fx-text-fill: #e63946;");
            statusLabel.setText("Error al procesar la solicitud.");
            e.printStackTrace();
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