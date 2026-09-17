package main.java.com.cisaacap.tourney.controller.auth;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import main.java.com.cisaacap.tourney.service.auth.UsuarioService;
import main.java.com.cisaacap.tourney.util.SceneManager;


public class LoginController implements Initializable {

    private SceneManager stage;
    private UsuarioService userService;

    public LoginController(SceneManager stage, UsuarioService userService) {
        this.stage = stage;
        this.userService = userService;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
