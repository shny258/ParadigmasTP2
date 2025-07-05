package tp_2;


import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prolog.ManejoProlog;

class InventarioTest {
	

	String pathRecetasTest= "archivos/testrecetas.json";
	String pathInventarioTest = "archivos/testinventario.json";
	String pathInventarioJsonOut = "inventarioJsonOut.json";
	Inventario inventarioJsonTest;
	RegistroObjetos registroObjetosTest;
	ManejadorArchivos manejador;
	ManejoProlog pl = ManejoProlog.getInstance(pathRecetasTest, "archivos/reglasProlog.txt");
	
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
		try {
			inventarioJsonTest = manejador.cargarInventarioDesdeJson(pathInventarioTest, registroObjetosTest);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR EL INVENTARIO TEST");
			return;
		}
		
	}
	
	@Test
	void CrearInventarioNoFalla() {
		Objeto marco = registroObjetosTest.obtenerObjeto("Marco");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto carbon = registroObjetosTest.obtenerObjeto("Carbon");
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Map<Objeto, Integer> objetos = new HashMap<>();
		objetos.put(marco, 10);
		objetos.put(palo, 5);
		objetos.put(tablon, 6);
		objetos.put(carbon, 20);;
		objetos.put(madera, 20);;
		
		Inventario inventarioCreado = new Inventario(objetos);
		assertEquals(objetos,inventarioCreado.getObjetos());
	}
	@Test
	void mostrarInventarioTestEsperado() {
		assertEquals("{Marco=1, Palo=2, Tablon=2, Carbon=4}",inventarioJsonTest.toString());
	}
	@Test
	void faltanteParaCraftearTestEsperado() {
		Objeto antorcha = registroObjetosTest.obtenerObjeto("Antorcha");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Map<Objeto, Integer> ingredientes = new HashMap<>();
		ingredientes.put(palo, 3);
		Receta faltanteParaCrearAntorcha = new Receta(9, 1, ingredientes);
		assertEquals(faltanteParaCrearAntorcha,inventarioJsonTest.faltantesParaCraftear(antorcha));
	}
	@Test
	void getObjetosEsperado() {
		Objeto marco = registroObjetosTest.obtenerObjeto("Marco");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto carbon = registroObjetosTest.obtenerObjeto("Carbon");
		Map<Objeto, Integer> objetos = new HashMap<>();
		objetos.put(marco, 1);
		objetos.put(palo, 2);
		objetos.put(tablon, 2);
		objetos.put(carbon, 4);
		assertEquals(objetos,inventarioJsonTest.getObjetos());
	}
	@Test
	void faltanteParaCraftearDesdeCeroTestEsperado() {
		Objeto antorcha = registroObjetosTest.obtenerObjeto("Antorcha");
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Map<Objeto, Integer> ingredientes = new HashMap<>();
		ingredientes.put(madera, 6);
		Receta faltanteParaCrearAntorchaDeCero = new Receta(9, 1, ingredientes);
		assertEquals(faltanteParaCrearAntorchaDeCero,inventarioJsonTest.faltantesParaCraftearDeCero(antorcha));
	}
	@Test
	void craftearMesaTestPuede() {
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto carbon = registroObjetosTest.obtenerObjeto("Carbon");
		Map<Objeto, Integer> inventarioEsperado = new HashMap<>();
		inventarioEsperado.put(carbon, 4);
		inventarioEsperado.put(tablon, 1);
		inventarioEsperado.put(mesa, 1);
		
		assertTrue(inventarioJsonTest.craftear(mesa));
		assertEquals(inventarioEsperado,inventarioJsonTest.getObjetos());
		assertEquals("{1=Mesa}",inventarioJsonTest.getHistorial().toString());	
	}
	@Test
	void craftearTornilloTestNoPuede() {
		Objeto tornillo = registroObjetosTest.obtenerObjeto("Tornillo");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto marco = registroObjetosTest.obtenerObjeto("Marco");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto carbon = registroObjetosTest.obtenerObjeto("Carbon");
		Map<Objeto, Integer> inventarioEsperado = new HashMap<>();
		inventarioEsperado.put(marco, 1);
		inventarioEsperado.put(palo, 2);
		inventarioEsperado.put(tablon, 2);
		inventarioEsperado.put(carbon, 4);
		
		assertFalse(inventarioJsonTest.craftear(tornillo));
		assertEquals(inventarioEsperado,inventarioJsonTest.getObjetos());
		assertEquals("{}",inventarioJsonTest.getHistorial().toString());	
	}
	
	@Test
	void cuantosPuedeCraftearTest_CraftearDosMesasSoloBasicos() {		
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Map<Objeto, Integer> inventarioActual = new HashMap<>();

		inventarioActual.put(madera, 57);
		Inventario inv = new Inventario(inventarioActual);
		
		assertEquals(2,inv.cuantosPuedoCraftear(mesa));
	}
	
	@Test
	void cuantosPuedeCraftearTest_CraftearDosMesasConIntermediosVarios() {		
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Objeto marco = registroObjetosTest.obtenerObjeto("Marco");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto tornillo = registroObjetosTest.obtenerObjeto("Tornillo");
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Map<Objeto, Integer> inventarioActual = new HashMap<>();
		inventarioActual.put(madera, 2);
		inventarioActual.put(marco, 1);
		inventarioActual.put(tablon, 3);
		inventarioActual.put(tornillo, 2);
		inventarioActual.put(palo, 15);
		
		Inventario inv = new Inventario(inventarioActual);
		
		assertEquals(2,inv.cuantosPuedoCraftear(mesa));
	}
	
	@Test
	void cuantosPuedeCraftearTest_CraftearDosMesasConIntermediosTornillos() {		
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto tornillo = registroObjetosTest.obtenerObjeto("Tornillo");
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Map<Objeto, Integer> inventarioActual = new HashMap<>();
		inventarioActual.put(madera, 45);
		inventarioActual.put(tornillo, 200);
		inventarioActual.put(palo, 5);
		
		Inventario inv = new Inventario(inventarioActual);
		
		assertEquals(2,inv.cuantosPuedoCraftear(mesa));
	}
	
	@Test
	void cuantosPuedeCraftearTest_CraftearTresMesasConIntermediosVarios() {		
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Objeto marco = registroObjetosTest.obtenerObjeto("Marco");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto tornillo = registroObjetosTest.obtenerObjeto("Tornillo");
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Map<Objeto, Integer> inventarioActual = new HashMap<>();
		inventarioActual.put(madera, 60);
		inventarioActual.put(marco, 1);
		inventarioActual.put(tablon, 1);
		inventarioActual.put(tornillo, 1);
		inventarioActual.put(palo, 1);
		
		Inventario inv = new Inventario(inventarioActual);
		
		assertEquals(3,inv.cuantosPuedoCraftear(mesa));
	}
}
