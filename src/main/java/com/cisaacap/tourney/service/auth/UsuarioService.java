package main.java.com.cisaacap.tourney.service.auth;

import main.java.com.cisaacap.tourney.dto.request.auth.LoginRequest;
import main.java.com.cisaacap.tourney.dto.request.auth.RegisterRequest;
import main.java.com.cisaacap.tourney.dto.response.auth.RegisterResponse;
import main.java.com.cisaacap.tourney.dto.response.auth.UsuarioResponse;
import main.java.com.cisaacap.tourney.repository.auth.UsuarioRepository;

import java.util.List;
import java.util.regex.Pattern;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse autenticarUsuario(LoginRequest req) {
        UsuarioResponse response = new UsuarioResponse();

        if (req == null || esVacio(req.getEmail()) || esVacio(req.getPsswrd())) {
            response.setMensaje("El email y la contraseña son obligatorios.");
            response.setExito(false);
            return response;
        }

        if (!EMAIL_PATTERN.matcher(req.getEmail()).matches()) {
            response.setMensaje("El formato del correo electrónico es inválido.");
            response.setExito(false);
            return response;
        }

        return usuarioRepository.autenticarUsuario(req);
    }

    public RegisterResponse registrarUsuario(RegisterRequest req) {
        RegisterResponse response = new RegisterResponse();

        if (req == null) {
            response.setMensaje("Los datos del usuario no pueden ser nulos.");
            response.setExito(false);
            return response;
        }

        if (esVacio(req.getNickname()) || esVacio(req.getEmail()) || esVacio(req.getPsswrd())) {
            response.setMensaje("Todos los campos (nickname, email, contraseña) son obligatorios.");
            response.setExito(false);
            return response;
        }

        if (!EMAIL_PATTERN.matcher(req.getEmail()).matches()) {
            response.setMensaje("El correo electrónico no cumple con un formato válido.");
            response.setExito(false);
            return response;
        }
        if (req.getPsswrd().length() < 8) {
            response.setMensaje("La contraseña debe tener al menos 8 caracteres.");
            response.setExito(false);
            return response;
        }

        return usuarioRepository.crearUsuario(req);
    }

    // OBTENER USUARIO POR ID
    public UsuarioResponse obtenerUsuarioPorId(int idUsuario) {
        if (idUsuario <= 0) {
            UsuarioResponse response = new UsuarioResponse();
            response.setMensaje("ID de usuario no válido.");
            response.setExito(false);
            return response;
        }
        return usuarioRepository.buscarUsuarioPorId(idUsuario);
    }

    // LISTAR TODOS LOS USUARIOS
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.listarUsuarios();
    }

    // ACTUALIZAR USUARIO
    public UsuarioResponse actualizarUsuario(String nickname, String email, String psswrd, int idUsuario) {
        UsuarioResponse response = new UsuarioResponse();

        if (idUsuario <= 0) {
            response.setMensaje("ID de usuario no válido.");
            response.setExito(false);
            return response;
        }

        if (esVacio(nickname) || esVacio(email) || esVacio(psswrd)) {
            response.setMensaje("Los campos no pueden estar vacíos.");
            response.setExito(false);
            return response;
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            response.setMensaje("El correo electrónico no cumple con un formato válido.");
            response.setExito(false);
            return response;
        }

        if (psswrd.length() < 8) {
            response.setMensaje("La nueva contraseña debe tener al menos 8 caracteres.");
            response.setExito(false);
            return response;
        }

        return usuarioRepository.actualizarUsuario(nickname, email, psswrd, idUsuario);
    }

    // ELIMINAR USUARIO
    public UsuarioResponse eliminarUsuario(int idUsuario) {
        if (idUsuario <= 0) {
            UsuarioResponse response = new UsuarioResponse();
            response.setMensaje("ID de usuario no válido.");
            response.setExito(false);
            return response;
        }
        return usuarioRepository.eliminarUsuario(idUsuario);
    }

    private boolean esVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
