package com.parkqr.util;

import java.sql.Connection;

public class ProbarConexion {

    public static void main(String[] args) {

        Connection conexion = ConexionBD.conectar();

        if (conexion != null) {
            System.out.println("ParkQR está conectado a MySQL.");

            try {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("No fue posible conectar con MySQL.");
        }
    }
}