package main.java.com.cisaacap.tourney.model.auth;

public class Usuarios {

    /*  ======================================
            ATRIBUTOS
          ====================================== */
    int idUsuario;
    String nickname;
    String email;
    String psswrd;

    /*  ======================================
            CONSTRUCTOR
          ====================================== */
    public Usuarios(int idUsuario, String nickname, String email, String psswrd) {
        this.idUsuario = idUsuario;
        this.nickname = nickname;
        this.email = email;
        this.psswrd = psswrd;
    }

    /*  ======================================
            GETTERS&SETTERS
          ====================================== */
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

    public String getPsswrd() {
        return psswrd;
    }

    public void setPsswrd(String psswrd) {
        this.psswrd = psswrd;
    }

}
