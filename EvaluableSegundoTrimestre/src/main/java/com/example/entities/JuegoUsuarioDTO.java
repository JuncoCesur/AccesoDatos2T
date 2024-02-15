package com.example.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class JuegoUsuarioDTO {

	@Id
	private Long idUser;

	private Long idSql;

	@ElementCollection
	private List<Long> listaJuegosUsuario = new ArrayList<Long>();

	public JuegoUsuarioDTO() {
	}

	public JuegoUsuarioDTO(Long idUser, Long idSql, List<Long> listaJuegosUsuario, Juego idJuego) {
		this.idUser = idUser;
		this.idSql = idSql;
		this.listaJuegosUsuario = listaJuegosUsuario;
	}

	@OneToMany
	@JoinColumn(name = "idJuego")
	public Juego idJuego;

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public List<Long> getListaJuegosUsuario() {
		return listaJuegosUsuario;
	}

	public void setListaJuegosUsuario(List<Long> listaJuegosUsuario) {
		this.listaJuegosUsuario = listaJuegosUsuario;
	}

	public Long getIdSql() {
		return idSql;
	}

	public void setIdSql(Long idSql) {
		this.idSql = idSql;
	}

	/*
	 * public Juego getIdJuego() { return idJuego; }
	 */

	public void setIdJuego(Juego idJuego) {
		this.idJuego = idJuego;
	}

	// Metodo para devolver el ID del juego
	public boolean recorreLista(Long id) {

		JuegoUsuarioDTO recorreLista = new JuegoUsuarioDTO();
		recorreLista.getListaJuegosUsuario();
		
		boolean result = false;

		if (recorreLista.getListaJuegosUsuario().size() != 0) {

			for (int i = 0; i <= recorreLista.getListaJuegosUsuario().size(); i++) {

				System.out.println(recorreLista.getListaJuegosUsuario().size());

				if (recorreLista.getListaJuegosUsuario().get(i) == id) {
					result = true;
				}

			}

		} 
		
		return result;

	}

	public void agregarJuegoAListaUsuario(Long id) {
		this.listaJuegosUsuario.add(id);

		JuegoUsuarioDTO muestraLista = new JuegoUsuarioDTO();

		for (int i = 0; i <= muestraLista.getListaJuegosUsuario().size(); i++) {
			System.out.println(this.listaJuegosUsuario.get(i));

		}
	}
}
