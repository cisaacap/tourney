package main.java.com.cisaacap.tourney.dto.request.auth;

public class LoginRequest {

    private String email;
    private String psswrd;

    public LoginRequest() {
    }

    public LoginRequest(String email, String psswrd) {
        this.email = email;
        this.psswrd = psswrd;
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
