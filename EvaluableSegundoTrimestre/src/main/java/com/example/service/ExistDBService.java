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

	private Long idSql;
	private Long idJuego;
	
	public void obtenerServicioXPath() throws Exception {
		// Lo mandamos al repositorio
		repositoryBBDD.obtenerServicioXPath();

	}
	
	public String insertar(Long idSql, Long idJuego, ExistJuegoUsuarioDTO usuarioExist, ExistJuego juegoExist) throws Exception {
		ExistDBService usuario = new ExistDBService();
		this.idSql = idSql;
		this.idJuego = idJuego;
		
		usuario.setIdSql(idSql);
		usuario.setIdJuego(idJuego);
		
		return repositoryBBDD.insertar(usuarioExist, usuario, juegoExist);

	}
	
	public String listado() {
		return repositoryBBDD.listado();

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
