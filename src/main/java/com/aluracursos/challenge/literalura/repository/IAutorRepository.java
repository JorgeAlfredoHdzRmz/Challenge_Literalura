package com.aluracursos.challenge.literalura.repository;

import java.util.List;
import java.util.Optional;

import com.aluracursos.challenge.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAutorRepository extends JpaRepository<Autor, Long>{
    List<Autor> findAllByOrderByNombreAsc();
    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.anioNacimiento < :anio AND a.anioFallecimiento > :anio ORDER BY a.nombre ASC")
    List<Autor> obtenerAutorVivoAnio(int anio);
}
