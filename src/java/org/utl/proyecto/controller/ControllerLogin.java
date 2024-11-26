package org.utl.proyecto.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import org.utl.proyecto.model.Usuario;
import java.sql.ResultSet;
import org.utl.proyecto.bd.ConexionMysql;

public class ControllerLogin {

    // Método para generar un token de sesión
    public String generarToken(String usuario, String contrasenia) {
        String token = null; // Inicializa el token como nulo

        try {
            // Verificar si las credenciales son válidas consultando la base de datos
            if (verificarCredenciales(usuario, contrasenia)) {
                // Crear un objeto Usuario con los datos proporcionados
                Usuario u = new Usuario();
                u.setUserName(usuario);
                u.setPassword(contrasenia);

                // Generar un timestamp
                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

                // Crear una cadena para el token concatenando usuario, contraseña y timestamp
                String cadenaToken = usuario + ":" + contrasenia + ":" + timeStamp;

                // Convertir la cadena a bytes
                byte[] tokenBytes = cadenaToken.getBytes(java.nio.charset.StandardCharsets.UTF_8);

                // Convertir los bytes a una representación hexadecimal
                token = bytesToHex(tokenBytes);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
            throw new RuntimeException("Error al generar el token", e); // Lanzar una excepción en caso de error
        }

        return token; // Devolver el token generado o nulo si las credenciales no son válidas
    }

    // Método para convertir bytes a representación hexadecimal
    public String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // Método para verificar las credenciales de inicio de sesión
    public boolean verificarCredenciales(String usuario, String contrasenia) throws SQLException {
        // Crear un objeto Usuario con los datos proporcionados
        Usuario u = new Usuario();
        u.setUserName(usuario);
        u.setPassword(contrasenia);

        boolean credencialesValidas = false; // Inicializar como falso

        // Consulta SQL para verificar las credenciales
        String query = "SELECT COUNT(*) FROM usuario WHERE user = ? AND password = ?";

        try {
            // Establecer la conexión a la base de datos
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();

            // Preparar la consulta
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, usuario);
            pstm.setString(2, contrasenia);

            // Ejecutar la consulta
            ResultSet rs = pstm.executeQuery();

            // Verificar el resultado de la consulta
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    credencialesValidas = true; // Credenciales válidas si se encontró al menos un registro
                }
            }

            // Cerrar recursos
            rs.close();
            pstm.close();
            connMysql.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }

        return credencialesValidas; // Devolver el resultado de la verificación
    }

    // Método para insertar un token en la base de datos
    public Usuario InsertarToken(Usuario e) throws SQLException {
        // Consulta SQL para insertar un token
        String query = "call InsertarToken(?,?,?)";

        try {
            // Establecer la conexión a la base de datos
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();

            // Preparar la llamada al procedimiento almacenado
            CallableStatement pstm = conn.prepareCall(query);
            pstm.setString(1, e.getUserName());
            pstm.setString(2, e.getPassword());
            pstm.setString(3, e.getToken());

            // Ejecutar la llamada al procedimiento almacenado
            pstm.execute();

            // Cerrar recursos
            pstm.close();
            connMysql.close();
        } catch (SQLException ex) {
            ex.printStackTrace(); // Manejo de excepciones
        }
        return e; // Devolver el objeto Usuario con el token insertado
    }
}
