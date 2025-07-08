package tp_2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Main;

class RegistroObjetosTest {

	String pathRecetasTest = Main.PATH_A_RECETAS + ManejadorArchivosTest.NOMBRE_ARCHIVO_RECETAS_TEST + ".json";
	String pathInventarioTest = Main.PATH_A_INVENTARIO + ManejadorArchivosTest.NOMBRE_ARCHIVO_INVENTARIO_TEST + ".json";
	RegistroObjetos registroObjetosTest;
	ManejadorArchivos manejador;

	@BeforeEach
	public void setUp() {
		registroObjetosTest = new RegistroObjetos();
		manejador = new ManejadorArchivos();
		try {
			manejador.cargarRecetasDesdeJson(pathRecetasTest, registroObjetosTest);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR LAS RECETAS TEST");
			return;
		}
	}

	@Test
	void objetoBasicoExiste_RetornaObjetoBasico() {
		Objeto madera = registroObjetosTest.obtenerObjeto("madera");
		IngredienteBasico maderaEsperada = new IngredienteBasico("Madera");
		assertTrue(madera instanceof IngredienteBasico);
		assertEquals(maderaEsperada, madera);
	}

	@Test
	void objetoIntermedioExiste_RetornaObjetoIntermedio() {
		Objeto palo = registroObjetosTest.obtenerObjeto("palo");
		Objeto madera = registroObjetosTest.obtenerObjeto("madera");
		HashMap<Objeto, Integer> ingredientesPalo = new HashMap<Objeto, Integer>();
		ingredientesPalo.put(madera, 3);
		Receta recetaPalo = new Receta(3, 2, ingredientesPalo);
		Intermedio paloEsperado = new Intermedio("Palo", recetaPalo);
		assertTrue(palo instanceof Intermedio);
		assertEquals(paloEsperado, palo);
	}

	@Test
	void objetoInexistente_retornaNull() {
		Objeto noExiste = registroObjetosTest.obtenerObjeto("noExiste");
		assertEquals(null, noExiste);
	}

}
