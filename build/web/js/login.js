// Funciones JavaScript para manejar el inicio de sesión y la autenticación
function login() {
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    fetch(`http://localhost:8080/GSucursales/api/login/generarToken?user=${username}&password=${password}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al iniciar sesión');
                }
                return response.json();
            })
            .then(data => {
                if (data.token && data.token !== "null") {
                    localStorage.setItem("token", data.token); // Guardar el token en el localStorage
                    window.location.href = "home.html"; // Redirigir al home
                } else {
                    alert("Credenciales inválidas");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("Error al iniciar sesión");
            });
}

function checkAuth() {
    var token = localStorage.getItem("token");
    if (token && token !== "null") {
        window.location.href = "home.html";
    }
}

function validar() {
    let ruta = (`http://localhost:8080/GSucursales/api/login/generarToken`);
    let v_user = document.getElementById("user").value;
    let v_password = document.getElementById("password").value;
    let _datos = {
        user: v_user,
        password: v_password
    };
    const queryString = new URLSearchParams(_datos).toString();
    const requestOptions = {
        method: 'GET',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    };

    fetch(`${ruta}?${queryString}`, requestOptions)
            .then(function (response) {
                if (!response.ok) {
                    throw new Error('Error en la solicitud');
                }
                return response.json();
            })
            .then(function (data) {
                console.log(data);
                // Guardar el token en localStorage
                sessionStorage.setItem("token", data.token);
                window.location.href = "sucursales.html";
            })
            .catch(function (error) {
                console.error('Error en la solicitud:', error);
            });
}

