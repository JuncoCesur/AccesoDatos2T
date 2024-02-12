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
	private List<Juegos> listaJuegos;
	
	public JuegoUsuarioDTO() {
		
	}
 
	public JuegoUsuarioDTO(Long idUser, List<Juegos> listaJuegos) {
		this.idUser = idUser;
		this.listaJuegos = listaJuegos;
	}
	
	@OneToMany
	@JoinColumn(name = "idJuegos")
	public Juegos juegos;
 
	public Long getIdUser() {
		return idUser;
	}
 
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
 
	public List<Juegos> getListaJuegos() {
		return listaJuegos;
	}
 
	public void setListaJuegos(List<Juegos> listaJuegos) {
		this.listaJuegos = listaJuegos;
	}
}
