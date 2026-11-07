package com.parkqr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/parkqr_db?useSSL=false&serverTimezone=America/Bogota&allowPublicKeyRetrieval=true";

    private static final String USUARIO = "root";

    // Escribe aquí la contraseña que configuraste en MySQL
    private static final String CONTRASENA = "contraseña mysql!";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conexion = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    CONTRASENA
            );

            System.out.println("Conexión a ParkQR exitosa.");

            return conexion;

        } catch (ClassNotFoundException | SQLException e) {

            System.out.println("Error al conectar con la base de datos.");
            e.printStackTrace();

            return null;
        }
    }
}