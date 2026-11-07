package com.parkqr.util;

import org.mindrot.jbcrypt.BCrypt;

public class Seguridad {

    // Generar hash de una contraseña
    public static String generarHash(String contrasena) {
        return BCrypt.hashpw(contrasena, BCrypt.gensalt(12));
    }

    // Verificar una contraseña
    public static boolean verificarContrasena(
            String contrasena,
            String hash) {

        return BCrypt.checkpw(contrasena, hash);
    }

    public static void main(String[] args) {

        String hash = generarHash("Admin123");

        System.out.println("Hash generado:");
        System.out.println(hash);

        System.out.println("Verificación: "
                + verificarContrasena("Admin123", hash));
    }
}