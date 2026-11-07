
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.parkqr.model.Vehiculo" %>
<%@ page import="com.parkqr.model.Apartamento" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ParkQR | Gestión de vehículos</title>
    <link rel="stylesheet" href="css/vehiculos.css">
</head>
<body>

<main class="contenedor">

    <header>
        <h1>Gestión de vehículos</h1>
        <p>Registra y administra los vehículos del conjunto residencial.</p>
        <a href="dashboard.html" class="boton-volver">Volver al panel principal</a>
    </header>

    <section class="panel">
        <h2>Registrar vehículo</h2>

        <form action="vehiculos" method="POST">

            <div class="campos">

                <div>
                    <label for="placa">Placa</label>
                    <input type="text" id="placa" name="placa"
                           maxlength="10" required>
                </div>

                <div>
                    <label for="tipo">Tipo de vehículo</label>
                    <select id="tipo" name="tipo" required>
                        <option value="">Seleccionar</option>
                        <option value="CARRO">Carro</option>
                        <option value="MOTOCICLETA">Motocicleta</option>
                    </select>
                </div>

                <div>
                    <label for="marca">Marca</label>
                    <input type="text" id="marca" name="marca">
                </div>

                <div>
                    <label for="modelo">Modelo</label>
                    <input type="text" id="modelo" name="modelo">
                </div>

                <div>
                    <label for="color">Color</label>
                    <input type="text" id="color" name="color">
                </div>

                <div>
                    <label for="idApartamento">Apartamento</label>

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

            <button type="submit">Registrar vehículo</button>

        </form>

        <%
            String registro = request.getParameter("registro");
            String error = request.getParameter("error");

            if ("exitoso".equals(registro)) {
        %>
            <p class="exito">Vehículo registrado correctamente.</p>
        <%
            } else if (error != null) {
        %>
            <p class="error">No fue posible registrar el vehículo. Verifica los datos.</p>
        <%
            }
        %>

    </section>

    <section class="panel">

        <h2>Vehículos registrados</h2>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Placa</th>
                    <th>Tipo</th>
                    <th>Marca</th>
                    <th>Modelo</th>
                    <th>Color</th>
                    <th>Apartamento</th>
                </tr>
            </thead>

            <tbody>

                <%
                    List<Vehiculo> vehiculos =
                        (List<Vehiculo>) request.getAttribute("vehiculos");

                    if (vehiculos != null && !vehiculos.isEmpty()) {
                        for (Vehiculo v : vehiculos) {
                %>

                    <tr>
                        <td><%= v.getIdVehiculo() %></td>
                        <td><%= v.getPlaca() %></td>
                        <td><%= v.getTipo() %></td>
                        <td><%= v.getMarca() %></td>
                        <td><%= v.getModelo() %></td>
                        <td><%= v.getColor() %></td>
                       <td>
                           <%
                               List<Apartamento> listaApartamentos =
                                   (List<Apartamento>) request.getAttribute("apartamentos");

                               boolean encontrado = false;

                               if (listaApartamentos != null) {
                                   for (Apartamento a : listaApartamentos) {
                                       if (a.getIdApartamento() == v.getIdApartamento()) {
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
                        <td colspan="7">No hay vehículos registrados.</td>
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
