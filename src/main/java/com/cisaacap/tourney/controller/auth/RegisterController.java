/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main.java.com.cisaacap.tourney.controller.auth;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import main.java.com.cisaacap.tourney.service.auth.UsuarioService;
import main.java.com.cisaacap.tourney.util.SceneManager;

public class RegisterController implements Initializable {

    private SceneManager stage;
    private UsuarioService userService;

    public RegisterController(SceneManager stage, UsuarioService userService) {
        this.stage = stage;
        this.userService = userService;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
