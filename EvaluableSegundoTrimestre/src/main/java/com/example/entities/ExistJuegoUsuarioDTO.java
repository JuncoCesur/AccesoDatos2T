package com.example.entities;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;


	@XmlRootElement(name = "usuario")
	public class ExistJuegoUsuarioDTO {

	    private Long idUsuario;
	    private List<ExistJuego> juegos;

	    
	    @XmlElement(name = "id")
	    public Long getIdUsuario() {
	        return idUsuario;
	    }
	    
	    public ExistJuegoUsuarioDTO() {

		}

		public ExistJuegoUsuarioDTO(Long idUsuario, List<ExistJuego> juegos) {
			this.idUsuario = idUsuario;
			this.juegos = juegos;
		}

		public void setIdUsuario(Long idUsuario) {
	        this.idUsuario = idUsuario;
	    }

	    @XmlElementWrapper(name = "juegos")
	    @XmlElement(name = "juego")
	    public List<ExistJuego> getJuegos() {
	        return juegos;
	    }

	    public void setJuegos(List<ExistJuego> juegos) {
	        this.juegos = juegos;
	    }
	}

