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

	public void agregarCaratula(String nombre, byte[] datos) {
		CaratulaJuego caratula = new CaratulaJuego();
		caratula.setId(UUID.randomUUID().toString());
		caratula.setNombreCaratula(nombre);
		caratula.setImagenCaratula(datos);
		caratulaRepository.save(caratula);
	}


	// Llamada al método para obtener los datos binarios de la imagen
	public byte[] obtenerDatosBinarios(String id) {
		
		byte[] datosImagen = obtenerDatosImagenDesdeMongoDB(id);{
			
			// Verificar si se encontraron datos de imagen
			if (datosImagen != null) {
				// Convertir los datos binarios en una imagen BufferedImage
				try {
					BufferedImage imagen = convertirBytesAImagen(datosImagen);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// Ahora puedes trabajar con la imagen en tu aplicación, como mostrarla en un
				// componente de interfaz de usuario
			} else {
				System.out.println("No se encontraron datos de imagen para el ID proporcionado.");
			}
		}
		return datosImagen;
	}
	
	// Método para obtener los datos binarios de la imagen desde MongoDB
	public byte[] obtenerDatosImagenDesdeMongoDB(String id) {

		Optional<CaratulaJuego> optionalArchivo = caratulaRepository.findById(id);

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