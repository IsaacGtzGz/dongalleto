package org.utl.proyecto.controller;

import cqrs.CQRSLibros;
import dao.DAOLibros;
import java.util.ArrayList;
import org.utl.proyecto.model.Libro;
import java.util.List;
import mvvm.MVVMLibros;

public class controllerLibro {

    private final CQRSLibros cqrsLibros;
    private final DAOLibros dao;

    public controllerLibro() {
        this.dao = new DAOLibros();
        this.cqrsLibros = new CQRSLibros();
    }

    // Método para insertar un libro
    public void insertLibro(String nombreLibro, String autor, String genero, String estatus, String archivoPdf) throws Exception {
        // Crear una instancia del libro con los datos proporcionados
        Libro libro = new Libro(0, nombreLibro, autor, genero, estatus, archivoPdf);
        // Insertar libro a través del servicio CQRS
        cqrsLibros.insert(libro);
    }

    // Método para actualizar un libro
    public void updateLibro(int id, String nombreLibro, String autor, String genero, String estatus, String archivoPdf) throws Exception {
        // Crear una instancia del libro con los datos proporcionados
        Libro libro = new Libro(id, nombreLibro, autor, genero, estatus, archivoPdf);
        // Actualizar libro a través del servicio CQRS
        cqrsLibros.update(libro);
    }

    // Método para actualizar el estatus de un libro
    public void reactivarLibro(int idLibro, String estatus) throws Exception {
        if (!estatus.equals("Activo") && !estatus.equals("Inactivo")) {
            throw new Exception("Estatus inválido. Debe ser 'Activo' o 'Inactivo'.");
        }
        // Actualizar el estatus del libro
        cqrsLibros.reactivarLibro(new Libro(idLibro, null, null, null, estatus, null));
    }

    // Método para obtener todos los libros
    public List<Libro> getAllLibros() throws Exception {
        // Obtener todos los libros a través del servicio CQRS
        return dao.getAll();
    }

    public List<MVVMLibros> getAllLibrosPublico() throws Exception {
        List<Libro>  l = dao.getAll();
        List<MVVMLibros> respuesta = new ArrayList<>();
        for (Libro item : l) {
            respuesta.add(new MVVMLibros(item)
            );
        }
        return respuesta;
    }

}
