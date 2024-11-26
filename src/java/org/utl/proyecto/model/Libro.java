// Libro.java
package org.utl.proyecto.model;

public class Libro {

    private int idLibro;
    private String nombreLibro;
    private String autor;
    private String genero;
    private String estatus;
    private String archivoPdf;

    // Constructor vacío
    public Libro() {
    }

    // Constructor con parámetros
    public Libro(int idLibro, String nombreLibro, String autor, String genero, String estatus, String archivoPdf) {
        this.idLibro = idLibro;
        this.nombreLibro = nombreLibro;
        this.autor = autor;
        this.genero = genero;
        this.estatus = estatus;
        this.archivoPdf = archivoPdf;
    }

    // Getters y Setters
    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
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

    public String getArchivoPdf() {
        return archivoPdf;
    }

    public void setArchivoPdf(String archivoPdf) {
        this.archivoPdf = archivoPdf;
    }

    @Override
    public String toString() {
        return "Libro{"
                + "idLibro=" + idLibro
                + ", nombreLibro='" + nombreLibro + '\''
                + ", autor='" + autor + '\''
                + ", genero='" + genero + '\''
                + ", estatus='" + estatus + '\''
                + ", archivoPdf='" + archivoPdf + '\''
                + '}';
    }
}
