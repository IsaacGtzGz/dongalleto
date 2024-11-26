// Función para verificar si el usuario está autenticado al cargar la página
function checkAuth() {
    var token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "index.html"; // Redirigir al inicio de sesión si no hay token
    } else {
        fetch(`/GSucursales/api/login/validarToken?token=${token}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error en la solicitud al servidor');
                    }
                    return response.json();
                })
                .then(data => {
                    if (!data.valid) {
                        localStorage.removeItem("token"); // Eliminar el token no válido del localStorage
                        window.location.href = "index.html"; // Redirigir al inicio de sesión
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    // Si hay un error en la solicitud al servidor, mantener al usuario en el dashboard
                });
    }
}

function sucursales() {
    window.location.href = "sucursales.html"; // Redirigir al inicio de sesión
}

// Función para cerrar sesión
function logout() {
    localStorage.removeItem("token");  // Elimina el token de sesión
    window.location.href = "index.html";  // Redirige al login
}

