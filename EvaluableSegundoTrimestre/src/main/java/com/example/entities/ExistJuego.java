package com.example.entities;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "juego")
public class ExistJuego {
	
	private String titulo;
    private String genero;

    public ExistJuego() {

	}
  
    public ExistJuego(String titulo, String genero) {
		this.titulo = titulo;
		this.genero = genero;
	}
    
    
    @XmlElement(name = "titulo")
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

	@XmlElement(name = "genero")
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
