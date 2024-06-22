package com.aluracursos.challenge.literalura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RDatos(
    @JsonAlias("results") List<RLibro> libros
) {
}
