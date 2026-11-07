
package com.parkqr.controller;

import com.parkqr.dao.VisitanteDAO;
import com.parkqr.dao.ApartamentoDAO;
import com.parkqr.model.Visitante;
import com.parkqr.model.Apartamento;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/visitantes")
public class VisitanteServlet extends HttpServlet {

    private final VisitanteDAO visitanteDAO = new VisitanteDAO();
    private final ApartamentoDAO apartamentoDAO = new ApartamentoDAO();

    // Mostrar formulario y visitantes registrados
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Visitante> visitantes = visitanteDAO.listar();
        List<Apartamento> apartamentos = apartamentoDAO.listar();

        request.setAttribute("visitantes", visitantes);
        request.setAttribute("apartamentos", apartamentos);

        request.getRequestDispatcher("/visitantes.jsp")
                .forward(request, response);
    }

    // Registrar visitante
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            Visitante visitante = new Visitante();

            visitante.setNombre(request.getParameter("nombre"));
            visitante.setDocumento(request.getParameter("documento"));
            visitante.setTelefono(request.getParameter("telefono"));
            visitante.setIdApartamento(
                    Integer.parseInt(request.getParameter("idApartamento"))
            );

            boolean registrado = visitanteDAO.registrar(visitante);

            if (registrado) {
                response.sendRedirect(
                        request.getContextPath() + "/visitantes?registro=exitoso"
                );
            } else {
                response.sendRedirect(
                        request.getContextPath() + "/visitantes?error=1"
                );
            }

        } catch (Exception e) {
            System.out.println("Error en VisitanteServlet: " + e.getMessage());

            response.sendRedirect(
                    request.getContextPath() + "/visitantes?error=1"
            );
        }
    }
}
