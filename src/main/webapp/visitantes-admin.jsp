
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.parkqr.model.VisitanteActivo" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%
    if (!"ADMINISTRADOR".equals(session.getAttribute("rol"))) {
        response.sendRedirect("index.html");
        return;
    }

    List<VisitanteActivo> visitantes =
        (List<VisitanteActivo>) request.getAttribute("visitantesActivos");

    SimpleDateFormat formato =
        new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ParkQR | Visitantes activos</title>
    <link rel="stylesheet" href="css/visitantes.css">
</head>
<body>

<main class="contenedor">

    <header>
        <h1>Visitantes activos</h1>
        <p>Supervisión de vehículos visitantes dentro del conjunto residencial.</p>

        <a href="dashboard" class="boton-volver">
            Volver al panel principal
        </a>
    </header>

    <section class="panel">

        <h2>Control de visitantes</h2>

        <table>
            <thead>
                <tr>
                    <th>Visitante</th>
                    <th>Placa</th>
                    <th>Tipo de vehículo</th>
                    <th>Marca</th>
                    <th>Apartamento que visita</th>
                    <th>Hora de ingreso</th>
                    <th>Tiempo dentro</th>
                    <th>Cobro generado</th>
                    <th>Pendiente</th>
                    <th>Estado del cobro</th>
                </tr>
            </thead>

            <tbody>

            <%
                if (visitantes != null && !visitantes.isEmpty()) {
                    for (VisitanteActivo v : visitantes) {

                        long horas = v.getMinutosDentro() / 60;
                        long minutos = v.getMinutosDentro() % 60;
            %>

                <tr>
                    <td><%= v.getNombre() %></td>
                    <td><%= v.getPlaca() %></td>
                    <td><%= v.getTipoVehiculo() %></td>
                    <td><%= v.getMarca() %></td>

                    <td>
                        Torre <%= v.getTorre() %> -
                        Piso <%= v.getPiso() %> -
                        Apartamento <%= v.getNumeroApartamento() %>
                    </td>

                    <td><%= formato.format(v.getFechaEntrada()) %></td>

                    <td><%= horas %> h <%= minutos %> min</td>

                    <td>
                        $<%= String.format("%,.0f", v.getTotalCobrado()) %>
                    </td>

                    <td>
                        $<%= String.format("%,.0f", v.getCobroPendiente()) %>
                    </td>

                    <td><%= v.getEstadoCobro() %></td>
                </tr>

            <%
                    }
                } else {
            %>

                <tr>
                    <td colspan="10">
                        No hay vehículos visitantes dentro del conjunto.
                    </td>
                </tr>

            <%
                }
            %>

            </tbody>
        </table>

    </section>

</main>

</body>
</html>
