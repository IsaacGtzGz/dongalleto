package org.utl.proyecto.bd;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionMysql {

    private Connection conn; // Objeto Connection para mantener la conexión

    // Método para abrir una conexión a la base de datos
    public Connection open() {
        String user = "root";
        String password = "root";
        String url = "jdbc:mysql://127.0.0.1:3306/sicefa";
        String parametros = "?useSSL=false&useUnicode=true&characterEncoding=utf-8";
        try {
            // Carga el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establece la conexión
            conn = DriverManager.getConnection(url + parametros, user, password);
            System.out.println("Conexión exitosa a la base de datos.");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al abrir la conexión con la base de datos", e);
        }
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada.");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error al cerrar la conexión", e);
            }
        }
    }
}

