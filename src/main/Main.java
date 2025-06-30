package main;

import tp_2.*;

public class Main {
	public static void main(String[] args) {
		RegistroObjetos registroObjetos = new RegistroObjetos();
		ManejadorArchivos manejador = new ManejadorArchivos();
		try {
			manejador.cargarRecetasDesdeJson("archivos/testrecetas.json", registroObjetos);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR LAS RECETAS");
			return;
		}
		Inventario inventario;
		try {
			inventario = manejador.cargarInventarioDesdeJson("archivos/testinventario.json", registroObjetos);
		} catch (Exception e) {
			System.err.println("ERROR AL CARGAR EL INVENTARIO");
			return;
		}
		Objeto antorcha = registroObjetos.obtenerObjeto("Antorcha");
		System.out.println("RECETA ANTORCHA:\n" + antorcha.obtenerReceta());
		System.out.println(
				"RECETA ANTORCHA COMPLETA DESCOMPUESTA EN SUBINGREDIENTES:\n" + antorcha.obtenerRecetaCompleta());

		Objeto mesa = registroObjetos.obtenerObjeto("Mesa");
		System.out.println("RECETA MESA:\n" + mesa.obtenerReceta());
		System.out.println("RECETA MESA COMPLETA DESCOMPUESTA EN SUBINGREDIENTES:\n" + mesa.obtenerRecetaCompleta());

		System.out.println("INVENTARIO:\n" + inventario + "\n");

		System.out.println("FALTANTES PARA CRAFTEAR ANTORCHA:\n" + inventario.faltantesParaCraftear(antorcha));
		System.out.println("FALTANTES PARA CRAFTEAR ANTORCHA DESDE CERO:\n" + inventario.faltantesParaCraftearDeCero(antorcha));

		inventario.craftear(mesa);
		System.out.println("CRAFTEAMOS MESA. INVENTARIO ACTUALIZADO:\n" + inventario + "\n");
		System.out.println("FALTANTES PARA CRAFTEAR MESA DESDE CERO:\n" + inventario.faltantesParaCraftearDeCero(mesa));

		System.out.println("HISTORIAL DE CRAFTEOS:\n" + inventario.getHistorial());

	}
}
