
package com.parkqr.controller;

import com.parkqr.dao.VisitanteActivoDAO;
import com.parkqr.model.VisitanteActivo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/visitantes-admin")
public class VisitantesAdminServlet extends HttpServlet {

    private final VisitanteActivoDAO visitanteActivoDAO =
            new VisitanteActivoDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        // Verificar que exista una sesión de administrador
        if (sesion == null ||
                !"ADMINISTRADOR".equals(sesion.getAttribute("rol"))) {

            response.sendRedirect(
                    request.getContextPath() + "/index.html"
            );
            return;
        }

        // Consultar visitantes activos
        List<VisitanteActivo> visitantes =
                visitanteActivoDAO.listarActivos();

        request.setAttribute("visitantesActivos", visitantes);

        // Abrir pantalla de supervisión
        request.getRequestDispatcher("/visitantes-admin.jsp")
                .forward(request, response);
    }
}
