
package com.parkqr.dao;

import com.parkqr.model.Apartamento;
import com.parkqr.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApartamentoDAO {

    // Registrar un apartamento
    public boolean registrar(Apartamento apartamento) {

        String sql = "INSERT INTO apartamentos (torre, piso, numero) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return false;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

                consulta.setInt(1, apartamento.getTorre());
                consulta.setInt(2, apartamento.getPiso());
                consulta.setInt(3, apartamento.getNumero());

                return consulta.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar apartamento:");
            e.printStackTrace();
            return false;
        }
    }

    // Consultar todos los apartamentos
    public List<Apartamento> listar() {

        List<Apartamento> apartamentos = new ArrayList<>();

        String sql = "SELECT * FROM apartamentos ORDER BY torre, piso, numero";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return apartamentos;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql);
                 ResultSet resultado = consulta.executeQuery()) {

                while (resultado.next()) {

                    Apartamento apartamento = new Apartamento();

                    apartamento.setIdApartamento(
                            resultado.getInt("id_apartamento"));

                    apartamento.setTorre(resultado.getInt("torre"));
                    apartamento.setPiso(resultado.getInt("piso"));
                    apartamento.setNumero(resultado.getInt("numero"));

                    apartamentos.add(apartamento);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar apartamentos:");
            e.printStackTrace();
        }

        return apartamentos;
    }
}
