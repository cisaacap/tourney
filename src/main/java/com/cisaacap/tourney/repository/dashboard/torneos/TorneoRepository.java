package main.java.com.cisaacap.tourney.repository.dashboard.torneos;

import main.java.com.cisaacap.tourney.config.ConnectionDB;
import main.java.com.cisaacap.tourney.dto.request.dashboard.torneos.TorneoRequest;
import main.java.com.cisaacap.tourney.dto.response.dashboard.torneos.TorneoResponse;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TorneoRepository {

    // CREATE
    public TorneoResponse crearTorneo(TorneoRequest req) {
        String sql = "INSERT INTO torneos (nombre_torneo, fecha_inicio, fecha_fin, id_deporte) VALUES (?, ?, ?, ?)";
        TorneoResponse response = new TorneoResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, req.getNombreTorneo());
            stmt.setDate(2, Date.valueOf(req.getFechaInicio()));
            stmt.setDate(3, Date.valueOf(req.getFechaFin()));
            stmt.setInt(4, req.getIdDeporte());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        response.setIdTorneo(generatedKeys.getInt(1));
                    }
                }
                response.setNombreTorneo(req.getNombreTorneo());
                response.setFechaInicio(req.getFechaInicio());
                response.setFechaFin(req.getFechaFin());
                response.setIdDeporte(req.getIdDeporte());
                response.setMensaje("Torneo creado exitosamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se pudo crear el torneo.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // READ ALL (Ideal para cargar en TableView)
    public ObservableList<TorneoResponse> listarTorneos() {
        String sql = "SELECT t.id_torneo, t.nombre_torneo, t.fecha_inicio, t.fecha_fin, "
                + "t.id_deporte, d.nombre_deporte "
                + "FROM torneos t "
                + "JOIN deportes d ON t.id_deporte = d.id_deporte";

        ObservableList<TorneoResponse> listaTorneos = FXCollections.observableArrayList();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TorneoResponse torneo = new TorneoResponse();
                torneo.setIdTorneo(rs.getInt("id_torneo"));
                torneo.setNombreTorneo(rs.getString("nombre_torneo"));
                torneo.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                torneo.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                torneo.setIdDeporte(rs.getInt("id_deporte"));
                torneo.setNombreDeporte(rs.getString("nombre_deporte"));
                torneo.setMensaje("Listado exitoso.");
                torneo.setExito(true);
                listaTorneos.add(torneo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaTorneos;
    }

    // READ BY ID
    public TorneoResponse buscarTorneoPorId(int idTorneo) {
        String sql = "SELECT t.id_torneo, t.nombre_torneo, t.fecha_inicio, t.fecha_fin, "
                + "t.id_deporte, d.nombre_deporte "
                + "FROM torneos t "
                + "JOIN deportes d ON t.id_deporte = d.id_deporte "
                + "WHERE t.id_torneo = ?";

        TorneoResponse response = new TorneoResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTorneo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    response.setIdTorneo(rs.getInt("id_torneo"));
                    response.setNombreTorneo(rs.getString("nombre_torneo"));
                    response.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                    response.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                    response.setIdDeporte(rs.getInt("id_deporte"));
                    response.setNombreDeporte(rs.getString("nombre_deporte"));
                    response.setMensaje("Torneo encontrado.");
                    response.setExito(true);
                } else {
                    response.setMensaje("Torneo no encontrado.");
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
    public TorneoResponse actualizarTorneo(int idTorneo, TorneoRequest req) {
        String sql = "UPDATE torneos SET nombre_torneo = ?, fecha_inicio = ?, fecha_fin = ?, id_deporte = ? WHERE id_torneo = ?";
        TorneoResponse response = new TorneoResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, req.getNombreTorneo());
            stmt.setDate(2, Date.valueOf(req.getFechaInicio()));
            stmt.setDate(3, Date.valueOf(req.getFechaFin()));
            stmt.setInt(4, req.getIdDeporte());
            stmt.setInt(5, idTorneo);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setIdTorneo(idTorneo);
                response.setNombreTorneo(req.getNombreTorneo());
                response.setFechaInicio(req.getFechaInicio());
                response.setFechaFin(req.getFechaFin());
                response.setIdDeporte(req.getIdDeporte());
                response.setMensaje("Torneo actualizado correctamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se encontró el torneo a actualizar.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // DELETE
    public TorneoResponse eliminarTorneo(int idTorneo) {
        String sql = "DELETE FROM torneos WHERE id_torneo = ?";
        TorneoResponse response = new TorneoResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTorneo);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setIdTorneo(idTorneo);
                response.setMensaje("Torneo eliminado correctamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se encontró el torneo a eliminar.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }
}
