package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.ExistJuego;
import com.example.entities.ExistJuegoUsuarioDTO;
import com.example.repository.ExistDBRepository;

@Service
public class ExistDBService {
	
	@Autowired
	protected ExistDBRepository repositoryBBDD;
	
	/**
	 * Conectamos e interactuamos con objetos de la base de datos de ExistDB
	 * 
	 *
	 * @author Patricia
	 * @see ExistDBRepository
	 */

	private Long idSql;
	private Long idJuego;
	
	public void obtenerServicioXPath() throws Exception {
		repositoryBBDD.obtenerServicioXPath();

	}
	
	public String insertar(Long idSql, Long idJuego, ExistJuegoUsuarioDTO usuarioExist, ExistJuego juego) throws Exception {
		ExistDBService usuario = new ExistDBService();
		this.idSql = idSql;
		this.idJuego = idJuego;
		
		usuario.setIdSql(idSql);
		usuario.setIdJuego(idJuego);
		
		return repositoryBBDD.insertar(usuarioExist, usuario, juego);

	}
	
	public String listado() {
		return repositoryBBDD.listado();

	}
	// Junco
	public String modificarNombre(Long id, String nuevoNombre) throws Exception {
	    return repositoryBBDD.modificarNombre(id, nuevoNombre);
	}


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
	
	
	
}
