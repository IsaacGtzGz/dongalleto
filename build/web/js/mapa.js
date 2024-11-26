// Asegúrate de que H se haya definido correctamente en el alcance global
const H = window.H;

// Configuración de la API de HERE Maps
const platform = new H.service.Platform({
    apikey: '0svHnus1bzYl-7eCSmRZjtViNhPEu9n9qd2mAE4zxWA' // Tu API Key de HERE Maps
});

// Coordenadas iniciales de la UTL
const utlLat = 21.070148173941405;
const utlLong = -101.5744665165302;

// Variables globales para los mapas
let mapSimple, mapMarker, mapDirections;

// Función para cargar un mapa con marcador
function cargarMapaConMarcador() {
    const mapContainer = document.getElementById('map-container-marker');
    mapContainer.innerHTML = ""; // Limpia el contenedor del mapa

    const defaultLayers = platform.createDefaultLayers(); // Capas por defecto

    // Crear el mapa centrado en las coordenadas iniciales
    mapMarker = new H.Map(mapContainer, defaultLayers.vector.normal.map, {
        center: {lat: utlLat, lng: utlLong}, // Coordenadas de inicio
        zoom: 10 // Zoom inicial
    });

    // Habilitar interacción en el mapa
    const behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(mapMarker));
    const ui = H.ui.UI.createDefault(mapMarker, defaultLayers);// Interfaz de usuario

    // Permite desplazamiento y zoom
    mapMarker.addEventListener('tap', function (evt) {
        if (evt.target instanceof H.map.Marker) {
            // Evita que el mapa se mueva cuando se hace clic en un marcador
            return;
        }
        // Habilita el desplazamiento y zoom en el mapa con marcador
        mapMarker.setBehavior(behavior);
    });

    // Obtener coordenadas de los campos de entrada
    const latitud = parseFloat(document.getElementById('txtlatitud').value);
    const longitud = parseFloat(document.getElementById('txtlongitud').value);

    // Validar coordenadas y agregar marcador si son válidas
    if (!isNaN(latitud) && !isNaN(longitud)) {
        const marker = new H.map.Marker({lat: latitud, lng: longitud});
        mapMarker.addObject(marker); // Añade marcador al mapa
    }
}
