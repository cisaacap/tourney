package main.java.com.cisaacap.tourney.dto.request.auth;

public class RegisterRequest {

    private String nickname;
    private String email;
    private String psswrd;

    public RegisterRequest() {
    }

    public RegisterRequest(String nickname, String email, String psswrd) {
        this.nickname = nickname;
        this.email = email;
        this.psswrd = psswrd;
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
