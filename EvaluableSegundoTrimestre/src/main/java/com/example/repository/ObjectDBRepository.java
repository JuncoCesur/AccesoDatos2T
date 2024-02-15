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

		emf = Persistence.createEntityManagerFactory("DB/juegoAsociado.odb");
		em = emf.createEntityManager();
	}

	protected void cerrar() {

		em.close();
		emf.close();
	}

	// Metodo para insertar un juego a un usuario de ObjectDB
	public String juegoAsociado(ObjectDBService so) throws SQLException {

		// Conectamos con la base de datos ObjectDB
		conectar();
		em.getTransaction().begin();

		// Vamos a buscar si el usuario ya está en ObjectDB, retornamos su ID de Object
		Long valorIdUsuario = existeUsuarioObject(so);

		JuegoUsuarioDTO usuarioObject = new JuegoUsuarioDTO();

		// Si el usuario no existe en ObjectDB, lo insertamos
		usuarioObject.setIdUser(valorIdUsuario);

		// Consultamos la lista de juegos general
		boolean confirmarJuegoExiste = encontrarJuegoTitulo(so);

		// Si no esta en la lista, la incluimos
		if (confirmarJuegoExiste == false) {
			insertarJuego(so);

		}

		// Cogemos la ID del juego para insertarlo en el usuario
		Long idJuego = obtenerIdJuego(so);
		
		// Verificamos si es necesario agregar el juego a la lista del usuario
		boolean confirmarAgregarJuegoAListaUsuario = verificaListaJuegos(so, idJuego);

		
		if (!confirmarAgregarJuegoAListaUsuario) {
			usuarioObject.setIdJuego(idJuego);
			// Agregamos el nuevo juego a la lista
			//JuegoUsuarioDTO agregaJuego = new JuegoUsuarioDTO();
			//agregaJuego.agregarJuego(idJuego);
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





	
	
	// -------------------------------------------------------------------------------------------------------------------------Usuarios-----------

	// Metodo para conocer si existe el usuario con ID de Mysql en ObjectDB
	public Long existeUsuarioObject(ObjectDBService so) throws SQLException {
		
		// Obtenemos primero el ID de MySql llamando al siguiente metodo
		Long valor = obtenerId(so);

		// Realizamos la consulta para encontrar el usuario por ID de MySql
		String queryString = "SELECT u FROM JuegoUsuarioDTO u WHERE u.idSql = :valor";
		List<JuegoUsuarioDTO> usuario = em.createQuery(queryString, JuegoUsuarioDTO.class).setParameter("valor", valor)
				.getResultList();

		// Verificamos si se encontró algún usuario con el ID proporcionado
		if (!usuario.isEmpty()) {
			System.out.println("Usuario encontrado.");

			// Retornamos la ID de ObjectDB
			return usuario.get(0).getIdUser();

		} else {
			System.out.println("No se encontró ningún usuario con el ID proporcionado.");

			// Retornamos la ID de ObjectDB
			return usuario.get(0).getIdUser();
		}		
	}
	
	// Metodo para obtener el ID de MySql
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
		if (resultSet.next()) {

			// Obtenemos el valor de id
			valor = resultSet.getLong("id");
		}

		connection_.close();
		return valor;
	}
	
	
	
	// -------------------------------------------------------------------------------------------------------------------------Juegos-----------

	// Metodo para insertar un juego nuevo
		public String insertarJuego(ObjectDBService so) {

			String comentario;

			// Cogemos tanto el titulo como el genero indicado
			String titulo = so.getTitulo();
			String genero = so.getGenero();
			
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

			return comentario;
		}
	
	// Metodo para obtener el ID del juego en la lista de juegos generales
	public Long obtenerIdJuego(ObjectDBService so) {

		// Cogemos el titulo del juego
		String tituloJuego = so.getTitulo();

		// Realizamos la consulta para obtener la ID del juego por su título
		String queryString = "SELECT j.idJuego FROM Juego j WHERE j.titulo = :titulo";
		List<Long> ids = em.createQuery(queryString, Long.class).setParameter("titulo", tituloJuego).getResultList();

		// Metemos el ID en una variable para retornarla
		Long idJuegoEncontrado = ids.get(0);

		return idJuegoEncontrado;
	}
	
	// Metodo para encontrar un juego en la lista del usuario por su ID
	public boolean encontrarJuegoIdEnListaUsuario(Long id) {

		boolean confirmar = false;

		JuegoUsuarioDTO verificaJuego = new JuegoUsuarioDTO();
		
		verificaJuego.getListaJuegosUsuario();
		
		// Recorremos la lista de juegos del usuario para encontrarlo
		for (int i = 0; i>=verificaJuego.getListaJuegosUsuario().size(); i++) {
			if( id == verificaJuego.recorreLista(id)) {
				confirmar = true;
			}
		}

		return confirmar;
	}
	
	// Metodo para encontrar un juego por su titulo
	public boolean encontrarJuegoTitulo(ObjectDBService so) {

		
		String titulo = so.getTitulo();
		boolean confirmarExisteJuegoTitulo = false;

		// Insertamos la query para ver si existe el juego por el titulo
		TypedQuery<Juego> query = em.createQuery("SELECT j FROM Juego j WHERE j.titulo = :titulo", Juego.class);
		query.setParameter("titulo", titulo);
		List<Juego> resultados = query.getResultList();

		// Si se ha encontrado
		if (!resultados.isEmpty()) {
			confirmarExisteJuegoTitulo = true;
			
			System.out.println("El juego se encuentra ya en la lista");

		} else {

			System.out.println("No se ha encontrado el juego en la lista");

		}

		return confirmarExisteJuegoTitulo;
	}
	
	// Metodo para encontrar un juego por su ID
	public boolean encontrarJuegoPorSuId(Long id) {

		boolean confirmar = false;

		// Insertamos la query para coger el juego por su ID
		TypedQuery<Juego> query = em.createQuery("SELECT j FROM Juego j WHERE j.idJuego = :id", Juego.class);
		query.setParameter("id", id);
		List<Juego> resultados = query.getResultList();

		// Si se ha encontrado
		if (!resultados.isEmpty()) {
			confirmar = true;
			System.out.println("El juego se encuentra ya en la lista de juegos generales");

		} else {
			confirmar = false;
			System.out.println("No se ha encontrado el juego en la lista de juegos generales");

		}

		return confirmar;
	}

	
	// -------------------------------------------------------------------------------------------------------------------------Listas-----------

	// Metodo para verificar si el usuario tiene una lista creada
	public boolean verificaListaJuegos(ObjectDBService so, Long idJuego) {

		JuegoUsuarioDTO verificador = new JuegoUsuarioDTO();
		
		boolean confirmarAgregarJuegoAListaUsuario = false;

		// Verificamos si la lista de juegos del usuario es nula
		List<Long> listaJuego = verificador.getListaJuegosUsuario();

		if (listaJuego == null) {

			// Si la lista de juegos es nula, creamos una nueva lista
			listaJuego = new ArrayList<Long>();
			System.out.println("Se ha añadido una nueva lista de Juegos para el usuario");
			
			confirmarAgregarJuegoAListaUsuario = true;

		} else {

			// Si la lista no es nula, verificamos si el juego ya existe en la lista del usuario
			boolean encontradoJuego = encontrarJuegoIdEnListaUsuario(idJuego);

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
	
	public Long pruebitaLista(ObjectDBService pruebita) {
		JuegoUsuarioDTO pruebaLista = new JuegoUsuarioDTO();

		Long cogeId = pruebita.getId();
		Long recogeId = pruebaLista.recorreLista(cogeId);
		
		return recogeId; //---------------------------------------------------------------------------------------------
	}

}
