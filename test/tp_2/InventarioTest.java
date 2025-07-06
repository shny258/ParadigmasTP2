package tp_2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import prolog.ManejoProlog;

class InventarioTest {
	String pathRecetasTest = "archivos/inventario/testrecetas.json";
	String pathInventarioTest = "archivos/inventario/testinventario.json";
	String pathInventarioJsonOut = "archivos/inventario/inventarioJsonOut.json";
	Inventario inventarioJsonTest;
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
		try {
			inventarioJsonTest = manejador.cargarInventarioDesdeJson(pathInventarioTest, registroObjetosTest);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR EL INVENTARIO TEST");
			return;
		}

	}

	@Test
	void crearInventarioNoFalla() {
		Objeto marco = registroObjetosTest.obtenerObjeto("Marco");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto carbon = registroObjetosTest.obtenerObjeto("Carbon");
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Map<Objeto, Integer> objetos = new HashMap<>();
		Map<Objeto, Integer> objetosVacio = new HashMap<>();
		objetos.put(marco, 10);
		objetos.put(palo, 5);
		objetos.put(tablon, 6);
		objetos.put(carbon, 20);
		objetos.put(madera, 20);

		Inventario inventarioCreado = new Inventario(objetos, null);
		Inventario inventarioVacio = new Inventario();
		assertEquals(objetos, inventarioCreado.getObjetos());
		assertEquals(objetosVacio, inventarioVacio.getObjetos());
	}

	@Test
	void agregarObjetoTest() {
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Inventario inv = new Inventario();
		Map<Objeto, Integer> objetos = new HashMap<>();
		objetos.put(madera, 20);
		inv.agregar(madera, 20);
		assertEquals(objetos, inv.getObjetos());
	}

	@Test
	void agregarSumarObjetoTest() {
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Inventario inv = new Inventario();
		Map<Objeto, Integer> objetos = new HashMap<>();
		objetos.put(madera, 40);
		inv.agregarSumar(madera, 20);
		inv.agregarSumar(madera, 20);
		assertEquals(objetos, inv.getObjetos());
	}

	@Test
	void sacarObjetoTest() {
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Inventario inv = new Inventario();
		Map<Objeto, Integer> objetos = new HashMap<>();
		inv.agregar(madera, 20);
		inv.sacar(madera);
		assertEquals(objetos, inv.getObjetos());
	}

	@Test
	void sacarObjetoTest_False() {
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Inventario inv = new Inventario();
		inv.agregar(madera, 20);
		assertFalse(inv.sacar(palo));
	}

	@Test
	void sacarRestarObjetoTest() {
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Inventario inv = new Inventario();
		Map<Objeto, Integer> objetos = new HashMap<>();
		objetos.put(madera, 20);
		inv.agregarSumar(madera, 20);
		inv.agregarSumar(madera, 20);
		inv.sacarRestar(madera, 20);
		assertEquals(objetos, inv.getObjetos());
	}

	@Test
	void sacarRestarObjetoTest_False() {
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Inventario inv = new Inventario();
		inv.agregarSumar(madera, 20);
		inv.agregarSumar(madera, 20);
		assertFalse(inv.sacarRestar(palo, 20));
		assertFalse(inv.sacarRestar(madera, 50));
		assertTrue(inv.sacarRestar(madera, 40));
	}

	@Test
	void mostrarInventarioTestEsperado() {
		assertEquals("{Marco=1, Palo=2, Tablon=2, Carbon=4}", inventarioJsonTest.toString());
	}

	@Test
	void faltanteParaCraftearTestEsperado() {
		Objeto antorcha = registroObjetosTest.obtenerObjeto("Antorcha");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Map<Objeto, Integer> ingredientes = new HashMap<>();
		ingredientes.put(palo, 3);
		Receta faltanteParaCrearAntorcha = new Receta(9, 1, ingredientes);
		assertEquals(faltanteParaCrearAntorcha, inventarioJsonTest.faltantesParaCraftear(antorcha));
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
		assertEquals(objetos, inventarioJsonTest.getObjetos());
	}

	@Test
	void faltanteParaCraftearDesdeCeroTestEsperado() {
		Objeto antorcha = registroObjetosTest.obtenerObjeto("Antorcha");
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Map<Objeto, Integer> ingredientes = new HashMap<>();
		ingredientes.put(madera, 6);
		Receta faltanteParaCrearAntorchaDeCero = new Receta(9, 1, ingredientes);
		assertEquals(faltanteParaCrearAntorchaDeCero, inventarioJsonTest.faltantesParaCraftearDeCero(antorcha));
	}

	@Test
	void craftearMesaTestPuede() {
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto carbon = registroObjetosTest.obtenerObjeto("Carbon");
		Inventario inventarioEsperado = new Inventario();
		inventarioEsperado.agregar(carbon, 4);
		inventarioEsperado.agregar(tablon, 1);
		inventarioEsperado.agregar(mesa, 1);

		assertTrue(inventarioJsonTest.craftear(mesa));
		assertEquals(inventarioEsperado, inventarioJsonTest);
		assertEquals("{1=Mesa}", inventarioJsonTest.getHistorial().toString());
	}

	@Test
	void craftearTornilloTestNoPuede() {
		Objeto tornillo = registroObjetosTest.obtenerObjeto("Tornillo");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto marco = registroObjetosTest.obtenerObjeto("Marco");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto carbon = registroObjetosTest.obtenerObjeto("Carbon");
		Inventario inventarioEsperado = new Inventario();
		inventarioEsperado.agregar(marco, 1);
		inventarioEsperado.agregar(palo, 2);
		inventarioEsperado.agregar(tablon, 2);
		inventarioEsperado.agregar(carbon, 4);

		assertFalse(inventarioJsonTest.craftear(tornillo));
		assertEquals(inventarioEsperado, inventarioJsonTest);
		assertEquals("{}", inventarioJsonTest.getHistorial().toString());
	}

	@Test
	void cuantosPuedeCraftearTest_CraftearDosMesasSoloBasicos() {
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Inventario inv = new Inventario();
		inv.agregar(madera, 57);
		assertEquals(2, inv.cuantosPuedoCraftear(mesa));
	}

	@Test
	void cuantosPuedeCraftearTest_CraftearDosMesasConIntermediosVarios() {
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Objeto marco = registroObjetosTest.obtenerObjeto("Marco");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto tornillo = registroObjetosTest.obtenerObjeto("Tornillo");
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Inventario inv = new Inventario();
		inv.agregar(madera, 2);
		inv.agregar(marco, 1);
		inv.agregar(tablon, 3);
		inv.agregar(tornillo, 2);
		inv.agregar(palo, 15);
		assertEquals(2, inv.cuantosPuedoCraftear(mesa));
	}

	@Test
	void cuantosPuedeCraftearTest_CraftearDosMesasConIntermediosTornillos() {
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto tornillo = registroObjetosTest.obtenerObjeto("Tornillo");
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Inventario inv = new Inventario();
		inv.agregar(madera, 45);
		inv.agregar(tornillo, 200);
		inv.agregar(palo, 5);
		assertEquals(2, inv.cuantosPuedoCraftear(mesa));
	}

	@Test
	void cuantosPuedeCraftearTest_CraftearTresMesasConIntermediosVarios() {
		Objeto madera = registroObjetosTest.obtenerObjeto("Madera");
		Objeto marco = registroObjetosTest.obtenerObjeto("Marco");
		Objeto palo = registroObjetosTest.obtenerObjeto("Palo");
		Objeto tablon = registroObjetosTest.obtenerObjeto("Tablon");
		Objeto tornillo = registroObjetosTest.obtenerObjeto("Tornillo");
		Objeto mesa = registroObjetosTest.obtenerObjeto("Mesa");
		Inventario inv = new Inventario();
		inv.agregar(madera, 60);
		inv.agregar(marco, 1);
		inv.agregar(tablon, 1);
		inv.agregar(tornillo, 1);
		inv.agregar(palo, 1);
		assertEquals(3, inv.cuantosPuedoCraftear(mesa));
	}
}
