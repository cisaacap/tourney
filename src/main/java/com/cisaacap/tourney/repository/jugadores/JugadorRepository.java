package main.java.com.cisaacap.tourney.repository.jugadores;

import main.java.com.cisaacap.tourney.config.ConnectionDB;
import main.java.com.cisaacap.tourney.dto.request.jugadores.JugadorRequest;
import main.java.com.cisaacap.tourney.dto.response.jugadores.JugadorResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorRepository {

    // CREATE (Crear Perfil de Jugador)
    public JugadorResponse crearJugador(JugadorRequest req) {
        String sql = "INSERT INTO jugadores (nombre, apellido, edad, id_equipo, id_usuario) VALUES (?, ?, ?, ?, ?)";
        JugadorResponse response = new JugadorResponse();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, req.getNombre());
            stmt.setString(2, req.getApellido());
            stmt.setInt(3, req.getEdad());
            stmt.setInt(4, req.getIdEquipo());
            stmt.setInt(5, req.getIdUsuario());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        response.setIdJugador(generatedKeys.getInt(1));
                    }
                }
                response.setNombre(req.getNombre());
                response.setApellido(req.getApellido());
                response.setEdad(req.getEdad());
                response.setIdEquipo(req.getIdEquipo());
                response.setIdUsuario(req.getIdUsuario());
                response.setMensaje("Perfil de Jugador creado exitosamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se pudo registrar el perfil de jugador.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // PASO 2 LOGIN / READ: Obtener Jugador por ID de Usuario
    public JugadorResponse buscarJugadorPorIdUsuario(int idUsuario) {
        String sql = "SELECT id_jugador, nombre, apellido, edad, id_equipo, id_usuario FROM jugadores WHERE id_usuario = ?";
        JugadorResponse response = new JugadorResponse();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    response.setIdJugador(rs.getInt("id_jugador"));
                    response.setNombre(rs.getString("nombre"));
                    response.setApellido(rs.getString("apellido"));
                    response.setEdad(rs.getInt("edad"));
                    response.setIdEquipo(rs.getInt("id_equipo"));
                    response.setIdUsuario(rs.getInt("id_usuario"));
                    response.setMensaje("Paso 2 completado: Perfil de jugador cargado.");
                    response.setExito(true);
                } else {
                    response.setMensaje("El usuario autenticado no tiene perfil de jugador registrado.");
                    response.setExito(false);
                }
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // READ (Buscar Jugador por ID de Jugador)
    public JugadorResponse buscarJugadorPorId(int idJugador) {
        String sql = "SELECT id_jugador, nombre, apellido, edad, id_equipo, id_usuario FROM jugadores WHERE id_jugador = ?";
        JugadorResponse response = new JugadorResponse();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idJugador);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    response.setIdJugador(rs.getInt("id_jugador"));
                    response.setNombre(rs.getString("nombre"));
                    response.setApellido(rs.getString("apellido"));
                    response.setEdad(rs.getInt("edad"));
                    response.setIdEquipo(rs.getInt("id_equipo"));
                    response.setIdUsuario(rs.getInt("id_usuario"));
                    response.setMensaje("Jugador encontrado.");
                    response.setExito(true);
                } else {
                    response.setMensaje("Jugador no encontrado.");
                    response.setExito(false);
                }
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // READ ALL (Listar todos los jugadores)
    public List<JugadorResponse> listarJugadores() {
        String sql = "SELECT id_jugador, nombre, apellido, edad, id_equipo, id_usuario FROM jugadores";
        List<JugadorResponse> lista = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                JugadorResponse jugador = new JugadorResponse();
                jugador.setIdJugador(rs.getInt("id_jugador"));
                jugador.setNombre(rs.getString("nombre"));
                jugador.setApellido(rs.getString("apellido"));
                jugador.setEdad(rs.getInt("edad"));
                jugador.setIdEquipo(rs.getInt("id_equipo"));
                jugador.setIdUsuario(rs.getInt("id_usuario"));
                jugador.setMensaje("Listado exitoso.");
                jugador.setExito(true);
                lista.add(jugador);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // UPDATE (Actualizar Jugador)
    public JugadorResponse actualizarJugador(JugadorRequest req, int idJugador) {
        String sql = "UPDATE jugadores SET nombre = ?, apellido = ?, edad = ?, id_equipo = ? WHERE id_jugador = ?";
        JugadorResponse response = new JugadorResponse();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, req.getNombre());
            stmt.setString(2, req.getApellido());
            stmt.setInt(3, req.getEdad());
            stmt.setInt(4, req.getIdEquipo());
            stmt.setInt(5, idJugador);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setIdJugador(idJugador);
                response.setNombre(req.getNombre());
                response.setApellido(req.getApellido());
                response.setEdad(req.getEdad());
                response.setIdEquipo(req.getIdEquipo());
                response.setMensaje("Perfil de jugador actualizado correctamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se encontró el jugador a actualizar.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // DELETE (Eliminar Jugador)
    public JugadorResponse eliminarJugador(int idJugador) {
        String sql = "DELETE FROM jugadores WHERE id_jugador = ?";
        JugadorResponse response = new JugadorResponse();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idJugador);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setIdJugador(idJugador);
                response.setMensaje("Jugador eliminado correctamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se encontró el jugador a eliminar.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }
}