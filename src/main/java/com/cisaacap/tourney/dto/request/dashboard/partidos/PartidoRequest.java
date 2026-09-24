package main.java.com.cisaacap.tourney.dto.request.dashboard.partidos;

import java.time.LocalDateTime;

public class PartidoRequest {

    private int idTorneo;
    private int idEquipoLocal;
    private int idEquipoVisitante;
    private LocalDateTime fechaHora;
    private int puntosLocal;
    private int puntosVisitante;

    public PartidoRequest() {
    }

    public PartidoRequest(int idTorneo, int idEquipoLocal, int idEquipoVisitante, LocalDateTime fechaHora, int puntosLocal, int puntosVisitante) {
        this.idTorneo = idTorneo;
        this.idEquipoLocal = idEquipoLocal;
        this.idEquipoVisitante = idEquipoVisitante;
        this.fechaHora = fechaHora;
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public int getIdEquipoLocal() {
        return idEquipoLocal;
    }

    public void setIdEquipoLocal(int idEquipoLocal) {
        this.idEquipoLocal = idEquipoLocal;
    }

    public int getIdEquipoVisitante() {
        return idEquipoVisitante;
    }

    public void setIdEquipoVisitante(int idEquipoVisitante) {
        this.idEquipoVisitante = idEquipoVisitante;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public void setPuntosLocal(int puntosLocal) {
        this.puntosLocal = puntosLocal;
    }

    public int getPuntosVisitante() {
        return puntosVisitante;
    }

    public void setPuntosVisitante(int puntosVisitante) {
        this.puntosVisitante = puntosVisitante;
    }
}
