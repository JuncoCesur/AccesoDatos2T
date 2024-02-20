package com.example.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.ExistDBRepository;
import com.example.service.ExistDBService;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;



	@XmlRootElement(name = "usuarios")
	public class ExistJuegoUsuarioDTO {

	    private Long idSql;
	    //private List<Long> juegos;
	    private Long idJuego;

	    /*@XmlElementWrapper(name = "juegos")
	    @XmlElement(name = "juego")
	    public List<Long> getJuegos() {
	        return juegos;
	    }

	    public void setJuegos(List<Long> listaJuegos) {
	        this.juegos = listaJuegos;
	    }*/
	    
	    @XmlElement(name = "idObjectSql")
    	public Long getIdSql() {
			return idSql;
		}

		public void setIdSql(Long idSql) {
			this.idSql = idSql;
		}
		
		
		public Long getIdJuego() {
			return idJuego;
		}

		public void setIdJuego(Long idJuego) {
			this.idJuego = idJuego;
		}


		@Autowired
		protected ExistDBService conexionService;
	    
		// Metodo para crear el usuario de ExistDB
	    public void llamarMetodoInsertar(JuegoUsuarioDTO usuarioObject) throws Exception {
	    	
	    	ExistJuegoUsuarioDTO usuarioExist = new ExistJuegoUsuarioDTO();

	    	Long idSqlObject = usuarioObject.getIdSql();
	    	Long idJuego = usuarioObject.getIdJuego();
	    	
	    	usuarioExist.setIdSql(idSqlObject);
	    	usuarioExist.setIdJuego(idJuego);
	    	
	    	conexionService.insertar(null, null, usuarioExist, null);	    	
	    }
	   
	}

