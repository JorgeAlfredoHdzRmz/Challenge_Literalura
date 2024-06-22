package com.aluracursos.challenge.literalura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aluracursos.challenge.literalura.principal.Principal;
import com.aluracursos.challenge.literalura.repository.IAutorRepository;
import com.aluracursos.challenge.literalura.repository.ILibroRepository;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner{
	@Autowired
	private ILibroRepository repositoryLibro;

	@Autowired
	private IAutorRepository repositoryAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositoryAutor, repositoryLibro);
		principal.muestraElMenu();
	}

}
