package com.example.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entities.CaratulaJuego;
import com.example.entities.VideojuegoUsuario;
import com.example.repository.CaratulaJuegoRepository;
import com.example.repository.VideojuegoUsuarioRepository;

// Service para trabajar con MongoDB
@Service
public class VideojuegoUsuarioService {

	@Autowired
	private VideojuegoUsuarioRepository videojuegoUsuarioRepository;
	@Autowired
	private CaratulaJuegoRepository caratulaRepository;
	
	/**
	 * 
	 * 
	 *
	 * @author Junco
	 * @see VideojuegoUsuarioRepository
	 */

	public List<VideojuegoUsuario> obtenerTodos() {
		return videojuegoUsuarioRepository.findAll();
	}

	//Los métodos son autoexplicativos
	public List<VideojuegoUsuario> obtenerPorId(String _id) {
		return videojuegoUsuarioRepository.findAllById(_id);
	}

	public List<VideojuegoUsuario> obtenerPorNombre(String nombre) {
		return videojuegoUsuarioRepository.findByNombre(nombre);
	}

	public List<VideojuegoUsuario> obtenerPorGenero(String genero) {
		return videojuegoUsuarioRepository.findByGenero(genero);
	}

	public List<VideojuegoUsuario> obtenerPorIdUsuario(Long idUsuario) {
		return videojuegoUsuarioRepository.findByIdUsuario(idUsuario);
	}

	public VideojuegoUsuario guardar(VideojuegoUsuario videojuegoUsuario) {
		return videojuegoUsuarioRepository.save(videojuegoUsuario);
	}

	public void eliminar(String id) {
		videojuegoUsuarioRepository.deleteById(id);
	}
	
	/**
	 * Insertamos y mostramos las caratulas de los juegos de la lista
	 * 
	 *
	 * @author Patricia
	 * @see CaratulaJuegoRepository
	 */

	// Metodo para agregar una caratula a MongoDB
	public void agregarCaratula(String nombre, byte[] datos) {
		CaratulaJuego caratula = new CaratulaJuego();
		
		// Le adjudicamos un id random
		caratula.setId(UUID.randomUUID().toString());
		// Le adjudicamos nombre e imagen
		caratula.setNombreCaratula(nombre);
		caratula.setImagenCaratula(datos);
		
		caratulaRepository.save(caratula);
	}


	// Llamada al método para obtener los datos binarios de la imagen
	public byte[] obtenerDatosBinarios(String id) {
		
		byte[] datosImagen = obtenerDatosImagenDesdeMongoDB(id);{
			
			// Verificamos si se encontraron datos de imagen
			if (datosImagen != null) {
				
				// Convertimos los datos binarios en una imagen BufferedImage
				try {
					BufferedImage imagen = convertirBytesAImagen(datosImagen);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				System.out.println("No se encontraron datos de imagen para el ID proporcionado.");
				
			}
		}
		return datosImagen;
	}
	
	// Método para obtener los datos binarios de la imagen desde MongoDB
	public byte[] obtenerDatosImagenDesdeMongoDB(String id) {

		// Buscamos en el repository la ID aportada
		Optional<CaratulaJuego> optionalArchivo = caratulaRepository.findById(id);

		// Si la encuentra, obtenemos la imagen
		if (optionalArchivo.isPresent()) {
			return optionalArchivo.get().getImagenCaratula();

		} else {
			return null;

		}
	}

	// Método para convertir los datos binarios en una imagen BufferedImage
	public BufferedImage convertirBytesAImagen(byte[] bytes) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		return ImageIO.read(bis);
	}
}