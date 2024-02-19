package com.example.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.JuegoUsuarioDTO;
import com.example.repository.ObjectDBRepository;

@Service
public class ObjectDBService {
	
	@Autowired
	protected ObjectDBRepository repositoryBBDD;

	private String email;
	private String titulo;
	private String genero;
	private Long id;
	private JuegoUsuarioDTO usuarioObject;
	

	public ObjectDBService() {}
	
	public void juegoAsociado(String email, String titulo, String genero) throws SQLException {
		
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
		repositoryBBDD.juegoAsociado(so, usuarioObject);

	}
	
	public void insertarJuego(String titulo, String genero) {
		
		// Creamos un objeto que vaya a coger los parámetros
		ObjectDBService so = new ObjectDBService();
		this.titulo = titulo;
		this.genero = genero;

		// Indicamos que los parámetros de este objeto son los indicados en Postman
		so.setTitulo(titulo);
		so.setGenero(genero);

		// Lo mandamos al repositorio
		repositoryBBDD.insertarJuego(so);
		
	}
	
	public void encontrarJuego(String titulo) {
		
		// Creamos un objeto que vaya a coger los parámetros
		ObjectDBService so = new ObjectDBService();
		this.titulo = titulo;

		// Indicamos que los parámetros de este objeto son los indicados en Postman
		so.setTitulo(titulo);

		repositoryBBDD.encontrarJuegoTitulo(so);
		
	}
	
	public void mostrar() {
		// Lo mandamos al repositorio
		repositoryBBDD.mostrar();

	}
	
	public void existeUsuarioObject(String email) throws SQLException {
		
		// Creamos un objeto que vaya a coger los parámetros
		ObjectDBService so = new ObjectDBService();
		this.email = email;

		// Indicamos que los parámetros de este objeto son los indicados en Postman
		so.setEmail(email);

		// Lo mandamos al repositorio
		repositoryBBDD.existeUsuarioObject(so);

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
	
	
	
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
