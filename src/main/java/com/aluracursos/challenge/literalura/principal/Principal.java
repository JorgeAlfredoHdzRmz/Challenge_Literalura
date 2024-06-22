package com.aluracursos.challenge.literalura.principal;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.aluracursos.challenge.literalura.model.Autor;
import com.aluracursos.challenge.literalura.model.RDatos;
import com.aluracursos.challenge.literalura.model.RAutor;
import com.aluracursos.challenge.literalura.model.RLibro;
import com.aluracursos.challenge.literalura.model.Libro;
import com.aluracursos.challenge.literalura.repository.IAutorRepository;
import com.aluracursos.challenge.literalura.repository.ILibroRepository;
import com.aluracursos.challenge.literalura.service.ConsumoAPI;
import com.aluracursos.challenge.literalura.service.ConvierteDatos;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private static final String URL_LANGUAGE_CODE = "https://wiiiiams-c.github.io/language-iso-639-1-json-spanish/language-iso-639-1.json";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private ILibroRepository repositoryLibro;
    private IAutorRepository repositoryAutor;
    private Libro libro;
    private List<Libro> libros;
    private List<Autor> autores;
    private Optional<Autor> autor;

    public Principal(IAutorRepository repositoryAutor, ILibroRepository repositoryLibro) {
        this.repositoryAutor = repositoryAutor;
        this.repositoryLibro = repositoryLibro;
    }

    public void muestraElMenu(){
        var opcionElegida = -1;

        while(opcionElegida != 0){
            try {
                System.out.println("BIENVENIDO A LITERALURA :D");
                System.out.println("SELECCIONA UNA OPCION:");
                System.out.println("1 - Buscar libro por titulo");
                System.out.println("2 - Listar libros registrados");
                System.out.println("3 - Listar autores registrados");
                System.out.println("4 - Listar autores vivos en un determinado Anio");
                System.out.println("5 - Listar libros por idioma");
                System.out.println("6 - Mostrar Libros mas descargados");
                System.out.println("");
                System.out.println("0 - Salir de la Aplicacion");
                opcionElegida = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException e) {
                teclado.nextLine();
                opcionElegida = -1;
            }
    
            switch (opcionElegida) {
                case 0:
                    System.out.println("Cerrando Aplicacion");
                    System.out.println("Gracias por tu visita :D");
                    break;
                case 1:
                    consultarLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    consultarAutoresVivosPorAnio();
                    break;
                case 5:
                    consultarLibrosPorIdioma();
                    break;
                case 6:
                    listarLibrosMasDescargados();
                    break;
                default:
                    System.out.println("Por favor ingresa una opcion valida");
            }
        }
    }

    private void consultarLibro() { //Funcion de la Opcion 1
        System.out.println("Ingrese algun nombre de libro: ");

        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "%20"));
        var datosBusqueda = conversor.obtenerDatos(json, RDatos.class);

//        System.out.println(datosBusqueda);

        Optional<RLibro> libroBuscado = datosBusqueda.libros().stream()
                .filter(l -> l.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst();
//
//        System.out.println(libroBuscado);

        if (libroBuscado.isPresent()) {
            RAutor rAutor = libroBuscado.get().autor().get(0);
            if(repositoryLibro.findByIdLibro(libroBuscado.get().idLibro()).isPresent()){
                System.out.println("\n" + "Libro registrado previamente" + "\n");
            }
            else{
                System.out.println("\n" + "Libro encontrado:" + "\n");

                System.out.println("Titulo: " + libroBuscado.get().titulo());
                System.out.println("Autor: " + libroBuscado.get().autor().get(0).nombre());
                System.out.println("Idioma: " + libroBuscado.get().idioma().get(0));
                System.out.println("Total de Descargas: " + libroBuscado.get().numeroDescargas());

                autor = repositoryAutor.findByNombre(rAutor.nombre());

                if(autor.isPresent()){
                    libro = new Libro(libroBuscado.get());
                    libro.setAutor(autor.get());
                    repositoryLibro.save(libro);
                }else{
                    libros = libroBuscado.stream()
                            .map(l -> new Libro(l))
                            .collect(Collectors.toList());

                    Autor autor1 = new Autor(rAutor);
                    autor1.setLibros(libros);
                    repositoryAutor.save(autor1);
                }

                System.out.println("\n" + "Libro guardado correctamenta" + "\n");
            }
        } else {
            System.out.println("No se ha podido guardar el libro, intentalo de nuevo" + "\n");
        }
    }

    private void listarLibros() {
        libros = repositoryLibro.findAll();
        System.out.println("Lista de libros disponibles en Literalura: " + "\n");

        if(libros.isEmpty()){
            System.out.println("Lo siento, de momento no hay libros disponibles en literalura" + "\n");
        }else{
            checarLibros(libros);
        }
    }

    private void listarAutores() {
        System.out.println("Lista de autores registrados en Literalura: " + "\n");

        autores = repositoryAutor.findAllByOrderByNombreAsc();

        if(autores.isEmpty()){
            System.out.println("No hay autores registrados en literalura...\n");
        }else{
            checarAutores(autores);
        }
    }

    private void consultarAutoresVivosPorAnio() {
        try {
            System.out.println("Ingresa el anio deseado:\n");
            int anio = teclado.nextInt();
    
            autores = repositoryAutor.obtenerAutorVivoAnio(anio);
            System.out.println("Autores que estan vivos en el año ingresado" + "\n");
    
            if(autores.isEmpty()){
                System.out.println("No hay autores disponibles en ese anio");
            }else{
                checarAutores(autores);
            }
        } catch (InputMismatchException e) {
            System.out.println("Ingresa un anio valido\n");
            teclado.nextLine();
        }
    }

    private void consultarLibrosPorIdioma() {
            System.out.println("Ingresa el idioma para buscar los libros");
            String codigo = teclado.nextLine();

            libros = repositoryLibro.findByIdioma(codigo);

            if(libros.isEmpty()){
                System.out.println("No hay libros disponibles en: " + "[" + codigo + "]" + " dentro de Literalura" + "\n");
            }else{
                checarLibros(libros);
            }

        }

    private void listarLibrosMasDescargados() {
        libros = repositoryLibro.findByOrderByNumeroDescargasDesc();

        if(libros.isEmpty()){
            System.out.println("No hay libros disponibles en literalura");
        }else{
            System.out.println("Libros mas descargados");

            checarLibros(libros);
        }
    }

    private void checarLibros(List<Libro> lista){
        lista.forEach(l -> System.out.println(l.toString()));
    }

    private void checarAutores(List<Autor> lista){
        lista.forEach(a -> System.out.println(a.toString()));
    }

    }



