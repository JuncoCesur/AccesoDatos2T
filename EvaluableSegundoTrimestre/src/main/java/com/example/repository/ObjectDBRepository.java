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
import com.example.entities.ExistJuego;
import com.example.entities.ExistJuegoUsuarioDTO;
import com.example.entities.Juego;
import com.example.service.ExistDBService;
import com.example.service.ObjectDBService;

@Repository
public class ObjectDBRepository {

	EntityManagerFactory emf;
	EntityManager em;

	public void conectar() {

		emf = Persistence.createEntityManagerFactory("DB/juegoAsociado.odb");
		em = emf.createEntityManager();
	}

	protected void cerrar() {

		em.close();
		emf.close();
	}
	
	/**
	 * Conectamos e interactuamos con usuarios y juegos
	 * de la base de datos de ObjectDB
	 *
	 * @author Patricia
	 * @see ObjectDBRepository
	 */

	// Metodo para insertar un juego a un usuario de ObjectDB
	public String juegoAsociado(ObjectDBService so, JuegoUsuarioDTO usuarioObject) throws Exception {

		// Conectamos con la base de datos ObjectDB
		conectar();
		em.getTransaction().begin();

		// Vamos a buscar si el usuario ya está en ObjectDB, retornamos su ID de Object
		boolean valorIdUsuario = existeUsuarioObject(so);

		if (valorIdUsuario == false) {
			// Si el usuario no existe en ObjectDB, lo insertamos
			usuarioObject.setIdSql(obtenerId(so));
			System.out.println("Se ha creado el usuario con idSql: " + usuarioObject.getIdSql());

		} else {
			// Si existe, llamamos al metodo para devolver el idUser del idSql aportado
			usuarioObject = encontrarUsuarioObject(so);

		}

		// Consultamos la lista de juegos general
		boolean confirmarJuegoExiste = encontrarJuegoTitulo(so);

		// Si no esta en la lista general, la incluimos
		if (confirmarJuegoExiste == false) {
			insertarJuego(so);

		}

		// Cogemos la ID del juego para insertarlo en el usuario
		Long idJuego = obtenerIdJuego(so);

		// Verificamos si es necesario agregar el juego a la lista del usuario
		boolean confirmarAgregarJuegoAListaUsuario = verificaListaJuegos(so, idJuego, usuarioObject);

		// Si es necesario añadir el juego
		if (confirmarAgregarJuegoAListaUsuario) {
			usuarioObject.agregarJuegoAListaUsuario(idJuego);
			usuarioObject.setIdJuego(idJuego);
			

			// Lo insertamos en la base de datos ObjectDB
			em.persist(usuarioObject);
					
		} 
		
		em.getTransaction().commit();

		// Cerramos conexiones
		em.close();
		
		// Metodo para insertar en ExistDB tras haber creado el usuario en ObjectDB
		ExistJuegoUsuarioDTO usuarioExist = new ExistJuegoUsuarioDTO();
		ExistJuegoUsuarioDTO existUser = usuarioExist.llamarMetodoInsertar(usuarioObject);
		ExistDBRepository repository = new ExistDBRepository();
		Juego juegoObject = new Juego();
		String titulo = so.getTitulo();
		String genero = so.getGenero();
		juegoObject.setTitulo(titulo);
		juegoObject.setGenero(genero);
		ExistJuego juego = new ExistJuego();
		ExistJuego juegoExist = juego.llamarMetodoInsertar(juegoObject);
		repository.insertar(existUser, null, juegoExist);
 
		return "Hecho";

	}

	// -------------------------------------------------------------------------------------------------------------------------Usuarios-----------

	// Metodo para conocer si existe el usuario con ID de Mysql en ObjectDB
	public Boolean existeUsuarioObject(ObjectDBService so) throws SQLException {

		// Obtenemos primero el ID de MySql
		Long valor = obtenerId(so);

		// Realizamos la consulta para encontrar el usuario por ID de MySql
		String queryString = "SELECT u FROM JuegoUsuarioDTO u WHERE u.idSql = :valor";
		List<JuegoUsuarioDTO> usuario = em.createQuery(queryString, JuegoUsuarioDTO.class).setParameter("valor", valor).getResultList();

		// Verificamos si se encontró algún usuario con el ID proporcionado
		if (!usuario.isEmpty()) {
			System.out.println("Hay un usuario con el idSql: " + valor);

			// Retornamos la ID de ObjectDB
			return true;

		} else {
			System.out.println("No se encontró ningún usuario con el ID proporcionado.");

			// Retornamos false
			return false;
		}
	}

	// Metodo para encontrar el usuario y devolver su idUser
	public JuegoUsuarioDTO encontrarUsuarioObject(ObjectDBService so) throws SQLException {

		// Obtenemos primero el ID de MySql llamando al siguiente metodo
		Long valor = obtenerId(so);

		// Realizamos la consulta para encontrar el usuario por ID de MySql
		String queryString = "SELECT u FROM JuegoUsuarioDTO u WHERE u.idSql = :valor";
		List<JuegoUsuarioDTO> usuario = em.createQuery(queryString, JuegoUsuarioDTO.class).setParameter("valor", valor)
				.getResultList();

		// Retornamos la ID de ObjectDB
		return usuario.get(0);

	}

	// Metodo para obtener el ID de MySql
	public Long obtenerId(ObjectDBService so) throws SQLException {

		// Llamamos al método que conecta nuestra base de datos
		BBDDRepository db = new BBDDRepository();
		Connection connection_ = db.llamarBaseDatos();

		// Tenemos que saber la ID del usuario para cogerla
		String sql = "SELECT id FROM users WHERE email = ?";
		PreparedStatement stmt = connection_.prepareStatement(sql);

		// Establecemos que nos diga la ID del email aportado
		stmt.setString(1, so.getEmail());

		// Ejecutamos la consulta
		ResultSet resultSet = stmt.executeQuery();
		Long valor = (long) 0;

		// Procesamos los resultados
		if (resultSet.next()) {

			// Obtenemos el valor de ID
			valor = resultSet.getLong("id");
		}

		connection_.close();
		return valor;
	}

	// -------------------------------------------------------------------------------------------------------------------------Juegos-----------

	// Metodo para insertar un juego nuevo a la lista general
	public String insertarJuego(ObjectDBService so) {

		String comentario;

		conectar();

		// Cogemos tanto el titulo como el genero indicado
		String titulo = so.getTitulo();
		String genero = so.getGenero();

		em.getTransaction().begin();

		// Lo metemos en un nuevo objeto
		Juego nuevoJuego = new Juego();
		nuevoJuego.setTitulo(titulo);
		nuevoJuego.setGenero(genero);

		// Metemos el juego en la lista general
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

		JuegoUsuarioDTO verificaJuego = new JuegoUsuarioDTO();

		verificaJuego.getListaJuegosUsuario();

		return verificaJuego.recorreLista(id);

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

			System.out.println("El juego se encuentra ya en la lista general");

		} else {

			System.out.println("No se ha encontrado el juego en la lista general");

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
			System.out.println("El juego se encuentra ya en la lista general");

		} else {
			confirmar = false;
			System.out.println("No se ha encontrado el juego en la lista general");

		}

		return confirmar;
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

	// -------------------------------------------------------------------------------------------------------------------------Listas-----------

	// Metodo para verificar si el usuario tiene una lista creada
	public boolean verificaListaJuegos(ObjectDBService so, Long idJuego, JuegoUsuarioDTO usuarioObject) {

		JuegoUsuarioDTO verificador = usuarioObject;

		boolean confirmarAgregarJuegoAListaUsuario = false;

		// Verificamos si la lista de juegos del usuario es nula
		List<Long> listaJuego = verificador.getListaJuegosUsuario();

		if (listaJuego == null || listaJuego.isEmpty()) {

			// Si la lista de juegos es nula o esta vacia, creamos una nueva lista
			List<Long> listaJuegoUsuario = new ArrayList<Long>();
			usuarioObject.setListaJuegosUsuario(listaJuegoUsuario);
			System.out.println("Se ha añadido una nueva lista de Juegos para el usuario");

			confirmarAgregarJuegoAListaUsuario = true;

		} else {

			// Verificamos si el juego ya existe en la lista del usuario
			boolean encontradoJuego = verificador.recorreLista(idJuego);

			if (encontradoJuego) {

				// Si el juego ya existe, no lo agregamos
				System.out.println("El juego ya existe en la lista de usuario.");

				confirmarAgregarJuegoAListaUsuario = false;

			} else {
				confirmarAgregarJuegoAListaUsuario = true;

			}
		}

		return confirmarAgregarJuegoAListaUsuario;
	}
	
	// Metodo para eliminar un juego de la base de datos ObjectDB
	public void eliminarJuego(Long idJuego) {
	    conectar();
	    //Se inicia la transacción
	    em.getTransaction().begin();
	    Juego juego = em.find(Juego.class, idJuego);
	    //Se comprueba que el usuario existe
	    if (juego != null) {
	    	//Se borra caso de que exista
	        em.remove(juego);
	        System.out.println("Juego eliminado correctamente");
	    } else {
	        System.out.println("No se encontró ningún juego con el ID proporcionado");
	    }
	    //Se hace un commit a la transacción
	    em.getTransaction().commit();
	    cerrar();
	}

	// Método para eliminar un usuario de la base de datos ObjectDB
	public void eliminarUsuario(Long idUsuario) {
	    conectar();
	    //Se inicia la transacción
	    em.getTransaction().begin();
	    JuegoUsuarioDTO usuario = em.find(JuegoUsuarioDTO.class, idUsuario);
	    //Se comprueba que el usuario existe
	    if (usuario != null) {
	    	//Se borra caso de que exista
	        em.remove(usuario);
	        System.out.println("Usuario eliminado correctamente");
	    } else {
	        System.out.println("No se encontró ningún usuario con el ID proporcionado");
	    }
	    //Se hace un commit a la transacción
	    em.getTransaction().commit();
	    cerrar();
	}

}
