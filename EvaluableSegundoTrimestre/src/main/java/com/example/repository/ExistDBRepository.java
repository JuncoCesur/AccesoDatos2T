package com.example.repository;

import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.modules.XPathQueryService;

@Repository
public class ExistDBRepository {
		
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";

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
    
    public String insertar() {
        
        try {
            String sQuery = "update insert <documento><id>6</id><nombre>Prueba 6</nombre></documento>" +
                    " into doc('Documentos/Prueba1')/documentos";
            XPathQueryService service = obtenerServicioXPath();
            service.query(sQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
            
        return "OK";
    }
}
