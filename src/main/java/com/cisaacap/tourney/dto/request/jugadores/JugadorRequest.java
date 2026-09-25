package main.java.com.cisaacap.tourney.dto.request.jugadores;

public class JugadorRequest {

    private String nombre;
    private String apellido;
    private int edad;
    private int idUsuario;
    private int idEquipo;
    
    public JugadorRequest() {
    }

    public JugadorRequest(String nombre, String apellido, int edad, int idUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.idUsuario = idUsuario;
    }
    
    public JugadorRequest(String nombre, String apellido, int edad, int idUsuario, int idEquipo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
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
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
}
