package main.java.com.cisaacap.tourney.dto.response.auth;

public class RegisterResponse {

    private int idUsuario;
    private String nickname;
    private String email;
    private String mensaje;
    private boolean exito;

    public RegisterResponse() {
    }

    public RegisterResponse(int idUsuario, String nickname, String email, String mensaje, boolean exito) {
        this.idUsuario = idUsuario;
        this.nickname = nickname;
        this.email = email;
        this.mensaje = mensaje;
        this.exito = exito;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
