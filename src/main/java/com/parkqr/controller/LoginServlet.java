package com.parkqr.controller;

import com.parkqr.dao.UsuarioDAO;
import com.parkqr.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String usuario = request.getParameter("usuario");
        String contrasena = request.getParameter("password");

        Usuario usuarioAutenticado =
                usuarioDAO.autenticar(usuario, contrasena);

        if (usuarioAutenticado != null) {

            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuarioAutenticado);
            sesion.setAttribute("rol", usuarioAutenticado.getRol());

            response.sendRedirect(
                    request.getContextPath() + "/dashboard"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath() + "/index.html?error=1"
            );
        }
    }
}