package main.java.com.cisaacap.tourney.repository.auth;

import main.java.com.cisaacap.tourney.config.ConnectionDB;
import main.java.com.cisaacap.tourney.dto.request.auth.LoginRequest;
import main.java.com.cisaacap.tourney.dto.request.auth.RegisterRequest;
import main.java.com.cisaacap.tourney.dto.response.auth.RegisterResponse;
import main.java.com.cisaacap.tourney.dto.response.auth.UsuarioResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    // PASO 1 LOGIN: Validar Credenciales de Usuario
    public UsuarioResponse autenticarUsuario(LoginRequest req) {
        String sql = "SELECT id_usuario, nickname, email FROM usuarios WHERE email = ? AND psswrd = ?";
        UsuarioResponse response = new UsuarioResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, req.getEmail());
            stmt.setString(2, req.getPsswrd());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    response.setIdUsuario(rs.getInt("id_usuario"));
                    response.setNickname(rs.getString("nickname"));
                    response.setEmail(rs.getString("email"));
                    response.setMensaje("Paso 1 completado: Credenciales válidas.");
                    response.setExito(true);
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

    // CREATE (Crear Usuario)
    public RegisterResponse crearUsuario(RegisterRequest req) {
        String sql = "INSERT INTO usuarios (nickname, email, psswrd) VALUES (?, ?, ?)";
        RegisterResponse response = new RegisterResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, req.getNickname());
            stmt.setString(2, req.getEmail());
            stmt.setString(3, req.getPsswrd());

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

    // READ (Buscar Usuario por ID)
    public UsuarioResponse buscarUsuarioPorId(int idUsuario) {
        String sql = "SELECT id_usuario, nickname, email FROM usuarios WHERE id_usuario = ?";
        UsuarioResponse response = new UsuarioResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

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

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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

    // UPDATE (Actualizar Usuario)
    public UsuarioResponse actualizarUsuario(String nickname, String email, String psswrd, int idUsuario) {
        String sql = "UPDATE usuarios SET nickname = ?, email = ?, psswrd = ? WHERE id_usuario = ?";
        UsuarioResponse response = new UsuarioResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nickname);
            stmt.setString(2, email);
            stmt.setString(3, psswrd);
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

    // DELETE (Eliminar Usuario)
    public UsuarioResponse eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        UsuarioResponse response = new UsuarioResponse();

        try (Connection conn = ConnectionDB.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

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
