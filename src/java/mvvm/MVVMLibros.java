package mvvm;

import org.utl.proyecto.model.Libro;

public class MVVMLibros {

    private int idLibro;
    private String nombre;
    private String autor;
    private String genero;
    private String estatus;
    private String pdf_file;

    public MVVMLibros() {
    }
//int idLibro, String nombre, String autor, String genero, String estatus, String pdf_file

    public MVVMLibros(Libro libro) {
        this.idLibro = libro.getIdLibro();
        this.nombre = libro.getNombreLibro();
        this.autor = libro.getAutor();
        this.genero = libro.getGenero();
        this.estatus = libro.getEstatus();
        this.pdf_file = libro.getArchivoPdf();
    }

    // Getters y Setters
    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getpdf_file() {
        return pdf_file;
    }

    public void setpdf_file(String pdf_file) {
        this.pdf_file = pdf_file;
    }

    @Override
    public String toString() {
        return "Libro{"
                + "idLibro=" + idLibro
                + ", nombre='" + nombre + '\''
                + ", autor='" + autor + '\''
                + ", genero='" + genero + '\''
                + ", estatus='" + estatus + '\''
                + ", pdf_file='" + pdf_file + '\''
                + '}';
    }
}
