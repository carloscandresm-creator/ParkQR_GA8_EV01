package com.parkqr.dao;

import com.parkqr.model.Usuario;
import com.parkqr.util.ConexionBD;
import com.parkqr.util.Seguridad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario autenticar(String nombreUsuario, String contrasena) {

        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND estado = 'ACTIVO'";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return null;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

                consulta.setString(1, nombreUsuario);

                try (ResultSet resultado = consulta.executeQuery()) {

                    if (resultado.next()) {

                        String hash = resultado.getString("contrasena");

                        if (Seguridad.verificarContrasena(contrasena, hash)) {

                            Usuario usuario = new Usuario();

                            usuario.setIdUsuario(resultado.getInt("id_usuario"));
                            usuario.setNombre(resultado.getString("nombre"));
                            usuario.setUsuario(resultado.getString("usuario"));
                            usuario.setContrasena(hash);
                            usuario.setRol(resultado.getString("rol"));
                            usuario.setEstado(resultado.getString("estado"));

                            return usuario;
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al autenticar usuario:");
            e.printStackTrace();
        }

        return null;
    }
}