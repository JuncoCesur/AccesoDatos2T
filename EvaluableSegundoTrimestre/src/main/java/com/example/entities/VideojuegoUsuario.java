package com.example.entities;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;



@Document(collection = "datos_juegos_mongo")
public class VideojuegoUsuario {
    
	@Id
    private String id;
    private String nombre;
    private String genero;
    private Long idUsuario;
    
    public VideojuegoUsuario(String id, String nombre, String genero, Long idUsuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.genero = genero;
		this.idUsuario = idUsuario;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	@Override
	public String toString() {
		return "VideojuegoUsuario [id=" + id + ", nombre=" + nombre + ", genero=" + genero + ", idUsuario=" + idUsuario
				+ "]";
	}
    
}
