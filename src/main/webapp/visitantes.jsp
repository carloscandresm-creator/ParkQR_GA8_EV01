
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.parkqr.model.Visitante" %>
<%@ page import="com.parkqr.model.Apartamento" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ParkQR | Gestión de visitantes</title>
    <link rel="stylesheet" href="css/visitantes.css">
</head>
<body>

<main class="contenedor">

    <header>
        <h1>Gestión de visitantes</h1>
        <p>Registra y consulta los visitantes del conjunto residencial.</p>
        <a href="dashboard.html" class="boton-volver">
            Volver al panel principal
        </a>
    </header>

    <section class="panel">

        <h2>Registrar visitante</h2>

        <form action="visitantes" method="POST">

            <div class="campos">

                <div>
                    <label for="nombre">Nombre completo</label>
                    <input type="text" id="nombre" name="nombre"
                           maxlength="100" required>
                </div>

                <div>
                    <label for="documento">Documento de identidad</label>
                    <input type="text" id="documento" name="documento"
                           maxlength="30" required>
                </div>

                <div>
                    <label for="telefono">Teléfono</label>
                    <input type="tel" id="telefono" name="telefono"
                           maxlength="20">
                </div>

                <div>
                    <label for="idApartamento">Apartamento que visita</label>

                    <select id="idApartamento" name="idApartamento" required>
                        <option value="">Seleccionar apartamento</option>

                        <%
                            List<Apartamento> apartamentos =
                                (List<Apartamento>) request.getAttribute("apartamentos");

                            if (apartamentos != null) {
                                for (Apartamento a : apartamentos) {
                        %>

                            <option value="<%= a.getIdApartamento() %>">
                                Torre <%= a.getTorre() %> -
                                Piso <%= a.getPiso() %> -
                                Apartamento <%= a.getNumero() %>
                            </option>

                        <%
                                }
                            }
                        %>

                    </select>
                </div>

            </div>

            <button type="submit">Registrar visitante</button>

        </form>

        <%
            String registro = request.getParameter("registro");
            String error = request.getParameter("error");

            if ("exitoso".equals(registro)) {
        %>
            <p class="exito">Visitante registrado correctamente.</p>
        <%
            } else if (error != null) {
        %>
            <p class="error">
                No fue posible registrar el visitante. Verifica los datos.
            </p>
        <%
            }
        %>

    </section>

    <section class="panel">

        <h2>Visitantes registrados</h2>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Documento</th>
                    <th>Teléfono</th>
                    <th>Apartamento que visita</th>
                </tr>
            </thead>

            <tbody>

                <%
                    List<Visitante> visitantes =
                        (List<Visitante>) request.getAttribute("visitantes");

                    if (visitantes != null && !visitantes.isEmpty()) {
                        for (Visitante v : visitantes) {
                %>

                    <tr>
                        <td><%= v.getIdVisitante() %></td>
                        <td><%= v.getNombre() %></td>
                        <td><%= v.getDocumento() %></td>
                        <td><%= v.getTelefono() %></td>

                        <td>
                            <%
                                boolean encontrado = false;

                                if (apartamentos != null) {
                                    for (Apartamento a : apartamentos) {
                                        if (a.getIdApartamento() ==
                                                v.getIdApartamento()) {
                            %>
                                Torre <%= a.getTorre() %> -
                                Piso <%= a.getPiso() %> -
                                Apartamento <%= a.getNumero() %>
                            <%
                                            encontrado = true;
                                            break;
                                        }
                                    }
                                }

                                if (!encontrado) {
                            %>
                                Sin apartamento
                            <%
                                }
                            %>
                        </td>
                    </tr>

                <%
                        }
                    } else {
                %>

                    <tr>
                        <td colspan="5">
                            No hay visitantes registrados.
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
