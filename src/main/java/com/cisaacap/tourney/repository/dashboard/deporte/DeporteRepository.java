package main.java.com.cisaacap.tourney.repository.dashboard.deporte;

import main.java.com.cisaacap.tourney.model.deporte.Deportes;
import main.java.com.cisaacap.tourney.config.ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DeporteRepository {

    public ObservableList<Deportes> findAll() {
        ObservableList<Deportes> deportes = FXCollections.observableArrayList();
        String sql = "SELECT id_deporte, nombre_deporte FROM deportes";

        try (PreparedStatement pstm = ConnectionDB.getConnection().prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                deportes.add(new Deportes(
                        rs.getInt("id_deporte"),
                        rs.getString("nombre_deporte")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deportes;
    }
}
