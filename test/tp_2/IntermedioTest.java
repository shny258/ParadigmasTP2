package tp_2;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Main;
import prolog.ManejoProlog;


class IntermedioTest {
	String pathRecetasTest= Main.PATH_A_RECETAS + ManejadorArchivosTest.NOMBRE_ARCHIVO_RECETAS_TEST + ".json";
	String pathInventarioTest = Main.PATH_A_INVENTARIO + ManejadorArchivosTest.NOMBRE_ARCHIVO_INVENTARIO_TEST  + ".json";	

	RegistroObjetos registroObjetosTest;
	ManejadorArchivos manejador;
	@BeforeEach
	public void setUp() {
		try {
			ManejoProlog.getInstance();
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR EL ARCHIVO DE PROLOG");
			return;
		}
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
	void recetaIntermedio_DevuelveSuReceta() {
		Objeto marco = registroObjetosTest.obtenerObjeto("Marco");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto tornillo = registroObjetosTest.obtenerObjeto("Tornillo");
		
		HashMap<Objeto, Integer> Ingredientes = new HashMap<Objeto, Integer>();
		Ingredientes.put(tablon, 2);
		Ingredientes.put(tornillo, 3);
		Receta recetaEsperada= new Receta(8, 1, Ingredientes);		
		assertEquals(marco.obtenerReceta(), recetaEsperada);				
	}
	
	@Test
	void recetaIntermedio_ObtenerRecetaCompleta() {
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		
		HashMap<Objeto, Integer> Ingredientes = new HashMap<Objeto, Integer>();
		Ingredientes.put(madera, 31);
		Receta recetaEsperada= new Receta(77, 1, Ingredientes);		
		assertEquals(mesa.obtenerRecetaCompleta(), recetaEsperada);				
	}
	
	@Test
	void objetoBasico_EsObjetoIntermedio() {
		Objeto intermedio = registroObjetosTest.obtenerObjeto("Palo");
		
		assertFalse(intermedio instanceof IngredienteBasico);
		assertTrue(intermedio instanceof Intermedio);
	}
	@Test
	void objetoBasico_EsCrafteable()
	{
		Objeto basico = registroObjetosTest.obtenerObjeto("Palo");
		assertTrue(basico.esCrafteable());
	}

}