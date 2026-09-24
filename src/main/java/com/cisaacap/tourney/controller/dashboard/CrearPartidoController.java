package main.java.com.cisaacap.tourney.controller.dashboard;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import main.java.com.cisaacap.tourney.dto.request.dashboard.partidos.PartidoRequest;
import main.java.com.cisaacap.tourney.dto.response.dashboard.partidos.PartidosResponse;
import main.java.com.cisaacap.tourney.dto.response.dashboard.torneos.TorneoResponse;
import main.java.com.cisaacap.tourney.model.equipos.Equipos;
import main.java.com.cisaacap.tourney.repository.dashboard.equipos.EquipoRepository;
import main.java.com.cisaacap.tourney.repository.dashboard.partidos.PartidoRepository;
import main.java.com.cisaacap.tourney.repository.dashboard.torneos.TorneoRepository;
import main.java.com.cisaacap.tourney.service.dashboard.partidos.PartidoService;

public class CrearPartidoController implements Initializable {

    @FXML private ComboBox<TorneoResponse> torneoComboBox;
    @FXML private ComboBox<Equipos> localComboBox;
    @FXML private ComboBox<Equipos> visitanteComboBox;
    @FXML private DatePicker fechaPicker;
    @FXML private TextField horaField;
    @FXML private TextField puntosLocalField;
    @FXML private TextField puntosVisitanteField;
    @FXML private Label statusLabel;
    @FXML private Button closeButton;

    private final PartidoService partidoService;
    private final TorneoRepository torneoRepository;
    private final EquipoRepository equipoRepository;

    public CrearPartidoController() {
        this.partidoService = new PartidoService(new PartidoRepository());
        this.torneoRepository = new TorneoRepository();
        this.equipoRepository = new EquipoRepository();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarListas();
        configurarFormatosComboBox();
    }

    private void cargarListas() {
        try {
            ObservableList<TorneoResponse> torneos = torneoRepository.obtenerTodos();
            ObservableList<Equipos> equipos = equipoRepository.obtenerTodos();

            torneoComboBox.setItems(torneos);
            localComboBox.setItems(equipos);
            visitanteComboBox.setItems(equipos);
        } catch (Exception e) {
            statusLabel.setText("Error al cargar la información de Torneos y Equipos.");
            e.printStackTrace();
        }
    }

    private void configurarFormatosComboBox() {
        // Formato para Torneos
        torneoComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(TorneoResponse item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombreTorneo());
            }
        });
        torneoComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(TorneoResponse item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombreTorneo());
            }
        });

        // Formato para Equipo Local
        localComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Equipos item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombreEquipo());
            }
        });
        localComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Equipos item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombreEquipo());
            }
        });

        // Formato para Equipo Visitante
        visitanteComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Equipos item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombreEquipo());
            }
        });
        visitanteComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Equipos item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getNombreEquipo());
            }
        });
    }

    @FXML
    private void handleGuardarPartido() {
        try {
            TorneoResponse torneoSel = torneoComboBox.getValue();
            Equipos localSel = localComboBox.getValue();
            Equipos visitanteSel = visitanteComboBox.getValue();

            if (torneoSel == null) {
                statusLabel.setText("Debe seleccionar un Torneo.");
                return;
            }

            if (localSel == null || visitanteSel == null) {
                statusLabel.setText("Debe seleccionar ambos equipos.");
                return;
            }

            if (localSel.getIdEquipo() == visitanteSel.getIdEquipo()) {
                statusLabel.setText("El equipo local y visitante no pueden ser el mismo.");
                return;
            }

            LocalDate fecha = fechaPicker.getValue();
            if (fecha == null) {
                statusLabel.setText("Debe seleccionar una fecha.");
                return;
            }

            String horaTexto = horaField.getText() != null ? horaField.getText().trim() : "00:00";
            LocalTime hora = LocalTime.parse(horaTexto);
            LocalDateTime fechaHora = LocalDateTime.of(fecha, hora);

            int puntosLocal = Integer.parseInt(puntosLocalField.getText().trim());
            int puntosVisitante = Integer.parseInt(puntosVisitanteField.getText().trim());

            PartidoRequest req = new PartidoRequest();
            req.setIdTorneo(torneoSel.getIdTorneo());
            req.setIdEquipoLocal(localSel.getIdEquipo());
            req.setIdEquipoVisitante(visitanteSel.getIdEquipo());
            req.setFechaHora(fechaHora);
            req.setPuntosLocal(puntosLocal);
            req.setPuntosVisitante(puntosVisitante);

            PartidosResponse resp = partidoService.programarPartido(req);

            if (resp.isExito()) {
                cerrarVentana();
            } else {
                statusLabel.setText(resp.getMensaje());
            }

        } catch (Exception e) {
            statusLabel.setText("Verifique los datos (formato hora HH:mm y números de puntos).");
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