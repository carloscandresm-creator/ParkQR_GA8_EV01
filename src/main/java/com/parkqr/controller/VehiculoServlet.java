
package com.parkqr.controller;

import com.parkqr.dao.VehiculoDAO;
import com.parkqr.model.Vehiculo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.List;

import com.parkqr.dao.ApartamentoDAO;
import com.parkqr.model.Apartamento;

@WebServlet("/vehiculos")
public class VehiculoServlet extends HttpServlet {

    private final VehiculoDAO vehiculoDAO = new VehiculoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        VehiculoDAO vehiculoDAO = new VehiculoDAO();
        ApartamentoDAO apartamentoDAO = new ApartamentoDAO();

        request.setAttribute("vehiculos", vehiculoDAO.listar());
        request.setAttribute("apartamentos", apartamentoDAO.listar());

        request.getRequestDispatcher("/vehiculos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            Vehiculo vehiculo = new Vehiculo();

            vehiculo.setPlaca(request.getParameter("placa").trim().toUpperCase());
            vehiculo.setTipo(request.getParameter("tipo"));
            vehiculo.setMarca(request.getParameter("marca"));
            vehiculo.setModelo(request.getParameter("modelo"));
            vehiculo.setColor(request.getParameter("color"));
            vehiculo.setIdApartamento(
                    Integer.parseInt(request.getParameter("idApartamento"))
            );

            boolean registrado = vehiculoDAO.registrar(vehiculo);

            if (registrado) {
                response.sendRedirect(
                        request.getContextPath() + "/vehiculos?registro=exitoso"
                );
            } else {
                response.sendRedirect(
                        request.getContextPath() + "/vehiculos?error=registro"
                );
            }

        } catch (NumberFormatException | NullPointerException e) {
            response.sendRedirect(
                    request.getContextPath() + "/vehiculos?error=datos"
            );
        }
    }
}
