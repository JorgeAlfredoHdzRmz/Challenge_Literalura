package com.aluracursos.challenge.literalura.service;

public interface IConvierteDatos {
    <T>T obtenerDatos(String json, Class<T> clase);
}
