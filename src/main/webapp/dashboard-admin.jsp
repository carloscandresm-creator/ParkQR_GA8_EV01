
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.parkqr.model.Usuario" %>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    if (usuario == null ||
        !"ADMINISTRADOR".equals(usuario.getRol())) {

        response.sendRedirect("index.html");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ParkQR | Panel del administrador</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body>

<div class="sidebar">

    <div class="brand">
        <h2>ParkQR</h2>
        <span>Gestión de parqueaderos</span>
    </div>

    <nav>
        <a class="active" href="dashboard">Inicio</a>
        <a href="apartamentos">Apartamentos</a>
        <a href="vehiculos">Vehículos</a>
        <a href="visitantes-admin">Visitantes activos</a>
        <a href="#">Ingresos y salidas</a>
        <a href="cobros">Cobros</a>
        <a href="#">Reportes</a>
        <a href="#">Usuarios</a>
    </nav>

    <a class="logout" href="logout">Cerrar sesión</a>

</div>

<main class="content">

    <header>
        <div>
            <h1>Panel del administrador</h1>
            <p>Supervisión y control del parqueadero residencial</p>
        </div>

        <div class="user-info">
            Administrador: <%= usuario.getUsuario() %>
        </div>
    </header>

    <section class="cards">

        <article class="card">
            <span>Vehículos registrados</span>
            <h2>0</h2>
            <p>Total de vehículos</p>
        </article>

        <article class="card">
            <span>Visitantes activos</span>
            <h2>0</h2>
            <p>Vehículos visitantes dentro</p>
        </article>

        <article class="card">
            <span>Vehículos dentro</span>
            <h2>0</h2>
            <p>Actualmente en el parqueadero</p>
        </article>

        <article class="card">
            <span>Cobros pendientes</span>
            <h2>$0</h2>
            <p>Valor pendiente de pago</p>
        </article>

    </section>

    <section class="welcome">
        <h2>Control de parqueadero</h2>

        <p>
            Desde este panel puedes supervisar los vehículos,
            consultar los visitantes activos, revisar sus tiempos
            de permanencia y controlar los cobros generados.
        </p>
    </section>

</main>

</body>
</html>
