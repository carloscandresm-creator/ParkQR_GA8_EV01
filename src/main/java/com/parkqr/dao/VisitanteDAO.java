
package com.parkqr.dao;

import com.parkqr.model.Visitante;
import com.parkqr.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VisitanteDAO {

    // Registrar visitante
    public boolean registrar(Visitante visitante) {

        String sql = "INSERT INTO visitantes " +
                "(nombre, documento, telefono, fecha_registro, id_apartamento) " +
                "VALUES (?, ?, ?, NOW(), ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, visitante.getNombre());
            ps.setString(2, visitante.getDocumento());
            ps.setString(3, visitante.getTelefono());
            ps.setInt(4, visitante.getIdApartamento());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al registrar visitante: " + e.getMessage());
            return false;
        }
    }

    // Listar visitantes
    public List<Visitante> listar() {

        List<Visitante> visitantes = new ArrayList<>();

        String sql = "SELECT * FROM visitantes ORDER BY id_visitante DESC";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Visitante visitante = new Visitante();

                visitante.setIdVisitante(rs.getInt("id_visitante"));
                visitante.setNombre(rs.getString("nombre"));
                visitante.setDocumento(rs.getString("documento"));
                visitante.setTelefono(rs.getString("telefono"));
                visitante.setIdApartamento(rs.getInt("id_apartamento"));

                visitantes.add(visitante);
            }

        } catch (Exception e) {
            System.out.println("Error al listar visitantes: " + e.getMessage());
        }

        return visitantes;
    }
}
