package main.java.com.cisaacap.tourney.service.jugadores;

import main.java.com.cisaacap.tourney.dto.request.jugadores.JugadorRequest;
import main.java.com.cisaacap.tourney.dto.response.jugadores.JugadorResponse;
import main.java.com.cisaacap.tourney.repository.jugadores.JugadorRepository;

import java.util.List;

public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    // CREAR JUGADOR (Perfil asociado al usuario)
    public JugadorResponse registrarJugador(JugadorRequest req) {
        JugadorResponse response = new JugadorResponse();

        if (req == null) {
            response.setMensaje("La solicitud no puede ser nula.");
            response.setExito(false);
            return response;
        }

        if (esVacio(req.getNombre()) || esVacio(req.getApellido())) {
            response.setMensaje("El nombre y el apellido del jugador son requeridos.");
            response.setExito(false);
            return response;
        }

        if (req.getEdad() < 12 || req.getEdad() > 90) {
            response.setMensaje("La edad debe ser mayor o igual a 12 años.");
            response.setExito(false);
            return response;
        }

        if (req.getIdUsuario() <= 0) {
            response.setMensaje("El id_usuario asignado no es válido.");
            response.setExito(false);
            return response;
        }

        return jugadorRepository.crearJugador(req);
    }

    public JugadorResponse obtenerJugadorPorIdUsuario(int idUsuario) {
        if (idUsuario <= 0) {
            JugadorResponse response = new JugadorResponse();
            response.setMensaje("ID de usuario no válido para recuperar perfil de jugador.");
            response.setExito(false);
            return response;
        }
        return jugadorRepository.buscarJugadorPorIdUsuario(idUsuario);
    }

    // OBTENER JUGADOR POR ID
    public JugadorResponse obtenerJugadorPorId(int idJugador) {
        if (idJugador <= 0) {
            JugadorResponse response = new JugadorResponse();
            response.setMensaje("ID de jugador no válido.");
            response.setExito(false);
            return response;
        }
        return jugadorRepository.buscarJugadorPorId(idJugador);
    }

    // LISTAR TODOS LOS JUGADORES
    public List<JugadorResponse> listarJugadores() {
        return jugadorRepository.listarJugadores();
    }

    // ACTUALIZAR JUGADOR
    public JugadorResponse actualizarJugador(JugadorRequest req, int idJugador) {
        JugadorResponse response = new JugadorResponse();

        if (idJugador <= 0) {
            response.setMensaje("ID de jugador no válido.");
            response.setExito(false);
            return response;
        }

        if (req == null || esVacio(req.getNombre()) || esVacio(req.getApellido())) {
            response.setMensaje("Los campos de texto no pueden estar vacíos.");
            response.setExito(false);
            return response;
        }

        if (req.getEdad() < 12 || req.getEdad() > 90) {
            response.setMensaje("La edad del jugador está fuera del rango permitido.");
            response.setExito(false);
            return response;
        }

        return jugadorRepository.actualizarJugador(req, idJugador);
    }

    // ELIMINAR JUGADOR
    public JugadorResponse eliminarJugador(int idJugador) {
        if (idJugador <= 0) {
            JugadorResponse response = new JugadorResponse();
            response.setMensaje("ID de jugador no válido.");
            response.setExito(false);
            return response;
        }
        return jugadorRepository.eliminarJugador(idJugador);
    }

    private boolean esVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
