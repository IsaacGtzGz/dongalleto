package cqrs;

import dao.DAOLibros;
import org.utl.proyecto.model.Libro;

public class CQRSLibros {

    private final DAOLibros dao;

    public CQRSLibros() {
        this.dao = new DAOLibros();
    }

    // Método para insertar un libro
    public void insert(Libro libro) throws Exception {
        validate(libro);  // Validar los datos del libro antes de insertarlo
        dao.insertLibro(libro.getNombreLibro(), libro.getAutor(), libro.getGenero(), libro.getEstatus(), libro.getArchivoPdf());
    }

    // Método para actualizar un libro
    public void update(Libro libro) throws Exception {
        validate(libro);  // Validar los datos del libro antes de actualizarlo
        dao.updateLibro(libro.getIdLibro(), libro.getNombreLibro(), libro.getAutor(), libro.getGenero(), libro.getEstatus(), libro.getArchivoPdf());
    }

    // Método para actualizar un libro
    public void reactivarLibro(Libro libro) throws Exception {
        validate(libro);  // Validar los datos del libro antes de actualizarlo
        dao.reactivarLibro(libro.getIdLibro(), libro.getEstatus());
    }

    // Validaciones de los datos del libro
    private void validate(Libro libro) throws Exception {
        // Validar el nombre del libro
        String nombre = libro.getNombreLibro();
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre del libro es requerido.");
        }
        if (nombre.length() < 5 || nombre.length() > 100) {
            throw new Exception("El nombre del libro debe tener entre 5 y 100 caracteres.");
        }
        
        // Validar el género
        String genero = libro.getGenero();
        if (genero == null || genero.isEmpty()) {
            throw new Exception("El género del libro es requerido.");
        }
        if (genero.length() < 5 || genero.length() > 30) {
            throw new Exception("El género del libro debe tener entre 5 y 30 caracteres.");
        }
    }

}
