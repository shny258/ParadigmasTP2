package tp_2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Main;
import prolog.ManejoProlog;

class RecetaTest {

	String pathRecetasTest= Main.PATH_A_RECETAS + ManejadorArchivosTest.NOMBRE_ARCHIVO_RECETAS_TEST + ".json";
	String pathInventarioTest = Main.PATH_A_INVENTARIO + ManejadorArchivosTest.NOMBRE_ARCHIVO_INVENTARIO_TEST  + ".json";	
	
	String pathInventarioJsonOut = "inventarioJsonOut.json";
	ManejoProlog pl = ManejoProlog.getInstance();
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
