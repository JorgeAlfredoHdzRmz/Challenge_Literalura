package com.aluracursos.challenge.literalura.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    private Long idLibro;

    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Long numeroDescargas;

    @ManyToOne
    @JoinColumn(name = "idAutor", nullable = false)
    private Autor autor;

    public Libro(RLibro rLibro){
        this.idLibro = rLibro.idLibro();
        this.titulo = rLibro.titulo();
        this.idioma = rLibro.idioma().get(0);
        this.numeroDescargas = rLibro.numeroDescargas();
    }

    public Libro() {
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Long numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public String soloNombre(Libro libro) {
        return libro.getTitulo();
    }

    @Override
    public String toString() {
        return "--------------------LIBRO--------------------" + "\n" +
                "Nombre: " + titulo + "\n" +
                "Autor: " + autor.getNombre() + "\n" +
                "Idioma: " + idioma + "\n" +
                "Descargas: " + numeroDescargas + "\n" +
                "--------------------------------------------" + "\n";
    }


}
