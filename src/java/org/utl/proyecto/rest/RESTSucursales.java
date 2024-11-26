package org.utl.proyecto.rest;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PUT;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.google.gson.Gson;
import java.sql.SQLException;

import java.util.List;  // Agrega la importación para la clase List
import jakarta.ws.rs.*;
import java.sql.*;

import org.utl.proyecto.controller.ControllerSucursal;
import org.utl.proyecto.model.Sucursal;

@Path("sucursal")

public class RESTSucursales extends Application {

    @Path("getall")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        String out = "";
        try {
            ControllerSucursal cp = new ControllerSucursal();
            List<Sucursal> Sucursals = cp.getAll();
            Gson gs = new Gson();
            out = gs.toJson(Sucursals);
        } catch (Exception ex) {
            out = "{\"error\":\"" + ex.toString() + "\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("insert")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(
            @FormParam("nombre") String nombre, // No se necesita @DefaultValue para estos parámetros
            @FormParam("titular") String titular, // No se necesita @DefaultValue para estos parámetros
            @FormParam("rfc") String rfc,
            @FormParam("domicilio") String domicilio,
            @FormParam("colonia") String colonia,
            @FormParam("codigoPostal") String codigoPostal,
            @FormParam("ciudad") String ciudad,
            @FormParam("estado") String estado,
            @FormParam("telefono") String telefono,
            @FormParam("latitud") String latitud,
            @FormParam("longitud") String longitud,
            @FormParam("estatus") String estatus
    ) {
        ControllerSucursal cp = new ControllerSucursal();
        cp.insertSucursal(nombre, titular, rfc, domicilio, colonia, codigoPostal, ciudad, estado, telefono, latitud, longitud, estatus);
        String out = String.format("{\"nombre\":\"%s\"}", nombre);
        return Response.status(Response.Status.CREATED).entity(out).build();
    }

    //Generamos el servico REST para actualizar un registro de la tabla sucursal
    private static final String URL = "jdbc:mysql://localhost:3306/sicefa";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSucursal(
            @FormParam("idSucursal") int idSucursal,
            @FormParam("nombre") String nombre,
            @FormParam("titular") String titular,
            @FormParam("rfc") String rfc,
            @FormParam("domicilio") String domicilio,
            @FormParam("colonia") String colonia,
            @FormParam("codigoPostal") String codigoPostal,
            @FormParam("ciudad") String ciudad,
            @FormParam("estado") String estado,
            @FormParam("telefono") String telefono,
            @FormParam("latitud") String latitud,
            @FormParam("longitud") String longitud,
            @FormParam("estatus") String estatus) {

        String query = "UPDATE sucursal SET nombre=?, titular=?, rfc=?, domicilio=?, colonia=?, "
                + "codigoPostal=?, ciudad=?, estado=?, telefono=?, latitud=?, longitud=?, estatus=? "
                + "WHERE idSucursal=?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombre);
            stmt.setString(2, titular);
            stmt.setString(3, rfc);
            stmt.setString(4, domicilio);
            stmt.setString(5, colonia);
            stmt.setString(6, codigoPostal);
            stmt.setString(7, ciudad);
            stmt.setString(8, estado);
            stmt.setString(9, telefono);
            stmt.setString(10, latitud);
            stmt.setString(11, longitud);
            stmt.setString(12, estatus);
            stmt.setInt(13, idSucursal);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return Response.ok("{\"message\":\"Sucursal actualizada correctamente\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"Sucursal no encontrada\"}").build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en el servidor\"}").build();
        }
    }

    @PUT
    @Path("/actin")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSucursal(
            @FormParam("idSucursal") int idSucursal,
            @FormParam("estatus") String estatus) {

        // Aquí deberías realizar la lógica para actualizar el estatus
        String query = "UPDATE sucursal SET estatus=? WHERE idSucursal=?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, estatus);
            stmt.setInt(2, idSucursal);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                return Response.ok("{\"message\":\"Estatus actualizado correctamente\"}").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\":\"Sucursal no encontrada\"}").build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\":\"Error en el servidor\"}").build();
        }
    }

}
