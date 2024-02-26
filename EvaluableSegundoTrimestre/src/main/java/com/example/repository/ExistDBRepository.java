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

    public XPathQueryService obtenerServicioXPath() throws Exception {
        String driver = "org.exist.xmldb.DatabaseImpl";
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
        Collection col =DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/", "admin", "");

        XPathQueryService service =(XPathQueryService) col.getService("XPathQueryService", "1.0");
        service.setProperty("pretty", "true");
        service.setProperty("encoding", "ISO-8859-1");

        return service;
    }
    
    public String listado() {
        try {    
            XPathQueryService service = obtenerServicioXPath();

            ResourceSet result = service.query("doc('SegundaEvaluacion/XMLAccesoDatos')//usuarios");
            ResourceIterator i = result.getIterator();
            
            while (i.hasMoreResources()) { 
                Resource r = i.nextResource();
                String xml = (String) r.getContent();
                System.out.println(xml);                                             
            }
            
            ResourceSet result2 = service.query("doc('SegundaEvaluacion/XMLAccesoDatosJuegos')//juegos");
            ResourceIterator i2 = result2.getIterator();
            
            while (i2.hasMoreResources()) { 
                Resource r = i2.nextResource();
                String xml = (String) r.getContent();
                System.out.println(xml);                                             
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }
  
    public String insertar(ExistJuegoUsuarioDTO usuarioExist, ExistDBService usuario, ExistJuego juego) throws Exception {
        String sQuery = construirConsultaInsercionUsuario(usuarioExist);
        String sQuery2 = construirConsultaInsercionJuego(usuarioExist, juego);

        XPathQueryService service = obtenerServicioXPath();
        service.query(sQuery);     	
        service.query(sQuery2);
            
        return "OK";
    }
    
    public String construirConsultaInsercionUsuario(ExistJuegoUsuarioDTO usuarioExist) {
        String sQuery = "update insert " + usuarioToXML(usuarioExist) +
                        " into doc('SegundaEvaluacion/XMLAccesoDatos')/plataforma";
        return sQuery;
    }
    
    public String construirConsultaInsercionJuego(ExistJuegoUsuarioDTO usuarioExist, ExistJuego juego) {
        String sQuery2 = "update insert " + juegoToXML(usuarioExist, juego) +
                        " into doc('SegundaEvaluacion/XMLAccesoDatosJuegos')/juegos";
        return sQuery2;
    }

    private String usuarioToXML(ExistJuegoUsuarioDTO usuarioExist) {
        return "<usuarios><usuario><idSql>" + usuarioExist.getIdSql() + "</idSql><idJuego>" + usuarioExist.getIdJuego() + "</idJuego></usuario></usuarios>";
    }
    
    private String juegoToXML(ExistJuegoUsuarioDTO usuarioExist, ExistJuego juego) {
        return "<juego><idJuego>" + usuarioExist.getIdJuego() + "</idJuego><titulo>" + juego.getTitulo() + "</titulo><genero>" + juego.getGenero() + "</genero></juego>";
    }
    
    public String insertarDirecto(ExistJuegoUsuarioDTO usuario) throws Exception {
        String sQuery = "update insert " + usuarioToXML(usuario) +
                        " into doc('SegundaEvaluacion/XMLAccesoDatos')/plataforma";
        
        XPathQueryService service = obtenerServicioXPath();
        service.query(sQuery);
        
        return "OK";
    }
    
    public String calcularJuegosDeCadaUsuario() {
        try {
            XPathQueryService service = obtenerServicioXPath();
            ResourceSet result = service.query("for $usuario in //usuarios/usuario " +
                                                "group by $idSql := $usuario/idSql " +
                                                "return concat('Usuario ', $idSql, ' tiene ', count($usuario/idJuego), ' juegos.')");

            ResourceIterator iterator = result.getIterator();
            StringBuilder responseBuilder = new StringBuilder();

            while (iterator.hasMoreResources()) {
                Resource resource = iterator.nextResource();
                String juegosPorUsuario = (String) resource.getContent();
                responseBuilder.append(juegosPorUsuario).append("\n");
            }

            return responseBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al calcular los juegos de cada usuario";
        }
    } 
}
