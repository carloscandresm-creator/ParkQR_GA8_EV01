
package com.parkqr.dao;

import com.parkqr.model.Vehiculo;
import com.parkqr.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    // Registrar un vehículo
    public boolean registrar(Vehiculo vehiculo) {

        String sql = "INSERT INTO vehiculos " +
                "(placa, tipo, marca, modelo, color, id_apartamento) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return false;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {

                consulta.setString(1, vehiculo.getPlaca());
                consulta.setString(2, vehiculo.getTipo());
                consulta.setString(3, vehiculo.getMarca());
                consulta.setString(4, vehiculo.getModelo());
                consulta.setString(5, vehiculo.getColor());
                consulta.setInt(6, vehiculo.getIdApartamento());

                return consulta.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar vehículo:");
            e.printStackTrace();
            return false;
        }
    }

    // Consultar todos los vehículos
    public List<Vehiculo> listar() {

        List<Vehiculo> vehiculos = new ArrayList<>();

        String sql = "SELECT * FROM vehiculos ORDER BY id_vehiculo DESC";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return vehiculos;
            }

            try (PreparedStatement consulta = conexion.prepareStatement(sql);
                 ResultSet resultado = consulta.executeQuery()) {

                while (resultado.next()) {

                    Vehiculo vehiculo = new Vehiculo();

                    vehiculo.setIdVehiculo(resultado.getInt("id_vehiculo"));
                    vehiculo.setPlaca(resultado.getString("placa"));
                    vehiculo.setTipo(resultado.getString("tipo"));
                    vehiculo.setMarca(resultado.getString("marca"));
                    vehiculo.setModelo(resultado.getString("modelo"));
                    vehiculo.setColor(resultado.getString("color"));
                    vehiculo.setIdApartamento(resultado.getInt("id_apartamento"));

                    vehiculos.add(vehiculo);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar vehículos:");
            e.printStackTrace();
        }

        return vehiculos;
    }
}
