package main.java.com.cisaacap.tourney.repository.dashboard.partidos;

import main.java.com.cisaacap.tourney.config.ConnectionDB;
import main.java.com.cisaacap.tourney.dto.request.dashboard.partidos.PartidoRequest;
import main.java.com.cisaacap.tourney.dto.response.dashboard.partidos.PartidosResponse;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PartidoRepository {

    // CREATE
    public PartidosResponse crearPartido(PartidoRequest req) {
        String sql = "INSERT INTO partidos (id_torneo, id_equipo_local, id_equipo_visitante, fecha_hora, puntos_local, puntos_visitante) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        PartidosResponse response = new PartidosResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, req.getIdTorneo());
            stmt.setInt(2, req.getIdEquipoLocal());
            stmt.setInt(3, req.getIdEquipoVisitante());
            stmt.setTimestamp(4, Timestamp.valueOf(req.getFechaHora()));
            stmt.setInt(5, req.getPuntosLocal());
            stmt.setInt(6, req.getPuntosVisitante());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        response.setIdPartido(generatedKeys.getInt(1));
                    }
                }
                response.setIdTorneo(req.getIdTorneo());
                response.setIdEquipoLocal(req.getIdEquipoLocal());
                response.setIdEquipoVisitante(req.getIdEquipoVisitante());
                response.setFechaHora(req.getFechaHora());
                response.setPuntosLocal(req.getPuntosLocal());
                response.setPuntosVisitante(req.getPuntosVisitante());
                response.setMensaje("Partido creado exitosamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se pudo registrar el partido.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // READ ALL
    public ObservableList<PartidosResponse> listarPartidos() {
        String sql = "SELECT p.id_partido, p.id_torneo, t.nombre_torneo, "
                + "p.id_equipo_local, el.nombre_equipo AS equipo_local, "
                + "p.id_equipo_visitante, ev.nombre_equipo AS equipo_visitante, "
                + "p.fecha_hora, p.puntos_local, p.puntos_visitante "
                + "FROM partidos p "
                + "JOIN torneos t ON p.id_torneo = t.id_torneo "
                + "JOIN equipos el ON p.id_equipo_local = el.id_equipo "
                + "JOIN equipos ev ON p.id_equipo_visitante = ev.id_equipo";

        ObservableList<PartidosResponse> listaPartidos = FXCollections.observableArrayList();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PartidosResponse partido = new PartidosResponse();
                partido.setIdPartido(rs.getInt("id_partido"));
                partido.setIdTorneo(rs.getInt("id_torneo"));
                partido.setNombreTorneo(rs.getString("nombre_torneo"));
                partido.setIdEquipoLocal(rs.getInt("id_equipo_local"));
                partido.setNombreEquipoLocal(rs.getString("equipo_local"));
                partido.setIdEquipoVisitante(rs.getInt("id_equipo_visitante"));
                partido.setNombreEquipoVisitante(rs.getString("equipo_visitante"));
                partido.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                partido.setPuntosLocal(rs.getInt("puntos_local"));
                partido.setPuntosVisitante(rs.getInt("puntos_visitante"));
                partido.setMensaje("Listado exitoso.");
                partido.setExito(true);

                listaPartidos.add(partido);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaPartidos;
    }

    // READ BY ID
    public PartidosResponse buscarPartidoPorId(int idPartido) {
        String sql = "SELECT p.id_partido, p.id_torneo, t.nombre_torneo, "
                + "p.id_equipo_local, el.nombre_equipo AS equipo_local, "
                + "p.id_equipo_visitante, ev.nombre_equipo AS equipo_visitante, "
                + "p.fecha_hora, p.puntos_local, p.puntos_visitante "
                + "FROM partidos p "
                + "JOIN torneos t ON p.id_torneo = t.id_torneo "
                + "JOIN equipos el ON p.id_equipo_local = el.id_equipo "
                + "JOIN equipos ev ON p.id_equipo_visitante = ev.id_equipo "
                + "WHERE p.id_partido = ?";

        PartidosResponse response = new PartidosResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPartido);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    response.setIdPartido(rs.getInt("id_partido"));
                    response.setIdTorneo(rs.getInt("id_torneo"));
                    response.setNombreTorneo(rs.getString("nombre_torneo"));
                    response.setIdEquipoLocal(rs.getInt("id_equipo_local"));
                    response.setNombreEquipoLocal(rs.getString("equipo_local"));
                    response.setIdEquipoVisitante(rs.getInt("id_equipo_visitante"));
                    response.setNombreEquipoVisitante(rs.getString("equipo_visitante"));
                    response.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                    response.setPuntosLocal(rs.getInt("puntos_local"));
                    response.setPuntosVisitante(rs.getInt("puntos_visitante"));
                    response.setMensaje("Partido encontrado.");
                    response.setExito(true);
                } else {
                    response.setMensaje("Partido no encontrado.");
                    response.setExito(false);
                }
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // UPDATE
    public PartidosResponse actualizarPartido(int idPartido, PartidoRequest req) {
        String sql = "UPDATE partidos SET id_torneo = ?, id_equipo_local = ?, id_equipo_visitante = ?, "
                + "fecha_hora = ?, puntos_local = ?, puntos_visitante = ? WHERE id_partido = ?";

        PartidosResponse response = new PartidosResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, req.getIdTorneo());
            stmt.setInt(2, req.getIdEquipoLocal());
            stmt.setInt(3, req.getIdEquipoVisitante());
            stmt.setTimestamp(4, Timestamp.valueOf(req.getFechaHora()));
            stmt.setInt(5, req.getPuntosLocal());
            stmt.setInt(6, req.getPuntosVisitante());
            stmt.setInt(7, idPartido);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setIdPartido(idPartido);
                response.setIdTorneo(req.getIdTorneo());
                response.setIdEquipoLocal(req.getIdEquipoLocal());
                response.setIdEquipoVisitante(req.getIdEquipoVisitante());
                response.setFechaHora(req.getFechaHora());
                response.setPuntosLocal(req.getPuntosLocal());
                response.setPuntosVisitante(req.getPuntosVisitante());
                response.setMensaje("Partido actualizado correctamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se encontró el partido a actualizar.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // DELETE
    public PartidosResponse eliminarPartido(int idPartido) {
        String sql = "DELETE FROM partidos WHERE id_partido = ?";
        PartidosResponse response = new PartidosResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPartido);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setIdPartido(idPartido);
                response.setMensaje("Partido eliminado correctamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se encontró el partido a eliminar.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }
}
