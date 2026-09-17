package main.java.com.cisaacap.tourney.model.inscripciones;

public class Inscripciones {

    /*  ======================================
            ATRIBUTOS
          ====================================== */
    int idTorneo;
    int idEquipo;
    String fechaInscripcion;

    /*  ======================================
            CONSTRUCTOR
          ====================================== */
    public Inscripciones(int idTorneo, int idEquipo, String fechaInscripcion) {
        this.idTorneo = idTorneo;
        this.idEquipo = idEquipo;
        this.fechaInscripcion = fechaInscripcion;
    }

    /*  ======================================
            GETTERS&SETTERS
          ====================================== */
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

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}
