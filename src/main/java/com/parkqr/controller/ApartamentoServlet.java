
package com.parkqr.controller;

import com.parkqr.dao.ApartamentoDAO;
import com.parkqr.model.Apartamento;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/apartamentos")
public class ApartamentoServlet extends HttpServlet {

    private final ApartamentoDAO apartamentoDAO = new ApartamentoDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<Apartamento> apartamentos = apartamentoDAO.listar();

        request.setAttribute("apartamentos", apartamentos);

        request.getRequestDispatcher("/apartamentos.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            int torre = Integer.parseInt(request.getParameter("torre"));
            int piso = Integer.parseInt(request.getParameter("piso"));
            int numero = Integer.parseInt(request.getParameter("numero"));

            Apartamento apartamento = new Apartamento();
            apartamento.setTorre(torre);
            apartamento.setPiso(piso);
            apartamento.setNumero(numero);

            boolean registrado = apartamentoDAO.registrar(apartamento);

            if (registrado) {
                response.sendRedirect(
                        request.getContextPath() + "/apartamentos?registro=exitoso");
            } else {
                response.sendRedirect(
                        request.getContextPath() + "/apartamentos?error=registro");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(
                    request.getContextPath() + "/apartamentos?error=datos");
        }
    }
}
