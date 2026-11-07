
package com.parkqr.dao;

import com.parkqr.model.VisitanteActivo;
import com.parkqr.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VisitanteActivoDAO {

    public List<VisitanteActivo> listarActivos() {

        List<VisitanteActivo> visitantes = new ArrayList<>();

        String sql =
                "SELECT " +
                        "v.nombre, " +
                        "vv.placa, " +
                        "vv.tipo, " +
                        "vv.marca, " +
                        "vv.color, " +
                        "a.torre, " +
                        "a.piso, " +
                        "a.numero, " +
                        "m.fecha_entrada, " +
                        "TIMESTAMPDIFF(MINUTE, m.fecha_entrada, NOW()) AS minutos_dentro, " +
                        "COALESCE(SUM(c.valor), 0) AS total_cobrado, " +
                        "COALESCE(SUM(CASE WHEN c.estado = 'PENDIENTE' " +
                        "THEN c.valor ELSE 0 END), 0) AS cobro_pendiente, " +
                        "CASE " +
                        "WHEN SUM(CASE WHEN c.estado = 'PENDIENTE' THEN 1 ELSE 0 END) > 0 " +
                        "THEN 'PENDIENTE' " +
                        "WHEN SUM(CASE WHEN c.estado = 'PAGADO' THEN 1 ELSE 0 END) > 0 " +
                        "THEN 'PAGADO' " +
                        "ELSE 'SIN COBRO' END AS estado_cobro " +
                        "FROM movimientos m " +
                        "INNER JOIN vehiculos_visitantes vv " +
                        "ON m.id_vehiculo_visitante = vv.id_vehiculo_visitante " +
                        "INNER JOIN visitantes v " +
                        "ON vv.id_visitante = v.id_visitante " +
                        "INNER JOIN apartamentos a " +
                        "ON v.id_apartamento = a.id_apartamento " +
                        "LEFT JOIN cobros c " +
                        "ON m.id_movimiento = c.id_movimiento " +
                        "WHERE m.estado = 'DENTRO' " +
                        "AND m.id_vehiculo_visitante IS NOT NULL " +
                        "GROUP BY m.id_movimiento, v.nombre, vv.placa, " +
                        "vv.tipo, vv.marca, vv.color, a.torre, a.piso, " +
                        "a.numero, m.fecha_entrada " +
                        "ORDER BY m.fecha_entrada ASC";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                VisitanteActivo visitante = new VisitanteActivo();

                visitante.setNombre(rs.getString("nombre"));
                visitante.setPlaca(rs.getString("placa"));
                visitante.setTipoVehiculo(rs.getString("tipo"));
                visitante.setMarca(rs.getString("marca"));
                visitante.setColor(rs.getString("color"));

                visitante.setTorre(rs.getInt("torre"));
                visitante.setPiso(rs.getInt("piso"));
                visitante.setNumeroApartamento(rs.getInt("numero"));

                visitante.setFechaEntrada(rs.getTimestamp("fecha_entrada"));
                visitante.setMinutosDentro(rs.getLong("minutos_dentro"));

                visitante.setTotalCobrado(rs.getDouble("total_cobrado"));

                visitante.setCobroPendiente(
                        rs.getDouble("cobro_pendiente")
                );

                visitante.setEstadoCobro(rs.getString("estado_cobro"));

                visitantes.add(visitante);
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al consultar visitantes activos: " + e.getMessage()
            );
        }

        return visitantes;
    }
}
