package main.java.com.cisaacap.tourney.controller.dashboard;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;

import main.java.com.cisaacap.tourney.dto.request.dashboard.partidos.PartidoRequest;
import main.java.com.cisaacap.tourney.dto.request.jugadores.JugadorRequest;
import main.java.com.cisaacap.tourney.dto.response.dashboard.partidos.PartidosResponse;
import main.java.com.cisaacap.tourney.dto.response.jugadores.JugadorResponse;
import main.java.com.cisaacap.tourney.service.dashboard.partidos.PartidoService;
import main.java.com.cisaacap.tourney.service.jugadores.JugadorService;
import main.java.com.cisaacap.tourney.util.SceneManager;

public class DashboardController implements Initializable {

    @FXML
    private HBox windowHeader;
    @FXML
    private Button closeButton;
    @FXML
    private Button minimizeButton;

    @FXML
    private TableView<PartidosResponse> partidosTableView;
    @FXML
    private TableColumn<PartidosResponse, String> colDeporte;
    @FXML
    private TableColumn<PartidosResponse, String> colEquipoLocal;
    @FXML
    private TableColumn<PartidosResponse, Integer> colPuntosLocal;
    @FXML
    private TableColumn<PartidosResponse, Integer> colPuntosVisitante;
    @FXML
    private TableColumn<PartidosResponse, String> colEquipoVisitante;

    @FXML
    private Button addActionButton;
    @FXML
    private ContextMenu addContextMenu;

    @FXML
    private Button userProfileButton;
    @FXML
    private VBox sideBarContainer;
    @FXML
    private TextField nombreProfileField;
    @FXML
    private TextField apellidoProfileField;
    @FXML
    private TextField edadProfileField;
    @FXML
    private Label statusProfileLabel;
    @FXML
    private Button saveProfileButton;
    @FXML
    private Button logoutButton;

    private double xOffset = 0;
    private double yOffset = 0;
    private int idJugadorActual = -1;

    private final SceneManager stage;
    private final PartidoService partidoService;
    private final JugadorService jugadorService;

    public DashboardController(SceneManager stage, PartidoService partidoService, JugadorService jugadorService) {
        this.stage = stage;
        this.partidoService = partidoService;
        this.jugadorService = jugadorService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupWindowControls();
        setupTableColumns();
        cargarPartidos();
    }

    private void setupTableColumns() {
        // Habilitar la edición en el TableView
        partidosTableView.setEditable(true);

        // Columnas no editables
        colDeporte.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue() != null ? cellData.getValue().getNombreTorneo() : ""));

        colEquipoLocal.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue() != null ? cellData.getValue().getNombreEquipoLocal() : ""));

        colEquipoVisitante.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue() != null ? cellData.getValue().getNombreEquipoVisitante() : ""));

        // --- COLUMNA PUNTOS LOCAL (Editable) ---
        colPuntosLocal.setCellValueFactory(cellData
                -> new SimpleIntegerProperty(cellData.getValue() != null ? cellData.getValue().getPuntosLocal() : 0).asObject());

        colPuntosLocal.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colPuntosLocal.setOnEditCommit(event -> {
            PartidosResponse partido = event.getRowValue();
            int nuevoPuntaje = event.getNewValue() != null ? event.getNewValue() : 0;

            partido.setPuntosLocal(nuevoPuntaje);
            guardarCambioPuntos(partido);
        });

        // --- COLUMNA PUNTOS VISITANTE (Editable) ---
        colPuntosVisitante.setCellValueFactory(cellData
                -> new SimpleIntegerProperty(cellData.getValue() != null ? cellData.getValue().getPuntosVisitante() : 0).asObject());

        colPuntosVisitante.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        colPuntosVisitante.setOnEditCommit(event -> {
            PartidosResponse partido = event.getRowValue();
            int nuevoPuntaje = event.getNewValue() != null ? event.getNewValue() : 0;

            partido.setPuntosVisitante(nuevoPuntaje);
            guardarCambioPuntos(partido);
        });
    }

    private void guardarCambioPuntos(PartidosResponse partido) {
        if (partido == null || partidoService == null) {
            return;
        }

        PartidoRequest req = new PartidoRequest();
        req.setIdTorneo(partido.getIdTorneo());
        req.setIdEquipoLocal(partido.getIdEquipoLocal());
        req.setIdEquipoVisitante(partido.getIdEquipoVisitante());
        req.setPuntosLocal(partido.getPuntosLocal());
        req.setPuntosVisitante(partido.getPuntosVisitante());
        req.setFechaHora(partido.getFechaHora());

        PartidosResponse res = partidoService.actualizarResultado(partido.getIdPartido(), req);

        if (!res.isExito()) {
            System.err.println("Error al actualizar puntos en la BD: " + res.getMensaje());
            cargarPartidos(); // Recargar datos para revertir la celda al valor original en la interfaz
        }
    }

    private void cargarPartidos() {
        if (partidoService != null) {
            ObservableList<PartidosResponse> partidos = partidoService.obtenerTodos();
            partidosTableView.setItems(partidos);
        }
    }

    @FXML
    private void handleToggleSidebar() {
        boolean visible = !sideBarContainer.isVisible();
        sideBarContainer.setVisible(visible);

        if (visible) {
            cargarPerfilJugador();
        }
    }

    private void cargarPerfilJugador() {
        if (stage.getTempUserId() == null) {
            statusProfileLabel.setText("No hay una sesión activa de usuario.");
            return;
        }

        int idUsuario = stage.getTempUserId().intValue();
        JugadorResponse jugador = jugadorService.obtenerJugadorPorIdUsuario(idUsuario);

        if (jugador != null && jugador.isExito()) {
            this.idJugadorActual = jugador.getIdJugador();
            nombreProfileField.setText(jugador.getNombre());
            apellidoProfileField.setText(jugador.getApellido());
            edadProfileField.setText(String.valueOf(jugador.getEdad()));
            statusProfileLabel.setText("");
        } else {
            statusProfileLabel.setText("Modo creación de datos del perfil.");
        }
    }

    @FXML
    private void handleGuardarPerfil() {
        if (esVacio(nombreProfileField.getText()) || esVacio(apellidoProfileField.getText())) {
            statusProfileLabel.setText("El nombre y el apellido son obligatorios.");
            return;
        }

        try {
            int edad = Integer.parseInt(edadProfileField.getText().trim());

            JugadorRequest req = new JugadorRequest();
            req.setNombre(nombreProfileField.getText().trim());
            req.setApellido(apellidoProfileField.getText().trim());
            req.setEdad(edad);

            if (stage.getTempUserId() != null) {
                req.setIdUsuario(stage.getTempUserId().intValue());
            }

            JugadorResponse res;
            if (idJugadorActual > 0) {
                res = jugadorService.actualizarJugador(req, idJugadorActual);
            } else {
                res = jugadorService.registrarJugador(req);
            }

            if (res.isExito()) {
                statusProfileLabel.setText("¡Perfil guardado con éxito!");
            } else {
                statusProfileLabel.setText(res.getMensaje());
            }
        } catch (NumberFormatException e) {
            statusProfileLabel.setText("La edad debe ser un número entero.");
        } catch (Exception e) {
            statusProfileLabel.setText("Error al procesar los datos.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleShowAddOptions() {
        if (addContextMenu != null) {
            addContextMenu.show(addActionButton, javafx.geometry.Side.TOP, 0, -10);
        }
    }

    @FXML
    private void handleCrearPartido() {
        abrirVentanaEmergente("/view/partidos/crear-partido-view.fxml", "Crear Nuevo Partido");
    }

    @FXML
    private void handleCrearTorneo() {
        abrirVentanaEmergente("/view/torneos/crear-torneo-view.fxml", "Crear Nuevo Torneo");
    }

    @FXML
    private void handleCrearEquipo() {
        abrirVentanaEmergente("/view/equipos/crear-equipo-view.fxml", "Crear Nuevo Equipo");
    }

    @FXML
    private void handleLogout() {
        try {
            stage.setTempUserId(null);
            stage.showLoginView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirVentanaEmergente(String fxmlPath, String titulo) {
        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                fxmlUrl = getClass().getResource("/main/resources" + fxmlPath);
            }

            if (fxmlUrl == null) {
                System.err.println("No se encontró el archivo FXML en la ruta: " + fxmlPath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.initStyle(StageStyle.TRANSPARENT);

            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);

            modalStage.setScene(scene);
            modalStage.setTitle(titulo);
            modalStage.centerOnScreen();
            modalStage.showAndWait();

            cargarPartidos();
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
}
