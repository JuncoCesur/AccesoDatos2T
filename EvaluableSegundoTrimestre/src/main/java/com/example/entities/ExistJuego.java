package com.example.entities;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "juego")
public class ExistJuego {
	
	private Long idJuego;
	private String titulo;
    private String genero;
   
    @XmlElement(name = "idJuego")
    public Long getIdJuego() {
		return idJuego;
	}

	public void setIdJuego(Long idJuego) {
		this.idJuego = idJuego;
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
    
	// Metodo para crear el juego de ExistDB
    public ExistJuego llamarMetodoInsertar(Juego juegoObject) throws Exception {
    	
    	ExistJuego juegoExist = new ExistJuego();

    	String tituloObject = juegoObject.getTitulo();
    	String generoJuego = juegoObject.getGenero();
    	
    	juegoExist.setTitulo(tituloObject);
    	juegoExist.setGenero(generoJuego);
    	
    	return juegoExist;
    	
    }
}
