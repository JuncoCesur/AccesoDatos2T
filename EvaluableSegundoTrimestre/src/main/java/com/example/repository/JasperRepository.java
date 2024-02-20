package com.example.repository;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		List<Juego> lista = new ArrayList<Juego>();

		lista.add(new Juego("Tomb Raider", "Aventura"));
		lista.add(new Juego("Rocket League", "Deporte"));
		lista.add(new Juego("League of Legend", "Competitivo"));
		lista.add(new Juego("Elden Ring", "Aventura"));
		lista.add(new Juego("World of Warcraft", "Competitivo"));
		lista.add(new Juego("Faraón", "Construcción"));



		return lista;
	}

	public boolean generarInforme() {
		
		long nowMillis = System.currentTimeMillis();
        String nombreFichero = "test " + nowMillis + ".pdf";
		
        List<Juego> lista = obtenerJuegos();
		
		
		Map<String, Object> empParams = new HashMap<String, Object>();
		empParams.put("titulo", "Informe de Juegos");
		JasperPrint empReport;
		try {
			empReport = JasperFillManager.fillReport(
							JasperCompileManager.compileReport(
									ResourceUtils.getFile("reports/Informe.jrxml").getAbsolutePath()),
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
