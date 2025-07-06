package tp_2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prolog.ManejoProlog;

class ManejadorArchivosTest {
	String pathRecetasTest= "archivos/testrecetas.json";
	String pathInventarioTest = "archivos/testinventario.json";
	String pathInventarioJsonOut = "inventarioJsonOut.json";
	ManejoProlog pl = ManejoProlog.getInstance(pathRecetasTest, "archivos/reglasProlog.txt");
	RegistroObjetos registroObjetosTest;
	ManejadorArchivos manejador;
	@BeforeEach
	public void setUp() {
		registroObjetosTest = new RegistroObjetos();
		manejador = new ManejadorArchivos();		
		}
	@Test
	void ArchivoConExtensionIncorrecta_NoEntra() {
	    assertThrows(Exception.class, () -> {
	        manejador.cargarRecetasDesdeJson("archivos/recetas.xml", registroObjetosTest);
	    });
	}
	
	@Test
	void ArchivoInexistente_NoEntra() {
	    assertThrows(Exception.class, () -> {
	        manejador.cargarRecetasDesdeJson("archivos/recetasFalsas.json", registroObjetosTest);
	    });
	}
	
	@Test
	void ArchivoVacio_NoEntra() {
	    assertThrows(Exception.class, () -> {
	        manejador.cargarRecetasDesdeJson("archivos/recetasVacias.json", registroObjetosTest);
	    });
	}
	
	@Test
	void ArchivoInvalido_NoEntra() {
	    assertThrows(Exception.class, () -> {
	        manejador.cargarRecetasDesdeJson("archivos/testrecetasInvalidas.json", registroObjetosTest);
	    });
	}

}
