package tp_2;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Main;
import prolog.ManejoProlog;


class IngredienteBasicoTest {
	String pathRecetasTest= Main.PATH_A_RECETAS + ManejadorArchivosTest.NOMBRE_ARCHIVO_RECETAS_TEST + ".json";
	String pathInventarioTest = Main.PATH_A_INVENTARIO + ManejadorArchivosTest.NOMBRE_ARCHIVO_INVENTARIO_TEST  + ".json";	
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
	void recetaBasico_DevuelveUnoTiempoCero() {
		Objeto basico = registroObjetosTest.obtenerObjeto("Madera");
		HashMap<Objeto, Integer> recetaM = new HashMap<Objeto, Integer>();
		recetaM.put(basico, 1);
		Receta recetaMadera= new Receta(0, 1, recetaM);		
		assertEquals(basico.obtenerReceta(), recetaMadera);				
	}
	
	@Test
	void objetoBasico_EsObjetoBasico() {
		Objeto basico = registroObjetosTest.obtenerObjeto("Madera");
		
		assertTrue(basico instanceof IngredienteBasico);
		assertFalse(basico instanceof Intermedio);
	}
	@Test
	void objetoBasico_NoEsCrafteable()
	{
		Objeto basico = registroObjetosTest.obtenerObjeto("Madera");
		assertFalse(basico.esCrafteable());
	}

}
