
package com.parkqr.dao;

import com.parkqr.model.Cobro;
import com.parkqr.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CobroDAO {

    // Registrar un cobro manual con vencimiento a 7 días
    public boolean registrar(Cobro cobro) {

        String sql = "INSERT INTO cobros " +
                "(id_movimiento, valor, motivo, fecha_cobro, fecha_vencimiento, estado) " +
                "VALUES (?, ?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), 'PENDIENTE')";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {

                ps.setInt(1, cobro.getIdMovimiento());
                ps.setDouble(2, cobro.getValor());
                ps.setString(3, cobro.getMotivo());

                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {
            System.out.println("Error al registrar cobro: " + e.getMessage());
            return false;
        }
    }

    // Registrar el pago según el rol y la fecha de vencimiento
    public boolean registrarPago(int idCobro, int idUsuarioRecibe,
                                 String rolUsuario) {

        String sql = "UPDATE cobros SET " +
                "estado = 'PAGADO', " +
                "fecha_pago = NOW(), " +
                "id_usuario_recibe = ? " +
                "WHERE id_cobro = ? " +
                "AND estado = 'PENDIENTE' " +
                "AND ( " +
                "   ( ? = 'VIGILANTE' AND fecha_vencimiento >= NOW() ) " +
                "   OR " +
                "   ( ? = 'ADMINISTRADOR' AND fecha_vencimiento < NOW() ) " +
                ")";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return false;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {

                ps.setInt(1, idUsuarioRecibe);
                ps.setInt(2, idCobro);
                ps.setString(3, rolUsuario);
                ps.setString(4, rolUsuario);

                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {
            System.out.println("Error al registrar pago: " + e.getMessage());
            return false;
        }
    }

    // Actualizar automáticamente los cobros de visitantes
    public void actualizarCobrosAutomaticos() {

        String sql = """
            SELECT
                m.id_movimiento,
                CASE
                    WHEN TIME(m.fecha_entrada) >= '21:00:00'
                    THEN m.fecha_entrada

                    WHEN DATE_ADD(m.fecha_entrada, INTERVAL 6 HOUR)
                         <= TIMESTAMP(DATE(m.fecha_entrada), '21:00:00')
                    THEN DATE_ADD(m.fecha_entrada, INTERVAL 6 HOUR)

                    ELSE TIMESTAMP(DATE(m.fecha_entrada), '21:00:00')
                END AS inicio_cobro

            FROM movimientos m

            WHERE m.estado = 'DENTRO'
              AND m.id_vehiculo_visitante IS NOT NULL

              AND NOW() > CASE
                    WHEN TIME(m.fecha_entrada) >= '21:00:00'
                    THEN m.fecha_entrada

                    WHEN DATE_ADD(m.fecha_entrada, INTERVAL 6 HOUR)
                         <= TIMESTAMP(DATE(m.fecha_entrada), '21:00:00')
                    THEN DATE_ADD(m.fecha_entrada, INTERVAL 6 HOUR)

                    ELSE TIMESTAMP(DATE(m.fecha_entrada), '21:00:00')
              END
            """;

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    int idMovimiento =
                            rs.getInt("id_movimiento");

                    Timestamp inicioCobro =
                            rs.getTimestamp("inicio_cobro");

                    if (inicioCobro == null) {
                        continue;
                    }

                    long minutosTranscurridos =
                            (System.currentTimeMillis()
                                    - inicioCobro.getTime()) / 60000;

                    long horasAdicionales =
                            Math.max(0, minutosTranscurridos / 60);

                    double valor =
                            5000 + (horasAdicionales * 500);

                    String sqlCobro = """
                        INSERT INTO cobros
                        (
                            id_movimiento,
                            valor,
                            motivo,
                            fecha_cobro,
                            fecha_vencimiento,
                            estado
                        )
                        VALUES (
                            ?, ?,
                            'Estacionamiento de visitante',
                            NOW(),
                            DATE_ADD(NOW(), INTERVAL 7 DAY),
                            'PENDIENTE'
                        )
                        ON DUPLICATE KEY UPDATE
                            valor = IF(
                                estado = 'PENDIENTE',
                                GREATEST(valor, VALUES(valor)),
                                valor
                            )
                        """;

                    try (PreparedStatement psCobro =
                                 conexion.prepareStatement(sqlCobro)) {

                        psCobro.setInt(1, idMovimiento);
                        psCobro.setDouble(2, valor);

                        psCobro.executeUpdate();
                    }
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al actualizar cobros automáticos: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }
    }

    // Consultar cobros con apartamento y usuario receptor
    public List<Cobro> listarCobros() {

        List<Cobro> lista = new ArrayList<>();

        String sql = "SELECT c.id_cobro, c.id_movimiento, c.valor, " +
                "c.motivo, c.fecha_cobro, c.fecha_vencimiento, " +
                "c.estado, c.fecha_pago, c.id_usuario_recibe, " +
                "a.torre, a.piso, a.numero, " +
                "u.nombre AS nombre_usuario " +
                "FROM cobros c " +
                "INNER JOIN movimientos m " +
                "ON c.id_movimiento = m.id_movimiento " +
                "INNER JOIN vehiculos_visitantes vv " +
                "ON m.id_vehiculo_visitante = vv.id_vehiculo_visitante " +
                "INNER JOIN visitantes v " +
                "ON vv.id_visitante = v.id_visitante " +
                "INNER JOIN apartamentos a " +
                "ON v.id_apartamento = a.id_apartamento " +
                "LEFT JOIN usuarios u " +
                "ON c.id_usuario_recibe = u.id_usuario " +
                "ORDER BY c.fecha_cobro DESC";

        try (Connection conexion = ConexionBD.conectar()) {

            if (conexion == null) {
                return lista;
            }

            try (PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Cobro cobro = new Cobro();

                    cobro.setIdCobro(rs.getInt("id_cobro"));
                    cobro.setIdMovimiento(rs.getInt("id_movimiento"));
                    cobro.setValor(rs.getDouble("valor"));
                    cobro.setMotivo(rs.getString("motivo"));

                    cobro.setFechaCobro(
                            rs.getTimestamp("fecha_cobro"));

                    cobro.setFechaVencimiento(
                            rs.getTimestamp("fecha_vencimiento"));

                    cobro.setEstado(rs.getString("estado"));

                    cobro.setFechaPago(
                            rs.getTimestamp("fecha_pago"));

                    cobro.setIdUsuarioRecibe(
                            rs.getInt("id_usuario_recibe"));

                    cobro.setTorre(rs.getInt("torre"));
                    cobro.setPiso(rs.getInt("piso"));

                    cobro.setNumeroApartamento(
                            rs.getInt("numero"));

                    cobro.setNombreUsuarioRecibe(
                            rs.getString("nombre_usuario"));

                    lista.add(cobro);
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al consultar cobros: " + e.getMessage());

            e.printStackTrace();
        }

        return lista;
    }
}
