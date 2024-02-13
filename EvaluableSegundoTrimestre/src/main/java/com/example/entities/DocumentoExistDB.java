package com.example.entities;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "documento")
public class DocumentoExistDB {

	@XmlAttribute(name = "id")
	private Long id;
	
	private JuegoUsuarioDTO idUser;
	
	@XmlElementWrapper(name = "juegos")
	@XmlElement(name = "juego")
	List<JuegoUsuarioDTO> juegos;
	
	public JuegoUsuarioDTO getIdUser() {
		return idUser;
	}

	public void setIdUser(JuegoUsuarioDTO idUser) {
		this.idUser = idUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<JuegoUsuarioDTO> getJuegos() {
		return juegos;
	}

	public void setJuegos(List<JuegoUsuarioDTO> juegos) {
		this.juegos = juegos;
	}


}
