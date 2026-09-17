package main.java.com.cisaacap.tourney.model.partidos;

public class Partidos {

    /*  ======================================
            ATRIBUTOS
          ====================================== */
    int idPartido;
    int idTorneo;
    int idEquipoLocal;
    int idEquipoVisitantes;
    String fechaHora;
    int puntosLocal;
    int puntosVisitantes;

    /*  ======================================
            CONSTRUCTOR
          ====================================== */
    public Partidos(int idPartido, int idTorneo, int idEquipoLocal, int idEquipoVisitantes, String fechaHora, int puntosLocal, int puntosVisitantes) {
        this.idPartido = idPartido;
        this.idTorneo = idTorneo;
        this.idEquipoLocal = idEquipoLocal;
        this.idEquipoVisitantes = idEquipoVisitantes;
        this.fechaHora = fechaHora;
        this.puntosLocal = puntosLocal;
        this.puntosVisitantes = puntosVisitantes;
    }

    /*  ======================================
            GETTERS&SETTERS
          ====================================== */
    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
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

    public int getIdEquipoVisitantes() {
        return idEquipoVisitantes;
    }

    public void setIdEquipoVisitantes(int idEquipoVisitantes) {
        this.idEquipoVisitantes = idEquipoVisitantes;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public void setPuntosLocal(int puntosLocal) {
        this.puntosLocal = puntosLocal;
    }

    public int getPuntosVisitantes() {
        return puntosVisitantes;
    }

    public void setPuntosVisitantes(int puntosVisitantes) {
        this.puntosVisitantes = puntosVisitantes;
    }
}
