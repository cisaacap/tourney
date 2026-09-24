package main.java.com.cisaacap.tourney.model.equipos;

public class Equipos {

    /*  ======================================
            ATRIBUTOS
          ====================================== */
    int idEquipo;
    String nombreEquipo;
    String ciudad;

    /*  ======================================
            CONSTRUCTOR
          ====================================== */

    public Equipos(int idEquipo, String nombreEquipo) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
    }
    
    public Equipos(int idEquipo, String nombreEquipo, String ciudad) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.ciudad = ciudad;
    }

    /*  ======================================
            GETTERS&SETTERS
          ====================================== */
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

}
