package main.java.com.cisaacap.tourney.dto.request.dashboard.torneos;

import java.time.LocalDate;

public class TorneoRequest {

    private String nombreTorneo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int idDeporte;

    public TorneoRequest() {
    }

    public TorneoRequest(String nombreTorneo, LocalDate fechaInicio, LocalDate fechaFin, int idDeporte) {
        this.nombreTorneo = nombreTorneo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idDeporte = idDeporte;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
    }
}
