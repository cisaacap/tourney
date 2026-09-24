package main.java.com.cisaacap.tourney.repository.dashboard.inscripciones;

import main.java.com.cisaacap.tourney.config.ConnectionDB;
import main.java.com.cisaacap.tourney.dto.request.dashboard.inscripciones.InscripcionRequest;
import main.java.com.cisaacap.tourney.dto.response.dashboard.inscripciones.InscripcionResponse;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InscripcionRepository {

    // CREATE
    public InscripcionResponse crearInscripcion(InscripcionRequest req) {
        String sql = "INSERT INTO inscripciones (id_torneo, id_equipo, fecha_inscripcion) VALUES (?, ?, ?)";
        InscripcionResponse response = new InscripcionResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, req.getIdTorneo());
            stmt.setInt(2, req.getIdEquipo());
            stmt.setDate(3, Date.valueOf(req.getFechaInscripcion()));

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setIdTorneo(req.getIdTorneo());
                response.setIdEquipo(req.getIdEquipo());
                response.setFechaInscripcion(req.getFechaInscripcion());
                response.setMensaje("Inscripción realizada exitosamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se pudo registrar la inscripción.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // READ ALL
    public ObservableList<InscripcionResponse> listarInscripciones() {
        String sql = "SELECT i.id_torneo, t.nombre_torneo, i.id_equipo, e.nombre_equipo, i.fecha_inscripcion "
                + "FROM inscripciones i "
                + "JOIN torneos t ON i.id_torneo = t.id_torneo "
                + "JOIN equipos e ON i.id_equipo = e.id_equipo";

        ObservableList<InscripcionResponse> listaInscripciones = FXCollections.observableArrayList();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                InscripcionResponse inscripcion = new InscripcionResponse();
                inscripcion.setIdTorneo(rs.getInt("id_torneo"));
                inscripcion.setNombreTorneo(rs.getString("nombre_torneo"));
                inscripcion.setIdEquipo(rs.getInt("id_equipo"));
                inscripcion.setNombreEquipo(rs.getString("nombre_equipo"));
                inscripcion.setFechaInscripcion(rs.getDate("fecha_inscripcion").toLocalDate());
                inscripcion.setMensaje("Listado exitoso.");
                inscripcion.setExito(true);
                listaInscripciones.add(inscripcion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaInscripciones;
    }

    // DELETE (Por clave primaria compuesta)
    public InscripcionResponse eliminarInscripcion(int idTorneo, int idEquipo) {
        String sql = "DELETE FROM inscripciones WHERE id_torneo = ? AND id_equipo = ?";
        InscripcionResponse response = new InscripcionResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTorneo);
            stmt.setInt(2, idEquipo);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setIdTorneo(idTorneo);
                response.setIdEquipo(idEquipo);
                response.setMensaje("Inscripción eliminada correctamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se encontró la inscripción a eliminar.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }
}
