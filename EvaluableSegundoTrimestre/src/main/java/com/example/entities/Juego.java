package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Juego {
	
	@Id
	@GeneratedValue
	private Long idJuego;
	private String titulo;
	private String genero;	
	private Long idUsuario;
 
	public Juego() {
 
	}
 
	public Juego(Long idJuego, String titulo, String genero) {
		this.idJuego = idJuego;
		this.titulo = titulo;
		this.genero = genero;
 
	}
	
	@OneToMany
	@JoinColumn(name = "idUser")
	public JuegoUsuarioDTO juegoUsuario;
 
	public Long getIdJuego() {
		return idJuego;
	}
 
	public void setIdJuego(Long idJuego) {
		this.idJuego = idJuego;
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
 
	public Long getIdUsuario() {
		return idUsuario;
	}
 
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
}
