package com.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

@Repository
public class BBDDRepository {
	
	/**
	 * Llamar a la base de datos de MySq
	 *
	 * @author Patricia y Junco
	 * @see BBDDController, BBDDService
	 */
	
	// Metodo para llamar a la base de datos de MySql
	public Connection llamarBaseDatos() {
		
		Connection conexion = null;
		String connectionUrl = "jdbc:mysql://localhost:3306/trabajofintrimestre";
	 
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
		conexion = DriverManager.getConnection(connectionUrl, "root", "");
		System.out.println("Conectando...");
		System.out.println("Conectado");
 
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
 
		}
		return conexion;
	}
 
	/**
	 * Se ejecuta solo al principio para crear las tablas y trabajar.
	 *
	 * @author Patricia y Junco
	 * @see BBDDController, BBDDService
	 */
 
	public void crearTablas() {
 
		BBDDRepository db = new BBDDRepository();
		Connection connection_ = db.llamarBaseDatos();
 
		Statement stmt;
 
		try {
 
			stmt = connection_.createStatement();
 
			System.out.println("Creando tabla USER");
 
			stmt.executeUpdate(
					"CREATE TABLE users (id INT PRIMARY KEY AUTO_INCREMENT, email VARCHAR(250) UNIQUE, contraseña VARCHAR(250))");
 
			System.out.println("Se ha creado satisfactoriamente la tabla USERS");
			System.out.println("Creando tabla PERFIL");
 
			stmt.executeUpdate("CREATE TABLE perfil (" + "id INT PRIMARY KEY AUTO_INCREMENT, "
					+ "partidas_jugadas INT, " + "victorias INT, " + "derrotas INT, " + "liga_actual VARCHAR(250), "
					+ "raza_principal VARCHAR(250), " + "idPerfil INT, "
					+ "FOREIGN KEY(idPerfil) REFERENCES users(id))");
 
			System.out.println("Se ha creado satisfactoriamente la tabla PERFIL");
			System.out.println("Creando tabla LISTA_AMIGOS");
 
			stmt.executeUpdate(
					"CREATE TABLE lista_amigos (id INT PRIMARY KEY AUTO_INCREMENT, nick_amigo VARCHAR(250) UNIQUE)");
 
			System.out.println("Se ha creado satisfactoriamente la tabla LISTA_AMIGOS");
 
			connection_.close();
			stmt.close();
 
		} catch (SQLException e) {
			e.printStackTrace();
 
		}
 
	}
}
