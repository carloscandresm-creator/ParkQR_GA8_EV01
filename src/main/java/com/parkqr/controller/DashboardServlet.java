
package com.parkqr.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sesion = request.getSession(false);

        if (sesion == null || sesion.getAttribute("rol") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/index.html"
            );
            return;
        }

        String rol = (String) sesion.getAttribute("rol");

        switch (rol) {

            case "ADMINISTRADOR":
                request.getRequestDispatcher("/dashboard-admin.jsp")
                        .forward(request, response);
                break;

            case "VIGILANTE":
                request.getRequestDispatcher("/dashboard-vigilante.jsp")
                        .forward(request, response);
                break;

            case "RESIDENTE":
                request.getRequestDispatcher("/dashboard-residente.jsp")
                        .forward(request, response);
                break;

            default:
                sesion.invalidate();
                response.sendRedirect(
                        request.getContextPath() + "/index.html"
                );
                break;
        }
    }
}
