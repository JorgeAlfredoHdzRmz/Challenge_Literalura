package com.aluracursos.challenge.literalura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RLibro(
    @JsonAlias("id") Long idLibro,
    @JsonAlias("title") String titulo,
    @JsonAlias("authors") List<RAutor> autor,
    @JsonAlias("languages") List<String> idioma,
    @JsonAlias("download_count") Long numeroDescargas
) {

}
