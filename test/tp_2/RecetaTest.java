package tp_2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prolog.ManejoProlog;

class RecetaTest {

	String pathRecetasTest= "archivos/testrecetas.json";
	String pathInventarioTest = "archivos/testinventario.json";
	String pathInventarioJsonOut = "inventarioJsonOut.json";
	ManejoProlog pl;
	RegistroObjetos registroObjetosTest;
	ManejadorArchivos manejador;
	@BeforeEach
	public void setUp() {
		try {
			pl = ManejoProlog.getInstance(pathRecetasTest, "archivos/reglasProlog.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public void recetaConSobrantes_devuelveSobrantes()
	{
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto tornillo = registroObjetosTest.obtenerObjeto("Tornillo");
		Map<Objeto, Integer> sobrantes = new HashMap<Objeto, Integer>();
		mesa.obtenerRecetaCompleta(sobrantes);
		Map<Objeto, Integer> sobrantesEsperados = new HashMap<Objeto, Integer>();
		sobrantesEsperados.put(palo, 1);
		sobrantesEsperados.put(tornillo, 2);
		assertEquals(sobrantesEsperados, sobrantes);	
	}
	
	@Test
	public void recetaSinSobrantes_NoDevuelveSobrantes()
	{

		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Map<Objeto, Integer> sobrantes = new HashMap<Objeto, Integer>();
		palo.obtenerRecetaCompleta(sobrantes);
		Map<Objeto, Integer> sobrantesEsperados = new HashMap<Objeto, Integer>();
		assertEquals(sobrantesEsperados, sobrantes);	
	}
	
	

}
