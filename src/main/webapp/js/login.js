const formulario = document.getElementById("loginForm");
const mensaje = document.getElementById("mensaje");

formulario.addEventListener("submit", function(event) {

    const usuario = document.getElementById("usuario").value.trim();
    const password = document.getElementById("password").value;

    if (usuario === "" || password === "") {
        event.preventDefault();

        mensaje.textContent = "Completa todos los campos.";
        mensaje.style.color = "red";
        return;
    }

    mensaje.textContent = "Verificando datos...";
    mensaje.style.color = "green";

});