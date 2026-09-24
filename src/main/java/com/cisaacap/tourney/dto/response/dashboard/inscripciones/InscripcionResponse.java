package main.java.com.cisaacap.tourney.dto.response.dashboard.inscripciones;

import java.time.LocalDate;

public class InscripcionResponse {

    private int idTorneo;
    private String nombreTorneo;
    private int idEquipo;
    private String nombreEquipo;
    private LocalDate fechaInscripcion;
    private String mensaje;
    private boolean exito;

    public InscripcionResponse() {
    }

    public InscripcionResponse(int idTorneo, String nombreTorneo, int idEquipo, String nombreEquipo, LocalDate fechaInscripcion) {
        this.idTorneo = idTorneo;
        this.nombreTorneo = nombreTorneo;
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.fechaInscripcion = fechaInscripcion;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }
}
