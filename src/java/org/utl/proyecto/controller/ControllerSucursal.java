package org.utl.proyecto.controller;

import com.mysql.cj.jdbc.CallableStatement;
import org.utl.proyecto.model.Sucursal;
import org.utl.proyecto.bd.ConexionMysql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class ControllerSucursal {

    public List<Sucursal> getAll() {
        // Se crea una lista llamada sucursals que almacenará objetos de tipo sucursal.
        List<Sucursal> sucursals = new ArrayList<>();
        // Se define una consulta SQL que selecciona todos los registros de la tabla "sucursal".
        String query = "SELECT * FROM sucursal";

        // Se establece una conexión con la base de datos.
        try {
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();
            PreparedStatement pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();

            // Se recorren los resultados y se crea un objeto sucursal para cada registro.
            while (rs.next()) {
                int idSucursal = rs.getInt("idSucursal");
                String nombre = rs.getString("nombre");
                String titular = rs.getString("titular");
                String rfc = rs.getString("rfc");
                String domicilio = rs.getString("domicilio");
                String colonia = rs.getString("colonia");
                String codigoPostal = rs.getString("codigoPostal");
                String ciudad = rs.getString("ciudad");
                String estado = rs.getString("estado");
                String telefono = rs.getString("telefono");
                String latitud = rs.getString("latitud");
                String longitud = rs.getString("longitud");
                String estatus = rs.getString("estatus");

                // Cada objeto sucursal se agrega a la lista sucursals.
                Sucursal sucursal = new Sucursal(idSucursal, nombre, titular, rfc, domicilio, colonia, codigoPostal, ciudad, estado, telefono, latitud, longitud, estatus);
                sucursals.add(sucursal);
            }
            // Se cierran los recursos de la base de datos.
            rs.close();
            pstm.close();
            connMysql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Finalmente, se devuelve la lista sucursals que contiene todos los registros de la tabla "sucursal".
        return sucursals;
    }

    public void insertSucursal(String nombre, String titular, String rfc, String domicilio, String colonia, String codigoPostal, String ciudad,
            String estado, String telefono, String latitud, String longitud, String estatus) {
        String query = "INSERT INTO sucursal(nombre,titular,rfc,domicilio,colonia,codigoPostal,ciudad,estado,telefono,latitud,longitud,estatus) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, nombre);
            pstm.setString(2, titular);
            pstm.setString(3, rfc);
            pstm.setString(4, domicilio);
            pstm.setString(5, colonia);
            pstm.setString(6, codigoPostal);
            pstm.setString(7, ciudad);
            pstm.setString(8, estado);
            pstm.setString(9, telefono);
            pstm.setString(10, latitud);
            pstm.setString(11, longitud);
            pstm.setString(12, estatus);

            pstm.executeUpdate(); // Cambiado a executeUpdate()

            pstm.close();
            connMysql.close();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir error para depuración
        }
    }

    // Método para actualizar una sucursal
    public void update(Sucursal p) throws SQLException {
        System.out.println("Llegamos al controller");// Mensaje para verificar la llegada al controlador
        System.out.println("Nombre:" + p.getNombre());// Imprimir el nombre de la sucursal para depuración
        String query = "{CALL insertarSucursal(?,?,?,?,?,?,?,?,?,?,?,?,?)}"; // Consulta para llamar al procedimiento almacenado 'insertarSucursal'
        ConexionMysql connMysql = new ConexionMysql();// Crear una nueva instancia de conexión a MySQL
        Connection conn = connMysql.open();// Abrir la conexión a la base de datos
        CallableStatement cstm = (CallableStatement) conn.prepareCall(query); // Preparar la llamada al procedimiento almacenado

        cstm.setInt(1, p.getIdSucursal());// Asignar el ID de la sucursal
        cstm.setString(2, p.getNombre());// Asignar el nombre de la sucursal
        cstm.setString(3, p.getTitular());// Asignar el nombre del titular
        cstm.setString(4, p.getRfc());// Asignar el RFC de la sucursal
        cstm.setString(5, p.getDomicilio());// Asignar el domicilio
        cstm.setString(6, p.getColonia());// Asignar la colonia
        cstm.setString(7, p.getCodigoPostal());// Asignar el código postal
        cstm.setString(8, p.getCiudad());// Asignar la ciudad
        cstm.setString(9, p.getEstado());// Asignar el estado
        cstm.setString(10, p.getTelefono());// Asignar el teléfono
        cstm.setString(11, p.getLatitud());// Asignar la latitud
        cstm.setString(12, p.getLongitud());// Asignar la longitud
        cstm.setString(13, p.getEstatus());// Asignar el estatus (activo/inactivo)

        cstm.execute();// Ejecutar el procedimiento almacenado
        cstm.close();// Cerrar el CallableStatement
        connMysql.close();// Cerrar la conexión a la base de datos MySQL
        conn.close();// Cerrar la conexión
    }

    public void reactivarSucursal(int idSucursal) {
        String query = "UPDATE sucursal SET estatus = 1 WHERE idSucursal = ?";
        try {
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setInt(1, idSucursal);
            pstm.executeUpdate(); // Cambiado a executeUpdate()

            pstm.close();
            connMysql.close();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir error para depuración
        }
    }

    public void updateSucursal(int idSucursal, String nombre, String titular, String rfc, String domicilio, String colonia, String codigoPostal, String ciudad, String estado, String telefono, String latitud, String longitud, String estatus) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
