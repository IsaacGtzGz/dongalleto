package org.utl.proyecto.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;

import org.utl.proyecto.controller.ControllerLogin;
import org.utl.proyecto.model.Usuario;

@Path("login") // Ruta inicial 
public class RestLogin {

    // Método para saludar al cliente
    @Path("saludar") // Ruta específica para saludar
    @GET
    @Produces(MediaType.APPLICATION_JSON) // Producir respuesta en formato JSON
    public Response saludar() {
        // Construir la respuesta en formato JSON
        String out = "{\"response\":\"Hola desde java\"}";
        return Response.ok(out).build(); // Retorna la respuesta HTTP 200 OK
    }//Saludar

    // Método para validar el inicio de sesión
    @Path("validarToken") // Ruta específica para validar el token
    @GET // Método HTTP GET
    @Produces(MediaType.APPLICATION_JSON) // Produce una respuesta en formato JSON
    public Response validarToken(@QueryParam("token") String token) {
        String out = ""; // Variable para almacenar la respuesta

        // Aquí deberías implementar la lógica para validar el token en tu sistema
        // Por ahora, simplemente retornaremos un token válido si existe
        if (token != null && !token.isEmpty()) {
            out = String.format("{\"valid\":true}");
        } else {
            out = String.format("{\"valid\":false}");
        }
        return Response.ok(out).build(); // Retorna la respuesta HTTP 200 OK
    }

    // Método para generar un token de sesión
    @Path("generarToken") // Ruta específica para generar un token de sesión
    @GET // Método HTTP GET
    @Produces(MediaType.APPLICATION_JSON) // Produce una respuesta en formato JSON
    public Response generarToken(@QueryParam("user") String user, @QueryParam("password") String password) throws SQLException {
        String out = ""; // Variable para almacenar la respuesta
        ControllerLogin cl = new ControllerLogin(); // Instancia del controlador de inicio de sesión

        // Verifica que se hayan proporcionado usuario y contraseña
        if (user.length() > 0 && password.length() > 0) {
            // Genera un token de sesión
            String token = cl.generarToken(user, password);

            if (token != null && !token.equals("null")) {
                // Crea un objeto Usuario con el token
                Usuario usuario = new Usuario();
                usuario.setUserName(user);
                usuario.setPassword(password);
                usuario.setToken(token);

                // Inserta el token en la base de datos
                cl.InsertarToken(usuario);

                // Formatea la respuesta JSON con el token generado
                out = String.format("{\"token\":\"%s\"}", token);
            } else {
                out = "{\"token\":\"null\"}";
            }
        } else {
            out = "{\"token\":\"null\"}"; // Respuesta vacía si no se proporcionan usuario y contraseña
        }

        return Response.ok(out)
                .build(); // Retorna la respuesta HTTP 200 OK
    }

}