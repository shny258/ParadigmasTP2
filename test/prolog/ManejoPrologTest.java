package prolog;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import tp_2.Inventario;
import tp_2.ManejadorArchivos;
import tp_2.Objeto;
import tp_2.RegistroObjetos;

class ManejoPrologTest {
	String pathRecetasTest = "archivos/testrecetas.json";
	//ManejoProlog pl = ManejoProlog.getInstance(pathRecetasTest, "archivos/reglasProlog.txt");
	@Test
	void DiferenteExtension_NoEntra() {
		Exception ex = assertThrows(Exception.class, () -> {
		    ManejoProlog.getInstance("archivos/archivo.pl", "archivos/reglasProlog.csv");
		});
		assertTrue(ex.getMessage().contains(".txt"));
	}
	@Test
	void Inexistente_NoEntra() {
		Exception ex = assertThrows(Exception.class, () -> {
		    ManejoProlog.getInstance("archivos/archivo.pl", "archivos/reglasPrologQueNoExisten.txt");
		});
		assertTrue(ex.getMessage().contains("no existe"));
	}
	@Test
	void Vacio_NoEntra() {
		Exception ex = assertThrows(Exception.class, () -> {
		    ManejoProlog.getInstance("archivos/archivo.pl", "archivos/reglasPrologVacias.txt");
		});
		assertTrue(ex.getMessage().contains("está vacío"));
	}
	
	@Test
	void Valido_Entra() {
		try {
			ManejoProlog pl = ManejoProlog.getInstance("archivos/archivo.pl", "archivos/reglasProlog.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Test
	void StringBuilders_GuardanHechos() {
		ManejoProlog pl = null;
		try {
			pl = ManejoProlog.getInstance("archivos/archivo.pl", "archivos/reglasProlog.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pl.elemento_basico("prueba");
		String pathRecetasTest = "archivos/testrecetas.json";
		String pathInventarioTest = "archivos/testinventario.json";
		Inventario inventarioJsonTest;
		RegistroObjetos registroObjetosTest;
		ManejadorArchivos manejador;
		registroObjetosTest = new RegistroObjetos();
		manejador = new ManejadorArchivos();
		try {
			manejador.cargarRecetasDesdeJson(pathRecetasTest, registroObjetosTest);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR LAS RECETAS TEST");
			return;
		}
		try {
			inventarioJsonTest = manejador.cargarInventarioDesdeJson(pathInventarioTest, registroObjetosTest);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR EL INVENTARIO TEST");
			return;
		}
		assertEquals("elemento_basico(\"prueba\").\n",pl.getPrologElementoBasico());
	}
	

}
