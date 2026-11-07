
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.parkqr.model.Cobro" %>
<%@ page import="com.parkqr.model.Usuario" %>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    if (usuario == null || !"ADMINISTRADOR".equals(usuario.getRol())) {
        response.sendRedirect("index.html");
        return;
    }

    List<Cobro> listaCobros =
        (List<Cobro>) request.getAttribute("listaCobros");

    SimpleDateFormat formato =
        new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Cobros - ParkQR</title>
    <link rel="stylesheet" href="css/cobros.css">
</head>
<body>

<div class="contenedor">

    <h1>Gestión de cobros</h1>

    <p>Consulta las deudas, los vencimientos y los pagos recibidos.</p>

    <a class="volver" href="dashboard">Volver al panel principal</a>

    <div class="tabla-contenedor">

        <table>
            <thead>
            <tr>
                <th>ID cobro</th>
                <th>Apartamento</th>
                <th>Valor</th>
                <th>Motivo</th>
                <th>Fecha de cobro</th>
                <th>Fecha de vencimiento</th>
                <th>Estado</th>
                <th>Fecha de pago</th>
                <th>Usuario que recibió</th>
            </tr>
            </thead>

            <tbody>

            <% if (listaCobros != null && !listaCobros.isEmpty()) {
                for (Cobro cobro : listaCobros) {

                    boolean vencido =
                        "PENDIENTE".equals(cobro.getEstado())
                        && cobro.getFechaVencimiento() != null
                        && cobro.getFechaVencimiento().before(
                            new java.sql.Timestamp(System.currentTimeMillis())
                        );
            %>

            <tr>
                <td><%= cobro.getIdCobro() %></td>

                <td>
                    Torre <%= cobro.getTorre() %> -
                    Piso <%= cobro.getPiso() %> -
                    Apto. <%= cobro.getNumeroApartamento() %>
                </td>

                <td>$<%= String.format("%,.0f", cobro.getValor()) %></td>

                <td><%= cobro.getMotivo() %></td>

                <td>
                    <%= cobro.getFechaCobro() != null
                        ? formato.format(cobro.getFechaCobro()) : "-" %>
                </td>

                <td>
                    <%= cobro.getFechaVencimiento() != null
                        ? formato.format(cobro.getFechaVencimiento()) : "-" %>
                </td>

                <td>
                    <%= vencido ? "VENCIDO" : cobro.getEstado() %>
                </td>

                <td>
                    <%= cobro.getFechaPago() != null
                        ? formato.format(cobro.getFechaPago()) : "-" %>
                </td>

                <td>
                    <%= cobro.getNombreUsuarioRecibe() != null
                        ? cobro.getNombreUsuarioRecibe() : "-" %>
                </td>
            </tr>

            <% } } else { %>

            <tr>
                <td colspan="9">No hay cobros registrados.</td>
            </tr>

            <% } %>

            </tbody>
        </table>

    </div>

</div>

</body>
</html>
