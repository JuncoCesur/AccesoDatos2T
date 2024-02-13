package com.example.entities;

import java.util.List;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "documentoExistDB")
public class DocumentoExistDB {

	@XmlAttribute(name = "id")
	private Long id;
	
	@XmlElementWrapper(name = "juegos")
	@XmlElement(name = "juegos")
	List<Juegos> juegos;
	
	@XmlTransient

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Juegos> getJuegos() {
		return juegos;
	}

	public void setJuegos(List<Juegos> juegos) {
		this.juegos = juegos;
	}


}
