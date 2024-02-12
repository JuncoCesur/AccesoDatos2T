package com.example.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.ObjectDBRepository;

@Service
public class ObjectDBService {
	
	@Autowired
	protected ObjectDBRepository repositoryBBDD;

	private String email;
	private String titulo;
	private String genero;

	public void juegosAsociados(String email, String titulo, String genero) throws SQLException {
		
		// Creamos un objeto que vaya a coger los parámetros
		ObjectDBService so = new ObjectDBService();
		this.email = email;
		this.titulo = titulo;
		this.genero = genero;

		// Indicamos que los parámetros de este objeto son los indicados en Postman
		so.setEmail(email);
		so.setTitulo(titulo);
		so.setGenero(genero);

		// Lo mandamos al repositorio
		repositoryBBDD.juegosAsociados(so);

	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}
	
}
