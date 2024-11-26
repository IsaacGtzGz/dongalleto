package org.utl.proyecto.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import mvvm.MVVMLibros;
import org.utl.proyecto.controller.controllerLibro;
import org.utl.proyecto.model.Libro;

@Path("libro")
public class RestLibro extends Application {

    private final controllerLibro controller;

    public RestLibro() {
        this.controller = new controllerLibro();
    }

    @Path("getall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLibros() throws Exception {
        String out = "";
        try {
            controllerLibro cp = new controllerLibro();
            List<Libro> libros = cp.getAllLibros();
            Gson gson = new Gson();
            out = gson.toJson(libros);
        } catch (SQLException ex) {
            out = "{\"error\":\"" + ex.toString() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    // Endpoint para obtener todos los libros disponibles públicamente
    @Path("publico/getall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLibrosPublico() {
        String out = "";
        try {
            controllerLibro cp = new controllerLibro();
            List<MVVMLibros> libros = cp.getAllLibrosPublico();
            Gson gson = new Gson();
            out = gson.toJson(libros);
        } catch (Exception ex) {
            out = "{\"error\":\"" + ex.toString() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @POST
    @Path("insert")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertLibro(
            @FormParam("nombreLibro") String nombreLibro,
            @FormParam("autor") String autor,
            @FormParam("genero") String genero,
            @FormParam("estatus") String estatus,
            @FormParam("archivoPdf") String archivoPdf) {
        try {
            controllerLibro cp = new controllerLibro();
            cp.insertLibro(nombreLibro, autor, genero, estatus, archivoPdf);
            String out = String.format("{\"nombreLibro\":\"%s\"}", nombreLibro);
            return Response.status(Response.Status.CREATED).entity(out).build();
        } catch (Exception e) {
            // Enviar una respuesta con un error claro
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"Error en la validación: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @Path("/update")
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLibro(
            @FormParam("idLibro") int idLibro,
            @FormParam("nombreLibro") String nombreLibro,
            @FormParam("autor") String autor,
            @FormParam("genero") String genero,
            @FormParam("estatus") String estatus,
            @FormParam("archivoPdf") String archivoPdf) throws Exception {

        try {
            controllerLibro cp = new controllerLibro();
            cp.updateLibro(idLibro, nombreLibro, autor, genero, estatus, archivoPdf);
            return Response.ok("{\"message\":\"Libro actualizado correctamente\"}").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en el servidor: " + e.getMessage() + "\"}").build();
        }
    }

    //Generamos el servico REST para actualizar un registro de la tabla libros
    private static final String URL = "jdbc:mysql://localhost:3306/sicefa";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    @PUT
    @Path("/actin")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateLibro(
            @FormParam("idLibro") int idLibro,
            @FormParam("estatus") String estatus) {

        // Aquí deberías realizar la lógica para actualizar el estatus
        String query = "UPDATE libros SET estatus=? WHERE idLibro=?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, estatus);
            stmt.setInt(2, idLibro);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                return Response.ok("{\"message\":\"Estatus actualizado correctamente\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"Libro no encontrada\"}").build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en el servidor\"}").build();
        }
    }

}
