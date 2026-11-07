
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

@WebServlet("/cobros")
public class CobrosServlet extends HttpServlet {

    private final CobroDAO cobroDAO = new CobroDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/index.html");
            return;
        }

        Usuario usuario = (Usuario) sesion.getAttribute("usuario");

        if (!"ADMINISTRADOR".equals(usuario.getRol())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permiso para consultar los cobros.");
            return;
        }

        request.setAttribute("listaCobros", cobroDAO.listarCobros());

        request.getRequestDispatcher("/cobros.jsp")
                .forward(request, response);
    }
}
