package main.java.com.cisaacap.tourney.dto.request.dashboard.inscripciones;

import java.time.LocalDate;

public class InscripcionRequest {

    private int idTorneo;
    private int idEquipo;
    private LocalDate fechaInscripcion;

    public InscripcionRequest() {
    }

    public InscripcionRequest(int idTorneo, int idEquipo, LocalDate fechaInscripcion) {
        this.idTorneo = idTorneo;
        this.idEquipo = idEquipo;
        this.fechaInscripcion = fechaInscripcion;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}
