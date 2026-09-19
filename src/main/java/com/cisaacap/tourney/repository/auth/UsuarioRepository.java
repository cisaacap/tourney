package main.java.com.cisaacap.tourney.repository.auth;

import main.java.com.cisaacap.tourney.config.ConnectionDB;
import main.java.com.cisaacap.tourney.dto.request.auth.LoginRequest;
import main.java.com.cisaacap.tourney.dto.request.auth.RegisterRequest;
import main.java.com.cisaacap.tourney.dto.response.auth.RegisterResponse;
import main.java.com.cisaacap.tourney.dto.response.auth.UsuarioResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import main.java.com.cisaacap.tourney.security.jbcrypt.BCrypt;

public class UsuarioRepository {

    // CREATE (Crear Usuario con Contraseña Encriptada)
    public RegisterResponse crearUsuario(RegisterRequest req) {
        String sql = "INSERT INTO usuarios (nickname, email, psswrd) VALUES (?, ?, ?)";
        RegisterResponse response = new RegisterResponse();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Generar Hash seguro de la contraseña
            String passwordEncriptada = BCrypt.hashpw(req.getPsswrd(), BCrypt.gensalt(12));

            stmt.setString(1, req.getNickname());
            stmt.setString(2, req.getEmail());
            stmt.setString(3, passwordEncriptada);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        response.setIdUsuario(generatedKeys.getInt(1));
                    }
                }
                response.setNickname(req.getNickname());
                response.setEmail(req.getEmail());
                response.setMensaje("Usuario creado exitosamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se pudo crear el usuario.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // PASO 1 LOGIN: Validar Credenciales comparando Hash de BCrypt
    public UsuarioResponse autenticarUsuario(LoginRequest req) {
        // Se busca al usuario únicamente por su email
        String sql = "SELECT id_usuario, nickname, email, psswrd FROM usuarios WHERE email = ?";
        UsuarioResponse response = new UsuarioResponse();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, req.getEmail());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String hashGuardado = rs.getString("psswrd");

                    // Validar la contraseña plana ingresada contra el Hash almacenado
                    if (BCrypt.checkpw(req.getPsswrd(), hashGuardado)) {
                        response.setIdUsuario(rs.getInt("id_usuario"));
                        response.setNickname(rs.getString("nickname"));
                        response.setEmail(rs.getString("email"));
                        response.setMensaje("Paso 1 completado: Credenciales válidas.");
                        response.setExito(true);
                    } else {
                        response.setMensaje("Correo o contraseña incorrectos.");
                        response.setExito(false);
                    }
                } else {
                    response.setMensaje("Correo o contraseña incorrectos.");
                    response.setExito(false);
                }
            }

        } catch (SQLException e) {
            response.setMensaje("Error en la base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // UPDATE (Actualizar Usuario / Cambiar Contraseña)
    public UsuarioResponse actualizarUsuario(String nickname, String email, String psswrd, int idUsuario) {
        String sql = "UPDATE usuarios SET nickname = ?, email = ?, psswrd = ? WHERE id_usuario = ?";
        UsuarioResponse response = new UsuarioResponse();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Encriptar la nueva contraseña
            String passwordEncriptada = BCrypt.hashpw(psswrd, BCrypt.gensalt(12));

            stmt.setString(1, nickname);
            stmt.setString(2, email);
            stmt.setString(3, passwordEncriptada);
            stmt.setInt(4, idUsuario);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setIdUsuario(idUsuario);
                response.setNickname(nickname);
                response.setEmail(email);
                response.setMensaje("Usuario actualizado correctamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se encontró el usuario a actualizar.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // READ (Buscar Usuario por ID)
    public UsuarioResponse buscarUsuarioPorId(int idUsuario) {
        String sql = "SELECT id_usuario, nickname, email FROM usuarios WHERE id_usuario = ?";
        UsuarioResponse response = new UsuarioResponse();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    response.setIdUsuario(rs.getInt("id_usuario"));
                    response.setNickname(rs.getString("nickname"));
                    response.setEmail(rs.getString("email"));
                    response.setMensaje("Usuario encontrado.");
                    response.setExito(true);
                } else {
                    response.setMensaje("Usuario no encontrado.");
                    response.setExito(false);
                }
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }

    // READ ALL (Listar todos los usuarios)
    public List<UsuarioResponse> listarUsuarios() {
        String sql = "SELECT id_usuario, nickname, email FROM usuarios";
        List<UsuarioResponse> listaUsuarios = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                UsuarioResponse usuario = new UsuarioResponse();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNickname(rs.getString("nickname"));
                usuario.setEmail(rs.getString("email"));
                usuario.setMensaje("Listado exitoso.");
                usuario.setExito(true);
                listaUsuarios.add(usuario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaUsuarios;
    }

    // DELETE (Eliminar Usuario)
    public UsuarioResponse eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        UsuarioResponse response = new UsuarioResponse();

        try (Connection conn = ConnectionDB.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                response.setIdUsuario(idUsuario);
                response.setMensaje("Usuario eliminado correctamente.");
                response.setExito(true);
            } else {
                response.setMensaje("No se encontró el usuario a eliminar.");
                response.setExito(false);
            }

        } catch (SQLException e) {
            response.setMensaje("Error en base de datos: " + e.getMessage());
            response.setExito(false);
        }

        return response;
    }
}