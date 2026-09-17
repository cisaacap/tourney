package main.java.com.cisaacap.tourney.service.auth;

import main.java.com.cisaacap.tourney.repository.auth.UsuarioRepository;

public class UsuarioService {

    private UsuarioRepository userRepo;

    public UsuarioService(UsuarioRepository userRepo) {
        this.userRepo = userRepo;
    }
}
