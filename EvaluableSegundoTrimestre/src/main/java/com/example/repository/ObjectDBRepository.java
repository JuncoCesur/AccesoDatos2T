package com.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.entities.JuegoUsuarioDTO;
import com.example.entities.Juego;
import com.example.service.ObjectDBService;

@Repository
public class ObjectDBRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private EntityManagerFactory emf;
	private EntityManager em;

	public void conectar() {

		emf = Persistence.createEntityManagerFactory("DB/juegosAsociados.odb");
		em = emf.createEntityManager();
	}

	protected void cerrar() {

		em.close();
		emf.close();
	}

	// Metodo para insertar un juego a un id de MySql
	public String juegosAsociados(ObjectDBService so) throws SQLException {

		// Obtenemos el id de MySql
		Long valor = obtenerId(so);

		// Conectamos con la base de datos ObjectDB
		conectar();
		em.getTransaction().begin();

		// Vamos a buscar si el usuario ya está repetido en ObjectDB
		boolean existeUsuario = existeUsuarioPorIdObject(valor);

		JuegoUsuarioDTO usuarioObject = new JuegoUsuarioDTO();

		// Si el usuario existe en ObjectDB, recuperamos su lista de juegos
		if (existeUsuario == true) {
			usuarioObject.getListaJuegosUsuario();
			System.out.println(usuarioObject.getListaJuegosUsuario());

		} else {

			// Como ya hemos comprobado que no esta, lo insertamos
			usuarioObject.setIdUser(valor);

			System.out.println("Creado usuario con ID: " + valor);

		}
		
		// Consultamos la lista de juegos
		TypedQuery<Juego> query = em.createQuery("SELECT j FROM Juego j", Juego.class);
		List<Juego> results = query.getResultList();

		// Recorremos todos los juegos y lo mostramos por pantalla
		for (Juego j : results) {
			System.out.println("El nombre del juegos es: " + j.getTitulo().toString() + ", del genero: " + j.getGenero() .toString()+ "con id: " + j.getIdJuego());

		}
			
		
		
		
		
		
		
		
		
		
		
		

		/*// Cogemos tanto el titulo como el genero indicado
		String titulo = so.getTitulo();
		String genero = so.getGenero();

		// Lo metemos en un nuevo objeto
		Juego juegoEjemplo = new Juego();
		juegoEjemplo.setTitulo(titulo);
		juegoEjemplo.setGenero(genero);

		JuegoUsuarioDTO arrayJuegos = new JuegoUsuarioDTO();

		// Obtenemos la lista de juegos del usuario
		List<Juego> listaJuego = arrayJuegos.getListaJuegos();

		// Verificamos si la lista de juegos del usuario es nula
		if (listaJuego == null) {

			// Si la lista de juegos es nula, creamos una nueva lista
			listaJuego = new ArrayList<>();

		} else {

			// Si la lista no es nula, verificamos si el juego ya existe en la lista
			for (Juego juego : listaJuego) {

				// Comparamos el titulo y el genero del juego actual con el juego que queremos
				// agregar
				if (juego.getTitulo().equals(titulo) && juego.getGenero().equals(genero)) {

					// Si el juego ya existe, no lo agregamos y salimos del bucle
					System.out.println("El juego ya existe en la lista.");

					break;
				}
			}
		}

		// Agregamos el nuevo juego a la lista
		listaJuego.add(juegoEjemplo);

		// Actualizamos la lista de juegos en el DTO
		arrayJuegos.setListaJuegos(listaJuego);

		System.out.println("Este usuario juega tambien a: " + juegoEjemplo.getTitulo() + " del género:  "
				+ juegoEjemplo.getGenero());

		// Lo insertamos en la base de datos ObjectDB
		em.persist(usuarioObject);
		em.persist(juegoEjemplo);

		em.getTransaction().commit();

		// Cerramos conexiones
		em.close();*/

		return "Hecho";

	}

	// Metodo para mostrar todos los juegos que hay en la lista
	public String mostrar() {

		conectar();

		// Insertamos la query para coger todos los juegos que hay
		TypedQuery<Juego> query = em.createQuery("SELECT j FROM Juego j", Juego.class);
		List<Juego> results = query.getResultList();

		// Recorremos todos los juegos y lo mostramos por pantalla
		for (Juego j : results) {
			System.out.println("El nombre del juego es: " + j.getTitulo() + ", del genero: " + j.getGenero() + "con id: " + j.getIdJuego());

		}

		// Cerramos conexiones
		cerrar();

		return "OK";

	}

	// Metodo para comprobar si el usuario a insertar en ObjectDB ya existe
	public boolean existeUsuarioPorIdObject(Long userId) {

		// Insertamos la query para saber is existe el id
		String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
		System.out.println(count);

		// Si count es mayor que 0, significa que el usuario existe
		return count > 0;

	}

	public Long obtenerId(ObjectDBService so) throws SQLException {

		// Llamamos al método que conecta nuestra base de datos
		BBDDRepository db = new BBDDRepository();
		Connection connection_ = db.llamarBaseDatos();

		// Tenemos que saber la ID del usuario para cogerla
		String sql = "SELECT id FROM users WHERE email = ?";
		PreparedStatement stmt = connection_.prepareStatement(sql);

		// Establecemos que nos diga la id del email aportado
		stmt.setString(1, so.getEmail());

		// Ejecutamos la consulta
		ResultSet resultSet = stmt.executeQuery();
		Long valor = (long) 0;

		// Procesamos los resultados
		while (resultSet.next()) {

			// Obtenemos el valor de id
			valor = resultSet.getLong("id");
		}

		System.out.println(valor);

		return valor;
	}

	public boolean existe(Long valor) {

		// Vamos a buscar si el usuario ya está repetido en la base de datos
		boolean usuarioRepetido = existeUsuarioPorIdObject(valor);

		// Si el usuario no existe en la base de datos, lo notificamos
		if (usuarioRepetido == false) {
			System.out.println("El usuario con ID " + valor + " no existe en la base de datos.");
		}

		return usuarioRepetido;

	}
}
