package main.java.com.cisaacap.tourney.model.jugadores;

public class Jugadores {

    /*  ======================================
            ATRIBUTOS
          ====================================== */
    int idJugador;
    String nombre;
    String apellido;
    int edad;
    int idEquipo;
    int idUsuario;

    /*  ======================================
            CONSTRUCTOR
          ====================================== */
    public Jugadores(int idJugador, String nombre, String apellido, int edad, int idEquipo, int idUsuario) {
        this.idJugador = idJugador;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.idEquipo = idEquipo;
        this.idUsuario = idUsuario;
    }

    /*  ======================================
            GETTERS&SETTERS
          ====================================== */
    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
