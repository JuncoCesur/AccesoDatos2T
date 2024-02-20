package com.example.repository;

import java.io.StringReader;

import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XPathQueryService;

import com.example.entities.ExistJuego;
import com.example.entities.ExistJuegoUsuarioDTO;
import com.example.entities.JuegoUsuarioDTO;
import com.example.service.ExistDBService;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

@Repository
public class ExistDBRepository {
		
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

    // Metodo para conectar con la base de datos ExistDB
    public XPathQueryService obtenerServicioXPath() throws Exception {
        String driver = "org.exist.xmldb.DatabaseImpl"; //Driver
        Class cl = Class.forName(driver);//Cargar el driver
        Database database = (Database) cl.newInstance(); //Instancia de la BD
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database); //Registrar la BD
        //Accedemos a la colección
        Collection col =DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/", "admin", "");

        XPathQueryService service =(XPathQueryService) col.getService("XPathQueryService", "1.0");
        service.setProperty("pretty", "true");
        service.setProperty("encoding", "ISO-8859-1");

        return service;
    }
    
    // Metodo para listar tanto usuarios como juegos 
    public String listado() {
    	
        try {    
        	
            XPathQueryService service = obtenerServicioXPath();

            //Consulta para mostrar los usuarios
            ResourceSet result = service.query("doc('SegundaEvaluacion/XMLAccesoDatos')//usuarios");
            ResourceIterator i = result.getIterator();
            
            while (i.hasMoreResources()) { //Procesamos el resultado
                Resource r = i.nextResource();
                String xml = (String) r.getContent();
                System.out.println(xml);                                             
                }
            
            //Consulta para mostrar los juegos

            ResourceSet result2 = service.query("doc('SegundaEvaluacion/XMLAccesoDatosJuegos')//juegos");
            ResourceIterator i2 = result2.getIterator();
            
            while (i2.hasMoreResources()) { //Procesamos el resultado
                Resource r = i2.nextResource();
                String xml = (String) r.getContent();
                System.out.println(xml);                                             
                }
            
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "OK";
    }
  
    // Metodo para isertar tanto usuarios como juegos en ExistDB
    public String insertar(ExistJuegoUsuarioDTO usuarioExist, ExistDBService usuario, ExistJuego juego) throws Exception {
    	
    	    // Contruimos la query para insertar usuario y juego
    	    String sQuery = construirConsultaInsercionUsuario(usuarioExist);
    	    String sQuery2 = construirConsultaInsercionJuego(usuarioExist, juego);

    	    // Ejecutamos la consulta en ExistDB
    	    XPathQueryService service = obtenerServicioXPath();
    	    service.query(sQuery);     	
    	    service.query(sQuery2);
            
        return "OK";
    }
    
    // Metodo para construir la query para la insercion de usuarios
	public String construirConsultaInsercionUsuario(ExistJuegoUsuarioDTO usuarioExist) {
	    String sQuery = "update insert " + usuarioToXML(usuarioExist) +
	                   " into doc('SegundaEvaluacion/XMLAccesoDatos')/plataforma";
	    return sQuery;
	}
	
    // Metodo para construir la query para la insercion de juegos
	public String construirConsultaInsercionJuego(ExistJuegoUsuarioDTO usuarioExist, ExistJuego juego) {
	    String sQuery2 = "update insert " + juegoToXML(usuarioExist, juego) +
	                   " into doc('SegundaEvaluacion/XMLAccesoDatosJuegos')/juegos";
	    return sQuery2;
	}

	// Metodo para construir el xml de los usuarios
	private String usuarioToXML(ExistJuegoUsuarioDTO usuarioExist) {
	    // Aquí conviertes el objeto Usuario a una representación XML para la inserción
	    // Puedes usar JAXB o construir manualmente la cadena XML
	    // Por simplicidad, asumimos que JAXB se utiliza para convertir el usuario a XML
	    // y devolvemos la cadena XML resultante.
		long nowMillis = System.currentTimeMillis();
		
	    return "<usuarios><usuario><idSql>" + usuarioExist.getIdSql() + "</idSql><idJuego>" + usuarioExist.getIdJuego() + "</idJuego></usuario></usuarios>";
	}
	
	// Metodo para construir el xml de los juegos
	private String juegoToXML(ExistJuegoUsuarioDTO usuarioExist, ExistJuego juego) {
	    // Aquí conviertes el objeto Usuario a una representación XML para la inserción
	    // Puedes usar JAXB o construir manualmente la cadena XML
	    // Por simplicidad, asumimos que JAXB se utiliza para convertir el usuario a XML
	    // y devolvemos la cadena XML resultante.
		long nowMillis = System.currentTimeMillis();
		
	    return "<juego><idJuego>" + usuarioExist.getIdJuego() + "</idJuego><titulo>" + juego.getTitulo() + "</titulo><genero>" + juego.getGenero() + "</genero></juego>";
	}
  
    
    
}
