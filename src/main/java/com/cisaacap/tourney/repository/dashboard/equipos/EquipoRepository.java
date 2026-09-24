package main.java.com.cisaacap.tourney.repository.dashboard.equipos;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.cisaacap.tourney.config.ConnectionDB;
import main.java.com.cisaacap.tourney.model.equipos.Equipos;

public class EquipoRepository {

    public EquipoRepository() {
    }

    public boolean save(String nombreEquipo, String ciudad) {

        String sql = "insert into equipos(nombre_equipo, ciudad) values (?, ?)";

        try (PreparedStatement pstm = ConnectionDB.getConnection().prepareStatement(sql)) {

            pstm.setString(1, nombreEquipo);
            pstm.setString(2, ciudad);

            int affectedRows = pstm.executeUpdate();

            if (affectedRows > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public ObservableList<Equipos> obtenerTodos() {
        String sql = "SELECT id_equipo, nombre_equipo FROM equipos";
        ObservableList<Equipos> lista = FXCollections.observableArrayList();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Equipos(
                        rs.getInt("id_equipo"),
                        rs.getString("nombre_equipo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
