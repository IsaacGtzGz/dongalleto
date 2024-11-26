// Variable global para almacenar el PDF anterior
let pdfAnterior = null;

// Función para insertar un nuevo libro
function insertarLibro() {
    let datosLibro = {
        nombreLibro: document.getElementById("nombreLibro").value,
        autor: document.getElementById("autor").value,
        genero: document.getElementById("genero").value,
        estatus: document.getElementById("estatus").value
    };

    // Validación de los campos del libro
    if (!validardatosL())
        return; // Detener si los campos están vacíos o inválidos

    // Verificar si hay un archivo PDF seleccionado
    const archivoInput = document.getElementById("archivoPDF").files[0];

    // Si no hay un archivo PDF seleccionado, mostrar una alerta y detener el proceso
    if (!archivoInput) {
        alertPDFRequerido(); // Mostrar alerta de que el PDF es obligatorio
        return; // Detener la ejecución para evitar que el libro se inserte sin PDF
    }

    // Procesar el archivo PDF
    let archivoPdf = null;
    const reader = new FileReader();
    reader.onloadend = function () {
        archivoPdf = reader.result.split(',')[1];  // Convertir a base64
        enviarDatosLibro(datosLibro, archivoPdf);  // Enviar los datos del libro junto con el PDF
    };
    reader.readAsDataURL(archivoInput);  // Leer el PDF como base64
}

// Función para enviar los datos del libro al servidor
function enviarDatosLibro(datosLibro, archivoPdf) {
    datosLibro.archivoPdf = archivoPdf; // Añadir el PDF al objeto datosLibro

    const queryString = new URLSearchParams(datosLibro).toString();
    fetch('http://localhost:8080/GSucursales/api/libro/insert', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: queryString
    })
            .then(response => response.json())
            .then(data => {
                if (data.message) {
                    alertErrorValidacion(data.message);  // Mostrar el mensaje de error si lo hay
                } else {
                    limpiarL(); // Limpiar los campos del formulario
                    alertexitoL();  // Mostrar mensaje de éxito
                    CargarTablaL(); // Recargar la tabla de libros
                }
            })
            .catch(error => {
                console.error("Error al insertar el libro: " + error);
                alertErrorValidacion("Error en la solicitud de inserción.");
            });
}

function modificarLibro() {
    const idLibro = document.getElementById("IdLibro").value;

    // Validar si se seleccionó un libro
    if (!idLibro) {
        alertErrorValidacion("Selecciona un libro para modificar.");
        return;
    }

    const archivoInput = document.getElementById("archivoPDF").files[0];
    let archivoPdf = null;

    const data = {
        idLibro: idLibro,
        nombreLibro: document.getElementById("nombreLibro").value,
        autor: document.getElementById("autor").value,
        genero: document.getElementById("genero").value,
        estatus: document.getElementById("estatus").value
    };

    // Validaciones antes de enviar los datos al servidor
    if (!validardatosL()) {
        return; // Si falla la validación, no continúa
    }

    // Si hay un nuevo archivo PDF, lo procesamos
    if (archivoInput) {
        const reader = new FileReader();
        reader.onloadend = function () {
            archivoPdf = reader.result.split(',')[1];  // Convertir a base64
            data.archivoPdf = archivoPdf;  // Asignar el nuevo PDF
            enviarModificacionLibro(data);  // Enviar datos del libro y nuevo PDF
        };
        reader.readAsDataURL(archivoInput);
    } else if (pdfAnterior) {
        // Si no hay nuevo PDF, mantener el PDF anterior
        data.archivoPdf = pdfAnterior;
        enviarModificacionLibro(data);  // Enviar datos con el PDF anterior
    } else {
        // Mostrar alerta si no se ha seleccionado ni un nuevo PDF ni se mantiene el anterior
        alertErrorValidacion("Debe seleccionar un PDF o mantener el anterior.");
    }
}

// Función para enviar los datos modificados al servidor
function enviarModificacionLibro(data) {
    const queryString = new URLSearchParams(data).toString();
    fetch("http://localhost:8080/GSucursales/api/libro/update", {
        method: 'PUT',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: queryString
    })
            .then(response => response.json())
            .then(data => {
                if (data.message) {
                    alertexitoL();  // Mostrar mensaje de éxito
                    CargarTablaL(); // Recargar la tabla
                    limpiarL(); // Limpiar el formulario                    
                } else {
                    alertErrorValidacion(data.message);  // Mostrar el mensaje de error si lo hay
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alertErrorValidacion("Error en la solicitud de modificación.");
            });
}

// Función para llenar los campos del libro y almacenar el PDF anterior
function llenarCamposL(fila) {
    document.getElementById("IdLibro").value = fila.idLibro;
    document.getElementById("nombreLibro").value = fila.nombreLibro;
    document.getElementById("autor").value = fila.autor;
    document.getElementById("genero").value = fila.genero;
    document.getElementById("estatus").value = fila.estatus;

    // Almacenar el PDF anterior en la variable global
    pdfAnterior = fila.archivoPdf;

    // Habilitar los botones de modificar/eliminar
    document.getElementById("btnModificar").disabled = false;
    document.getElementById("btnToggleStatus").disabled = false;

    previsualizarPDF(fila.archivoPdf);  // Mostrar el PDF si es necesario
}

// Función para activar o inactivar un libro.
function toggleLibroStatus() {
    const idLibro = document.getElementById("IdLibro").value; // Obtener el ID del libro

    if (!idLibro) { // Validar que se haya seleccionado un libro
        return;
    }

    const currentStatus = document.getElementById("estatus").value; // Obtener el estado actual del libro
    const newStatus = currentStatus === "Activo" ? "Inactivo" : "Activo"; // Cambiar el estado según el actual

    const data = {// Crear un objeto con el nuevo estado
        idLibro: idLibro,
        estatus: newStatus // Cambiar estatus a Activo o Inactivo
    };

    const queryString = new URLSearchParams(data).toString(); // Convertir los datos a un formato URL

    fetch("http://localhost:8080/GSucursales/api/libro/actin", {// Hacer la solicitud de cambio de estado
        method: 'PUT', // Usar método PUT para modificar el estado
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}, // Encabezados HTTP
        body: queryString // Cuerpo de la solicitud
    })
            .then(response => {
                if (!response.ok) { // Manejar error si la respuesta no es exitosa
                    throw new Error(`Error en la solicitud: ${response.statusText}`);
                }
                return response.json(); // Parsear la respuesta a JSON
            })
            .then(data => {
                if (data.message) { // Mostrar mensaje de éxito
                    alertEstatusAct(data.message);  // Llamar a la alerta de estado actualizado
                    CargarTablaL(); // Recargar la tabla
                    limpiarL(); // Limpiar el formulario
                }
            })
            .catch(error => { // Manejo de errores
                console.error('Error:', error);
                alertErrorSolicitud(error.message);  // Llamar a la alerta de error de solicitud
            });
}

// Función para cargar la tabla con todos los libros
function CargarTablaL() {
    let ruta = "http://localhost:8080/GSucursales/api/libro/getall";

    fetch(ruta)
            .then(response => response.json())
            .then(data => {
                libros = data;  // Guardar los datos globalmente
                actualizarTabla(data);  // Actualizar la tabla
            })
            .catch(error => console.error("Error al cargar la tabla: " + error));
}


// Función para limpiar todos los campos del formulario de libros
function limpiarL() {
    document.querySelectorAll("input").forEach(input => input.value = "");
    document.getElementById("archivoPDF").value = "";
    document.getElementById("btnModificar").disabled = true;
    document.getElementById("btnToggleStatus").disabled = true; // Deshabilitar el botón de eliminar
    CargarTablaL(); // Recargar tabla después de limpiar
}

// Función para validar datos del libro
function validardatosL() {
    const campos = ["nombreLibro", "autor", "genero", "estatus"];
    const vacio = campos.some(campo => document.getElementById(campo).value.trim() === "");

    const nombreLibro = document.getElementById("nombreLibro").value;
    const genero = document.getElementById("genero").value;

    // Validar si los campos están vacíos
    if (!nombreLibro || !genero) {
        alertcampos(); // Mostrar alerta de campos vacíos
        return false;
    }

    // Validación de caracteres mínimos y máximos
    if (nombreLibro.length < 5 || nombreLibro.length > 100) {
        alertErrorValidacion("El nombre del libro debe tener entre 5 y 100 caracteres.");
        return false;
    }

    if (genero.length < 5 || genero.length > 30) {
        alertErrorValidacion("El género debe tener entre 5 y 30 caracteres.");
        return false;
    }

    if (vacio) {
        alert("Por favor, completa todos los campos.");
        return false;
    }

    return true;
}


// Función para actualizar la tabla con los libros
function actualizarTabla(data) {
    const tablaLibros = document.getElementById("tablaLibros").getElementsByTagName('tbody')[0];
    tablaLibros.innerHTML = "";  // Limpiar la tabla

    data.forEach(fila => {
        const nuevaFila = tablaLibros.insertRow(-1);
        const campos = ['idLibro', 'nombreLibro', 'autor', 'genero', 'estatus'];

        campos.forEach((campo, index) => {
            const celda = nuevaFila.insertCell(index);
            celda.innerHTML = fila[campo];
        });

        // Añadir botón para visualizar PDF
        const celdaPDF = nuevaFila.insertCell(campos.length);
        const btnVerPDF = document.createElement("button");
        btnVerPDF.textContent = "Ver PDF";
        btnVerPDF.onclick = () => previsualizarPDF(fila.archivoPdf);
        celdaPDF.appendChild(btnVerPDF);

        // Asignar evento click para llenar los campos
        nuevaFila.addEventListener("click", () => llenarCamposL(fila));
    });
}

// Función para previsualizar el PDF cargado
function previsualizarPDF(base64PDF) {
    let iframe = document.createElement('iframe');
    iframe.src = 'data:application/pdf;base64,' + base64PDF;
    iframe.width = "100%";
    iframe.height = "350x";
    iframe.border = "1px solid #ddd";
    
    let divPreview = document.getElementById("divPrevisualizacionPDF");
    divPreview.style.display = 'block';
    divPreview.innerHTML = '';

    let closeButton = document.createElement('button');
    closeButton.textContent = 'Cerrar';
    closeButton.addEventListener('click', function () {
        divPreview.style.display = 'none';
        divPreview.innerHTML = '';
    });

    divPreview.appendChild(closeButton);
    divPreview.appendChild(iframe);
}

// Función para buscar sucursales en la tabla de registros
function buscarL() {
    // Obtiene el valor ingresado por el usuario en el campo de búsqueda y lo convierte a minúsculas
    const searchTerm = document.getElementById("searchLib").value.toLowerCase(// Función para previsualizar el PDF cargado
function previsualizarPDF(base64PDF) {
    let iframe = document.createElement('iframe');
    iframe.src = 'data:application/pdf;base64,' + base64PDF;
    iframe.width = "100%";
    iframe.height = "350x";
    iframe.border = "1px solid #ddd";
    
    let divPreview = document.getElementById("divPrevisualizacionPDF");
    divPreview.style.display = 'block';
    divPreview.innerHTML = '';

    let closeButton = document.createElement('button');
    closeButton.textContent = 'Cerrar';
    closeButton.addEventListener('click', function () {
        divPreview.style.display = 'none';
        divPreview.innerHTML = '';
    });

    divPreview.appendChild(closeButton);
    divPreview.appendChild(iframe);
});
    // Filtra las sucursales almacenadas en la variable global `sucursales`
    // `sucursales` es un arreglo que contiene objetos con los datos de cada sucursal
    const libroFiltrado = libros.filter(libr =>
        // Convierte los valores de cada propiedad de la sucursal a string y minúsculas
        // Verifica si alguna propiedad contiene el término de búsqueda
        Object.values(libr).some(val => val.toString().toLowerCase().includes(searchTerm))
    );
    // Verifica si hay resultados de búsqueda
    if (libroFiltrado.length > 0) {
        // Si hay resultados, actualiza la tabla con las sucursales filtradas
        actualizarTabla(libroFiltrado);
    } else {
        // Si no se encuentran resultados, muestra una alerta con un mensaje de información
        swal({
            title: "No se encontró el Libro", // Título de la alerta
            text: "No se encontraron Libros que coincidan con el término de búsqueda.", // Mensaje de la alerta
            icon: "info", // Tipo de alerta (icono de información)
            button: "Aceptar" // Texto del botón de la alerta
        });
    }
}

// Función para inicializar la tabla de libros al cargar la página
function initLibros() {
    CargarTablaL();
}

initLibros();  // Inicializar la tabla de libros cuando cargue la página


