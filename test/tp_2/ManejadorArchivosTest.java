package tp_2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Main;
import prolog.ManejoProlog;

class ManejadorArchivosTest {
	
	public static final String NOMBRE_ARCHIVO_RECETAS_TEST = "testrecetas"; 
	public static final String NOMBRE_ARCHIVO_INVENTARIO_TEST = "testinventario"; 
	String pathRecetasTest= Main.PATH_A_RECETAS + NOMBRE_ARCHIVO_RECETAS_TEST + ".json";
	String pathInventarioTest = Main.PATH_A_INVENTARIO + NOMBRE_ARCHIVO_INVENTARIO_TEST + ".json";
	ManejoProlog pl = ManejoProlog.getInstance();
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
