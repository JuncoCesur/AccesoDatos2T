package com.example.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class JuegoUsuarioDTO {
	
	@Id
	private Long idUser;
	
	@ElementCollection
	private List<Long> listaJuegosUsuario;
	
	private Long idJuego;
	
	public JuegoUsuarioDTO() {
		this.listaJuegosUsuario = new ArrayList<>();
		
	}
 
	public JuegoUsuarioDTO(Long idUser, List<Long> listaJuegosUsuario, Long idJuego) {
		this.idUser = idUser;
		this.listaJuegosUsuario = listaJuegosUsuario;
		this.idJuego = idJuego;
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
 
	public List<Long> getListaJuegosUsuario() {
		return listaJuegosUsuario;
	}
 
	public void setListaJuegosUsuario(List<Long> listaJuegosUsuario) {
		this.listaJuegosUsuario = listaJuegosUsuario;
	}
	

	public Long getIdJuego() {
		return idJuego;
	}

	public void setIdJuego(Long idJuego) {
		this.idJuego = idJuego;
	}

	public void agregarJuego(Long idJuego) {
        this.listaJuegosUsuario.add(idJuego);
    }
}
