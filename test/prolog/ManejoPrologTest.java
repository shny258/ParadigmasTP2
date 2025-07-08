package prolog;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.Main;
import tp_2.ManejadorArchivos;
import tp_2.RegistroObjetos;

class ManejoPrologTest {
	public static final String NOMBRE_ARCHIVO_RECETAS_TEST = "testrecetas";
	public static final String NOMBRE_ARCHIVO_INVENTARIO_TEST = "testinventario";
	String pathRecetasTest = "archivos/";

	@Test
	void DiferenteExtension_NoEntra() {
		Exception ex = assertThrows(Exception.class, () -> {
			ManejoProlog.verificarReglas(pathRecetasTest + "reglasProlog.txt");
		});
		assertTrue(ex.getMessage().contains(".pl"));
	}

	@Test
	void Inexistente_NoEntra() {
		Exception ex = assertThrows(Exception.class, () -> {
			ManejoProlog.verificarReglas(pathRecetasTest + "reglasPrologQueNoExisten.pl");
		});
		assertTrue(ex.getMessage().contains("no existe"));
	}

	@Test
	void Vacio_NoEntra() {
		Exception ex = assertThrows(Exception.class, () -> {
			ManejoProlog.verificarReglas(pathRecetasTest + "reglasPrologVacias.pl");
		});
		// System.err.println(ex.getMessage());
		assertTrue(ex.getMessage().contains("está vacío"));
	}

	@Test
	void StringBuilders_GuardanHechos() {
		String pathRecetasTest = Main.PATH_A_RECETAS + NOMBRE_ARCHIVO_RECETAS_TEST + ".json";
		String pathInventarioTest = Main.PATH_A_INVENTARIO + NOMBRE_ARCHIVO_INVENTARIO_TEST + ".json";
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
			manejador.cargarInventarioDesdeJson(pathInventarioTest, registroObjetosTest);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR EL INVENTARIO TEST");
			return;
		}
		ManejoProlog pl;
		try {
			pl = ManejoProlog.getInstance();
			pl.elemento_basico("prueba");
			assertEquals("elemento_basico(\"prueba\").\n", (pl.getPrologElementoBasico().split("\n")[2] + "\n"));
		} catch (Exception e) {
			fail();
		}

	}

}