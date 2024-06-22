package com.aluracursos.challenge.literalura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aluracursos.challenge.literalura.model.Libro;

public interface ILibroRepository extends JpaRepository<Libro, Long>{
    Optional<Libro> findByIdLibro(Long idLibro);
    List<Libro> findByIdioma(String idioma);
    List<Libro> findByOrderByNumeroDescargasDesc();
}
