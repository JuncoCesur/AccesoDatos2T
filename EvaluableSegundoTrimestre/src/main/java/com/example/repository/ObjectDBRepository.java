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

import org.springframework.stereotype.Repository;

import com.example.entities.JuegoUsuarioDTO;
import com.example.entities.Juego;
import com.example.service.ObjectDBService;

@Repository
public class ObjectDBRepository {

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

		// Conectamos con la base de datos ObjectDB
		conectar();
		em.getTransaction().begin();

		// Vamos a buscar si el usuario ya está repetido en ObjectDB
		boolean existeUsuario = existeUsuarioObject(so);

		JuegoUsuarioDTO usuarioObject = new JuegoUsuarioDTO();

		// Si el usuario no existe en ObjectDB, lo insertamos
		if (existeUsuario == false) {
			
			// Creamos el usuario
			Long valor = obtenerId(so);
			usuarioObject.setIdUser(valor);
			System.out.println("Creado usuario con ID: " + valor);

		}

		// Consultamos la lista de juegos
		boolean confirmarJuegoExiste = encontrarJuegoTitulo(so);

		// Si no esta en la lista, la incluimos
		if (confirmarJuegoExiste == false) {
			insertarJuego(so);

		}

		// Cogemos la id del juego para insertarlo en el usuario
		Long idJuego = obtenerIdJuego(so);
		
		boolean confirmarAgregarJuegoAListaUsuario = verificaListaJuegos(so);

		
		if (confirmarAgregarJuegoAListaUsuario) {
			// Agregamos el nuevo juego a la lista
			JuegoUsuarioDTO agregaJuego = new JuegoUsuarioDTO();
			agregaJuego.agregarJuego(idJuego);
		}

		// Lo insertamos en la base de datos ObjectDB 
		em.persist(usuarioObject);

		em.getTransaction().commit();

		// Cerramos conexiones
		em.close();

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
			System.out.println("El nombre del juego es: " + j.getTitulo() + ", del genero: " + j.getGenero()
					+ ", con id: " + j.getIdJuego());

		}

		return "OK";

	}

	// Metodo para obtener el id de MySql
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

		return valor;
	}

	// Metodo para encontrar un juego por su titulo
	public boolean encontrarJuegoTitulo(ObjectDBService so) {

		conectar();

		String titulo = so.getTitulo();
		boolean confirmarExisteJuegoTitulo = false;

		// Insertamos la query para coger todos los juegos que hay
		TypedQuery<Juego> query = em.createQuery("SELECT j FROM Juego j WHERE j.titulo = :titulo", Juego.class);
		query.setParameter("titulo", titulo);
		List<Juego> resultados = query.getResultList();

		// Recorremos todos los juegos y mostramos si se ha encontrado o no
		if (!resultados.isEmpty()) {
			confirmarExisteJuegoTitulo = true;
			System.out.println("El juego se encuentra ya en la lista");

		} else {
			confirmarExisteJuegoTitulo = false;
			System.out.println("No se ha encontrado el juego en la lista");

		}

		return confirmarExisteJuegoTitulo;
	}

	// Metodo para encontrar un juego por su id
	public boolean encontrarJuegoId(Long id) {

		conectar();

		boolean confirmar = false;

		// Insertamos la query para coger todos los juegos que hay
		TypedQuery<Juego> query = em.createQuery("SELECT j FROM Juego j WHERE j.idJuego = :id", Juego.class);
		query.setParameter("id", id);
		List<Juego> resultados = query.getResultList();

		// Recorremos todos los juegos y mostramos si se ha encontrado o no
		if (!resultados.isEmpty()) {
			confirmar = true;
			System.out.println("El juego se encuentra ya en la lista de juegos generales");

		} else {
			confirmar = false;
			System.out.println("No se ha encontrado el juego en la lista de juegos generales");

		}

		return confirmar;
	}

	// Metodo para insertar un juego nuevo
	public String insertarJuego(ObjectDBService so) {

		String comentario;

		// Cogemos tanto el titulo como el genero indicado
		String titulo = so.getTitulo();
		String genero = so.getGenero();

		// LLamamos la metodo para saber si ya existe o no
		boolean confirmarExisteJuegoTitulo = encontrarJuegoTitulo(so);
		System.out.println(confirmarExisteJuegoTitulo);

		// Si ya existe, no lo incluimos
		if (confirmarExisteJuegoTitulo) {

			comentario = "No se puede incluir, ya existe";
			System.out.println("No se puede incluir, ya existe");

		// Si no se encuentra, lo insertamos
		} else {

			conectar();
			em.getTransaction().begin();

			// Lo metemos en un nuevo objeto
			Juego nuevoJuego = new Juego();
			nuevoJuego.setTitulo(titulo);
			nuevoJuego.setGenero(genero);
			
			// Metemos el juego en la lista
			em.persist(nuevoJuego);
			em.getTransaction().commit();

			comentario = "Insertado: " + titulo + " " + genero;
			System.out.println("Insertado: " + titulo + " " + genero);

		}

		return comentario;
	}

	// Metodo para conocer si existe el usuario con id de Mysql en ObjectDB
	public boolean existeUsuarioObject(ObjectDBService so) throws SQLException {

		boolean confirmar;

		// Conectamos con ObjectDB
		conectar();

		// Obtenemos primero el ID de MySql llamando al siguiente metodo
		Long valor = obtenerId(so);

		// Realizamos la consulta para encontrar el usuario por ID de MySql
		String queryString = "SELECT u FROM JuegoUsuarioDTO u WHERE u.idUser = :valor";
		List<JuegoUsuarioDTO> usuario = em.createQuery(queryString, JuegoUsuarioDTO.class).setParameter("valor", valor)
				.getResultList();

		// Verificamos si se encontró algún usuario con el ID proporcionado
		if (!usuario.isEmpty()) {
			System.out.println("Usuario encontrado.");

			// Retornamos que se ha encontrado
			confirmar = true;

		} else {
			System.out.println("No se encontró ningún usuario con el ID proporcionado.");

			// Retornamos que no existe ningun usuario
			confirmar = false;
		}

		return confirmar;
	}

	// Metodo para obtener el id del juego en la lista de juegos generales
	public Long obtenerIdJuego(ObjectDBService so) {

		conectar();

		// Cogemos el titulo del juego
		String tituloJuego = so.getTitulo();

		// Realizamos la consulta para obtener la ID del juego por su título
		String queryString = "SELECT j.idJuego FROM Juego j WHERE j.titulo = :titulo";
		List<Long> ids = em.createQuery(queryString, Long.class).setParameter("titulo", tituloJuego).getResultList();

		// Metemos el ID en una variable para retornarla
		Long idJuegoEncontrado = ids.get(0);

		return idJuegoEncontrado;
	}

	// Metodo para verificar si el usuario tiene una lista creada
	public boolean verificaListaJuegos(ObjectDBService so) {

		JuegoUsuarioDTO verifica = new JuegoUsuarioDTO();
		
		// Llamamos al metodo para obtener el id del juego que queremos insertar 
		Long idJuego = obtenerIdJuego(so);
		boolean confirmarAgregarJuegoAListaUsuario = false;

		// Verificamos si la lista de juegos del usuario es nula
		List<Long> listaJuego = verifica.getListaJuegosUsuario();

		if (listaJuego == null) {

			// Si la lista de juegos es nula, creamos una nueva lista
			listaJuego = new ArrayList<Long>();
			System.out.println("Se ha añadido una nueva lista de Juegos para el usuario");
			
			confirmarAgregarJuegoAListaUsuario = true;

		} else {

			// Si la lista no es nula, verificamos si el juego ya existe en la lista
			boolean encontradoJuego = encontrarJuegoId(idJuego);

			if (encontradoJuego) {

				// Si el juego ya existe, no lo agregamos
				System.out.println("El juego ya existe en la lista.");
				
				confirmarAgregarJuegoAListaUsuario = false;

			} else {
				confirmarAgregarJuegoAListaUsuario = true;
				
			}
		}
		
		return confirmarAgregarJuegoAListaUsuario;
	}

}
