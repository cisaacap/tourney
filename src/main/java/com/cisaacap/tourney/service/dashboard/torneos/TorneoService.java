package main.java.com.cisaacap.tourney.service.dashboard.torneos;

import main.java.com.cisaacap.tourney.dto.request.dashboard.torneos.TorneoRequest;
import main.java.com.cisaacap.tourney.dto.response.dashboard.torneos.TorneoResponse;
import main.java.com.cisaacap.tourney.repository.dashboard.torneos.TorneoRepository;

import javafx.collections.ObservableList;
import main.java.com.cisaacap.tourney.model.deporte.Deportes;
import main.java.com.cisaacap.tourney.repository.dashboard.deporte.DeporteRepository;

public class TorneoService {

    private final TorneoRepository torneoRepository;
    private final DeporteRepository depRepo;
    
    public TorneoService(TorneoRepository torneoRepository, DeporteRepository depRepo) {
        this.torneoRepository = torneoRepository;
        this.depRepo = depRepo;
    }

    public TorneoResponse registrarTorneo(TorneoRequest req) {
        TorneoResponse responseError = validarEntrada(req);
        if (responseError != null) {
            return responseError;
        }

        return torneoRepository.crearTorneo(req);
    }

    public ObservableList<TorneoResponse> obtenerTodos() {
        return torneoRepository.listarTorneos();
    }

    public TorneoResponse obtenerPorId(int idTorneo) {
        if (idTorneo <= 0) {
            TorneoResponse res = new TorneoResponse();
            res.setMensaje("El ID del torneo no es válido.");
            res.setExito(false);
            return res;
        }
        return torneoRepository.buscarTorneoPorId(idTorneo);
    }

    public TorneoResponse modificarTorneo(int idTorneo, TorneoRequest req) {
        if (idTorneo <= 0) {
            TorneoResponse res = new TorneoResponse();
            res.setMensaje("El ID del torneo a actualizar no es válido.");
            res.setExito(false);
            return res;
        }

        TorneoResponse responseError = validarEntrada(req);
        if (responseError != null) {
            return responseError;
        }

        return torneoRepository.actualizarTorneo(idTorneo, req);
    }

    public TorneoResponse darDeBajaTorneo(int idTorneo) {
        if (idTorneo <= 0) {
            TorneoResponse res = new TorneoResponse();
            res.setMensaje("El ID del torneo no es válido para eliminar.");
            res.setExito(false);
            return res;
        }
        return torneoRepository.eliminarTorneo(idTorneo);
    }

    // --- Validación interna de entradas/negocio ---
    private TorneoResponse validarEntrada(TorneoRequest req) {
        TorneoResponse response = new TorneoResponse();

        if (req == null) {
            response.setMensaje("La solicitud no contiene datos.");
            response.setExito(false);
            return response;
        }

        if (req.getNombreTorneo() == null || req.getNombreTorneo().trim().isEmpty()) {
            response.setMensaje("El nombre del torneo es obligatorio.");
            response.setExito(false);
            return response;
        }

        if (req.getIdDeporte() <= 0) {
            response.setMensaje("Debe seleccionar un deporte válido.");
            response.setExito(false);
            return response;
        }

        if (req.getFechaInicio() == null || req.getFechaFin() == null) {
            response.setMensaje("Las fechas de inicio y fin son obligatorias.");
            response.setExito(false);
            return response;
        }

        if (req.getFechaFin().isBefore(req.getFechaInicio())) {
            response.setMensaje("La fecha de fin no puede ser anterior a la fecha de inicio.");
            response.setExito(false);
            return response;
        }

        return null; // Sin errores
    }
    
    public ObservableList<Deportes> obtenerDeportes() {
        return depRepo.findAll();
    }
    
}
