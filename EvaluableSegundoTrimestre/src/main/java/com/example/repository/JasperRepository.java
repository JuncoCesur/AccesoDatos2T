package com.example.repository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import com.example.entities.Juego;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

@Repository
public class JasperRepository {

	private List<Juego> obtenerJuegos() {
		
		ObjectDBRepository obtenerJuego = new ObjectDBRepository();
		obtenerJuego.conectar();

		// Insertamos la query para coger todos los juegos que hay
		TypedQuery<Juego> query = obtenerJuego.em.createQuery("SELECT j FROM Juego j", Juego.class);
		List<Juego> results = query.getResultList();

		return results;
	}

	public boolean generarInforme() {
		
		long nowMillis = System.currentTimeMillis();
        String nombreFichero = "Informe_Juegos " + nowMillis + ".pdf";
		
        List<Juego> lista = obtenerJuegos();
		
		
		Map<String, Object> empParams = new HashMap<String, Object>();
		empParams.put("titulo", "Informe de Juegos");
		empParams.put("url_starCraft", "starcraft.png");

		
		JasperPrint empReport;
		try {
			empReport = JasperFillManager.fillReport(
							JasperCompileManager.compileReport(
									ResourceUtils.getFile("reports/InformeJuegos.jrxml").getAbsolutePath()),
							empParams, 
							new JRBeanCollectionDataSource(lista)
					);
		
			
			JRPdfExporter exporter = new JRPdfExporter();
	
			exporter.setExporterInput(new SimpleExporterInput(empReport));
			exporter.setExporterOutput(
			  new SimpleOutputStreamExporterOutput(nombreFichero));
	
			SimplePdfReportConfiguration reportConfig
			  = new SimplePdfReportConfiguration();
			reportConfig.setSizePageToContent(true);
			reportConfig.setForceLineBreakPolicy(false);
	
			SimplePdfExporterConfiguration exportConfig
			  = new SimplePdfExporterConfiguration();
			exportConfig.setMetadataAuthor("Patricia Martos Moreno");
			exportConfig.setEncrypted(true);
			exportConfig.setAllowedPermissionsHint("PRINTING");
	
			exporter.setConfiguration(reportConfig);
			exporter.setConfiguration(exportConfig);
	
			exporter.exportReport();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (JRException e) {
			e.printStackTrace();
		}
		
		return true; 
	}
}
