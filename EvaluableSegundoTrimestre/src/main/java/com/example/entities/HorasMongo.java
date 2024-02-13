package com.example.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "horas")
public class HorasMongo {
	
    @Field(name = "horas")
	private int horas;

    public HorasMongo() {
    	
    }
    
    public HorasMongo(int horas) {
    	this.horas = horas;
    }

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}
    
}
