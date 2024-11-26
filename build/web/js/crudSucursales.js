//------------------------------------------------------------------------------
// Función para insertar una nueva sucursal.                           [Descripción de la función]
function insertarS() {
    let ruta = "http://localhost:8080/GSucursales/api/sucursal/insert";// Ruta del API para insertar la sucursal
    let _datos = {// Crear un objeto con los datos de la sucursal
        nombre: document.getElementById("txtnombre").value, // Obtener el valor del campo 'nombre'
        titular: document.getElementById("txttitular").value, // Obtener el valor del campo 'titular'
        rfc: document.getElementById("txtrfc").value, // Obtener el valor del campo 'rfc'
        domicilio: document.getElementById("txtDomicilio").value, // Obtener el valor del campo 'domicilio'
        colonia: document.getElementById("txtColonia").value, // Obtener el valor del campo 'colonia'
        codigoPostal: document.getElementById("txtcodigoPostal").value, // Obtener el valor del campo 'codigoPostal'
        ciudad: document.getElementById("txtCiudad").value, // Obtener el valor del campo 'ciudad'
        estado: document.getElementById("txtestado").value, // Obtener el valor del campo 'estado'
        telefono: document.getElementById("txttelefono").value, // Obtener el valor del campo 'telefono'
        latitud: document.getElementById("txtlatitud").value, // Obtener el valor del campo 'latitud'
        longitud: document.getElementById("txtlongitud").value, // Obtener el valor del campo 'longitud'
        estatus: document.getElementById("txtestatus").value// Obtener el valor del campo 'estatus'
    };

    if (!validardatosS())
        return;// Validar los datos antes de proceder

    const queryString = new URLSearchParams(_datos).toString();// Convertir los datos a un formato de consulta URL
    const requestOptions = {
        method: 'POST', // Usar método POST para insertar datos
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}, // Encabezados HTTP
        body: queryString// Cuerpo de la solicitud
    };

    fetch(ruta, requestOptions)// Hacer la solicitud al servidor
            .then(response => response.json())// Parsear la respuesta a JSON
            .then(() => {
                limpiarS();// Limpiar los campos del formulario
                alertexito();// Mostrar alerta de éxito
                CargarTablaS();// Cargar la tabla nuevamente con la sucursal insertada
            })
            .catch(error => console.error("Error al insertar la sucursal: " + error)); // Manejo de errores
}

//------------------------------------------------------------------------------
// Función para modificar una sucursal existente [Descripción de la función]
function modificarSucursal() {
    const idSucursal = document.getElementById("txtId").value;// Obtener el ID de la sucursal a modificar
    if (!idSucursal) {// Validar si se seleccionó una sucursal
        alert("Selecciona una sucursal para modificar.");
        return;
    }
    const data = {// Crear un objeto con los datos de la sucursal
        idSucursal: idSucursal,
        nombre: document.getElementById("txtnombre").value, // Obtener el valor del campo 'nombre'
        titular: document.getElementById("txttitular").value, // Obtener el valor del campo 'titular'
        rfc: document.getElementById("txtrfc").value, // Obtener el valor del campo 'rfc'
        domicilio: document.getElementById("txtDomicilio").value, // Obtener el valor del campo 'domicilio'
        colonia: document.getElementById("txtColonia").value, // Obtener el valor del campo 'colonia'
        codigoPostal: document.getElementById("txtcodigoPostal").value, // Obtener el valor del campo 'codigoPostal'
        ciudad: document.getElementById("txtCiudad").value, // Obtener el valor del campo 'ciudad'
        estado: document.getElementById("txtestado").value, // Obtener el valor del campo 'estado'
        telefono: document.getElementById("txttelefono").value, // Obtener el valor del campo 'telefono'
        latitud: document.getElementById("txtlatitud").value, // Obtener el valor del campo 'latitud'
        longitud: document.getElementById("txtlongitud").value, // Obtener el valor del campo 'longitud'
        estatus: document.getElementById("txtestatus").value                      // Obtener el valor del campo 'estatus'
    };

    const queryString = new URLSearchParams(data).toString();                     // Convertir los datos a un formato URL
    fetch("http://localhost:8080/GSucursales/api/sucursal/update", {// Hacer la solicitud de actualización
        method: 'PUT', // Usar método PUT para modificar los datos
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}, // Encabezados HTTP
        body: queryString                                                         // Cuerpo de la solicitud
    })
            .then(response => {
                if (!response.ok) {                                                   // Manejar error si la respuesta no es exitosa
                    throw new Error(`Error en la solicitud: ${response.statusText}`);
                }
                return response.json();                                               // Parsear la respuesta a JSON
            })
            .then(data => {
                if (data.message) {                                                   // Si hay mensaje, mostrar alerta de éxito
                    alertexito(data.message);
                    CargarTablaS();                                                   // Recargar la tabla
                    limpiarS();                                                       // Limpiar el formulario
                }
            })
            .catch(error => {                                                         // Manejo de errores
                console.error('Error:', error);
                alert(`Hubo un error al modificar la sucursal: ${error.message}`);
            });
}

//------------------------------------------------------------------------------
// Función para activar o inactivar una sucursal  [Descripción de la función]
function toggleSucursalStatus() {
    const idSucursal = document.getElementById("txtId").value;// Obtener el ID de la sucursal
    if (!idSucursal) {// Validar que se haya seleccionado una sucursal
        alert("Selecciona una sucursal para activar/inactivar.");
        return;
    }

    const currentStatus = document.getElementById("txtestatus").value;// Obtener el estado actual de la sucursal
    const newStatus = currentStatus === "Activo" ? "Inactivo" : "Activo";// Cambiar el estado según el actual

    const data = {// Crear un objeto con el nuevo estado
        idSucursal: idSucursal,
        estatus: newStatus// Cambiar estatus a Activo o Inactivo
    };

    const queryString = new URLSearchParams(data).toString(); // Convertir los datos a un formato URL
    fetch("http://localhost:8080/GSucursales/api/sucursal/actin", {// Hacer la solicitud de cambio de estado
        method: 'PUT', // Usar método PUT para modificar el estado
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}, // Encabezados HTTP
        body: queryString// Cuerpo de la solicitud
    })
            .then(response => {
                if (!response.ok) {// Manejar error si la respuesta no es exitosa
                    throw new Error(`Error en la solicitud: ${response.statusText}`);
                }
                return response.json();// Parsear la respuesta a JSON
            })
            .then(data => {
                if (data.message) {// Mostrar mensaje de éxito
                    alertexito(data.message);
                    CargarTablaS();                                                   // Recargar la tabla
                    limpiarS();                                                       // Limpiar el formulario
                }
            })
            .catch(error => {                                                         // Manejo de errores
                console.error('Error:', error);
                alert(`Hubo un error al cambiar el estado de la sucursal: ${error.message}`);
            });
}

//------------------------------------------------------------------------------
// Función para cargar todas las sucursales en la tabla.                 [Descripción de la función]
function CargarTablaS() {
    let ruta = "http://localhost:8080/GSucursales/api/sucursal/getall";// Ruta del API para obtener todas las sucursales

    fetch(ruta)// Hacer la solicitud al servidor
            .then(response => response.json())// Parsear la respuesta a JSON
            .then(data => {
                sucursales = data;// Almacenar los datos globalmente
                actualizarTabla(data);// Actualizar la tabla con los datos
            })
            .catch(error => console.error("Error al cargar la tabla: " + error));// Manejo de errores
}

//------------------------------------------------------------------------------
// Función para llenar los campos con los datos de una sucursal seleccionada
function llenarCamposS(fila) {
    // Llenar los campos con los datos de la sucursal seleccionada
    document.getElementById("txtId").value = fila.idSucursal;
    document.getElementById("txtnombre").value = fila.nombre;
    document.getElementById("txttitular").value = fila.titular;
    document.getElementById("txtrfc").value = fila.rfc;
    document.getElementById("txtDomicilio").value = fila.domicilio;
    document.getElementById("txtColonia").value = fila.colonia;
    document.getElementById("txtcodigoPostal").value = fila.codigoPostal;
    document.getElementById("txtCiudad").value = fila.ciudad;
    document.getElementById("txtestado").value = fila.estado;
    document.getElementById("txttelefono").value = fila.telefono;
    document.getElementById("txtlatitud").value = fila.latitud;
    document.getElementById("txtlongitud").value = fila.longitud;
    document.getElementById("txtestatus").value = fila.estatus;

    // Habilitar el botes
    document.getElementById("btnModificar").disabled = false;
    document.getElementById("btnToggleStatus").disabled = false;
    document.getElementById("btnToggleStatus").disabled = false; // Habilitar el botón de activar/inactivar
    cargarMapaConMarcador();
}


//------------------------------------------------------------------------------
function limpiarS() {
    // Limpiar todos los campos y deshabilitar el botón de modificar
    document.querySelectorAll("input").forEach(input => input.value = "");
    document.getElementById("btnModificar").disabled = true;
    document.getElementById("btnToggleStatus").disabled = true;
    CargarTablaS();
    cargarMapaConMarcador();
}

//------------------------------------------------------------------------------
function validardatosS() {
    const campos =
            [
                "txtnombre",
                "txttitular",
                "txtrfc",
                "txtDomicilio",
                "txtColonia",
                "txtCiudad",
                "txtestado",
                "txtcodigoPostal",
                "txttelefono",
                "txtlongitud",
                "txtlatitud"
            ];
    const vacio = campos.some(campo => document.getElementById(campo).value === "");
    if (vacio) {
        alertcampos();
        return false;
    }
    // Verificar que los campos numéricos contengan solo números
    const numerosInvalidos = ["txtcodigoPostal", "txttelefono", "txtlongitud", "txtlatitud"].some(campo =>
        isNaN(parseFloat(document.getElementById(campo).value))
    );
    if (numerosInvalidos) {
        alertnumeros();
        return false;
    }
    alertexito(); // Mostrar mensaje de éxito
    return true;
}

//------------------------------------------------------------------------------
// Función para actualizar la tabla con los resultados filtrados
function actualizarTabla(data) {
    const tablaRegistros = document.getElementById("tablaRegistros").getElementsByTagName('tbody')[0];
    tablaRegistros.innerHTML = "";  // Limpiar la tabla

    data.forEach(fila => {
        const nuevaFila = tablaRegistros.insertRow(-1);
        const campos = ['idSucursal', 'nombre', 'titular', 'rfc', 'domicilio', 'colonia',
            'codigoPostal', 'ciudad', 'estado', 'telefono', 'latitud', 'longitud', 'estatus'];

        campos.forEach((campo, index) => {
            const celda = nuevaFila.insertCell(index);
            celda.innerHTML = fila[campo];
        });

        // Asignar evento click para llenar los campos
        nuevaFila.addEventListener("click", () => llenarCamposS(fila));
    });
}

//------------------------------------------------------------------------------
// Función para buscar sucursales en la tabla de registros
function buscarS() {
    // Obtiene el valor ingresado por el usuario en el campo de búsqueda y lo convierte a minúsculas
    const searchTerm = document.getElementById("searchSuc").value.toLowerCase();
    // Filtra las sucursales almacenadas en la variable global `sucursales`
    // `sucursales` es un arreglo que contiene objetos con los datos de cada sucursal
    const sucursalFiltrada = sucursales.filter(sucur =>
        // Convierte los valores de cada propiedad de la sucursal a string y minúsculas
        // Verifica si alguna propiedad contiene el término de búsqueda
        Object.values(sucur).some(val => val.toString().toLowerCase().includes(searchTerm))
    );
    // Verifica si hay resultados de búsqueda
    if (sucursalFiltrada.length > 0) {
        // Si hay resultados, actualiza la tabla con las sucursales filtradas
        actualizarTabla(sucursalFiltrada);
    } else {
        // Si no se encuentran resultados, muestra una alerta con un mensaje de información
        swal({
            title: "No se encontró la sucursal", // Título de la alerta
            text: "No se encontraron sucursales que coincidan con el término de búsqueda.", // Mensaje de la alerta
            icon: "info", // Tipo de alerta (icono de información)
            button: "Aceptar" // Texto del botón de la alerta
        });
    }
}

//------------------------------------------------------------------------------
function init() {
    CargarTablaS();  // Cargar las sucursales al inicio
    cargarMapaConMarcador();
}

init();