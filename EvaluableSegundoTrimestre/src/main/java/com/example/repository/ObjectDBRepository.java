package com.example.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.entities.JuegoUsuarioDTO;
import com.example.entities.Juegos;
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

	public String juegosAsociados(ObjectDBService so) throws SQLException {

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
		int valor = 0;

		// Procesamos los resultados
		while (resultSet.next()) {

			// Obtenemos el valor de id
			valor = resultSet.getInt("id");
		}

		System.out.println(valor);

		// Conectamos con la base de datos ObjectDB
		conectar();
		em.getTransaction().begin();

		// Vamos a buscar si el usuario ya está repetido enla base de datos
		boolean usuarioRepetido = existeUsuarioPorId(valor);

		// Si el usuario ya existe en la base de datos, lo notificamos
		if (usuarioRepetido == false) {
			System.out.println("El usuario con ID " + valor + " no existe en la base de datos.");

		} else {

			// Como ya hemos comprobado que esta, lo insertamos
			JuegoUsuarioDTO usuarioEjemplo = new JuegoUsuarioDTO();

			// Indicamos la ID y los juegos nuevos
			usuarioEjemplo.setIdUser((long) valor);

			System.out.println("Creado usuario con ID: " + valor);			
						
			String titulo = so.getTitulo();
			String genero = so.getGenero();
			
			Juegos juegoEjemplo = new Juegos();
			juegoEjemplo.setTitulo(titulo);
			juegoEjemplo.setGenero(genero);
			
			JuegoUsuarioDTO arrayJuegos = new JuegoUsuarioDTO();
			
			// Obtener la lista de juegos del usuario
			List<Juegos> listaJuego = arrayJuegos.getListaJuegos();
			
			// Verificar si la lista de juegos es nula (esto puede ocurrir si no se ha inicializado)
			if (listaJuego == null) {
			    // Si la lista de juegos es nula, crea una nueva lista
			    listaJuego = new ArrayList<>();
			}
			
			// Agregamos el nuevo juego a la lista
			listaJuego.add(juegoEjemplo);

			// Actualizamos la lista de juegos en el DTO
			arrayJuegos.setListaJuegos(listaJuego);
			

			System.out.println("Este usuario juega tambien a: " + juegoEjemplo.getTitulo() + juegoEjemplo.getGenero());

			// Lo insertamos en la base de datos ObjectDB

			em.persist(usuarioEjemplo);
			em.persist(juegoEjemplo);
		}

		em.getTransaction().commit();

		/*
		 * TypedQuery<Usuario> query =
		 * em.createQuery("SELECT a FROM users a JOIN FETCH a.juegos", Usuario.class);
		 * List<Usuario> usuarioLista = query.getResultList();
		 * System.out.println(usuarioLista);
		 *
		 * for (Usuario usuario : usuarioLista) {
		 * System.out.println("Nombre del Jugador: " + usuario.getId());
		 *
		 * List<Juegos> juegosLista = usuario.getJuegos();
		 * System.out.println("Juegos asociados:");
		 *
		 * for (Juegos juego : juegosLista) { System.out.println("- " +
		 * juego.getTitulo()); }
		 *
		 * System.out.println("------------------------"); }
		 */

		em.close();
		return "Hecho";

	}

	public String crearJuego() {
		conectar();

		em.getTransaction().begin();

		Juegos juegoEjemplo = new Juegos();
		juegoEjemplo.setTitulo("Minecraft");
		juegoEjemplo.setGenero("Supervivencia");

		em.persist(juegoEjemplo);

		juegoEjemplo = new Juegos();
		juegoEjemplo.setTitulo("CaesarIV");
		juegoEjemplo.setGenero("Construccion");

		em.persist(juegoEjemplo);

		em.getTransaction().commit();

		em.close();
		return "Hecho";

	}

	/*
	 * public String mostrar() {
	 * 
	 * conectar();
	 * 
	 * TypedQuery<Usuario> query =
	 * em.createQuery("SELECT a FROM Jugador a JOIN FETCH a.juegos", Usuario.class);
	 * List<Usuario> usuarios = query.getResultList(); System.out.println(usuarios);
	 * 
	 * for (Usuario usuario : usuarios) { //
	 * 
	 * System.out.println("Nombre del Amigo: " + valor);
	 * 
	 * List<Juegos> juego = usuario.getJuegos();
	 * System.out.println("Direcciones asociadas:");
	 * 
	 * for (Juegos juegos : juego) { System.out.println("- " + juegos.getTitulo());
	 * 
	 * }
	 * 
	 * System.out.println("------------------------"); }
	 * 
	 * cerrar(); return "OK";
	 * 
	 * 
	 * }
	 */

	// Metodo para comprobar si el usuario a insertar en la base de datos ya existe
	public boolean existeUsuarioPorId(int userId) {

		String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, userId);

		// Si count es mayor que 0, significa que el usuario existe
		return count > 0;
	}

}
