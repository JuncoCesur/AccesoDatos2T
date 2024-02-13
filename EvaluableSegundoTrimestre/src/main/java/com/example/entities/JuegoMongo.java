package com.example.entities;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "juegos.horas")
public class JuegoMongo {

	private String titulo;
	private String genero;	
	private HorasMongo horas;
 
	public JuegoMongo() {
 
	}
 
	public JuegoMongo(String titulo, String genero) {
		this.titulo = titulo;
		this.genero = genero;
 
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

	public HorasMongo getHoras() {
		return horas;
	}

	public void setHoras(HorasMongo horas) {
		this.horas = horas;
	}
 
}
