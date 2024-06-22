package com.aluracursos.challenge.literalura.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAutor;

    @Column(unique = true)
    private String nombre;
    private Integer anioNacimiento;
    private Integer anioFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(RAutor rAutor){
        this.nombre = rAutor.nombre();
        this.anioNacimiento = rAutor.anioNacimiento();
        this.anioFallecimiento = rAutor.anioFallecimiento();
    }

    public Autor() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioNacimiento(Integer anioNacimiento) {
        this.anioNacimiento = anioNacimiento;
    }

    public Integer getAnioFallecimiento() {
        return anioFallecimiento;
    }

    public void setAnioFallecimiento(Integer anioFallecimiento) {
        this.anioFallecimiento = anioFallecimiento;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(l -> l.setAutor(this));
        this.libros = libros;
    }



    @Override
    public String toString() {
        String librosNombres = libros.stream()
                .map(Libro::getTitulo)
                .toList().toString();

        var retorno = "-----------------AUTOR-------------------" + "\n" +
                "Nombre: " + nombre + "\n" +
                "Anio de Nacimiento: " + (anioNacimiento==null?"N/A":anioNacimiento) + "\n" +
                "Anio Fallecimiento: " + (anioFallecimiento==null?"N/A":anioFallecimiento) + "\n" +
                "Libros: " + "\n" + librosNombres + "\n" +
                "----------------------------------------" + "\n";
        return retorno.replace("[","").replace("]","");
    }
}
