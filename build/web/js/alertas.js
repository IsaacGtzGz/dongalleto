// ALERTAS SUCURSALES//---------------------------------------------------------
function alertexito() {
    swal({
        title: "Operación exitosa",
        text: "Se ha agregado correctamente la sucursal",
        icon: "success",
        button: "Aceptar"
    });
}

function alertamodificarS() {
    swal({
        title: "¿Quieres modificar la sucursal seleccionada?",
        icon: "warning",
        buttons: true,
        dangerMode: true
    }).then(willModify => {
        if (willModify) {
            modificarSucursal(); // Se asume que esta función está definida en otro lugar
            actualizarTabla(sucursales);
            limpiarS();
            swal("¡Sucursal modificada con éxito!", {icon: "success"});
        } else {
            swal("¡Los cambios de la sucursal han sido cancelados!");
        }
    });
}

function alertaActivoInS() {
    swal({
        title: "¿Quieres modificar el estatus de la sucursal seleccionada?",
        icon: "warning",
        buttons: true,
        dangerMode: true
    }).then(willModify => {
        if (willModify) {
            toggleSucursalStatus(); // Se asume que esta función está definida en otro lugar
            actualizarTabla(sucursales);
            limpiarS();
            swal("¡Se actualizado el estatus con éxito!", {icon: "success"});
        } else {
            swal("¡Los cambios del estatus han sido cancelados!");
        }
    });
}

function alertnumeros() {
    swal({
        title: "¡Atención!",
        text: "Ingrese los números en los campos necesarios",
        icon: "warning",
        button: "Aceptar"
    });
}

function alertcampos() {
    swal({
        title: "¡Atención!",
        text: "Complete todos los campos",
        icon: "warning",
        button: "Aceptar"
    });
}


// ALERTAS LIBROS//-------------------------------------------------------------

function alertexitoL() {
    swal({
        title: "Operación exitosa",
        text: "Se ha agregado correctamente el Libro",
        icon: "success",
        button: "Aceptar"
    });
}

function alertamodificarL() {
    swal({
        title: "¿Quieres modificar el Libro seleccionada?",
        icon: "warning",
        buttons: true,
        dangerMode: true
    }).then(willModify => {
        if (willModify) {
            modificarLibro(); // Se asume que esta función está definida en otro lugar
        } else {
            swal("¡Los cambios del Libro han sido cancelados!");
        }
    });
}

function alertaActivoInL() {
    swal({
        title: "¿Quieres modificar el estatus del Libro seleccionada?",
        icon: "warning",
        buttons: true,
        dangerMode: true
    }).then(willModify => {
        if (willModify) {
            toggleLibroStatus(); // Se asume que esta función está definida en otro lugar
            actualizarTabla(libros);
            limpiarL();
        } else {
            swal("¡Los cambios del estatus han sido cancelados!");
        }
    });
}

// Alerta para cuando el PDF es requerido
function alertPDFRequerido() {
    swal({
        title: "PDF requerido",
        text: "Debes seleccionar un archivo PDF antes de continuar.",
        icon: "warning",
        button: "Aceptar"
    });
}

function alertErrorSolicitud(mensaje) {
    swal({
        title: "Error en la solicitud",
        text: mensaje,
        icon: "error",
        button: "Aceptar"
    });
}

function alertEstatusAct(mensaje) {
    swal({
        title: "Estatus actualizado",
        text: mensaje,
        icon: "success",
        button: "Aceptar"
    });
}

function alertErrorValidacion(mensaje) {
    swal({
        title: "Error de validación",
        text: mensaje,
        icon: "error",
        button: "Aceptar"
    });
}
