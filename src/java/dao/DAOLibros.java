package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mvvm.MVVMLibros;
import org.utl.proyecto.bd.ConexionMysql;
import org.utl.proyecto.controller.controllerLibro;
import org.utl.proyecto.model.Libro;

public class DAOLibros {

    // Obtener todos los libros
    public List<Libro> getAll() throws SQLException {
        List<Libro> librs = new ArrayList<>();
        String query = "SELECT * FROM libros";

        try {
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();
            PreparedStatement pstm = conn.prepareStatement(query);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("idLibro"),
                        rs.getString("nombreLibro"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        rs.getString("estatus"),
                        rs.getString("archivoPdf")
                );
                librs.add(libro);
            }

            rs.close();
            pstm.close();
            connMysql.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return librs;
    }

   
    // Insertar un libro
    public void insertLibro(String nombreLibro, String autor, String genero, String estatus, String archivoPdf) {
        String query = "INSERT INTO libros (nombreLibro, autor, genero, estatus, archivoPdf) VALUES (?, ?, ?, ?, ?)";

        try {
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, nombreLibro);
            pstm.setString(2, autor);
            pstm.setString(3, genero);
            pstm.setString(4, estatus);
            pstm.setString(5, archivoPdf);
            pstm.executeUpdate();

            pstm.close();
            connMysql.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Actualizar un libro
    public void updateLibro(int idLibro, String nombreLibro, String autor, String genero, String estatus, String archivoPdf) throws SQLException {
        String query = "UPDATE libros SET nombreLibro = ?, autor = ?, genero = ?, estatus = ?, archivoPdf = ? WHERE idLibro = ?";

        ConexionMysql connMysql = new ConexionMysql();
        Connection conn = connMysql.open();
        PreparedStatement pstm = conn.prepareStatement(query);

        pstm.setString(1, nombreLibro);
        pstm.setString(2, autor);
        pstm.setString(3, genero);
        pstm.setString(4, estatus);
        pstm.setString(5, archivoPdf);
        pstm.setInt(6, idLibro);

        pstm.executeUpdate();
        pstm.close();
        connMysql.close();
    }

    // Reactivar un libro
    public void reactivarLibro(int idLibro, String estatus) throws SQLException {
        String query = "UPDATE libros SET estatus = ? WHERE idLibro = ?";

        try {
            ConexionMysql connMysql = new ConexionMysql();
            Connection conn = connMysql.open();
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, estatus); // Actualiza el estatus según el valor pasado
            pstm.setInt(2, idLibro);
            pstm.executeUpdate();
            pstm.close();
            connMysql.close();
        } catch (SQLException e) {
            throw new SQLException("Error actualizando el estatus en la base de datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        controllerLibro controller = new controllerLibro();
        try {
            List<MVVMLibros> librosPublico = controller.getAllLibrosPublico();
            System.out.println(librosPublico);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
