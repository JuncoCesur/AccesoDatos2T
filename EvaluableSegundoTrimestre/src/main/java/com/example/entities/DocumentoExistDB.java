package com.example.entities;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "documentoExistDB")
public class DocumentoExistDB {
		
		private Long id; 
		
		private String nombre;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		} 
		
}
