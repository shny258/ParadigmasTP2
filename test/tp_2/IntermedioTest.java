package tp_2;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import prolog.ManejoProlog;


class IntermedioTest {
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