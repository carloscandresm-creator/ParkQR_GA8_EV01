
<%@ page import="java.util.List" %>
<%@ page import="com.parkqr.model.Apartamento" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ParkQR | Apartamentos</title>
    <link rel="stylesheet" href="css/apartamentos.css">
</head>
<body>

<div class="contenedor">

    <header>
        <h1>Gestión de apartamentos</h1>
        <p>Administra las torres, pisos y apartamentos del conjunto residencial.</p>
        <a href="dashboard.html" class="volver">Volver al panel principal</a>
    </header>

    <section class="formulario">
        <h2>Registrar apartamento</h2>

        <form action="apartamentos" method="POST">

            <div class="campos">
                <div>
                    <label for="torre">Torre</label>
                    <input type="number" id="torre" name="torre"
                           min="1" required>
                </div>

                <div>
                    <label for="piso">Piso</label>
                    <input type="number" id="piso" name="piso"
                           min="1" max="16" required>
                </div>

                <div>
                    <label for="numero">Número de apartamento</label>
                    <input type="number" id="numero" name="numero"
                           min="1" required>
                </div>
            </div>

            <button type="submit">Registrar apartamento</button>
        </form>

        <% if ("exitoso".equals(request.getParameter("registro"))) { %>
            <p class="exito">Apartamento registrado correctamente.</p>
        <% } %>

        <% if (request.getParameter("error") != null) { %>
            <p class="error">No fue posible registrar el apartamento. Verifica los datos o si ya existe.</p>
        <% } %>
    </section>

    <section class="tabla">
        <h2>Apartamentos registrados</h2>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Torre</th>
                    <th>Piso</th>
                    <th>Apartamento</th>
                </tr>
            </thead>

            <tbody>
                <%
                    List<Apartamento> apartamentos =
                        (List<Apartamento>) request.getAttribute("apartamentos");

                    if (apartamentos != null && !apartamentos.isEmpty()) {
                        for (Apartamento apartamento : apartamentos) {
                %>
                    <tr>
                        <td><%= apartamento.getIdApartamento() %></td>
                        <td><%= apartamento.getTorre() %></td>
                        <td><%= apartamento.getPiso() %></td>
                        <td><%= apartamento.getNumero() %></td>
                    </tr>
                <%
                        }
                    } else {
                %>
                    <tr>
                        <td colspan="4">No hay apartamentos registrados.</td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </section>

</div>

</body>
</html>
