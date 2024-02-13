package com.example.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class JuegoUsuarioDTO {
	
	@Id
	private Long idUser;
	private List<Juego> listaJuegosUsuario;
	
	public JuegoUsuarioDTO() {
		
	}
 
	public JuegoUsuarioDTO(Long idUser, List<Juego> listaJuegosUsuario) {
		this.idUser = idUser;
		this.listaJuegosUsuario = listaJuegosUsuario;
	}
	
	@OneToMany
	@JoinColumn(name = "idJuegos")
	public Juego juegos;
 
	public Long getIdUser() {
		return idUser;
	}
 
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
 
	public List<Juego> getListaJuegosUsuario() {
		return listaJuegosUsuario;
	}
 
	public void setListaJuegosUsuario(List<Juego> listaJuegosUsuario) {
		this.listaJuegosUsuario = listaJuegosUsuario;
	}
}
