package com.example.entities;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;


	@XmlRootElement(name = "usuario")
	public class ExistJuegoUsuarioDTO {

	    private Long idUsuario;
	    private List<Juego> juegos;

	    
	    @XmlElement(name = "id")
	    public Long getIdUsuario() {
	        return idUsuario;
	    }
	    
	    public ExistJuegoUsuarioDTO() {
			super();
		}

		public ExistJuegoUsuarioDTO(Long idUsuario, List<Juego> juegos) {
			this.idUsuario = idUsuario;
			this.juegos = juegos;
		}

		public void setIdUsuario(Long idUsuario) {
	        this.idUsuario = idUsuario;
	    }

	    @XmlElementWrapper(name = "juegos")
	    @XmlElement(name = "juego")
	    public List<Juego> getJuegos() {
	        return juegos;
	    }

	    public void setJuegos(List<Juego> juegos) {
	        this.juegos = juegos;
	    }
	}

	
	
	
	
	
	
	class Juego {
		
	    private String titulo;
	    private String genero;

	    public Juego() {

		}

	    
	    public Juego(String titulo, String genero) {
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




