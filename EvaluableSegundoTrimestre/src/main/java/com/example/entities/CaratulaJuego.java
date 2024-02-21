package com.example.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "caratulas")
public class CaratulaJuego {

	@Id
	private String id;
	
	private String nombreCaratula;
	private byte[] imagenCaratula;
	
	public CaratulaJuego() {}
	
	public CaratulaJuego(String id, String nombreCaratula, byte[] imagenCaratula) {
		this.id = id;
		this.nombreCaratula = nombreCaratula;
		this.imagenCaratula = imagenCaratula;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombreCaratula() {
		return nombreCaratula;
	}

	public void setNombreCaratula(String nombreCaratula) {
		this.nombreCaratula = nombreCaratula;
	}

	public byte[] getImagenCaratula() {
		return imagenCaratula;
	}

	public void setImagenCaratula(byte[] imagenCaratula) {
		this.imagenCaratula = imagenCaratula;
	}
}
