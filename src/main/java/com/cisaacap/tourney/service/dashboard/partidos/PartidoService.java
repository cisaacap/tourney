package main.java.com.cisaacap.tourney.service.dashboard.partidos;

import main.java.com.cisaacap.tourney.dto.request.dashboard.partidos.PartidoRequest;
import main.java.com.cisaacap.tourney.dto.response.dashboard.partidos.PartidosResponse;
import main.java.com.cisaacap.tourney.repository.dashboard.partidos.PartidoRepository;

import javafx.collections.ObservableList;

public class PartidoService {

    private final PartidoRepository partidoRepository;

    public PartidoService(PartidoRepository partidoRepository) {
        this.partidoRepository = partidoRepository;
    }

    public PartidosResponse programarPartido(PartidoRequest req) {
        PartidosResponse responseError = validarEntrada(req);
        if (responseError != null) {
            return responseError;
        }

        return partidoRepository.crearPartido(req);
    }

    public ObservableList<PartidosResponse> obtenerTodos() {
        return partidoRepository.listarPartidos();
    }

    public PartidosResponse obtenerPorId(int idPartido) {
        if (idPartido <= 0) {
            PartidosResponse res = new PartidosResponse();
            res.setMensaje("El ID del partido no es válido.");
            res.setExito(false);
            return res;
        }
        return partidoRepository.buscarPartidoPorId(idPartido);
    }

    public PartidosResponse actualizarResultado(int idPartido, PartidoRequest req) {
        if (idPartido <= 0) {
            PartidosResponse res = new PartidosResponse();
            res.setMensaje("El ID del partido a actualizar no es válido.");
            res.setExito(false);
            return res;
        }

        PartidosResponse responseError = validarEntrada(req);
        if (responseError != null) {
            return responseError;
        }

        return partidoRepository.actualizarPartido(idPartido, req);
    }

    public PartidosResponse cancelarPartido(int idPartido) {
        if (idPartido <= 0) {
            PartidosResponse res = new PartidosResponse();
            res.setMensaje("El ID del partido no es válido.");
            res.setExito(false);
            return res;
        }
        return partidoRepository.eliminarPartido(idPartido);
    }

    // --- Validación interna de entradas/negocio ---
    private PartidosResponse validarEntrada(PartidoRequest req) {
        PartidosResponse response = new PartidosResponse();

        if (req == null) {
            response.setMensaje("La solicitud no contiene datos.");
            response.setExito(false);
            return response;
        }

        if (req.getIdTorneo() <= 0) {
            response.setMensaje("Debe asociar el partido a un torneo válido.");
            response.setExito(false);
            return response;
        }

        if (req.getIdEquipoLocal() <= 0 || req.getIdEquipoVisitante() <= 0) {
            response.setMensaje("Debe seleccionar equipos local y visitante válidos.");
            response.setExito(false);
            return response;
        }

        if (req.getIdEquipoLocal() == req.getIdEquipoVisitante()) {
            response.setMensaje("El equipo local y el equipo visitante no pueden ser el mismo.");
            response.setExito(false);
            return response;
        }

        if (req.getFechaHora() == null) {
            response.setMensaje("La fecha y hora del partido son obligatorias.");
            response.setExito(false);
            return response;
        }

        if (req.getPuntosLocal() < 0 || req.getPuntosVisitante() < 0) {
            response.setMensaje("Los puntos de los equipos no pueden ser valores negativos.");
            response.setExito(false);
            return response;
        }

        return null; // Sin errores
    }
}
