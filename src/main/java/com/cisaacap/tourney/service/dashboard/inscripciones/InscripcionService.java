package main.java.com.cisaacap.tourney.service.dashboard.inscripciones;

import main.java.com.cisaacap.tourney.dto.request.dashboard.inscripciones.InscripcionRequest;
import main.java.com.cisaacap.tourney.dto.response.dashboard.inscripciones.InscripcionResponse;
import main.java.com.cisaacap.tourney.repository.dashboard.inscripciones.InscripcionRepository;

import java.time.LocalDate;
import javafx.collections.ObservableList;

public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    public InscripcionResponse inscribirEquipo(InscripcionRequest req) {
        InscripcionResponse response = new InscripcionResponse();

        if (req == null) {
            response.setMensaje("Los datos de inscripción son nulos.");
            response.setExito(false);
            return response;
        }

        if (req.getIdTorneo() <= 0) {
            response.setMensaje("Debe seleccionar un torneo válido.");
            response.setExito(false);
            return response;
        }

        if (req.getIdEquipo() <= 0) {
            response.setMensaje("Debe seleccionar un equipo válido.");
            response.setExito(false);
            return response;
        }

        if (req.getFechaInscripcion() == null) {
            response.setMensaje("La fecha de inscripción es requerida.");
            response.setExito(false);
            return response;
        }

        if (req.getFechaInscripcion().isAfter(LocalDate.now())) {
            response.setMensaje("La fecha de inscripción no puede ser una fecha futura.");
            response.setExito(false);
            return response;
        }

        return inscripcionRepository.crearInscripcion(req);
    }

    public ObservableList<InscripcionResponse> obtenerTodas() {
        return inscripcionRepository.listarInscripciones();
    }

    public InscripcionResponse cancelarInscripcion(int idTorneo, int idEquipo) {
        InscripcionResponse response = new InscripcionResponse();

        if (idTorneo <= 0 || idEquipo <= 0) {
            response.setMensaje("Los identificadores de torneo y equipo deben ser válidos.");
            response.setExito(false);
            return response;
        }

        return inscripcionRepository.eliminarInscripcion(idTorneo, idEquipo);
    }
}
