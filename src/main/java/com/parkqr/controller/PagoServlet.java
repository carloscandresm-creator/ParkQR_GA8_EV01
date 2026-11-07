
package com.parkqr.controller;

import com.parkqr.dao.CobroDAO;
import com.parkqr.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/pagar-cobro")
public class PagoServlet extends HttpServlet {

    private final CobroDAO cobroDAO = new CobroDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession sesion = request.getSession(false);

        // Comprobar que exista una sesión
        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }

        // Obtener el usuario autenticado
        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        int idUsuario = usuario.getIdUsuario();
        String rol = usuario.getRol();

        // Validar el rol
        if (!rol.equals("VIGILANTE") && !rol.equals("ADMINISTRADOR")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permiso para registrar pagos.");
            return;
        }

        try {
            int idCobro = Integer.parseInt(
                    request.getParameter("idCobro"));

            if (idCobro <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Registrar el pago con el usuario autenticado
            boolean resultado = cobroDAO.registrarPago(
                    idCobro,
                    idUsuario,
                    rol
            );

            if (resultado) {
                response.sendRedirect(
                        request.getContextPath() +
                                "/dashboard?mensaje=pago_exitoso"
                );
            } else {
                response.sendRedirect(
                        request.getContextPath() +
                                "/dashboard?mensaje=pago_no_registrado"
                );
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "El identificador del cobro no es válido.");
        }
    }
}
