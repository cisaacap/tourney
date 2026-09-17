package main.java.com.cisaacap.tourney.model.torneos;

public class Torneos {

    /*  ======================================
            ATRIBUTOS
          ====================================== */
    int idTorneo;
   String nombreTorneo;
   String fechaInicio;
   String fechaFin;
   int idDeporte;
   
   /*  ======================================
            CONSTRUCTOR
          ====================================== */

    public Torneos(int idTorneo, String nombreTorneo, String fechaInicio, String fechaFin, int idDeporte) {
        this.idTorneo = idTorneo;
        this.nombreTorneo = nombreTorneo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idDeporte = idDeporte;
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

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
    }
}
